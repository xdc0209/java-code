#!/bin/bash

BASE_PATH=$(readlink -m $0)
BASE_DIR=$(dirname $BASE_PATH)
BASE_NAME=$(basename $BASE_PATH .sh)


# 启动virgo: 清理、打开调试端口、等待调试连接 
# $BASE_DIR/startup.sh -clean -debug 8000 -suspend &

# 启动virgo: 清理、打开调试端口
$BASE_DIR/startup.sh -clean -debug 8000 &
