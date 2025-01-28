#include <stdio.h>

int main(int argc, char** argv) {
 int n;
 char u[64];
 FILE* fp;
 fp = popen("ps -ef | awk '{print $1}' | sort | uniq -c | sort -nr | head -n 1", "r");
 fscanf(fp, "%d %s", &n, u);
 pclose(fp);
 printf("%s: %d\n", u, n);
 return 0;
}
