#include <stdio.h>
#include <stdlib.h>
#include<sys/wait.h>
#include "unistd.h"

int create_process(int n) {
    if(n == 0) {
        return 0;
    }
    n--;
    int pid = fork();
    if(pid == 0) {
        printf("From Copil:");
        printf("Copil:%d,Parinte:%d\n",getpid(),getppid());
        create_process(n);
    }else {
        return 0;
    }
}
int main(void) {
    int n;
    scanf("%d",&n);

    if(n < 1) {
        printf("BAD USSAGE!");
        exit(1);
    }



    create_process(n);
    wait(0);
    exit(0);
}
