//
// Created by horatiu on 5/13/24.
//
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>
#include <time.h>

char* welcomes[3] = { "Hi, process A, This is process B speaking\n", "Hi, Process A, All's good\n", "Nothing Much Process A\n"};
char* continues[3] = {"I like the way we work togheter Process A\n","I don't like the way init shell acts, process A\n","Mo lasat nevasta Process A\n"};
char* enders[3] = {"Alright process A, see you another time!\n","Alright, Goodbye process A\n","It was nice talking to you too Process A!\n"};


int main(int argc, char** argv){
    srand ( time(NULL) );
    int a2b,b2a;
    int x= 0;
    a2b = open("a2b",O_RDONLY);
    b2a = open("b2a",O_WRONLY);

    read(a2b,&x,sizeof(int));
    sleep(1);
    printf("%s",welcomes[x]);

    x = rand() % 3;
    sleep(1);
    printf("%s",continues[x]);
    write(b2a,&x,sizeof(int));

    read(a2b,&x,sizeof(int));
    sleep(1);
    printf("%s",enders[x]);

    close(a2b);
    close(b2a);

}
