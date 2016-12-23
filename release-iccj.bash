#!/bin/bash

#version=1.0.3
version=$1

mv /opt/icc /opt/icc-pre-${version}
mkdir /opt/icc
mkdir /opt/icc/bin
mkdir /opt/icc/repo
mkdir /opt/icc/conf

chown -R dana:dana /opt/icc

./deploy-iccj.bash

cp changelog.txt ~/projects/dist/icc-${version}-changelog.txt

cd /opt
zip -r ~/projects/dist/iccj-${version}.zip icc

# for immediate testing:
mv icc icc-${version}-dist
unzip ~/projects/dist/iccj-${version}.zip
chown -R dana:dana icc

