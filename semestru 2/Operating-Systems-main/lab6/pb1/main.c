#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
int main(){
	if(fork()==0){
		if(fork()==0) exit(0);
		if(fork()==0) exit(0);
		if(fork()==0) exit(0);
	}
	if(fork()==0){
                if(fork()==0) exit(0);
                if(fork()==0) exit(0);
                if(fork()==0) exit(0);
        }
	if(fork()==0){
                if(fork()==0) exit(0);
                if(fork()==0) exit(0);
                if(fork()==0) exit(0);
        }
	wait(0);
	exit(0);
}
