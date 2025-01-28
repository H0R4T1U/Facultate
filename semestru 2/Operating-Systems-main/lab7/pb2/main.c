#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>
int main(){
	int n;
	char* buffer = malloc(sizeof(char)*100);;
	int p2a[2];
	pipe(p2a);
	if(fork() == 0){
		read(p2a[0],&n,sizeof(int));
		buffer =realloc(buffer,n);
		read(p2a[0],buffer,sizeof(char)*n);
		printf("From Child\n");
		printf("Your string is:%s",buffer);
		close(p2a[0]);
		exit(0);
	}
	close(p2a[0]);
	fgets(buffer,100,stdin);
	n = strlen(buffer);
	write(p2a[1],&n,sizeof(int));
	write(p2a[1],buffer,sizeof(char)*n);
	close(p2a[1]);
	wait(0);
	exit(0);
}
