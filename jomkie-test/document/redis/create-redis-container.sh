#!/bin/bash
# create a container for redis
# Author: Jomkie

docker run \
  -p 6379:6379 \
  --name redis00 \
  -v /opt/docker-container-data/redis00/conf/redis.conf:/etc/redis/redis.conf \
  -v /opt/docker-container-data/redis00/data:/data \
  -d redis:5.0.12 redis-server /etc/redis/redis.conf --appendonly yes
