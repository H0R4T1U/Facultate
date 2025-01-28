//
// Created by horatiu on 11/10/23.
//

#include "./fileRepository.h"
#include "../Validator/validator.h"
#include "../Utility/Utility.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "accounts.h"
#include "../Services/crud.h"
#include "../Utility/dynamic_allocation.h"

int load_transaction_csv(struct transaction** a) {
    /*
     *LOADS csv data into transactions array
     */
    FILE* fptr = NULL;
    char line[1000];
    int len = 1;
    fptr = fopen("../Data/Transactions.csv","r");
    if(fptr == NULL) {
        fprintf(stderr,"FIle I/O Error");
    }
    fgets(line, 1000, fptr);
    while (fgets(line,1000,fptr)!= NULL)
    {
        char type[10],description[100];


        char* token = strtok(line, ",");
        int id = atoi(token);
        token = strtok(NULL, ",");
        int account_id = atoi(token);
        token = strtok(NULL, ",");
        int day = atoi(token);
        token = strtok(NULL, ",");
        int month = atoi(token);
        token = strtok(NULL, ",");
        int year = atoi(token);
        token = strtok(NULL, ",");
        int amount = atoi(token);
        token = strtok(NULL, ",");
        strcpy(type,token);
        token = strtok(NULL, "\n");
        strcpy(description,token);

        struct transaction transaction = createTransaction(id,account_id,day,month,year,amount,type,description);
        (*a)[len-1] = transaction;
        len = reallocTransaction(a,len);

    }
    return len;
}



void save_transaction_csv( struct transaction * transactions,int len){
    /*
     * This function saves the data from the transactions array into the file
     * Input: len - the length of the transactions array
     *      transactions - the array of transactions
     */
    FILE * fptr;
    fptr = fopen("../Data/Transactions.csv", "w");
    fprintf(fptr,"TRANSACTION ID,ACCOUNT ID,DAY,MONTH,YEAR,AMMOUNT,TYPE,DESCRIPTION");
    for(int i = 0; i < len; i++){
        int id = get_id(&transactions[i]);
        if(id != 0) {
            fprintf(fptr,"\n%d,",get_id(&transactions[i]));
            fprintf(fptr,"%d,",get_account(&transactions[i]));
            fprintf(fptr,"%d,",get_day(&transactions[i]));
            fprintf(fptr,"%d,",get_month(&transactions[i]));
            fprintf(fptr,"%d,",get_year(&transactions[i]));
            fprintf(fptr,"%d,",get_sum(&transactions[i]));
            fprintf(fptr,"%s,",get_type(&transactions[i]));
            fprintf(fptr,"%s",get_description(&transactions[i]));
            fprintf(fptr,"");
        }

    }
    fclose(fptr);
}



void save_account_csv(struct account * a,int len)
{
    /*
     * This function saves the data from the accounts array into the file
     * Input: len - the length of the transactions array
     *      a - the array of accounts
     */
    // file pointer
    FILE* fptr = NULL;
    char* name[20],passwd[15],type[10];
    int id = 0;

    // opens an existing csv file or creates a new file.
    fptr = fopen("../Data/Accounts.csv", "w");
    fprintf(fptr,"Id,Balance,Type,Name,Password");
    for(int i = 0; i < len; i++) {
        id = get_id_account(&a[i]);
        if(id != 0) {
            strcpy(type,get_type_account(&a[i]));
            strcpy(passwd,get_password(&a[i]));
            strcpy(name,get_name(&a[i]));
            encrypt(type,13);
            encrypt(name,13);
            fprintf(fptr,"\n%d,",id);
            fprintf(fptr,"%d,",get_balance(&a[i]));
            fprintf(fptr,"%s,",type);
            fprintf(fptr,"%s,",name);
            fprintf(fptr,"%s",passwd);
            fprintf(fptr,"");
        }
    }
    fclose(fptr);

}

int load_account_csv(struct account** a) {
    /*
     * LOADS csv data into accounts array
     * INPUT: a -> accounts array
     */
    FILE* fptr = NULL;
    char line[1000];
    int len = 1;
    fptr = fopen("../Data/Accounts.csv","r");
    if(fptr == NULL) {
        fprintf(stderr,"FIle I/O Error");
    }
    fgets(line, 1000, fptr);
    while (feof(fptr) != 1)
    {
        char type[10],name[20],passwd[15];

        fgets(line, 1000, fptr);

        char* token = strtok(line, ",");
        int id = atoi(token);
        token = strtok(NULL, ",");
        int amount = atoi(token);
        token = strtok(NULL, ",");
        strcpy(type,token);
        token = strtok(NULL, ",");
        strcpy(name,token);
        token = strtok(NULL, "\n");
        strcpy(passwd,token);

        decrypt(type,13);
        decrypt(name,13);


        struct account account = createAccount(id,amount,type,name,passwd);
        (*a)[len-1] = account;
        len = reallocAccount(a,len);

    }
    return len;
}


void export_data_csv(struct account* accounts, int len_accounts, struct transaction* transactions,int len_transactions,int session,char* file_ext) {
    /*
     *Exports account data in the specified file Format
     *Input: accounts : accounts array
     *       len_accounts : accounts array length
     *       transacstions: transactions array
     *       len_transactions: transactions array length
     *       session : instance session of the account
     *       file_ext : file extension for the file.
     *              The data is decrypted
    */
    FILE* fptr = NULL;
    int index = get_loc_by_id(accounts,len_accounts,session);
    struct account acc = accounts[index];
    char file_name[100] = "../Export/";
    strcat(file_name,get_name(&acc));
    strcat(file_name,file_ext);
    fptr = fopen(file_name,"w");
    // WRITE TRANSACTIONS TO FILE
    fprintf(fptr,"TRANSACTION ID,ACCOUNT ID,DAY,MONTH,YEAR,AMMOUNT,TYPE,DESCRIPTION");
    for(int i = 0 ; i < len_transactions; i++) {
        if(get_account(&transactions[i]) == session) {
            fprintf(fptr,"\n%d,",get_id(&transactions[i]));
            fprintf(fptr,"%d,",get_account(&transactions[i]));
            fprintf(fptr,"%d,",get_day(&transactions[i]));
            fprintf(fptr,"%d,",get_month(&transactions[i]));
            fprintf(fptr,"%d,",get_year(&transactions[i]));
            fprintf(fptr,"%d,",get_sum(&transactions[i]));
            fprintf(fptr,"%s,",get_type(&transactions[i]));
            fprintf(fptr,"%s",get_description(&transactions[i]));
            fprintf(fptr,"");
        }
    }
    //WRITE ACCOUNT BALANCE AND ACCOUNT TYPE

    fprintf(fptr,"\nACCOUNT TYPE AND BALANCE");
    char* type = get_type_account(&acc);
    char* name = get_name(&acc);
    int balance = checkAccoundBalance(transactions,len_transactions,session);

    fprintf(fptr,"\n%s Account, Owner %s, Balance %d",type,name,balance);
}