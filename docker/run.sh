#!/usr/bin/env bash

# Pulls and starts docker container.

if [ "$#" -ne 2 ];
then
  echo "Usage: $0 version env" >&2
  exit 1
fi

echo "version: $1 env: $2"

docker login -u rtb-dev-team -p rtb-dev-team-password ci.bw-sw.com:5000
docker pull ci.bw-sw.com:5000/rtb-dev-team/rtb-exchange:$1
docker stop rtb-exchange || true && docker rm rtb-exchange || true

docker network create rtb-ci-network || true
docker network connect --alias=db rtb-ci-network same-postgres || true
docker run --name rtb-exchange  -p 8081:8081 --network=rtb-ci-network ci.bw-sw.com:5000/rtb-dev-team/rtb-exchange:$1 -Dconfig.resource=application.$2.conf -jar rtb-exchange.jar
