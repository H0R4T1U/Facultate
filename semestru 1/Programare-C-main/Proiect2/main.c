#include <stdio.h>
#include <stdlib.h>
#include "./Repository/transaction.h"
#include "./Repository/fileRepository.h"
#include "./Ui/mainMenu.h"
#include "./Tests/testService.h"
#include "Utility/dynamic_allocation.h"

int main() {

    testAll();

    int len_transactions = 1;
    int len_accounts = 1;
    struct transaction* transactions = allocateTransaction();
    struct account* accounts = allocateAccount();


    len_accounts = load_account_csv(&accounts);
    len_transactions = load_transaction_csv(&transactions);
    mainMenu(&accounts,len_accounts,&transactions,len_transactions);

    free(transactions);
    free(accounts);
    return 0;
}
