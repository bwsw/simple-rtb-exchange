#!/usr/bin/env bash

if [ "$#" -ne 1 ];
then
  echo "Usage: $0 endpoint" >&2
  exit 1
fi

RETRIES=60 # connection retries

echo "waiting for rtb exchange to up..."
until [ $(curl --write-out %{http_code} --silent --output /dev/null "$1/definetly404") -eq "404" ]; do
    printf '.'
    RETRIES=$((RETRIES - 1))
    if [ $RETRIES -eq "0" ];
    then
        echo "waiting time is over"
        exit 1
    fi
    sleep 1
done
echo "UP!"
