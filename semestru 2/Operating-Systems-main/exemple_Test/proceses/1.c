#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <time.h>
#include <fcntl.h>

int suma_cifre(int n){
	int sum = 0;
	while(n!=0){
		sum += n%10;
		n/=10;
	}
	return sum;
}

int prod_cifre(int n){
	int prod = 1;
	while(n != 0){
		prod *= n%10;
		n/=10;
	}
	return prod;
}

int main(void){
	srandom(time(NULL));
	int n,k;
	int a2b[2];
	pipe(a2b);
	if(fork() == 0){
		close(a2b[1]);
		int fd = open("textfile.txt",O_CREAT+O_WRONLY);
		read(a2b[0],&k,sizeof(int));
		while(k != 0){
			read(a2b[0],&n,sizeof(int));
			if(k % 2 == 0){
				int sum = suma_cifre(n);
				char tmpbuf[50];
				int len = sprintf(tmpbuf, "%d %d %d\n", k,n,sum);
				write(fd, tmpbuf, len);
			}else{
				int prod = prod_cifre(n);
				char tmpbuf[50];
				int len = sprintf(tmpbuf, "%d %d %d\n", k,n,prod);
				write(fd, tmpbuf, len);
			}
			read(a2b[0],&k,sizeof(int));
		}
		close(a2b[0]);
		close(fd);
		exit(0);
	}
	close(a2b[0]);
	k = 1;
	while(k != 0){
		scanf("%d",&k);
		n = random() % 1000;
		write(a2b[1],&k,sizeof(int));
		write(a2b[1],&n,sizeof(int));
	}

	close(a2b[1]);
	wait(0);
	exit(0);	

}

