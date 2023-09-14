#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"


STUB="$SCRIPT_DIR/../config/encoding-api-stub.yaml"
PIPELINE="$SCRIPT_DIR/../config/consumer-pipeline.yaml"
WORKLOAD="$SCRIPT_DIR/../config/consumer-workload.yaml"

kubectl apply -f "$STUB"
kubectl apply -f "$PIPELINE"

kubectl delete -f "$WORKLOAD" || true
kubectl apply -f "$WORKLOAD"
