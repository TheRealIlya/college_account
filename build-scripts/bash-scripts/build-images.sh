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
