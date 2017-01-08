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

if [ -z "${3}" ]; then
    cp -r target/appassembler/bin/* $iccdir/bin
    cp -r target/appassembler/conf/* $iccdir/conf
fi

#if [ -e /usr/libexec/java_home ]; then
echo "Before release:"
echo "Add the following to $iccdir/bin/icc near the end before the check for -x \$JAVACMD"
echo "Around line # 81 probably"

echo 'if $darwin; then'
echo '   if [ ! -d $JAVA_HOME ]; then'
echo '      JAVA_HOME=`/usr/libexec/java_home`'
echo '      JAVACMD="$JAVA_HOME/bin/java"'
echo "   fi"
echo "fi"

#echo "if [ -e /usr/libexec/java_home ]; then"
#echo "   export JAVA_HOME=`/usr/libexec/java_home`"
#echo "fi"
#fi



