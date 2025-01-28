#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <arpa/inet.h>
#include <stdlib.h>

int main() {
    int c;
    struct sockaddr_in server;
    // address family => AF_INNET = IPV4
    // SOCK_STREAM = TCP, SOCK_DIAGRAM = UDP
    c = socket(AF_INET, SOCK_STREAM, 0); // C = file Descriptor in C deci int
    if (c < 0) {
        printf("Error creating client socket\n");
        return 1;
    }
    
    memset(&server, 0, sizeof(server));
    server.sin_port = htons(12345); // port
    server.sin_family = AF_INET; // ipv
    server.sin_addr.s_addr = inet_addr("192.168.1.105");
    // connect 
    if (connect(c, (struct sockaddr *)&server, sizeof(server)) < 0) {
        printf("Error connecting to server\n");
        return 1;
    }

    char *a = NULL;
    char *b = NULL;
    size_t size_a = 0;
    size_t size_b = 0;

    printf("Enter string a: ");
    getline(&a, &size_a, stdin);

    printf("Enter string b: ");
    getline(&b, &size_b, stdin);

    size_a = strlen(a);
    if (a[size_a - 1] == '\n') {
        a[size_a - 1] = '\0';
        size_a--;  
    }
    // scapam de \n din ambele stringuri 
    size_b = strlen(b);
    if (b[size_b - 1] == '\n') {
        b[size_b - 1] = '\0';
        size_b--;  
    }

    // trecem din host in network byte order
    uint32_t net_size_a = htonl(size_a);
    uint32_t net_size_b = htonl(size_b);

    // trimitem pachetele astfel, prima oara size apoi sir
    send(c, &net_size_a, sizeof(net_size_a), 0);
    send(c, a, size_a, 0);  

    send(c, &net_size_b, sizeof(net_size_b), 0);
    send(c, b, size_b, 0);

	uint32_t length;
	recv(c,&length,sizeof(length),MSG_WAITALL);
	printf("Received Length of interclasare");
	length = ntohl(length);
	char *intercls = (char *)malloc(length);
	recv(c,intercls,sizeof(char)*length,MSG_WAITALL);
	printf("Received:%s \n",intercls);
    free(a);
    free(b);
	free(intercls);
    return 0;
}
