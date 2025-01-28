#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <string.h>


int suma_cifre(int n){
	int sum = 0;
	while(n != 0){
		sum += n%10;
		n/=10;
	}
	return sum;
}
int prod_cifre(int n){
	int prod = 1;
	while(n != 1){
		prod *= n%10;
		n/=10;
	}
	return prod;
}


int main(void){
	int a2b[2];
	pipe(a2b);
	if(fork() == 0){
		close(a2b[1]);
		int a;
		char op[1];
		while(1){
			if(read(a2b[0],op,sizeof(char)) <= 0){
				break;
			}
			read(a2b[0],&a,sizeof(int));
			if(op[0] == '+'){
				printf("%1s %d %d\n",op,a,suma_cifre(a));
			}else{
				printf("%1s %d %d\n",op,a,prod_cifre(a));
			}
		}
		close(a2b[0]);
		exit(0);

	}
	close(a2b[0]);
	FILE* fd = fopen("4.txt","r");
	char* buffer;
	size_t len = 0;
	char op[1];
	int a;
	while(getline(&buffer,&len,fd) != EOF){
		sscanf(buffer,"%1s %d",op,&a);
		write(a2b[1],op,sizeof(char));
		write(a2b[1],&a,sizeof(int));
	}
	close(a2b[1]);
	wait(0);
	exit(0);
}
