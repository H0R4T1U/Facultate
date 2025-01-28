
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h> 
#include <arpa/inet.h>
 #include <netdb.h>
int main() {
  int c;
  struct sockaddr_in server;
  uint16_t a, b, suma;
  struct addrinfo hints, *res;
  memset(&hints, 0, sizeof hints);

  hints.ai_family =AF_UNSPEC;
  hints.ai_socktype = SOCK_STREAM;
  int status;

  const char *hostname = "linux.scs.ubbcluj.ro";
  if ((status = getaddrinfo(hostname, "53476", &hints, &res)) != 0) {
        return 1;
    }
  c = socket(AF_INET, SOCK_STREAM, 0);
  if (c < 0) {
    printf("Eroare la crearea socketului client\n");
    return 1;
  }
  struct sockaddr_in *addr = (struct sockaddr_in *)res->ai_addr;

  memset(&server, 0, sizeof(server));
  server.sin_port = htons(53476);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = addr->sin_addr.s_addr;
  if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0) {
    printf("Eroare la conectarea la server\n");
    return 1;
  }
  printf("Conectat la %s prin %s \n",hostname,inet_ntoa(addr->sin_addr));
  printf("a = ");
  scanf("%hu", &a);
  printf("b = ");
  scanf("%hu", &b);
  a = htons(a);
  b = htons(b);
  send(c, &a, sizeof(a), 0);
  send(c, &b, sizeof(b), 0);
  recv(c, &suma, sizeof(suma), 0);
  suma = ntohs(suma);
  printf("Suma este %hu\n", suma);
  close(c);
  freeaddrinfo(res); 

}
