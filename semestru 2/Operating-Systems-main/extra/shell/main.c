#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>
#define TRUE 1
#define MAX_COMMAND_LEN 100
#define MAX_PARAMS_LEN 100
int main(){
    char command[MAX_COMMAND_LEN];
    char params[MAX_PARAMS_LEN];
    while(TRUE){
        printf("\n");
        printf("COMMAND:");
        fgets(command,MAX_COMMAND_LEN,stdin);
        printf("PARAMETERS:");
        fgets(params,MAX_PARAMS_LEN,stdin);
        printf("\n");
        command[strcspn(command, "\n")] = '\0';
        params[strcspn(params, "\n")] = '\0';
        if(fork() == 0){
            char *args[MAX_PARAMS_LEN];
            char *token;
            int i = 0;

            // Tokenizing params
            token = strtok(params, " ");
            while (token != NULL) {
                args[i++] = token;
                token = strtok(NULL, " ");
            }
            args[i] = NULL; // Null-terminate the array

            execvp(command, args);

            // execvp only returns if an error occurs
            perror("execvp failed");
            return 1;
        }
        waitpid(-1,NULL,0);   
    }
}
