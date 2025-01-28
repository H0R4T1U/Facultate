 #include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int main(){
    int s,c,l;
    struct sockaddr_in server,client;
    s = socket(AF_INET,SOCK_STREAM,0);
    if(s < 0){
        printf("eroare la socket");
        return -1;
    }
    memset(&server,0,sizeof(server));
    server.sin_family = AF_INET;
    server.sin_port = htons(1234);
    server.sin_addr.s_addr = INADDR_ANY;
    if(bind(s,(struct sockaddr *)&server,sizeof(server))<0){
        printf("eroare la bind!");
        return -1;
    }
    listen(s,5);
    memset(&client,0,sizeof(client));
    l = sizeof(client);
    while(1){
        uint32_t lungime_sir_char;
        char caracter;
        c = accept(s,(struct sockaddr *)&client,&l);
        if( c < 0){
            printf("eroare conectare client \n");
            return -1;
        }
        printf("Conectat client!\n");

        recv(c,&lungime_sir_char,sizeof(lungime_sir_char),MSG_WAITALL);
        size_t lungime_sir = ntohl(lungime_sir_char);
        printf("Am primit: %d\n",lungime_sir);

        char* sir_char = (char*)malloc(lungime_sir);
        recv(c,sir_char,sizeof(char)*lungime_sir,MSG_WAITALL);
        printf("AM PRIMIT:%s\n",sir_char);

        recv(c,&caracter,sizeof(char),MSG_WAITALL);
        printf("Am primit char:%c\n",caracter);

        int pozitii[100];
        int nr_aparitii = 0;
        for(int i = 0 ; i < lungime_sir; i++){
            if(sir_char[i] == caracter){
                pozitii[nr_aparitii++] = i+1;
            }
        }

        uint32_t nr_ap_net = htonl(nr_aparitii);
        send(c,&nr_ap_net,sizeof(uint32_t),0);
        send(c,pozitii,sizeof(int)*nr_aparitii,0);

    }

}