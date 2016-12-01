#!/usr/bin/env bash

# Executes query using psql

if [ "$#" -ne 2 ];
then
  echo "Usage: $0 fake-bidder-host query.sql" >&2
  exit 1
fi

export PGPASSWORD="rtb_user"
script=`cat $2 |tr '\n' ' '`
replaced_script="${script//\{\{host\}\}/$1}"

IFS=';'
for x in $replaced_script; do
    psql -h localhost -d rtb_exchange_e2e -U rtb_user -p 5432 -w  -c $x
done
