//
// Created by horatiu on 11/7/23.
//


#include "transaction.h"
#include <string.h>

int get_id(struct transaction *t) {
    /*
     * Returns the id of a transaction
     * Input: t - pointer to a transaction
     * Output: the id of the transaction
     */
    return t->id;
}

int get_account(struct transaction* t) {
    return t->id_account;
}

int get_day(struct transaction* t) {
    /*
     * Returns the day of a transaction
     * Input: t - pointer to a transaction
     * Output: the day of the transaction
     */
    return t->day;
}


int get_month(struct transaction* t) {
    /*
     * Returns the month of a transaction
     * Input: t - pointer to a transaction
     * Output: the month of the transaction
     */
    return t->month;
}


int get_year(struct transaction *t) {
    /*
     * Returns the year of a transaction
     * Input: t - pointer to a transaction
     * Output: the year of the transaction
     */
    return t->year;
}


int get_sum(struct transaction* t) {
    /*
     * Returns the sum of a transaction
     * Input: t - pointer to a transaction
     * Output: the sum of the transaction
     */
    return t->sum;
}


char* get_type(struct transaction *t) {
    /*
     * Returns the type of a transaction
     * Input: t - pointer to a transaction
     * Output: the type of the transaction
     */
    return t->type;
}


char* get_description(struct transaction* t) {
    /*
     * Returns the description of a transaction
     * Input: t - pointer to a transaction
     * Output: the description of the transaction
     */
    return t->description;
}


void set_day(struct transaction* t, int day) {
    /*
     * Sets the day of a transaction
     *  Input: t - pointer to a transaction
     *       day - the day of the transaction
     */
    t->day = day;
}


void set_month(struct transaction* t, int month) {
    /*
     * Sets the month of a transaction
     * Input: t - pointer to a transaction
     *      month - the month of the transaction
     */
    t->month = month;
}


void set_year(struct transaction* t, int year) {
    /*
     * Sets the year of a transaction
     * Input: t - pointer to a transaction
     *     year - the year of the transaction
     */
    t->year = year;
}


void set_sum(struct transaction* t, int sum) {
    /*
     * Sets the sum of a transaction
     * Input: t - pointer to a transaction
     *     sum - the sum of the transaction
     */
    t->sum = sum;
}


void set_type(struct transaction* t, char* type) {
    /*
     * Sets the type of a transaction
     * Input: t - pointer to a transaction
     *    type - the type of the transaction
     */
    strncpy(t->type,type,10);


}


void set_description(struct transaction* t, char* description) {
    /*
     * Sets the description of a transaction
     * Input: t - pointer to a transaction
     * description - the description of the transaction
     *
     */
    strcpy(t->description,description);
}

struct transaction  createTransaction(int id,int id_account, int day, int month, int year, int sum, char type[], char description[]){
    /*
     * Creates a transaction
     * Input: id - the id of the transaction
     *      day - the day of the transaction
     *      month - the month of the transaction
     *      year - the year of the transaction
     *      sum - the sum of the transaction
     *      type - the type of the transaction
     *      description - the description of the transaction
     * Output: a transaction
     */
    struct transaction t;
    t.id = id;
    t.id_account = id_account;
    set_day(&t, day);
    set_month(&t, month);
    set_year(&t, year);
    set_sum(&t, sum);
    set_type(&t, type);
    set_description(&t, description);


    return t;
}

