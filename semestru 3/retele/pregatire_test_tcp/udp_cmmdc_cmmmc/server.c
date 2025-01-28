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
    int s,l;
    struct sockaddr_in server,client;
    s = socket(AF_INET,SOCK_DGRAM,0);
    if(s < 0){
        printf("eroare la socket");
        return -1;
    }
    memset(&server,0,sizeof(server));
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_family = AF_INET;
    server.sin_port = htons(1234);
    if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
        printf("eroare la bind");
        return -1;
    }
    memset(&client,0,sizeof(client));
    l = sizeof(client);
    while(1){
        struct tuplu a_b;
        recvfrom(s,&a_b,sizeof(a_b),0,(struct sockaddr*)&client,&l);
        printf("primit date!");

        a_b.nr1 = ntohl(a_b.nr1);
        a_b.nr2 = ntohl(a_b.nr2);
        int cmmdc=0,cmmmc=0;
        int r=0;
        int a = a_b.nr1;
        int b = a_b.nr2;
        while(b != 0){
            r = a % b;
            a = b;
            b = r;
        }
        cmmdc = a;
        cmmmc = (a_b.nr1 * a_b.nr2)/cmmdc;
        struct tuplu final;
        final.nr1 = htonl(cmmdc);
        final.nr2 = htonl(cmmmc);
        for(int i = 0; i < 10 ; i++){
            sendto(s,&final,sizeof(final),0,(struct sockaddr*)&client,sizeof(client));
        }
    }

}