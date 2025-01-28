#! /bin/bash

df -h | grep -e "[1-9].[0-9]G" -e "[2-9][0-9]%"| awk '{ print $1 }' | sed 's/^./\/&/'
