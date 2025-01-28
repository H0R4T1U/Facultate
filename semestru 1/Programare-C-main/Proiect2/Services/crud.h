//
// Created by horatiu on 11/7/23.
//

#ifndef PROIECT_TESTCRUD_H
#define PROIECT_CRUD_H
#include "../Repository/transaction.h"
#include "../Utility/dynamic_allocation.h"
#include "../Repository/fileRepository.h"
int addTransaction(struct transaction ** transactions,int id_account, int day, int month, int year, int sum, char* type, char* description,int len_transactions);
int checkAccoundBalance(struct transaction * transactions,int len,int session);
int addTransfer(struct transaction ** transactions,int id_account, int day, int month, int year, int sum, char * type, char * description,int* len,struct account ** accounts,int len_account);
int get_loc_by_id(struct account* accounts, int len_accounts, int session);
int check_pending_requests(struct account* accounts,int len_accounts,int session, struct transaction * transactions,int len_transactions);
void resolve_pending(struct account* accounts,int len_accounts,int session,struct transaction * transactions, int len_transactions);
#endif //PROIECT_TESTCRUD_H
