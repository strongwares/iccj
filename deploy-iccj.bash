#!/bin/bash

cp -r target/appassembler/repo/* /opt/icc/repo

if [ -z "${1}" ]; then
    cp -r target/appassembler/bin/* /opt/icc/bin
    cp -r target/appassembler/conf/* /opt/icc/conf
fi


