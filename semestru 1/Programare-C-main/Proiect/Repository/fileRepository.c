//
// Created by horatiu on 11/10/23.
//

#include "fileRepository.h"
#include "../Validator/validator.h"
#include "../Utility/Utility.h"
#include <stdio.h>

void loadFile(char* filename, struct transaction * transactions){
    /*
     * This function loads the data from the file into the transactions array
     * Input: filename - the name of the file
     *       transactions - the array of transactions
     */
    FILE * fptr;
    int i = 0;
    char type[10];
    char* c;
    char description[100];
    int id = 0, day = 0,month=0,year = 0,sum = 0;
    fptr = fopen("./Repository/FinancialData.txt", "r");
    while(fscanf(fptr,"%d",&id) == 1){
        fscanf(fptr,"%d",&day);
        fscanf(fptr,"%d",&month);
        fscanf(fptr,"%d",&year);
        fscanf(fptr,"%d",&sum);
        fscanf(fptr,"%s ",type);
        fgets(description,100,fptr);

        stringToUpper(type);
        stringToUpper(description);
        struct transaction t = createTransaction(id, day, month, year, sum, type, description);
        if(validator(t)){
            transactions[i] = t;
            i++;
        }
    }
    fclose(fptr);

}


void saveFile(int len, struct transaction * transactions){
    /*
     * This function saves the data from the transactions array into the file
     * Input: len - the length of the transactions array
     *      transactions - the array of transactions
     */
    FILE * fptr;
    fptr = fopen("./Repository/FinancialData.txt", "w");
    for(int i = 0; i < len; i++){

        fprintf(fptr,"%d ",get_id(&transactions[i]));
        fprintf(fptr,"%d ",get_day(&transactions[i]));
        fprintf(fptr,"%d ",get_month(&transactions[i]));
        fprintf(fptr,"%d ",get_year(&transactions[i]));
        fprintf(fptr,"%d ",get_sum(&transactions[i]));
        fprintf(fptr,"%s ",get_type(&transactions[i]));
        fprintf(fptr,"%s\n",get_description(&transactions[i]));
    }
    fclose(fptr);
}