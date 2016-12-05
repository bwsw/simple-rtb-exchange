#!/usr/bin/env bash

if [ "$#" -ne 1 ];
then
  echo "Usage: $0 endpoint" >&2
  exit 1
fi

echo "waiting for rtb exchange to up..."

until [ $(curl --write-out %{http_code} --silent --output /dev/null "$1/definetlynotfound") -eq "404" ]; do
    printf '.'
    sleep 1
done

echo "UP!"
