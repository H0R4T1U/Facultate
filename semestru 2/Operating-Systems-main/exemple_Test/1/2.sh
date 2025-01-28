#include /bin/bash

while read  line;do
	for word in $line; do
		echo $word
	done
done<"a.txt"
