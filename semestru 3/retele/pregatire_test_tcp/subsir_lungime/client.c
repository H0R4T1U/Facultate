#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main(){
    int c;
    struct sockaddr_in server;
    c = socket(AF_INET,SOCK_STREAM,0);
    if(c < 0){
        printf("eroare la socket\n");
        return -1;
    }
    memset(&server,0,sizeof(server));
    server.sin_addr.s_addr = inet_addr("127.0.0.1");
    server.sin_family = AF_INET;
    server.sin_port = htons(1234);

    if(connect(c,(struct sockaddr*)&server,sizeof(server))){
        printf("eroare conectare server!\n");
        return -1;
    }
    char* buffer = NULL;
    size_t x = 0;
    printf("sir:");
    ssize_t lungime_sir = getline(&buffer,&x,stdin);
    lungime_sir -=1;

    int i=0;
    int l=0;
    printf("i:");
    scanf("%d",&i);
    printf("l:");
    scanf("%d",&l);

    uint32_t l_sir_net = htonl(lungime_sir);
    send(c,&l_sir_net,sizeof(l_sir_net),0);
    send(c,buffer,sizeof(char)*lungime_sir,0);
    i = htonl(i);
    l = htonl(l);
    send(c,&i,sizeof(int),0);
    send(c,&l,sizeof(int),0);
    l = ntohl(l);
    char* subsir = (char*)malloc(l);
    recv(c,subsir,sizeof(char)*l,MSG_WAITALL);
    printf("%s\n",subsir);

    
}