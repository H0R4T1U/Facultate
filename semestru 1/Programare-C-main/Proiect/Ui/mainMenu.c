//
// Created by horatiu on 11/7/23.
//

#include <stdio.h>
#include <ctype.h>
#include "mainMenu.h"
#include "../Views/transactionsView.h"
#include "../Repository/fileRepository.h"
#include "../Services/Reports.h"
#include "../Utility/Utility.h"


void financialReportMenu(struct transaction * transactions);
void printMainMenu(){
    printf("1. Add Transaction\n");
    printf("2. Transactions History\n");
    printf("3. Check Account Balance\n");
    printf("R. Financial Report\n");
    printf("S. Save to FILE\n");
    printf("q. Exit\n");
}


void addTransactionMenu(struct transaction * transactions) {
    // read id, day, month, year, sum, type, description
    // call addTransaction
    // print success or error

    int day, month, year, sum;
    char type[10];
    char description[100];

    printf("Enter day: ");
    scanf("%d", &day);


    printf("Enter month: ");
    scanf("%d", &month);

    printf("Enter year: ");
    scanf("%d", &year);

    printf("Enter sum: ");
    scanf("%d", &sum);

    printf("Enter type: ");
    scanf("%s" , type);

    getchar();

    printf("Enter description: ");
    fgets(description, 100, stdin);

    stringToUpper(type);
    stringToUpper(description);
    if(addTransaction(transactions, day, month, year, sum, type, description)){
        printf("Transaction added successfully\n");
    }else{
        printf("Transaction could not be added\n");
    }
}


void mainMenu(struct transaction * transactions) {
        // menu for adding transactions
        //  print transactions
        //  exit
    printMainMenu();
    char option = tolower(getchar());

    while (1) {

        if(option == '1') {
            addTransactionMenu(transactions);
            printMainMenu();
        }
        else if(option == '2') {
            printTransactions(transactions);
            printMainMenu();
        }else if(option == '3'){
            int balance = checkAccoundBalance(transactions);
            printAccountBalance(balance);
            printMainMenu();
        }else if(option == 'r'){
            financialReportMenu(transactions);
            printMainMenu();
        }else if (option == 's') {
            saveFile(lengthTransactions(transactions), transactions);
            printMainMenu();

        }else if (option == 'q') {
            break;
        }
        option = tolower(getchar());
    }
}




void financialReportMenu(struct transaction * transactions) {
    // read start day, start month, start year, end day, end month, end year
    // call financialReport
    int dayL, monthL, yearL, dayR, monthR, yearR;
    printf("Enter start day: ");
    scanf("%d", &dayL);
    printf("Enter Start Month : ");
    scanf("%d", &monthL);
    printf("Enter Start Year : ");
    scanf("%d", &yearL);
    printf("Enter End Day: ");
    scanf("%d", &dayR);
    printf("Enter End Month : ");
    scanf("%d", &monthR);
    printf("Enter End Year : ");
    scanf("%d", &yearR);
    financialReport(transactions, dayL, monthL, yearL, dayR, monthR, yearR);

}