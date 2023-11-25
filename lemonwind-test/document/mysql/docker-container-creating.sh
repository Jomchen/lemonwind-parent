#!/bin/bash
# Author: lemonwind

containerName=mysql00
baseDir=/opt/docker-container/mysql/${containerName}
logDir=${baseDir}/logs
dataDir=${baseDir}/datas

if [ ! -e ${baseDir} ];then
  mkdir -p ${baseDir}
fi
if [ ! -e ${logDir} ];then
  mkdir -p ${logDir}
fi
if [ ! -e ${dataDir} ];then
  mkdir -p ${dataDir}
fi

# 是映射 配置路径
  # 挂载配置 -v xxx:/etc/mysql \
  # 挂载日志 -v xxx:/var/log/mysql \
  # 挂载数据 -v xxx:/var/lib/mysql \
  # mysql版本 -d mysql:5.7.27 \

docker run \
  --privileged=true \
  --name ${containerName} \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=root \
  -v ${logDir}:/var/log/mysql \
  -v ${dataDir}:/var/lib/mysql \
  -d mysql:8.0.34

unset containerName
unset baseDir
unset logDir
unset dataDir

# Notice that you may be fail when you login the server with password "root". 
# The password was initialed to EMPTY.
