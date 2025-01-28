#include <stdio.h>

int main() {
    int a,ai,b,bi;
    printf("Insert The real part of a:");
    scanf("%d",&a);
    printf("Insert The complex part of a:");
    scanf("%d",&ai);
    printf("Insert The real part of b:");
    scanf("%d",&b);
    printf("Insert The complex part of b:");
    scanf("%d",&bi);
    a = a+b;
    ai = ai+b;

    printf("The new number is %d+%di",a,ai);
    return 0;
}
