#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <arpa/inet.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>

void interclasare(char lista1[], int marime1, char lista2[], int marime2, char rezultat[]) {
    int i = 0, j = 0, k = 0;

    while (i < marime1 && j < marime2) {
        if (lista1[i] < lista2[j]) {
            rezultat[k++] = lista1[i++];
        } else {
            rezultat[k++] = lista2[j++];
        }
    }

    while (i < marime1) {
        rezultat[k++] = lista1[i++];
    }

    while (j < marime2) {
        rezultat[k++] = lista2[j++];
    }
    rezultat[k] = '\0';
}
void serve(int c){

        uint32_t net_size_a, net_size_b;
        size_t size_a, size_b;

        recv(c, &net_size_a, sizeof(net_size_a), MSG_WAITALL);
        size_a = ntohl(net_size_a);
        char *a = (char *)malloc(size_a + 1); 
        recv(c, a, size_a, MSG_WAITALL); 
        a[size_a] = '\0'; // adauga, null terminator 
        printf("Received string a: %s\n", a);

        
        recv(c, &net_size_b, sizeof(net_size_b), MSG_WAITALL);
        size_b = ntohl(net_size_b);
        char *b = (char *)malloc(size_b + 1);
        recv(c, b, size_b, MSG_WAITALL);
        b[size_b] = '\0'; 
        printf("Received string b: %s\n", b);

        char* rezultat = (char *)malloc(size_a + size_b + 1);
        interclasare(a, strlen(a), b, strlen(b), rezultat);
		printf("Rezultat pe server:%s",rezultat);
		uint32_t length = htonl(strlen(rezultat));
		printf("Sending length");

    	send(c, &length, sizeof(length), 0);
   		send(c, rezultat, length, 0); 

        free(a);
        free(b);
        free(rezultat); 

}
int main(int argc, char* argv[]) {
    int s, c, l;
    struct sockaddr_in server, client;

    s = socket(AF_INET, SOCK_STREAM, 0);
    if (s < 0) {
        printf("Error creating server socket\n");
        return 1;
    } // CREAM SERVER IPV4

    memset(&server, 0, sizeof(server));
    server.sin_port = htons(atoi(argv[1]));
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    // FACEM  BIND DE SOCKET
    if (bind(s, (struct sockaddr *)&server, sizeof(server)) < 0) {
        printf("Error binding socket\n");
        return 1;
    }
    // ascultam pe socketul bind uit
    listen(s, 5);
    l = sizeof(client);

    while (1) {
        // conecteaza erver
        // ce e socklen_t? C way of adding names to int,
        // reprez lungimea adresei pe are o primeste serveru
        // yack
        c = accept(s, (struct sockaddr *)&client,(socklen_t*)&l);
        if (c < 0) {
            printf("Error accepting connection\n");
            return 1;
        }
        printf("A client connected.\n");
        if(fork() == 0){
            serve(c);
        }
    }

    return 0;
}
