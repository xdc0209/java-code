#!/bin/bash

BASE_PATH=$(readlink -m $0)
BASE_DIR=$(dirname $BASE_PATH)
BASE_NAME=$(basename $BASE_PATH .sh)

function wait_until_all_plans_active()
{
    KERNEL_HOME=$BASE_DIR/..
    START_LOG=$KERNEL_HOME/serviceability/logs/start.log
    VIRGO_JMX_PORT=9875

    # Waiting for virgo jmx port $VIRGO_JMX_PORT to start...##
    for ((i=0; i<30; ++i))
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
    for ((i=0; i<30; ++i))
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

