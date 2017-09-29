#!/bin/bash

set -ex

echo "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"
echo "% Building UI                     %"
echo "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"


echo "Copying index.html and style.css across to resources/ui folder"
cp elmUi/index.html practice-service/src/main/resources/ui/
cp elmUi/css/style.css practice-service/src/main/resources/ui/css/

cd elmUi

echo "Making ELM"
elm make Main.elm --output ../practice-service/src/main/resources/ui/js/index.js --yes

