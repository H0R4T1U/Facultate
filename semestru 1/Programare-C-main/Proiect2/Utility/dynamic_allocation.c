//
// Created by h on 1/7/24.
//

#include "dynamic_allocation.h"

#include <stdio.h>
#include <stdlib.h>

struct account* allocateAccount() {
    //alocates account array
    struct account* a =  (struct account*)malloc(1*sizeof(struct account));
    return a;

}

struct transaction* allocateTransaction() {
    //alocates transaction array
    return (struct transaction*)malloc(1*sizeof(struct transaction));
}

int reallocAccount(struct account** a,int n) {
    //reallocates accounts array by 1
    n++;
    *a = (struct account*)realloc(*a,n*sizeof(struct account));
    if(*a == NULL) {
        fprintf(stderr,"Can't allocate more memory!");
        return 0;
    }
    return n;

}
int reallocTransaction(struct transaction** t, int n) {
    //reallocates transactions array by 1
    n++;
    *t = (struct transaction*)realloc(*t,n*sizeof(struct transaction));
    if(*t == NULL) {
        printf("Can't allocate more memory!");
        return 0;

    }
    return n;

}
