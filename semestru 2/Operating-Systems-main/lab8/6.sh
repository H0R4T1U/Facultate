# /bin/bash

files=$(find $1 -type f -perm /o=w)

for file in $files; do 
	echo $(ls -alt $file)
	chmod o-w "$file"
	echo $(ls -alt $file)

done;
