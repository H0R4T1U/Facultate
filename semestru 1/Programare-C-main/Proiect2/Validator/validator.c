//
// Created by horatiu on 11/7/23.
//
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "validator.h"

#include "../Repository/accounts.h"
#include "../Services/crud.h"


int validator(struct transaction t){
    //The Id is guaranteed to not exist because of the way it is generated!
    // check if day, month and year are valid
    //check if sum is valid, >0
    //check if type is correct (expense , income,pending)
    int day = get_day(&t);
    int month = get_month(&t);
    int year = get_year(&t);
    if(validateDate(day,month,year) == 0){
        printf("\033[1;31mInvalid date!\033[1;0m\n");
        return 0;
    }
    if(get_sum(&t) < 1){
        printf("\033[1;31mInvalid amount!\033[1;0m\n");
        return 0;
    }
    if(strcmp(get_type(&t), "INCOME") != 0 &&
        strcmp(get_type(&t),"EXPENSE") != 0 &&
        strcmp(get_type(&t),"PENDING") != 0 &&
        strcmp(get_type(&t),"REQUEST") != 0 &&
        strcmp(get_type(&t),"DENIED") != 0){

        printf("\033[1;31mInvalid type!\033[1;0m\n");
        return 0;
    }

    return 1;

}


int validateDate(int day, int month, int year) {
    /*
     * Checks if a date is valid
     * Input: day, month, year
     * Output: 1 if the date is valid, 0 otherwise
     */
    if (year < 1990) {
        printf("\033[1;31mInvalid Year!\033[1;0m\n");

        return 0;
    }
    if (month < 1 || month > 12) {
        printf("\033[1;31mInvalid Month!\033[1;0m\n");
        return 0;
    }
    if (day < 1 || day > 31) {
        printf("\033[1;31mInvalid Day!\033[1;0m\n");
        return 0;
    }
    if (month == 2) {
        if (year % 4 == 0) {
            if (day > 29) {
                printf("\033[1;31mInvalid Day for gap year!\033[1;0m\n");
                return 0;
            }
        } else {
            if (day > 28) {
                printf("\033[1;31mInvalid Day for non-gap year!\033[1;0m\n");
                return 0;
            }
        }
    }
    if (month == 4 || month == 6 || month == 9 || month == 11) {
        if (day > 30) {
            printf("I\033[1;31mnvalid Day for this month!\033[1;0m\n");
            return 0;
        }
    }
    return 1;
}


int validateDateInterval(int dayL, int monthL, int yearL, int dayR, int monthR, int yearR){
    /*
     * Checks if a date interval is valid
     * Input: dayL, monthL, yearL, dayR, monthR, yearR
     * Output: 1 if the date interval is valid, 0 otherwise
     */
    if(validateDate(dayL,monthL,yearL) == 0){
        printf("\033[1;31mInvalid Start date!\033[1;0m\n");
        return 0;
    }
    if(validateDate(dayR,monthR,yearR) == 0){
        printf("\033[1;31mInvalid End date!\033[1;0m\n");
        return 0;
    }
    if(yearL > yearR){
        printf("\033[1;31mInvalid Year Interval!\033[1;0m\n");
        return 0;
    }
    if(yearL == yearR && monthL > monthR){
        printf("\033[1;31mInvalid Month Interval!\033[1;0m\n");
        return 0;
    }
    if(yearL == yearR && monthL == monthR && dayL > dayR){
        printf("\033[1;31mInvalid Day Interval!\033[1;0m\n");
        return 0;
    }
    return 1;
}

int validate_new_account(struct account* accounts,int len_accounts,char* nume,char* type) {
    /* Validates a new account
     * Input: ACcounts - accounts array
     *        len_accounts: length of accounts array
     *        nume: name of the new account
     *        type: type of the new accounts
     */
    for(int i = 0; i < len_accounts; i++) {
        if(strcmp(get_name(&accounts[i]),nume)==0) {
            return 0;
        }
    }
    if(strcmp(type,"CREDIT") != 0 && strcmp(type,"DEBIT") != 0) {
        return -1;
    }
    return 1;
}
int validate_transfer(struct account* accounts, int len_accounts,struct transaction t) {
    /* Validates a new transfer, checks if the account has enough money, checks the input data
     * INPUT: Accounts - accounts array
     *        len_accounts - accounts array length
     *        t - transaction
     */
    if(validator(t) != 1) {
        return 0;
    }
    int id = get_account(&t);
    int index = get_loc_by_id(accounts,len_accounts,id);
    if(index == -1) {
        printf("\033[1;31mAccount doesn't Exist!\033[1;0m\n");
        return 0;
    }
    if(strcmp(get_type(&t),"EXPENSE") == 0 && strcmp(get_type_account(&accounts[index]),"DEBIT") == 0) {
        if(get_balance(&accounts[index]) < get_sum(&t)) {
            printf("\033[1;31mInsuficient Funds!\033[1;0m\n");
            return 0;
        }
    }
    for(int i = 0 ; i < len_accounts; i++) {
        if(get_id_account(&accounts[i]) == get_account(&t)) {
            return 1;
        }
    }
    return 0;
}