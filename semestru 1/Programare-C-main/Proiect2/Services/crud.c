//
// Created by horatiu on 11/7/23.
//
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "./crud.h"

#include <ctype.h>


#include "../Validator/validator.h"

int addTransaction(struct transaction ** transactions,int id_account, int day, int month, int year, int sum, char * type, char * description,int len) {
    /*
     * This function adds a transaction to the transactions array
     * Input: transactions - the array of transactions
     *      day - the day of the transaction
     *      month - the month of the transaction
     *      year - the year of the transaction
     *      sum - the sum of the transaction
     *      type - the type of the transaction
     *      description - the description of the transaction
     */
    int id = len;

    struct transaction t = createTransaction(id,id_account, day, month, year, sum, type, description);

    if(validator(t)){
        (*transactions)[len-1] = t;
        reallocTransaction(transactions,len);
        return 1;

    }
    return 0;
}


int checkAccoundBalance(struct transaction * transactions,int len,int session){
    /*
     * This function returns the account balance
     * Input: transactions - the array of transactions
     *        len: length of the array of transactions
     *        session: instance session of the account
     * Output: sum - the account balance
     */
    int sum = 0;
    for(int i = 0; i < len; i++){
        if(strcmp(get_type(&transactions[i]), "INCOME")== 0 && get_account(&transactions[i]) == session){
            sum += get_sum(&transactions[i]);
        }else if(strcmp(get_type(&transactions[i]), "EXPENSE")== 0 && get_account(&transactions[i]) == session){
            sum -= get_sum(&transactions[i]);
        }
    }
    return sum;
}

int addTransfer(struct transaction ** transactions,int id_account, int day, int month, int year, int sum, char * type, char * description,int* len,struct account ** accounts,int len_account) {
    /*
  * This function adds a transfer to the transactions array
  * Input: transactions - the array of transactions
  *      id_account - id of the beneficiary
  *      day - the day of the transaction
  *      month - the month of the transaction
  *      year - the year of the transaction
  *      sum - the sum of the transaction
  *      type - the type of the transaction
  *      description - the description of the transaction
  *      accounts - accounts array
  *      len_accounts - length of accounts array
  */
    int id = *len;

    struct transaction t = createTransaction(id,id_account, day, month, year, sum, type, description);

    if(validate_transfer(*accounts,len_account,t)){
        int length = *len;
        (*transactions)[length - 1] = t;
        reallocTransaction(transactions,*len);
        save_transaction_csv(*transactions,*len);
        *len = length + 1;
        return 1;

    }
    return 0;
}
int get_loc_by_id(struct account* accounts, int len_accounts, int session) {
    /* Returns the position of the session account in the accounts array
     *INPUT:    accounts: accounts array
     *          len_accounts: length of accounts array
     *          session: instance session
     */
    for(int i = 0; i < len_accounts;i++) {
        if(get_id_account(&accounts[i]) == session) {
            return i;
        }
    }
    return -1;
}

void resolve_pending(struct account* accounts,int len_accounts,int session,struct transaction * transactions, int len_transactions) {
    /*  Finds and Resolves Pending Requests
     *  Input: Accounts: accounts array
     *         len_accounts: length of accounts array
     *         session: instance session
     *         transactions: transactions array
     *         len_Transactions: length of transactions array
     */
    int ind = get_loc_by_id(accounts,len_accounts,session);
    struct account account = accounts[ind];
    int balance = get_balance(&account);
    char* type = get_type_account(&account);

    for(int i = 0 ; i < len_transactions; i ++) {
        if( strcmp(get_type(&transactions[i]),"REQUEST") == 0 && get_account(&transactions[i]) == session) {
            int ind_beneficiar = get_loc_by_id(accounts,len_accounts,get_account(&transactions[i+1]));
            char* nume_beneficiar = get_name(&accounts[ind_beneficiar]);
            int suma = get_sum(&transactions[i]);
            char* details = get_description(&transactions[i]);
            printf("\033[1;33mRequest: %d $ from %s, %s\033[1;0m\n",suma,nume_beneficiar,details);
            printf("\033[1;32m1. Accept\033[1;0m\n");
            printf("\033[1;31m2. Reject\033[1;0m\n");
            char option = tolower(getchar());
            while(option != '1' && option != '2') {
                option = tolower(getchar());
            }
            if(option == '1' && (balance >= suma || strcmp(type,"CREDIT") == 0)) {
                balance -= suma;
                set_balance(&account,balance);
                set_type(&transactions[i],"EXPENSE");
                set_type(&transactions[i+1],"INCOME");

            }
            if(option == '2') {
                set_type(&transactions[i],"DENIED");
                set_type(&transactions[i+1],"DENIED");
            }

        }
    }
    save_transaction_csv(transactions,len_transactions-1);
}

