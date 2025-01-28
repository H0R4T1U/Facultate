//
// Created by horatiu on 11/10/23.
//

#ifndef PROIECT_REPORTS_H
#define PROIECT_REPORTS_H
#include "../Repository/transaction.h"

void financialReport(struct transaction * transactions,int len,int session, int dayL, int monthL,int yearL,int dayR,int monthR,int yearR);
#endif //PROIECT_REPORTS_H
