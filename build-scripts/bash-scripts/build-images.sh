#!/bin/bash

function build_basic() {
  DOCKERFILE_PATH=$1
  APP_NAME=$2

  docker build -f ${DOCKERFILE_PATH}/Dockerfile \
    -t ${APP_NAME}:kubernetes .
}

echo "Building auth service image"
cd ../..
build_basic web college-auth

echo "Building logger service image"
cd ../college_logger
build_basic . college-logger

echo "Building core service image"
cd ../college_core
build_basic . college-core

#echo "Building Docker images"
#build_basic ./config-server/target/config-server-${APP_VERSION}.jar application/config-server
#build_basic ./discovery-service/target/discovery-service-${APP_VERSION}.jar application/discovery-service
#build_basic ./examinator/target/examinator-${APP_VERSION}.jar application/examinator
