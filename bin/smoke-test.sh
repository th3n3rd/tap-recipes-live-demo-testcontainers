#!/bin/bash

APP_URL=${1:-$(kubectl get kservice consumer -o yaml | yq '.status.url' | tr -d '\n' )}
EXPECTED="{\"original\":\"Hello World!\",\"encoded\":\"SGVsbG8gV29ybGQh\"}"
ACTUAL=$(curl -s -XGET "$APP_URL") # add --insecure for self-signed certs
if [ "$ACTUAL" != "$EXPECTED" ]; then
    echo "Smoke test failed: expected \"$EXPECTED\" but was \"$ACTUAL\"" 1>&2
    exit 1
fi

echo "Smoke test succeeded"
