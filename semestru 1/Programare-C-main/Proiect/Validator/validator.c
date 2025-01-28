//
// Created by horatiu on 11/7/23.
//
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "validator.h"



int validator(struct transaction t){
    //The Id is guaranteed to not exist because of the way it is generated!
    // check if day, month and year are valid
    //check if sum is valid, >0
    //check if type is correct (expense or income)
    int day = get_day(&t);
    int month = get_month(&t);
    int year = get_year(&t);
    if(validateDate(day,month,year) == 0){
        printf("Invalid date!\n");
        return 0;
    }
    if(get_sum(&t) < 1){
        printf("Invalid amount!\n");
        return 0;
    }
    if(strcmp(get_type(&t), "INCOME") != 0 && strcmp(get_type(&t),"EXPENSE") != 0){

        printf("Invalid type!\n");
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
        printf("Invalid Year!\n");

        return 0;
    }
    if (month < 1 || month > 12) {
        printf("Invalid Month!\n");
        return 0;
    }
    if (day < 1 || day > 31) {
        printf("Invalid Day!\n");
        return 0;
    }
    if (month == 2) {
        if (year % 4 == 0) {
            if (day > 29) {
                printf("Invalid Day for gap year!\n");
                return 0;
            }
        } else {
            if (day > 28) {
                printf("Invalid Day for non-gap year!\n");
                return 0;
            }
        }
    }
    if (month == 4 || month == 6 || month == 9 || month == 11) {
        if (day > 30) {
            printf("Invalid Day for this month!\n");
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
        printf("Invalid Start date!\n");
        return 0;
    }
    if(validateDate(dayR,monthR,yearR) == 0){
        printf("Invalid End date!\n");
        return 0;
    }
    if(yearL > yearR){
        printf("Invalid Year Interval!\n");
        return 0;
    }
    if(yearL == yearR && monthL > monthR){
        printf("Invalid Month Interval!\n");
        return 0;
    }
    if(yearL == yearR && monthL == monthR && dayL > dayR){
        printf("Invalid Day Interval!\n");
        return 0;
    }
    return 1;
}