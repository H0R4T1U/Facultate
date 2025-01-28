//
// Created by h on 1/7/24.
//

#include "encryption.h"

#include <string.h>

void encrypt(char* word, int key) {
    // encryption function using rot13
    // can be changed to a more complex encryption
    for (int n = 0, len = strlen(word); n < len; n++) {
        char currentLetter = word[n];

        // Check if the letter is lowercase
        if (currentLetter >= 'a' && currentLetter <= 'z') {
            word[n] = ((currentLetter - 'a' + key) % 26) + 'a';
        }
        // Check if the letter is uppercase
        else if (currentLetter >= 'A' && currentLetter <= 'Z') {
            word[n] = ((currentLetter - 'A' + key) % 26) + 'A';
        }
    }
}

void decrypt(char* word,int key) {
    //decryption function for rot13
    for (int n = 0, len = strlen(word); n < len; n++) {
        char currentLetter = word[n];

        // Check if the letter is lowercase
        if (currentLetter >= 'a' && currentLetter <= 'z') {
            word[n] = ((currentLetter - 'a' + key) % 26) + 'a';
        }
        // Check if the letter is uppercase
        else if (currentLetter >= 'A' && currentLetter <= 'Z') {
            word[n] = ((currentLetter - 'A' + key) % 26) + 'A';
        }
    }
}