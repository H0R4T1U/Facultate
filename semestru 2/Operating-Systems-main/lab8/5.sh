#! /bin/bash

while true; do
	for i in $*; do
		pids=$(ps -ef | grep $i | sort | awk '{print $2}')
		for pid in $pids; do
			if [ $pid -ne $$ ] ; then
				err=$(kill -9 $pid 2>/dev/null)
				if [ -z $err ] ; then
					echo "Process $i killed Succsesfully!"
				fi
			fi
		done
	done
	sleep 5
done
