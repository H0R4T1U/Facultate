#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <unistd.h>


struct packet {
    int i;
    int l;
    char buffer[100];
};

int main() {
    int c;
    struct sockaddr_in server;
    c = socket(AF_INET, SOCK_DGRAM, 0);
    if (c < 0) {
        printf("Error creating socket!\n");
        return -1;
    }
    memset(&server, 0, sizeof(server));
    server.sin_addr.s_addr = inet_addr("127.0.0.1");
    server.sin_port = htons(1234);
    server.sin_family = AF_INET;

    char* buffer = NULL;
    size_t inutil;
    int i = 0, l = 0;
    
    printf("sir: ");
    ssize_t lungime_sir = getline(&buffer, &inutil, stdin);
    if (lungime_sir > 0 && buffer[lungime_sir - 1] == '\n') {
        buffer[lungime_sir - 1] = '\0';  // Replace newline with null terminator
        lungime_sir--;
    }

    printf("i: ");
    scanf("%d", &i);
    printf("l: ");
    scanf("%d", &l);

    i = htonl(i);
    l = htonl(l);
    
    struct packet x;
    x.i = i;
    x.l = l;
    strncpy(x.buffer, buffer, sizeof(x.buffer) - 1);
    x.buffer[sizeof(x.buffer) - 1] = '\0';  // Ensure null termination

    for (int h = 0; h < 10; h++) {
        sendto(c, &x, sizeof(struct packet), 0, (struct sockaddr*)&server, sizeof(server));
    }

    char* subsir = (char*)malloc(ntohl(x.l) + 1);  // Allocate enough memory for the received string
    memset(subsir, 0, ntohl(x.l) + 1);
    socklen_t server_len = sizeof(server);

    recvfrom(c, subsir, ntohl(x.l), 0, (struct sockaddr*)&server, &server_len);
    printf("Received substring: %s\n", subsir);

    free(subsir);  // Free allocated memory
    free(buffer);  // Free getline() buffer
    close(c);  // Close the socket
    return 0;
}
