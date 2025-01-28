//
// Created by mepho on 11/9/23.
//

#ifndef PROIECT_TRANSACTIONSVIEW_H
#define PROIECT_TRANSACTIONSVIEW_H
#include "../Repository/transaction.h"
#include "../Repository/accounts.h"
void printTransactions(struct transaction* transactions,int len,int session);
void printAccountBalance(struct account* accounts,int len_accounts,int session,int sum);
#endif //PROIECT_TRANSACTIONSVIEW_H
