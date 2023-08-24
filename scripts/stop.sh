#!/bin/bash

ROOT_PATH="/home/ubuntu/face-backend"
JAR="$ROOT_PATH/application.jar"
STOP_LOG="$ROOT_PATH/stop.log"
SERVICE_PID=$(pgrep -f $JAR) # 실행중인 Spring 서버의 PID

if [ -n "$SERVICE_PID" ]; then
  echo "서비스 종료 " >> $STOP_LOG
  kill "$SERVICE_PID"
  
else
  echo "서비스 NouFound" >> $STOP_LOG
fi
