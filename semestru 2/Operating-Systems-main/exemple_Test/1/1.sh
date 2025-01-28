#! /bin/bash

if [[ $# -lt 1 ]]; then
	echo "Utilizare ./1.sh file1 file2 ..."
	exit  
fi

read var
while [ $var != "stop" ]; do
	for file in $@; do
		found=$(find $file -type f -exec file {} + 2>/dev/null | grep "text" | awk '{ print $1}' | sed "s/://")
		if [ $found ]; then
			echo "$found"
			number=$(cat $found | grep -Ec "$var")
			echo "nr linii:$number"
			if [ $(($number % 2)) -eq 0 ]; then
				echo "$( cat $found | grep -E "$var" | tail -n 1)" >> $found
			fi
		else
			echo "fisierul $file nu este de tip text"
		fi
	done
	read var

done;
