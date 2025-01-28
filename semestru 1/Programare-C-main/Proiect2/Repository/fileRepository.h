//
// Created by horatiu on 11/10/23.
//

#ifndef PROIECT_FILEREPOSITORY_H
#define PROIECT_FILEREPOSITORY_H
#include "./transaction.h"
#include "./accounts.h"
#include "../Utility/encryption.h"
int load_transaction_csv(struct transaction ** transactions);
void save_transaction_csv(struct transaction * transactions,int len);
void save_account_csv(struct account * a,int len);
int load_account_csv(struct account** a);
void export_data_csv(struct account* accounts, int len_accounts, struct transaction* transactions,int len_transactions,int session,char* file_ext);
#endif //PROIECT_FILEREPOSITORY_H
