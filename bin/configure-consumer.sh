#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"

WORKLOAD="$SCRIPT_DIR/../config/consumer-workload.yaml"

kubectl delete -f "$WORKLOAD" || true
kubectl apply -f "$WORKLOAD"
