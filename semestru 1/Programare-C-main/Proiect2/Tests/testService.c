//
// Created by horatiu on 11/10/23.
//

#include "testService.h"


#include <string.h>
#include "testService.h"

#include <assert.h>
#include <stdlib.h>


void testGetters() {
    struct transaction t = createTransaction(1,1,1, 2, 2023, 450, "income", "descriere");

    assert(get_id(&t) == 1);
    assert(get_day(&t) == 1);
    assert(get_month(&t) == 2);
    assert(get_year(&t) == 2023);
    assert(get_sum(&t) == 450);
    assert(strcmp(get_type(&t), "income") == 0);
    assert(strcmp(get_description(&t), "descriere") == 0);
}

void testSetters() {
    struct transaction t = createTransaction(1,1,1, 2, 2023, 450, "income", "descriere");

    set_day(&t, 2);
    set_month(&t, 3);
    set_year(&t, 2024);

    assert(get_day(&t) == 2);
    assert(get_month(&t) == 3);
    assert(get_year(&t) == 2024);

}


void testAccountBallance(){
    struct transaction* transactions = allocateTransaction();
    int len = 1;
    addTransaction(&transactions,1,25,4,2023,450,"INCOME","descriere",len);
    assert(checkAccoundBalance(transactions,1,1) == 450);
    free(transactions);
}

void testAddTransaction(){
    struct transaction* transactions = allocateTransaction();
    int len_transaction = 1;
    addTransaction(&transactions,1,25,4,2023,450,"INCOME","descriere",len_transaction);
    assert(transactions[0].id == 1);
    free(transactions);
}
void testAll(){
    testGetters();
    testSetters();
    testAddTransaction();
    testAccountBallance();
}