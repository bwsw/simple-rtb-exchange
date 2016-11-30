#!/usr/bin/env bash

# Build and publish docker image to bw docker registry.

if [ "$#" -ne 1 ];
then
  echo "Usage: $0 version" >&2
  exit 1
fi

docker build -t private/rtb-exchange . --build-arg VERSION=$1
docker login -u rtb-dev-team -p rtb-dev-team-password ci.bw-sw.com:5000
docker tag private/rtb-exchange ci.bw-sw.com:5000/rtb-dev-team/rtb-exchange:$1
#docker push ci.bw-sw.com:5000/rtb-dev-team/rtb-exchange:$1
