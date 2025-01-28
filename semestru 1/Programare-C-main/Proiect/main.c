#include <stdio.h>
#include <stdlib.h>
#include "./Repository/transaction.h"
#include "./Repository/fileRepository.h"
#include "./Ui/mainMenu.h"
#include "Tests/testService.h"

int main() {
    testAll();
    struct transaction transactions[100];
    for(int i= 0 ; i < 100; i++){
        struct transaction a = {0,0,0,0,0,"",""};
        transactions[i] = a  ;
    }

    loadFile("FinancialData.txt", transactions);
    mainMenu(transactions);
    return 0;
}
