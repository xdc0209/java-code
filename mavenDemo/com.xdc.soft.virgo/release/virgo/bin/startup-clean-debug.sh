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

function wait_until_all_plans_active()
{
    KERNEL_HOME=$BASE_DIR/..
    START_LOG=$KERNEL_HOME/serviceability/logs/start.log
    VIRGO_JMX_PORT=9875

    # Waiting for virgo jmx port $VIRGO_JMX_PORT to start...##
    for ((i = 0; i < 30; ++i))
    do
        echo "Waiting for virgo jmx port $VIRGO_JMX_PORT to start..."
        sleep 10

        lsof -i:$VIRGO_JMX_PORT >>$START_LOG
        [ $? -eq 0 ] && break
    done
    lsof -i:$VIRGO_JMX_PORT >>$START_LOG
    [ $? -ne 0 ] && echo "Start virgo failed. Wait for virgo jmx port $VIRGO_JMX_PORT to start timeout." && exit 1

    find $KERNEL_HOME/repository $KERNEL_HOME/pickup -name "*.plan" >>$START_LOG
    total_plan_num=$(find $KERNEL_HOME/repository $KERNEL_HOME/pickup -name "*.plan" | wc -l)

    # Waiting for virgo plan to start...##
    for ((i = 0; i < 30; ++i))
    do
        echo "Waiting for virgo plan to start..."
        sleep 10

        $KERNEL_HOME/bin/plan.sh >>$START_LOG
        active_plan_num=$($KERNEL_HOME/bin/plan.sh -s ACTIVE -t)
        [ "$active_plan_num" -ge "$total_plan_num" ] && break
    done
    [ "$active_plan_num" -lt "$total_plan_num" ] && echo "Start virgo failed. Wait for virgo plan to start timeout. Check the logs in [ $START_LOG ] and [ $KERNEL_HOME/serviceability/logs/log.log ] for details." && exit 1
}

# 启动virgo: 清理、打开调试端口、等待调试连接##
# $BASE_DIR/startup.sh -clean -debug 8000 -suspend &

# 启动virgo: 清理、打开调试端口##
$BASE_DIR/startup.sh -clean -debug 8000 &

# 等待plan都启动成功##
wait_until_all_plans_active
