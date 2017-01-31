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

mac=0
darwin=`uname | grep -i darwin`
if [ "${darwin}" = "Darwin" ]; then
    mac=1
fi

MAC_FIX='if $darwin;  then JAVA_HOME=`/usr/libexec/java_home`; JAVACMD="$JAVA_HOME/bin/java"; fi'
ICC_SCRIPT=target/appassembler/bin/icc

if [ "${mac}" = "1" ]; then
    # Stupid sed on macos, doesn't interpret \n as newline in replacement pattern:
    lf=$'\n'
    sed -i '' "s#^\\(if.*-x.*JAVACMD.*then\\)\$#${MAC_FIX}\\${lf}\\${lf}\\1#g" $ICC_SCRIPT
else
    sed -i "s#^\\(if.*-x.*JAVACMD.*then\\)\$#${MAC_FIX}\\n\\n\\1#g" $ICC_SCRIPT
fi

if [ -z "${3}" ]; then
    cp -r target/appassembler/bin/* $iccdir/bin
    cp -r target/appassembler/conf/* $iccdir/conf
fi




