#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include "unistd.h"


int main(void) {
    int n;
    scanf("%d",&n);

    if(n < 1) {
        printf("BAD USSAGE!");
        exit(1);
    }

    for(int i = 0; i<n;i++) {
        int pid = fork();
        if(pid == 0) {
            printf("From Copil:");
            printf("Copil:%d,Parinte:%d\n",getpid(),getppid());
        }else {
            printf("From Parent:");
            printf("Copil:%d\n",pid);
            wait(0);
            exit(0);
        }

    }

}
