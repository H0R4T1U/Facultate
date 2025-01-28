// IMPLEMENTEAZA
/*
 * ACASA CAUTA DINTR O IERARHIE DE FISIERE
 *  SECVEBTIAL SI RECURSIV
 *  WORKER THREADS
 *  GASESTI CUVINTELE DINTR UN FISIER
 *  coada
*/
#include <dirent.h>
#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#define READ_SIZE 4096
char buffer[4096];

struct queue {
	struct queue* next;
	char data[100];
};
struct queue* files = NULL;
struct queue* current = NULL;
void addToQueue(char* file){
	struct queue* add = malloc(sizeof(struct queue));
	strcpy(add->data,file);
    add->next = NULL;

	if(files == NULL){

		files = add;

	}else{
        current = files;
        while(current->next != NULL){
            current = current->next;
        }
        current->next = add;
    }
}
void takeFromQueue(char* file){
	strcat(file,current->data);
	current = current->next;
}
int secvential_read(char* file, char* phrase){
	int count = 0;
	int fd = open(file,O_RDONLY);
	if (fd == -1) {
        	perror("Error opening file");
        	exit(EXIT_FAILURE);
    	}
    	ssize_t bytes_read;        
	while ((bytes_read = read(fd, buffer, READ_SIZE)) > 0) {
        	buffer[bytes_read] = '\0'; // Null-terminate buffer
        	char *token = strtok(buffer, " \t\n"); // Split by space, tab, newline
        	while (token != NULL) {
            		if(strcmp(token,phrase) == 0){
				count +=1;
			}
			token = strtok(NULL, " \t\n"); // Get next word
        	}
    	}

    	if (bytes_read == -1) {
        	perror("Error reading file");
        	exit(EXIT_FAILURE);
   	 }
	close(fd);
	return count;
}
void get_files(char* PATH ){
	DIR* d;
	struct dirent* dir;
	d = opendir(PATH);
	if(d){
		while((dir = readdir(d)) != NULL){
			if(dir->d_type == DT_REG){
				addToQueue(dir->d_name);		
			}
		}

		closedir(d);
	}

}
int main(int argc, char** argv){
	if(argc != 3){
		printf("Usage: ./main $file_path $phrase");
		exit(1);
	}
    int count = 0;
    char file[100];
    get_files(argv[1]);
    current = files;
    while(current != NULL){
        strcat(file,argv[1]);
        takeFromQueue(file);
        count += secvential_read(file,argv[2]);
        strcpy(file,"");
    }
    printf("Word Count:%d\n",count);
    return 0;
}	
