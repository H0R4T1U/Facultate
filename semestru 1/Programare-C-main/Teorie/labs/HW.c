#include <stdio.h>

int main() {
   /* my first program in C */
   int a=5;
   int b=6;
   int c=a+b;
   int d; 
   
   float nr=4.4;
  
   printf("%d\n",c+b);
   
   printf("%f\n",nr+a);

   printf("%d\n", c%a);

   d=c+a;
   
 
    printf("%d\n", d);
 


   a=d;
    printf("%d\n", a);
   c=a=b;
    printf("%d\n", a);


    c++;
     printf(" rezultatul este:  %d \n", c);
    c-=a; 
     printf("%d\n", c);
       
   return 0;
}
