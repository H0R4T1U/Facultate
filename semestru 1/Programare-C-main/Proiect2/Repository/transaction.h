//
// Created by horatiu on 11/7/23.
//

#ifndef PROIECT_TRANSACTION_H
#define PROIECT_TRANSACTION_H

struct  transaction {
    int id;
    int id_account;
    int day;
    int month;
    int year;
    int sum;
    char type[10];
    char description[100];
};
int get_id(struct transaction * t);
int get_account(struct transaction* t);
int get_day(struct transaction * t);
int get_month(struct transaction * t);
int get_year(struct transaction * t);
int get_sum(struct transaction * t);
char* get_type(struct transaction * t);
char* get_description(struct transaction * t);


void set_day(struct transaction * t, int day);
void set_month(struct transaction  *t, int month);
void set_year(struct transaction  *t, int year);
void set_sum(struct transaction  *t, int sum);
void set_type(struct transaction  *t, char * type);
void set_description(struct transaction* t, char * description);

struct transaction createTransaction(int id,int id_account, int day, int month, int year, int sum, char* type, char* description);
#endif //PROIECT_TRANSACTION_H
