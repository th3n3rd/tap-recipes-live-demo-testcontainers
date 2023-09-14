#!/bin/bash

APP_NAME=${1:-"demo"}
if [ -z "$APP_NAME" ]; then
    echo "Application name is required as first parameter"
    exit 1
fi

spring init -d=web,devtools --build=maven "$APP_NAME"
