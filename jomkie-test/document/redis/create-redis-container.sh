#!/bin/bash
# create a container for redis
# Author: Jomkie

containerName=redis00
baseDir=/opt/docker-container/redis
configDir=${baseDir}/conf
dataDir=${baseDir}/data


if [ ! -e ${baseDir} ];then
  mkdir -p ${baseDir}
fi
if [ ! -e ${logDir} ];then
  mkdir -p ${logDir}
fi
if [ ! -e ${dataDir} ];then
  mkdir -p ${dataDir}
fi
if [ ! -e ${configDir} ];then
  mkdir -p ${configDir}
  cp ./conf/redis.conf ${configDir}
fi

docker run \
  -p 6379:6379 \
  --name ${containerName} \
  -v ${configDir}/redis.conf:/etc/redis/redis.conf \
  -v ${dataDir}/data:/data \
  -d redis:5.0.12 redis-server /etc/redis/redis.conf --appendonly yes

# After you creating the container, copy the configuration file to the path</etc/redis/> of the container created by you.

unset containerName
unset baseDir
unset configDir
unset dataDir
