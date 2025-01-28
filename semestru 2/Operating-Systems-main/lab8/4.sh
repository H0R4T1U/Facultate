#! /bin/bash

list=$(find $1)
for file in $list; do
	if test -L $file; then
		if test -e $file; then
			echo $file
		fi
	fi
	
done
