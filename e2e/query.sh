#!/usr/bin/env bash

# Executes query using psql

if [ "$#" -ne 3 ];
then
  echo "Usage: $0 environment fake-bidder-host query.sql" >&2
  exit 1
fi

connectionString=`cat ../db/$1.liquibase.properties | grep url | cut -d":" -f3- | tr -d ' '`
username=`cat ../db/$1.liquibase.properties | grep username | cut -d":" -f2- | tr -d ' '`
password=`cat ../db/$1.liquibase.properties | grep password | cut -d":" -f2- | tr -d ' '`
export PGPASSWORD=$password

script=`cat $3 |tr '\n' ' '`
replaced_script="${script//\{\{host\}\}/$2}"

IFS=';'
for x in $replaced_script; do
    [ -z "${x// }" ] && continue
    psql $connectionString -U $username -w  -c $x
done
