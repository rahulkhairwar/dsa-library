#!/usr/bin/env bash

#$loop = 0
#$ctr = 0
#$ctr2 = 0
while
#	javac BruteSolution.java
	javac TestsGenerator.java
	java TestsGenerator > input.txt
	# head -n$3 input.txt
	echo "1 is : " $1 ", 2 is : " $2
	utime="$( TIMEFORMAT='%lR';time ( java CountOnATree2 < input.txt > bruteOutput.txt; ) 2>&1 1>/dev/null )"
	if [ $? -ne 0 ]
	then
		echo "Damn It" $1 "failed."
		exit 1
	fi
	utime2="$( TIMEFORMAT='%lR';time ( ./$2 < input.txt > output.txt; ) 2>&1 1>/dev/null )"
	if [ $? -ne 0 ]
	then
		echo "Damn It" $2 "failed."
		exit 1
	fi
	# echo ${utime2};
	echo $utime $utime2;

#	if $utime ne $utime2
#	then
#		ctr = ctr + 1
#	else
#		ctr2 = ctr2 + 1
#	fi
#
#	if $loop % 10 == 0
#	then
#		echo "java : " $ctr ", c++ : " $ctr2
#	fi

	diff bruteOutput.txt output.txt;
do :; done