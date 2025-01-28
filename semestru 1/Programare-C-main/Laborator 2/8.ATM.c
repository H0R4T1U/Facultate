#include <stdio.h>

int main() {
    int a;
    printf("ATM");
    printf("Please enter your PIN CODE:");
    scanf("%d",&a);
    if(a < 1000 || a > 9999){
        printf("Pin Invalid");
    }else{
        printf("Utilizator logat");
    }

    return 0;
}
