//
// Created by horatiu on 11/9/23.
//

#include "transactionsView.h"
#include "../Services/crud.h"
#include <stdio.h>


void printTransactions(struct transaction* transactions) {
    //Prints all transactions

    printf("\n%-18s%-25s%-17s%-19s%-10s\n", "ID", "DATE","AMOUNT", "TYPE", "Description");
    printf("\n");

    int len = lengthTransactions(transactions);
    for(int i = 0; i < len; i++){
        struct transaction t = transactions[i];
        int id = get_id(&t);
        int day = get_day(&t);
        int month = get_month(&t);
        int year = get_year(&t);
        int suma = get_sum(&t);
        char* type = get_type(&t);
        char* Description = get_description(&t);
        char d[3] = {'0'+ (day/10),'0'+(day%10),'\0'}; // converts in format 03 for days smaller than 10
        char m[3] = {'0'+ (month/10),'0'+month%10,'\0'}; // Converts in format 03 for months smaller than 10

        printf("%-15d%-3s%-3s%-22d%-5d%-12s%-19s%-12s\n", id,d,m,year,suma," $",type, Description);


    }
    char c[116];
    for(int i = 0 ; i < 117; i++)
        c[i] = '#';
    printf("%s \n", c);

}

void printAccountBalance(int sum){
    //Prints the account balance
    printf("###### BALANCE ######\n");
    printf("Your account balance is: %d $\n", sum);
    printf("#####################\n");
}