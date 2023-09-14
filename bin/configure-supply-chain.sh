# https://docs.vmware.com/en/VMware-Tanzu-Application-Platform/1.6/tap/scc-install-ootb-sc-wtest.html
#!/bin/bash

if tanzu apps cluster-supply-chains get source-test-to-url &> /dev/null ; then
    echo "Supply chain already installed"
    exit 0
fi

TMP_FOLDER=$(mktemp -d)
trap "rm -rf $TMP_FOLDER" EXIT

PACKAGE_VERSION=$(tanzu package installed get ootb-supply-chain-basic -n tap-install -o yaml | yq '.[0].package-version')
VALUES_FILE="$TMP_FOLDER/ootb-supply-chain-testing-values.yaml"

tanzu package installed get ootb-supply-chain-basic -n tap-install --values > "$VALUES_FILE"

tanzu package install ootb-supply-chain-testing \
    --package ootb-supply-chain-testing.tanzu.vmware.com \
    --version "$PACKAGE_VERSION" \
    --namespace tap-install \
    --values-file "$VALUES_FILE"
