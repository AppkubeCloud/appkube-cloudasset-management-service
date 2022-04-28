#!/usr/bin/env bash

CMD=$1
NOHUP=${NOHUP:=$(which nohup)}
PS=${PS:=$(which ps)}

# default java
JAVA_CMD=${JAVA_CMD:=$(which java)}

get_pid() {
    cat "${APPKUBE_ASSET_PID}" 2> /dev/null
}

pid_running() {
    kill -0 $1 2> /dev/null
}

die() {
    echo $*
    exit 1
}

if [ -n "$JAVA_HOME" ]
then
    # try to use $JAVA_HOME
    if [ -x "$JAVA_HOME"/bin/java ]
    then
        JAVA_CMD="$JAVA_HOME"/bin/java
    else
        die "$JAVA_HOME"/bin/java is not executable
    fi
fi


# take variables from environment if set
SERVICE_JAR=${SERVICE_JAR:=target/cloudasset-management-service-0.0.1-SNAPSHOT.jar}

APPKUBE_ASSET_PID=${APPKUBE_ASSET_PID:=appkuve-asset_service.pid}
LOG_FILE=${LOG_FILE:=console.log}
#LOG4J=${LOG4J:=}
DEFAULT_JAVA_OPTS="--SERVER=18.234.236.211 --SERVER_PORT=5057 --PSQL_HOST=localhost --PSQL_PORT=5432 --PSQL_DB=cloudassetdb --PSQL_USER=postgres --PSQL_PSWD=postgres -Djdk.tls.acknowledgeCloseNotify=true -Xms1g -Xmx1g -XX:NewRatio=1 -XX:+ResizeTLAB -XX:+UseConcMarkSweepGC -XX:+CMSConcurrentMTEnabled -XX:+CMSClassUnloadingEnabled -XX:-OmitStackTraceInFastThrow"

if $JAVA_CMD -XX:+PrintFlagsFinal 2>&1 |grep -q UseParNewGC; then
	DEFAULT_JAVA_OPTS="${DEFAULT_JAVA_OPTS} -XX:+UseParNewGC"
fi

JAVA_OPTS="${JAVA_OPTS:="$DEFAULT_JAVA_OPTS"}"

start() {
    echo "Starting appkuve-asset service ...${JAVA_CMD}"
    #"${NOHUP}" "${JAVA_CMD}" -jar "${SERVICE_JAR}" ${JAVA_OPTS} >> "${LOG_FILE}" 2>> "${LOG_FILE}" &
	"${NOHUP}" "${JAVA_CMD}" -jar "${SERVICE_JAR}" ${JAVA_OPTS}  >> "${LOG_FILE}" 2>> "${LOG_FILE}" & echo $! > "${APPKUBE_ASSET_PID}"
	#"${NOHUP}" "${JAVA_CMD}" -jar "${SERVICE_JAR}" ${JAVA_OPTS} > /dev/null 2>&1 & echo $! > "${APPKUBE_ASSET_PID}" 
}

run() {
    echo "Running appkuve-asset service ..."
    exec "${JAVA_CMD}" -jar "${SERVICE_JAR}" ${JAVA_OPTS}  >> "${LOG_FILE}" 2>> "${LOG_FILE}" & echo $! > "${APPKUBE_ASSET_PID}"
}

stop() {
    if [ ! -f "${APPKUBE_ASSET_PID}" ]; then
      die "Not stopping. PID file not found: ${APPKUBE_ASSET_PID}"
    fi

    PID=$(get_pid)

    echo "Stopping appkuve-asset service ($PID) ..."
    echo "Waiting for appkuve-asset service to halt."

    kill $PID

    while "$PS" -p $PID > /dev/null; do sleep 1; done;
    rm -f "${APPKUBE_ASSET_PID}"

    echo "appkuve-asset service stopped"
}

restart() {
    echo "Restarting appkuve-asset service ..."
    stop
    start
}

status() {
    PID=$(get_pid)
    if [ ! -z $PID ]; then
        if pid_running $PID; then
            echo "appkuve-asset service running with PID ${PID}"
            return 0
        else
            rm "${APPKUBE_ASSET_PID}"
            die "Removed stale PID file ${APPKUBE_ASSET_PID} with ${PID}."
        fi
    fi

    die "appkuve-asset service not running"
}

case "$CMD" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        restart
        ;;
    status)
        status
        ;;
    run)
        run
        ;;
    *)
        echo "Usage $0 {start|stop|restart|status|run}"
esac

