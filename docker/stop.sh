#!/usr/bin/env bash

# Stops and removes docker container.

docker stop rtb-exchange || true && docker rm rtb-exchange || true

