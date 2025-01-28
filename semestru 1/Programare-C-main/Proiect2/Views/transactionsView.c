//
// Created by horatiu on 11/9/23.
//

#include "transactionsView.h"
#include "../Services/crud.h"
#include <stdio.h>
#include <string.h>


void printTransactions(struct transaction* transactions,int len,int session) {
    //Prints all transactions
    int nr = 0;
    printf("\033[1;31m\n%-18s%-25s%-17s%-19s%-10s\033[1;0m\n", "ID", "DATE","AMOUNT", "TYPE", "Description");
    printf("\n");


    for(int i = 0; i < len; i++){
        struct transaction t = transactions[i];
        int acc = get_account(&t);
        if(acc == session) {
            int id = get_id(&t);
            int day = get_day(&t);
            int month = get_month(&t);
            int year = get_year(&t);
            int suma = get_sum(&t);
            char* type = get_type(&t);
            if(strcmp(type,"REQUEST") == 0) {
                nr++;
            }
            char* Description = get_description(&t);
            char d[3] = {'0'+ (day/10),'0'+(day%10),'\0'}; // converts in format 03 for days smaller than 10
            char m[3] = {'0'+ (month/10),'0'+month%10,'\0'}; // Converts in format 03 for months smaller than 10

            printf("%-15d%-3s%-3s%-22d%-5d%-12s%-19s%-12s\n", id,d,m,year,suma," $",type, Description);
        }



    }
    if(nr) {
        printf("\033[1;33mYOU HAVE %d PENDING REQUESTS!\033[1;0m\n",nr);
    }

    char c[116];
    for(int i = 0 ; i < 117; i++)
        c[i] = '#';
    printf("%s \n", c);

}

void printAccountBalance(struct account* accounts,int len_accounts,int session,int sum){
    //Prints the account balance
    int index = get_loc_by_id(accounts,len_accounts,session);

    printf("\033[1;36m###### BALANCE ######\033[1;0m\n");
    printf("Welcome Back \033[1;35m%s\033[1;0m\n",get_name(&accounts[index]));
    printf("%s account\n",get_type_account(&accounts[index]));
    printf("Your account balance is:\033[1;32m %d $\033[1;0m\n", sum);
    printf("\033[1;36m#####################\033[1;0m\n");
}