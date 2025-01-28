#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
int main(){
	int i ;
	int j ;
	for(i=0;i<3;i++){
		if(fork()==0){
			printf("Child of root, process %d\n",i);
			for(j=0;j<3;j++){
				if(fork()==0){
					printf("\tchild of %d, process %d\n",i,j);
					exit(0);
				}
			}
			wait(NULL);
			exit(0);
		}
	}
	wait(NULL);
	exit(0);
}
