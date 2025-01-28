#include <stdio.h>

int main() {
    int d;
    scanf("%d",&d);
    if(d % 4 == 0){
        printf("This year is a leap year ");
    }else{
        printf("This year isn't a leap year");
    }
    return 0;
}
