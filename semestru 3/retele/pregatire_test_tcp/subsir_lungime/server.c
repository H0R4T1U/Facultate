#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main(){
    int s,c,l;
    struct sockaddr_in server,client;
    s = socket(AF_INET,SOCK_STREAM,0);
    memset(&server,0,sizeof(server));
    server.sin_family = AF_INET;
    server.sin_port = htons(1234);
    server.sin_addr.s_addr = INADDR_ANY;
    if(bind(s,(struct sockaddr *)&server,sizeof(server)) < 0){
        printf("EROARE LA BIND\n");
        return -1;
    }
    listen(s,5);
    memset(&client,0,sizeof(client));
    l = sizeof(client);
    while(1){
        c = accept(s,(struct sockaddr*)&client,&l);
        if(c < 0){
            printf("EROARE LA connext\n");
            return -1;
        }
        printf("Sa conectat un client\n");

        uint32_t lun_net;
        int lungime=0;
        int i,l;
        recv(c,&lun_net,sizeof(lun_net),MSG_WAITALL);
        lungime = ntohl(lun_net);
        
        char* sir = (char*)malloc(lungime);
        recv(c,sir,sizeof(char)*lungime,MSG_WAITALL);

        recv(c,&i,sizeof(int),MSG_WAITALL);
        i = ntohl(i);
        recv(c,&l,sizeof(int),MSG_WAITALL);
        l = ntohl(l);

        if(i >= 0 && i+1 < lungime){
            char* buffer = (char*)malloc(l);
            strncpy(buffer,sir+i,l);
            buffer[l] = '\0';
            send(c,buffer,sizeof(char)*l,0);
        }
    }
}