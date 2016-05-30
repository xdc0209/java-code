#!/bin/bash

# 对PS4赋值，定制-x选项的提示符，L:行号，S:脚本，F:函数调用堆栈(反序)。shell调试信息最多为99字符，函数调用太多，堆栈太长，会出现意外截断的问题。为了保持美观，将格式设置为"+[94个字符的内容]: "，最终长度即为94+5=99##
export PS4='+[$(debug_info=$(printf "T: %s, L:%3s, S: %s, F: %s" "$(date +%H%M%S)" "$LINENO" "$(basename "${BASH_SOURCE[0]}")" "$(for ((i=${#FUNCNAME[*]}-1; i>=0; i--)) do func_stack="$func_stack ${FUNCNAME[i]}"; done; echo $func_stack)") && echo ${debug_info:0:94})]: '

BASE_PATH=$(readlink -m $0)
BASE_DIR=$(dirname $BASE_PATH)
BASE_NAME=$(basename $BASE_PATH .sh)

log=$BASE_DIR/$BASE_NAME.log

function write_log()
{
    [ -n "$@" ] && echo "[`date "+%F %T"`]    $@" >>$log
}

function print_log()
{
    [ -n "$@" ] && echo "$@"
    [ -n "$@" ] && echo "[`date "+%F %T"`]    $@" >>$log
}

function die()
{
    print_log "$@"
    print_log "See log [$log]."
    exit 1
}

java_src=$BASE_DIR

echo "=============================================================================="
echo "本地构建开始: $java_src"
echo "=============================================================================="
echo

mvn source:jar -s $java_src/mvn_settings_linux.xml -f $java_src/com.xdc.soft/pom.xml            || exit 1
mvn source:jar -s $java_src/mvn_settings_linux.xml -f $java_src/com.xdc.soft.virgo/pom.xml      || exit 1
mvn source:jar -s $java_src/mvn_settings_linux.xml -f $java_src/com.xdc.soft.opensource/pom.xml || exit 1
mvn source:jar -s $java_src/mvn_settings_linux.xml -f $java_src/com.xdc.soft.mini/pom.xml       || exit 1
echo

echo "=============================================================================="
echo "本地构建成功: $java_src"
echo "=============================================================================="

