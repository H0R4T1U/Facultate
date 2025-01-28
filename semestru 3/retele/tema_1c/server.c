#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <arpa/inet.h>
#include <stdlib.h>
int main(int argc,char* argv[]){
    int s;
    int l;
    struct sockaddr_in server,client;
    s = socket(AF_INET,SOCK_DGRAM,0);
    if(s < 0){
        printf("eroare la obtinere socket\n");
        return 1;
    }
    memset(&server,0,sizeof(server));
    server.sin_port = htons(atoi(argv[1]));
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    if(bind(s,(struct sockaddr *)&server,sizeof(server))<0){
        printf("Eroare la bin \n");
        return 1;
    }
    l = sizeof(client); 
    memset(&client,0,sizeof(client));
    char buffer[100]={0};
    while(1){
        memset(&buffer,0,sizeof(char)*100);
        while(buffer[0] == 0){
            recvfrom(s,&buffer,sizeof(char)*100,0,(struct sockaddr *)&client,&l);
        }

        char send_buff[strlen(buffer)];
        int n = strlen(buffer);
        for(int i = 0; i< n;i++){
            send_buff[i] = buffer[n-i-1];
        }
        for(int i = 0 ; i<10;i++){
            sendto(s,&send_buff,sizeof(send_buff),0,(struct sockaddr * )&client,sizeof(client));
        }   
        printf("Received:%s",buffer);
    }
    close(s);
    
}