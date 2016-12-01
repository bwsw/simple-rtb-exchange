#!/usr/bin/env bash

# Executes query using psql

if [ "$#" -ne 1 ];
then
  echo "Usage: $0 query.sql" >&2
  exit 1
fi

export PGPASSWORD="rtb_user"

psql -h localhost -d rtb_exchange_e2e -U rtb_user -p 5432 -w -q -f $1
