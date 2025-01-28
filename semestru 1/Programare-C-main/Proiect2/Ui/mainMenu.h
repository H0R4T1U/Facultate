//
// Created by horatiu on 11/7/23.
//

#ifndef PROIECT_MAINMENU_H
#define PROIECT_MAINMENU_H
#include "../Repository/transaction.h"
#include "../Services/crud.h"
#include "../Repository/accounts.h"

void mainMenu(struct account** accounts, int len_accounts, struct transaction  ** transactions,int len_transactions);
void addTransactionMenu(struct account** accounts, int len_accounts, struct transaction  ** transactions,int* len_transactions,int session);
void printMainMenu();
void intraTransactionUi(struct transaction  ** transactions,int* len_transactions,int session);
void login_menu(struct account** accounts, int* len_accounts, int* session);
void financialReportMenu(struct transaction * transactions,int len_transactions,int session);
void transferTransactionUI(struct account** accounts, int len_accounts, struct transaction  ** transactions,int *len_transactions,int session);
#endif //PROIECT_MAINMENU_H
