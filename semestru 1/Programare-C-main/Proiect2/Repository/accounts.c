//
// Created by h on 1/7/24.
//

#include "accounts.h"
#include <string.h>

int get_id_account(struct account* a) {
    /*
     * Returns the id of a account
     * Input: a - pointer to a account
     * Output: the id of the account
     */
    return a->id;
}

int get_balance(struct account* a) {
    return a->balance;
}

char* get_type_account(struct account *a) {
    /*
     * Returns the type of a account
     * Input: a - pointer to a account
     * Output: the type of the account
     */
    return a->type;
}


char* get_name(struct account* a) {
    /*
     * Returns the description of a account
     * Input: a - pointer to a account
     * Output: the description of the account
     */
    return a->name;
}
char* get_password(struct account* a) {
    /*
     * Returns the description of a account
     * Input: a - pointer to a account
     * Output: the description of the account
     */
    return a->password;
}


void set_balance(struct account* a,int amount) {
    a->balance = amount;
}

void set_type_account(struct account* a, char* type) {
    /*
     * Sets the type of a account
     * Input: a - pointer to a account
     *    type - the type of the account
     */
    strncpy(a->type,type,10);


}


void set_name(struct account* a, char* name) {
    /*
     * Sets the description of a account
     * Input: a - pointer to a account
     * description - the description of the account
     *
     */
    strncpy(a->name,name,20);
}
void set_password(struct account* a, char* password) {
    /*
     * Sets the description of a account
     * Input: a - pointer to a account
     * description - the description of the account
     *
     */
    strncpy(a->password,password,15);
}
struct account createAccount(int id,int balance,char* type, char* name,char* password){
    /*
     * Creates a account
     * Input: id - the id of the account
     *      day - the day of the account
     *      month - the month of the account
     *      year - the year of the account
     *      sum - the sum of the account
     *      type - the type of the account
     *      description - the description of the account
     * Output: a account
     */
    struct account a;
    a.id = id;
    set_balance(&a,balance);
    set_type_account(&a, type);
    set_name(&a, name);
    set_password(&a, password);


    return a;
}

