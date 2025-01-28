#! /bin/bash

users=$(cat who.fake | awk '{print $1}')
for user in $users; do
	proceses=$(cat ps.fake | grep "^$user" | cut -d" " -f1)
	no=0
	for process in $proceses; do
		no=$((no+1))
	done
	echo "$user: $no"
done

