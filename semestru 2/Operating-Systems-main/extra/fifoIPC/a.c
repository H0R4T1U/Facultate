//
// Created by horatiu on 5/13/24.
//
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>
#include <time.h>

char* welcomes[3] = { "Hi, im process A, and I would like to talk to you, process B\n", "Hi, How are you doing Process B?\n", "HOW'S IT GOING PROCESS B?\n"};
char* continues[3] = {"We do be working fine Process B!\n","Yeah, you're right process B shell init should get a life\n","Ce sa mai si zici de viata asta Process B\n"};
char* enders[3] = {"That's about it for today, we should wrap up process B!\n","We should end this now Process B!\n","It was nice talking to your Process B\n"};

int main(int argc, char** argv){
    srand ( time(NULL) );
    int a2b,b2a;
    a2b = open("a2b",O_WRONLY);
    b2a = open("b2a",O_RDONLY);

    int x = rand() % 3;
    printf("%s",welcomes[x]);
    write(a2b,&x,sizeof(int));

    read(b2a,&x,sizeof(int));
    sleep(1);
    printf("%s",continues[x]);

    x = rand() % 3;
    sleep(1);
    printf("%s",enders[x]);
    write(a2b,&x,sizeof(int));

    close(a2b);
    close(b2a);

}
