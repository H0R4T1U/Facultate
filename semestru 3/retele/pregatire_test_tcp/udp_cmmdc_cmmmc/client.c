#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
struct tuplu {
    int nr1;
    int nr2;
};

int main(){
    int c,l;
    struct sockaddr_in server;
    c = socket(AF_INET,SOCK_DGRAM,0);
    l = sizeof(server);
    if(c < 0){
        printf("eroare la socket!");
        return -1;
    }
    memset(&server,0,sizeof(server));
    server.sin_addr.s_addr = inet_addr("127.0.0.1");
    server.sin_family = AF_INET;
    server.sin_port = htons(1234);
    int a=0,b=0;
    printf("a:");
    scanf("%d",&a);

    printf("b:");
    scanf("%d",&b);
    struct tuplu a_b;
    a_b.nr1 = htonl(a);
    a_b.nr2 = htonl(b);
    for(int i = 0; i < 10; i ++){
        sendto(c,&a_b,sizeof(a_b),0,(struct sockaddr*)&server,sizeof(server));
    }
    struct tuplu final;
    recvfrom(c,&final,sizeof(final),0,(struct sockaddr*)&server,&l);
    final.nr1 = ntohl(final.nr1);
    final.nr2 = ntohl(final.nr2);
    printf("cmmdc:%d,cmmmc:%d",final.nr1,final.nr2);

}