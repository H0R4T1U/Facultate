#! /bin/bash

if [ ! -f "./hashes" ]; then

	files=$(find $1 -type f)
	for file in $files; do
		md5sum $file >> hashes
	done
else
	md5sum -c ./hashes
fi
