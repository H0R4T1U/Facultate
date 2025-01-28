#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
int main(){
	int a2b[2];
	pipe(a2b);
	if(fork() == 0){
		close(a2b[1]);
		char op[1];
		int a,b;
		while(1){
			if(read(a2b[0],op,sizeof(char))<= 0){
				break;
			}
			read(a2b[0],&a,sizeof(int));
			read(a2b[0],&b,sizeof(int));
			if(strcmp("+",op) == 0){
				printf("%d %1s %d = %d\n",a,op,b,a+b);
			}else{
				printf("%d %1s %d = %d\n",a,op,b,a-b);
			}
		}
		close(a2b[0]);
		exit(0);
	}
	close(a2b[0]);
	char* buffer = NULL;
	FILE* fd = fopen("operatii.txt","r");
	size_t len;
	while(getline(&buffer,&len,fd) != EOF){
		int a,b;
		char op[1];
		sscanf(buffer,"%1s %d %d",op,&a,&b);
		write(a2b[1],op,sizeof(char));
		write(a2b[1],&a,sizeof(int));
		write(a2b[1],&b,sizeof(int));
	}
	close(a2b[1]);
	wait(0);
	

}
