#! /bin/bash

files=$(find $1 -type f)
echo "" > hashes
for file in $files; do
	md5sum $file >> hashes
done

uniqs=$(cat hashes | sort | awk '{ print $1 }' | uniq -d)
for uniq in $uniqs; do
	names=$(cat hashes | grep -e "$uniq" | awk '{ print $2 }')
	echo "MD5 hash: $uniq"
	for name in $names; do
		echo "$name"
	done
done
