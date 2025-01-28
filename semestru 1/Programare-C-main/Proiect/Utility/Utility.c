//
// Created by horatiu on 11/10/23.
//

#include <string.h>
#include <ctype.h>
#include "Utility.h"


void stringToUpper(char* str){
    //Converts a string to uppercase
    for(int i = 0; i < strlen(str); i++){
        str[i] = toupper(str[i]);
    }
}