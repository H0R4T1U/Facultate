#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
//1. Review the special behavior of open when working with FIFO
//2. Implement a program that continuously reads two integers from the console, sends them to another process, gets back
//from that process their sum and product, and display them to the console. The program stops when the sum and the
//product are equal
int main(){
	int a,b,sum,prod;
	int p2a[2];
	int a2p[2];
	pipe(p2a);
	pipe(a2p);
	while(1){
		if(fork() == 0){
			while(1){
				read(p2a[0],&a,sizeof(int));
				read(p2a[0],&b,sizeof(int));
				sum = a+b;
				prod = a*b;
				write(a2p[1],&sum,sizeof(int));
				write(a2p[1],&prod,sizeof(int));
				if(sum == prod){
					close(p2a[0]);
					close(a2p[1]);
					exit(0);
				}
			}
		}
		close(a2p[1]);
		close(p2a[0]);
		printf("a:");
		scanf("%d",&a);
		printf("b:");
		scanf("%d",&b);
		write(p2a[1],&a,sizeof(int));
		write(p2a[1],&b,sizeof(int));
		read(a2p[0],&sum,sizeof(int));
		read(a2p[0],&prod,sizeof(int));
		printf("sum:%d;prod:%d\n",sum,prod);
		if(sum == prod){
			wait(0);
			close(a2p[0]);
			close(p2a[1]);
			exit(0);
		}
	}
}
