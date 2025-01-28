#! /bin/bash

start_date=$(date -d "$(date +%Y-%m-01) 00:00:00" +"%Y-%m-%d %H:%M:%S")
end_date=$(date -d "$(date -d "+1 month" +%Y-%m-01) 00:00:00" +"%Y-%m-%d %H:%M:%S")

last -s "$start_date" -t "$end_date" | awk '{print $1}' | sort | uniq -c | sort -nr
