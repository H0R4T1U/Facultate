#! /bin/bash

echo "    ███      ▄█    ▄▄▄▄███▄▄▄▄      ▄████████    ▄████████ 
▀█████████▄ ███  ▄██▀▀▀███▀▀▀██▄   ███    ███   ███    ███ 
   ▀███▀▀██ ███▌ ███   ███   ███   ███    █▀    ███    ███ 
    ███   ▀ ███▌ ███   ███   ███  ▄███▄▄▄      ▄███▄▄▄▄██▀ 
    ███     ███▌ ███   ███   ███ ▀▀███▀▀▀     ▀▀███▀▀▀▀▀   
    ███     ███  ███   ███   ███   ███    █▄  ▀███████████ 
    ███     ███  ███   ███   ███   ███    ███   ███    ███ 
   ▄████▀   █▀    ▀█   ███   █▀    ██████████   ███    ███ 
                                                ███    ███ "

if [ $# != 2 ]; then 
	echo "USAGE: ./timer.sh PATH PHRASE"
	exit 1
fi
$(gcc -Wall -g -o Secvential secvential.c)
$(gcc -Wall -g -o Threaded threaded.c)
echo "Running Timer for Secvential File Reading..."
echo "$(time ./Secvential $1 $2)"


echo ""
echo ""
echo "Running Timer for Threaded File Reading..."
echo "$(time ./Threaded $1 $2)"

echo ""
echo "DONE"
