#include <stdio.h>

int main() {
    int a;
    printf("ENTER A YEAR:");
    scanf("%d",&a);
    if(a < 10 && a > 0){
        printf("The year does not have 2 digits!");
    }else if( a < 0) {
        printf("The year is invalid!");
    }else{
        printf("The last 2 digits of the year %d are %d",a,a%100);
    }

    return 0;
}
