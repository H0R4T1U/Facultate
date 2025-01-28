//
// Created by h on 1/7/24.
//

#include "Login.h"

#include <stdio.h>
#include <string.h>

#include "../Utility/Utility.h"


int check_name(char* nume,struct account* accounts,int len_accounts);
int check_login(char* nume,char* passwd,struct account* accounts,int len_accounts);

int login(struct account* accounts,int len_accounts) {
    //Login Function
    char nume[20];
    char passwd[15];
    printf("username:");
    scanf("%20s",nume);
    printf("Password:");
    scanf("%15s",passwd);
    int id = 0;
    id = check_login(nume,passwd,accounts,len_accounts);
    if(!id) {
        printf("\033[1;31mIncorect username/password Combination!\nIf you don't have an Account try signing-up!\033[0m\n");
        return 0;
    }
    return id;
}

int sign_up(struct account** accounts, int len_accounts) {
    /* Creates an Account
     * INPUT: Accounts : accounts array
     *        len_accounts : accounts array length
     */
    char nume[20];
    char passwd[15];
    char type[10];

    printf("username:");
    scanf("%20s",nume);

    printf("Password:");
    scanf("%15s",passwd);
    encrypt(passwd,13);
    printf("Account type(credit,debit):");
    scanf("%10s",type);
    stringToUpper(type);
    int status = validate_new_account(*accounts,len_accounts,nume,type);
    if(status == 0) {
        printf("\033[1;31mAccount Already Exist!\033[0m\n");
        return 0;
    }
    if(status == -1) {
        printf("\033[1;31mType Can Only by CREDIT or DEBIT\033[0m\n");
        return 0;
    }

    (*accounts)[len_accounts-1] = createAccount(len_accounts,0,type,nume,passwd);
    reallocAccount(accounts,len_accounts);
    return 1;

}

int check_login(char* nume,char* passwd,struct account* accounts,int len_accounts) {
    /* Validates Login
     * Input: nume : name of the account
     *        passwd: passowrd of the account
     *        accounts : accounts array
     *        len_accounts : accounts array length
     *        Password verification is made encrypting the input passwd and comparing it with the stored encrypted string
     */
    encrypt(passwd,13);
    for(int i = 0; i < len_accounts-1; i++) {
        if( strcmp( get_name(&accounts[i]),nume ) == 0) {
            if(strcmp(get_password(&accounts[i]),passwd) == 0) {
                return get_id_account(&accounts[i]);
            }else {
                return 0;
            }
        }
    }
    return 0;
}