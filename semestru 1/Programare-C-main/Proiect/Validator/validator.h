//
// Created by horatiu on 11/7/23.
//

#ifndef PROIECT_VALIDATOR_H
#define PROIECT_VALIDATOR_H
#include "../Repository/transaction.h"
// validator function that validates transactaction data with a list of transactions
int validator(struct transaction t);
int validateDate(int day, int month, int year);
int validateDateInterval(int dayL, int monthL, int yearL, int dayR, int monthR, int yearR);
#endif //PROIECT_VALIDATOR_H
