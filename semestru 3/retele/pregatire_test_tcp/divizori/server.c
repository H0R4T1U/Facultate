#include <sys/socket.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

int main(int argc, char* argv[]){
    int s, c, l;
    if ((s = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("Error obtaining socket");
        return -1;
    }
    struct sockaddr_in server, client;
    memset(&server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_port = htons(atoi(argv[1]));
    server.sin_addr.s_addr = INADDR_ANY;

    if (bind(s, (struct sockaddr *)&server, sizeof(server)) < 0) {
        perror("Error in bind");
        return -1;
    }

    listen(s, 5);
    l = sizeof(client);

    while(1) {
        uint32_t a;
        c = accept(s, (struct sockaddr *)&client, &l);
        if (c < 0) {
            perror("Error accepting connection");
            continue;
        }
        printf("Client connected.\n");

        recv(c, &a, sizeof(a), MSG_WAITALL);
        a = ntohl(a);  // Assign the result of ntohl back to `a`
        printf("Received number: %d\n", a);

        int divizori[100];
        int nr_divizori = 0;
        for(int i = 1; i <=a; i++){
            if(a%i == 0){
                divizori[nr_divizori++] = i;
            }
        }
        uint32_t nr_div = htonl(nr_divizori);
        send(c,&nr_div,sizeof(nr_div),0);
        send(c,divizori,sizeof(divizori),0);

        close(c);  // Close the client socket
    }

    close(s);  // Close the server socket
    return 0;
}
