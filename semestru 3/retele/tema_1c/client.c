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

int main(int argc, char* argv[]){
    int c;
    struct sockaddr_in server;

    c = socket(AF_INET,SOCK_DGRAM,0);
    if(c < 0 ){
        printf("eroare la creare socket!");
        return 1;

    }
    memset(&server,0,sizeof(server)); 
    server.sin_family = AF_INET;
    server.sin_port = htons(atoi(argv[1]));
    server.sin_addr.s_addr = inet_addr(argv[2]);
    char* buffer;
    size_t size_buffer;
    strcpy(buffer,argv[3]);
    char recieved_buffer[100];
    
    int l = sizeof(server);
    while(!recieved_buffer[0]){
        sendto(c,buffer,sizeof(buffer),0,(struct sockaddr *)&server,sizeof(server));
        recvfrom(c,recieved_buffer,sizeof(buffer),0,(struct sockaddr *)&server,&l);
    }
    printf("Received:%s",recieved_buffer);
    

    close(c);
}
