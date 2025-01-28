#! /bin/bash

list=$(find $1 -name "*.log")
for file in $list; do
	cat $file | sort > "$file.bak"
	mv "$file.bak" $file
	rm "$file.bak"
done
