#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>

void f(int signal){
	char s[10];
	printf("Are you sure you want to kill the process?[Y/n]:");
	scanf("%s",s);
	if(strcmp("y",s) == 0){
		exit(0);
	}
}

int main(int argc,char** argv){
	signal(SIGINT,f);
	while(1);
	

	exit(0);
}
