#!/bin/bash

# ----------------------------- xdc's shell comm lib start -------------------------------------

# Make sure to execute this script with bash. Bash works well on suse, redhat, aix.##
# 确保以bash执行此脚本。Bash在suse、redhat、aix上表现很出色。##
[ -z "$BASH" ] && echo "Please use bash to run this script [ bash $0 ] or make sure the first line of this script [ $0 ] is [ #!/bin/bash ]." && exit 1

# Set the bash debug info style to pretty format. +[T: <Time>, L: <LineNumber>, S: <ScriptName>, F: <Function>]##
# 设置bash的调试信息为漂亮的格式。+[T: <Time>, L: <LineNumber>, S: <ScriptName>, F: <Function>]##
[ -c /dev/stdout ] && export PS4_COLOR="32"
[ ! -c /dev/stdout ] && export PS4_COLOR=""
export PS4='+[$(debug_info=$(printf "T: %s, L:%3s, S: %s, F: %s" "$(date +%H%M%S)" "$LINENO" "$(basename $(cd $(dirname ${BASH_SOURCE[0]}) && pwd))/$(basename ${BASH_SOURCE[0]})" "$(for ((i=${#FUNCNAME[*]}-1; i>=0; i--)) do func_stack="$func_stack ${FUNCNAME[i]}"; done; echo $func_stack)") ; [ -z "$PS4_COLOR" ] && echo ${debug_info:0:94} ; [ -n "$PS4_COLOR" ] && echo -e "\e[${PS4_COLOR}m${debug_info:0:80}\e[0m")]: '

# 保存调试状态，用于调用子脚本。调用子脚本样例：bash $DEBUG_SWITCH subscript.sh##
# Save the debug state to invoke the subscript. Invoke the subscript example: bash $DEBUG_SWITCH subscript.sh##
(echo "${SHELLOPTS}" | grep -q "xtrace") && export DEBUG_SWITCH=-x

# Get the absolute path of this script.##
# 获取脚本的绝对路径。##
BASE_DIR=$(cd $(dirname $0) && pwd)
BASE_NAME=$(basename $0 .sh)

# 设置日志文件。##
# Set the log file.##
log=$BASE_DIR/$BASE_NAME.log

function print_error()
{
    echo "[$(date "+%F %T")] ERROR: $*" | tee -a $log 1>&2
}

function print_info()
{
    echo "[$(date "+%F %T")] INFO: $*" | tee -a $log
}

function log_error()
{
    [ -n "$log" ] && echo "[$(date "+%F %T")] ERROR: $*" >>$log
}

function log_info()
{
    [ -n "$log" ] && echo "[$(date "+%F %T")] INFO: $*" >>$log
}

function die()
{
    print_error "$*"
    print_error "See log [ $log ] for details."
    exit 1
}

# ----------------------------- xdc's shell comm lib end ---------------------------------------

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
    done <$BASE_DIR/ip_proxy.conf

    # 启动##
    while read line
    do
        line=$(resolve_line $line)
        [ -z "$line" ] && continue

        do_start $line
    done <$BASE_DIR/ip_proxy.conf
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
    done <$BASE_DIR/ip_proxy.conf
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
    done <$BASE_DIR/ip_proxy.conf
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
    done <$BASE_DIR/ip_proxy.conf
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
