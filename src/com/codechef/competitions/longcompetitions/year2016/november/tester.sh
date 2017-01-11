#!/usr/bin/env bash
while
	javac UrbanDevTestGenerator.java
	java UrbanDevTestGenerator
	head -n$3 UrbanDevInput.txt
#	utime="$( TIMEFORMAT='%lR';time ( ./$1<UrbanDevInput.txt>brute.txt; ) 2>&1 1>/dev/null )"
#	if [ $? -ne 0 ]
#	then
#		echo "Damn It" $1 "  Failed."
#		exit 1
#	fi
	utime2="$( TIMEFORMAT='%lR';time ( ./$2<UrbanDevInput.txt>output.txt; ) 2>&1 1>/dev/null )"
	if [ $? -ne 0 ]
	then
		echo "Damn It" $2 "  Failed."
		exit 1
	fi
	echo ${utime2};
#	echo $utime $utime2;
#	diff brute.txt output;
do :; done