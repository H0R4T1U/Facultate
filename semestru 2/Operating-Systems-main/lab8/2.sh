#! /bin/bash

list=$(find $1 -name "*.c")
founds=0
for file in $list; do 
	count=$( wc <"$file" -l | cut -d' ' -f1 )
	if [ $count -gt 500 ];
	then
		echo $file
		founds=$((founds+1))
	fi
	if [ $founds -eq 2 ];
	then
		break
	fi
done	

