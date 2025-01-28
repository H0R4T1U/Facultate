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
    int s;
    struct sockaddr_in server, client;
    s = socket(AF_INET, SOCK_DGRAM, 0);
    if (s < 0) {
        printf("Error creating socket!\n");
        return -1;
    }
    memset(&server, 0, sizeof(server));
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_family = AF_INET;
    server.sin_port = htons(1234);

    if (bind(s, (struct sockaddr*)&server, sizeof(server)) < 0) {
        printf("Error binding socket!\n");
        return -1;
    }

    socklen_t client_len = sizeof(client);

    while (1) {
        struct packet x;
        recvfrom(s, &x, sizeof(struct packet), 0, (struct sockaddr*)&client, &client_len);

        printf("Received union packet\n");

        x.i = ntohl(x.i);
        x.l = ntohl(x.l);
        printf("%d,%d,%s",x.i,x.l,x.buffer);
        if (x.i >= 0 && x.i+1 <= strlen(x.buffer)) {
            char* subsir = (char*)malloc(x.l + 1);
            strncpy(subsir, x.buffer + x.i, x.l);
            subsir[x.l] = '\0';  // Null-terminate the substring

            for (int i = 0; i < 10; i++) {
                sendto(s, subsir, x.l + 1, 0, (struct sockaddr*)&client, client_len);
            }

            free(subsir);  // Free allocated memory
        } else {
            printf("Invalid substring bounds.\n");
        }
    }

    close(s);  // Close the socket
    return 0;
}
