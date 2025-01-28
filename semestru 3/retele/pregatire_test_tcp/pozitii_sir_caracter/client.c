#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <arpa/inet.h>
#include <stdlib.h>

int main(){
    int c,l;
    struct sockaddr_in server;
    c = socket(AF_INET,SOCK_STREAM,0);
    if( c < 0){
        printf("EROARE LA BIND");
        return -1;
    }
    memset(&server,0,sizeof(server));
    server.sin_family = AF_INET;
    server.sin_port = htons(1234);
    server.sin_addr.s_addr = inet_addr("127.0.0.1");
    
    if(connect(c,(struct sockaddr*)&server,sizeof(server)) < 0 ){
        printf("eroare conectare server!");
        return -1;
    }

    char* buffer = NULL;
    size_t lungime_sir = 0;
    char ch = 0;

    printf("sir:");
    ssize_t actual_length = getline(&buffer,&lungime_sir,stdin);

    printf("char:");
    scanf("%c",&ch);

    actual_length-=1;
    uint32_t lung_sir = htonl(actual_length);

    send(c,&lung_sir,sizeof(lung_sir),0);
    send(c,buffer,sizeof(char)*actual_length,0);
    send(c,&ch,sizeof(char),0);
    uint32_t nr_ap_net =0;
    recv(c,&nr_ap_net,sizeof(nr_ap_net),MSG_WAITALL);
    size_t nr_aparitii = ntohl(nr_ap_net);
    int* aparitii = (int*)malloc(nr_aparitii);
    recv(c,aparitii,sizeof(int)*nr_aparitii,MSG_WAITALL);
    printf("Caracterul %c apare pe pozitiile:",ch);
    for(int i = 0 ; i < nr_aparitii; i ++){
        printf("%d,",aparitii[i]);
    }

}