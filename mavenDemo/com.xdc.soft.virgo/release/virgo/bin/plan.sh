#!/bin/bash

BASE_PATH=$(readlink -m $0)
BASE_DIR=$(dirname $BASE_PATH)
BASE_NAME=$(basename $BASE_PATH .sh)

KERNEL_HOME=$BASE_DIR/..

. $KERNEL_HOME/bin/setupClasspath.sh

$JAVA_HOME/bin/java -classpath $CLASSPATH com.xdc.basic.api.jmx.virgo.client.VirgoMain plan "$@"
