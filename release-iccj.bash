#!/bin/bash

#version=1.0.3
version=$1

rm -rf  /opt/icc-pre-${version} > /dev/null 2>&1 

mv /opt/icc /opt/icc-pre-${version}
mkdir /opt/icc
mkdir /opt/icc/bin
mkdir /opt/icc/repo
mkdir /opt/icc/conf

chown -R dana:dana /opt/icc

./deploy-iccj.bash

cp changelog.txt ~/projects/dist/icc-${version}-changelog.txt

cd /opt
rm -f ~/projects/dist/iccj-${version}.zip  > /dev/null 2>&1
zip -r ~/projects/dist/iccj-${version}.zip icc

# for immediate testing:
rm -rf icc-${version}-dist > /dev/null 2>&1 
mv icc icc-${version}-dist
unzip ~/projects/dist/iccj-${version}.zip
chown -R dana:dana icc

