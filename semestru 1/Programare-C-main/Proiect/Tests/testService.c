//
// Created by horatiu on 11/10/23.
//

#include "testService.h"


#include <string.h>
#include "testService.h"

#include <assert.h>



void testGetters() {
    struct transaction t = createTransaction(1,1, 2, 2023, 450, "income", "descriere");

    assert(get_id(&t) == 1);
    assert(get_day(&t) == 1);
    assert(get_month(&t) == 2);
    assert(get_year(&t) == 2023);
    assert(get_sum(&t) == 450);
    assert(strcmp(get_type(&t), "income") == 0);
    assert(strcmp(get_description(&t), "descriere") == 0);
}

void testSetters() {
    struct transaction t = createTransaction(1,1, 2, 2023, 450, "income", "descriere");

    set_day(&t, 2);
    set_month(&t, 3);
    set_year(&t, 2024);

    assert(get_day(&t) == 2);
    assert(get_month(&t) == 3);
    assert(get_year(&t) == 2024);

}


void testAccountBallance(){
    struct transaction transactions[2];
    for(int i= 0 ; i < 2; i++){
        struct transaction a = {0,25,0,0,0,"",""};
        transactions[i] = a  ;
    }
    addTransaction(transactions,25,4,2023,450,"INCOME","descriere");
    assert(checkAccoundBalance(transactions) == 450);
}

void testAddTransaction(){
    struct transaction transactions[2];
    for(int i= 0 ; i < 2; i++){
        struct transaction a = {0,25,0,0,0,"",""};
        transactions[i] = a  ;
    }
    assert(lengthTransactions(transactions) == 0);
    addTransaction(transactions,25,4,2023,450,"INCOME","descriere");
    assert(lengthTransactions(transactions) == 1);
    assert(transactions[0].id == 1);
}
void testAll(){
    testGetters();
    testSetters();
    testAddTransaction();
    testAccountBallance();
}