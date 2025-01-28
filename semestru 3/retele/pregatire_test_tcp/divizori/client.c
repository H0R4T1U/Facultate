#include <sys/socket.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <unistd.h>

int main(int argc, char* argv[]){
    int c;
    if ((c = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("Socket creation error");
        return -1;
    }
    struct sockaddr_in server;
    server.sin_family = AF_INET;
    server.sin_port = htons(atoi(argv[1]));
    server.sin_addr.s_addr = inet_addr("127.0.0.1"); // Use "127.0.0.1" instead of "localhost"

    if(connect(c, (struct sockaddr *)&server, sizeof(server)) < 0){
        perror("Error connecting to server");
        return -1;
    }

    uint32_t nr;
    printf("Nr: ");
    scanf("%d", &nr);
    nr = htonl(nr);

    send(c, &nr, sizeof(nr), 0);
    uint32_t nr_div;
    recv(c,&nr_div,sizeof(uint32_t),MSG_WAITALL);
    nr_div = ntohl(nr_div);
    int* divizori = (int*)malloc(sizeof(nr_div));
    recv(c,divizori,sizeof(int)*nr_div,MSG_WAITALL);
    for(int i = 0; i <nr_div;i++){
        printf("%d,",divizori[i]);
    }
    close(c); // Close the socket
    return 0;
}
