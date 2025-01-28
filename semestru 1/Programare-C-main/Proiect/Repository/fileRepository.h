//
// Created by horatiu on 11/10/23.
//

#ifndef PROIECT_FILEREPOSITORY_H
#define PROIECT_FILEREPOSITORY_H
#include "./transaction.h"

void loadFile(char* filename, struct transaction * transactions);
void saveFile(int len, struct transaction * transactions);
#endif //PROIECT_FILEREPOSITORY_H
