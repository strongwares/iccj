#!/bin/bash

dir=/opt
iccdir=$dir/icc

if [ ! -d $dir ]; then
    if [ -z "${1}" ]; then
        echo "For initial deploy, pass user and group on command line"
        echo You are:
        id
        exit
    fi

    if [ -z "${2}" ]; then
        echo "For initial deploy, pass user and group on command line"
        echo You are:
        id
        exit
    fi
fi

user=$1
group=$2

if [ ! -d $dir ]; then
    sudo mkdir $dir
    sudo chown $user:$group $dir
fi

if [ ! -d $iccdir ]; then
    sudo mkdir $iccdir
    sudo chown $user:$group $iccdir

    mkdir $iccdir/bin
    mkdir $iccdir/repo
    mkdir $iccdir/conf
fi

cp -r target/appassembler/repo/* $iccdir/repo

MAC_FIX='if $darwin;  then JAVA_HOME=`/usr/libexec/java_home`; JAVACMD="$JAVA_HOME/bin/java"; fi'
ICC_SCRIPT=target/appassembler/bin/icc
sed -i "s#^\\(if.*-x.*JAVACMD.*then\\)\$#${MAC_FIX}\\n\\n\\1#g" $ICC_SCRIPT

if [ -z "${3}" ]; then
    cp -r target/appassembler/bin/* $iccdir/bin
    cp -r target/appassembler/conf/* $iccdir/conf
fi




