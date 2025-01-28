#include <stdio.h>

int main(){
	int p,r,t;
	printf("Principal:");
	scanf("%d",&p);
	printf("\nRate:");
	scanf("%d",&r);
	printf("Time:");
	scanf("%d",&t);
	int simpleI = (p*r*t)/100;
	printf("\n Simple Interest = %d",simpleI); 
	return 0;
}
