#!/bin/bash

BASE_DIR=$(cd $(dirname $0); pwd)
BASE_NAME=$(basename $0 .sh)
BASE_PATH=$BASE_DIR/$BASE_NAME.sh

log=$BASE_DIR/$BASE_NAME.log

export PS4='+[$(debug_info=$(printf "T: %s, L:%3s, S: %s, F: %s" "$(date +%H%M%S)" "$LINENO" "$(basename $BASH_SOURCE)" "$(for ((i=${#FUNCNAME[*]}-1; i>=0; i--)) do func_stack="$func_stack ${FUNCNAME[i]}"; done; echo $func_stack)") && echo ${debug_info:0:94})]: '

# 检测并预处理行##
function resolve_line()
{
    local line=$@

    # 预处理行：1.删除前导空白、末尾空白 2.重复空白转换成一个空格##
    line=$(echo $line | sed 's/^[[:space:]]*//g' | sed 's/[[:space:]]*$//g' | sed 's/[[:space:]][[:space:]]*/ /g')

    # 配置格式为：<proxy-port> <server-hostname> <server-port>##
    ipv4_ip_regexp="((?:(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9]))\\.){3}(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])"
    ipv4_port_regexp="[1-9]|[1-9][0-9]|[1-9][0-9]{2}|[1-9][0-9]{3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]"
    echo $line | grep -E "^$ipv4_port_regexp $ipv4_ip_regexp $ipv4_port_regexp\$"
}

function do_start_check()
{
    local line=$@

    local line_num=$(ps aux | grep -v grep | grep -w -c "$line")
    if [ "0" != "$line_num" ]; then
        echo "ERROR: There is too many item with reference to [ $line ], the program may already running, please excute [ sh ip_proxy.sh status ] to check."
        exit 1
    fi
}

function do_start()
{
    local line=$@

    nohup $JAVA_CMD_FOR_TOOLS -jar $BASE_DIR/IpProxy.jar $line >>$log 2>&1 &
    echo "INFO: Start listen [ $line ] successfully."
}

function do_stop()
{
    local line=$@

    local line_num=$(ps aux | grep -v grep | grep -w -c "$line")
    if [ "0" == "$line_num" ]; then
        echo "Warn: There is no item with reference to [ $line ], no need to stop."
    elif [ "1" == "$line_num" ]; then
        pid=$(ps aux | grep -v grep | grep -w "$line" | awk '{print $2 }')
        [ -n "$pid" ] && kill -9 $pid
        echo "INFO: Stop listen [ $line ] successfully."
    else
        echo "ERROR: There is too many item with reference to [ $line ], can not handle it, please handle it yourself."
        exit 1
    fi
}

function do_process_status()
{
    local line=$@
    ps aux | grep -v grep | grep -w --color "$line"
}

function do_network_status()
{
    local proxy_port=$@
    netstat -anp | grep -w --color "$proxy_port"
}

function start()
{
    # 启动前检测##
    while read line
    do
        line=$(resolve_line $line)
        [ -z "$line" ] && continue

        do_start_check $line
    done <$BASE_DIR/ip_proxy.cfg

    # 启动##
    while read line
    do
        line=$(resolve_line $line)
        [ -z "$line" ] && continue

        do_start $line
    done <$BASE_DIR/ip_proxy.cfg
    echo ""

    echo "INFO: Start successfully."
    echo ""

    # 获取下当前状态##
    sleep 5s
    status
}

function stop()
{
    # 获取下当前状态##
    status

    # 停止##
    while read line
    do
        line=$(resolve_line $line)
        [ -z "$line" ] && continue

        do_stop $line
    done <$BASE_DIR/ip_proxy.cfg
    echo ""

    echo "INFO: Stop successfully."
    echo ""
}

function status()
{
    # 检查进程状态##
    echo "Process status:"
    ps aux | sed -n '1p'
    while read line
    do
        line=$(resolve_line $line)
        [ -z "$line" ] && continue

        do_process_status $line
    done <$BASE_DIR/ip_proxy.cfg
    echo ""

    # 检查端口状态##
    echo "Network status:"
    netstat -anp | sed -n '2p'
    while read line
    do
        line=$(resolve_line $line)
        [ -z "$line" ] && continue

        proxy_port=$(echo $line | awk '{print $1}')
        do_network_status $proxy_port
    done <$BASE_DIR/ip_proxy.cfg
    echo ""
}

function set_java_env()
{
    [ -z "$JAVA_CMD_FOR_TOOLS" ] && which java >/dev/null 2>&1 && JAVA_CMD_FOR_TOOLS=java

    # 查找正在运行的java进程。如果没有就退出。##
    [ -z "$JAVA_CMD_FOR_TOOLS" ] && java_pids=$(ps -eww -o pid,user:20,cmd | grep -v grep | grep java | awk '{print $1}')
    [ -z "$JAVA_CMD_FOR_TOOLS" ] && [ -z "$java_pids" ] && echo "Find no java executable file." && exit 1

    # 如果存在正在运行的java进程，则根据正在运行的java进程，找到java可执行文件的位置，提示设置JAVA_CMD_FOR_TOOLS。##
    [ -z "$JAVA_CMD_FOR_TOOLS" ] && echo "Env [ JAVA_CMD_FOR_TOOLS ] not set. Please Set env first by using one of the following commands: "
    [ -z "$JAVA_CMD_FOR_TOOLS" ] && printf "%-10s %-20s %s\n" "PID" "USER" "SET_JAVA_ENV_COMMAND"
    [ -z "$JAVA_CMD_FOR_TOOLS" ] && ps -eww -o pid,user:20,cmd | grep -v grep | grep java | awk '{printf ("%-10s %-20s [ export JAVA_CMD_FOR_TOOLS=%s ]\n", $1, $2, $3)}'
    [ -z "$JAVA_CMD_FOR_TOOLS" ] && exit 1
}

# 校验执行的用户。##
[ "$(whoami)" != "root" ] && echo "You must run with [ root ] user." && exit 1

set_java_env

case "$1" in
    "start")
        start
        ;;
    "stop")
        stop
        ;;
    "status")
        status
        ;;
    *)
        echo "Usage: sh $0 {start|stop|status}"
        ;;
esac

