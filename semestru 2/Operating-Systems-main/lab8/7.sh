#! /bin/bash

usernames=$(sed 's/$/@scs.ubbcluj.ro/' $1)
total=$(echo "$usernames" | wc -l)
counter=1
for username in $usernames; do
	if [ $counter -lt $total ]; then
		echo -n "$username,"
	else
		echo "$username"
	fi
	((counter++))
done;
