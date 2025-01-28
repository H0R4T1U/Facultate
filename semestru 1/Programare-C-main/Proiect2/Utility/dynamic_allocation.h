//
// Created by h on 1/7/24.
//

#ifndef DYNAMIC_ALLOCATIO_H
#define DYNAMIC_ALLOCATIO_H
#include "../Repository/transaction.h"
#include "../Repository/accounts.h"
struct transaction* allocateTransaction();
int reallocTransaction(struct transaction** t, int n);
struct account* allocateAccount();
int  reallocAccount(struct account** a,int n);
#endif //DYNAMIC_ALLOCATIO_H
