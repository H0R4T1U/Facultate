//
// Created by h on 1/7/24.
//

#ifndef ACCOUNTS_H
#define ACCOUNTS_H
#include "transaction.h"
struct  account {
    int id;
    int balance;
    char type[10];
    char name[20];
    char password[15];
};

int get_id_account(struct account * a);
int get_balance(struct account* a );
char* get_type_account(struct account * a);
char* get_name(struct account * a);
char* get_password(struct account * a);

void set_balance(struct account* a, int ammount);
void set_type_account(struct account  *a, char * type);
void set_name(struct account  *a, char *  name);
void set_password(struct account  *a, char * password);

struct account createAccount(int id,int balance, char* type, char* name,char* password);
#endif //ACCOUNTS_H
