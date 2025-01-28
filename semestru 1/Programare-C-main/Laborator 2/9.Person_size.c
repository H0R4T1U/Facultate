#include <stdio.h>

int main() {
    int height;
    printf("Please enter your height(cm):");
    scanf("%d",&height);
    if(height <= 147){
        printf("The person is a dwarf");
    }else if(height <= 180){
        printf("The person is an Average Person");
    }else{
        printf("The person is tall");
    }

    return 0;
}
