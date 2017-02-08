#!/usr/bin/env bash
while
	javac *.java
	java TestsGenerator > input.txt
	# head -n$3 input.txt
	utime="$( TIMEFORMAT='%lR';time ( java BruteSolution < input.txt > bruteOutput.txt; ) 2>&1 1>/dev/null )"
	if [ $? -ne 0 ]
	then
		echo "Damn It" $1 "  Failed."
		exit 1
	fi
	utime2="$( TIMEFORMAT='%lR';time ( ./$2 < input.txt > output.txt; ) 2>&1 1>/dev/null )"
	if [ $? -ne 0 ]
	then
		echo "Damn It" $2 "  Failed."
		exit 1
	fi
	# echo ${utime2};
	echo $utime $utime2;
	diff brute.txt output.txt;
do :; done