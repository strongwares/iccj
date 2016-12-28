#!/bin/bash

if [ -z "${1}" ]; then
    echo "Pass user and group on command line"
    echo You are:
    id
    exit
fi

if [ -z "${2}" ]; then
    echo "Pass user and group on command line"
    echo You are:
    id
    exit
fi

user=$1
group=$2
dir=/opt
iccdir=$dir/icc

mac=false
darwin=`uname | grep -i darwin`
if [ $darwin = "Darwin" ]; then
    mac=true
fi

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

if [ -z "${3}" ]; then
    cp -r target/appassembler/bin/* $iccdir/bin
    cp -r target/appassembler/conf/* $iccdir/conf
fi

if [ -e /usr/libexec/java_home ]; then
    echo "Add the following to $iccdir/bin/icc near the end before the check for -x \$JAVACMD"

    echo 'if $darwin; then'
    echo '   if [ ! -d $JAVA_HOME ]; then'
    echo '      JAVA_HOME=`/usr/libexec/java_home`'
    echo '      JAVACMD="$JAVA_HOME/bin/java"'
    echo "   fi"
    echo "fi"
    
    #echo "if [ -e /usr/libexec/java_home ]; then"
    #echo "   export JAVA_HOME=`/usr/libexec/java_home`"
    #echo "fi"
fi



