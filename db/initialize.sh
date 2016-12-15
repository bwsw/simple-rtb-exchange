#!/usr/bin/env bash

# Prepares initialize.sql with given environment

if [ "$#" -ne 1 ];
then
  echo "Usage: $0 environment" >&2
  exit 1
fi

path=`dirname $0`
user=`cat $path/$1.liquibase.properties | grep username | cut -d":" -f2- | tr -d ' '`
password=`cat $path/$1.liquibase.properties | grep password | cut -d":" -f2- | tr -d ' '`
db=`cat $path/$1.liquibase.properties | grep url | cut -d"/" -f4- | tr -d ' '`

sed -e "s/{{user}}/${user}/g;s/{{password}}/${password}/g;s/{{db}}/${db}/g" \
    $path/init/initialize.tmplsql > $path/init/initialize.sql

echo "initialize.sql created"
