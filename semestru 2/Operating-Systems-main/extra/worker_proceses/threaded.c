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

pthread_mutex_t m;
char path[100], phrase[100];
int count = 0;

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
    pthread_mutex_lock(&m);

    strcat(file,path);
    strcat(file,current->data);
    struct queue* temp = current;
    current = current->next;
    pthread_mutex_unlock(&m);
    free(temp);


}
void* threaded_read(){
    char file[100];
    char buffer[READ_SIZE + 1];
    int local_count = 0;
    while(current != NULL){

        takeFromQueue(file);

        int fd = open(file,O_RDONLY);

        if (fd == -1) {
            perror("Error opening file");
            exit(EXIT_FAILURE);
        }
        ssize_t bytes_read;
        while ((bytes_read = read(fd, buffer, READ_SIZE)) > 0) {
            buffer[bytes_read] = '\0'; // Null-terminate buffer
            char* saveptr;
            char *token = strtok_r(buffer, " \t\n",&saveptr); // Split by space, tab, newline
            while (token != NULL) {
                if(strcmp(token,phrase) == 0){
                    local_count += 1;
                }
                token = strtok_r(NULL, " \t\n",&saveptr); // Get next word
            }
        }

        if (bytes_read == -1) {
            perror("Error reading file");
            exit(EXIT_FAILURE);
        }
        close(fd);
        memset(file, 0, sizeof(file));
    }
    pthread_mutex_lock(&m);
    count += local_count;
    pthread_mutex_unlock(&m);
    return NULL;
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
    pthread_t t[16];

    strcpy(path,argv[1]);
    strcpy(phrase,argv[2]);

    pthread_mutex_init(&m,NULL);

    get_files(argv[1]);

    current = files;
    for(int i=0; i<16; i++) {
        pthread_create(&t[i], NULL, threaded_read, NULL);
    }
    for(int i=0; i<16; i++) {
        pthread_join(t[i], NULL);
    }
    pthread_mutex_destroy(&m);
    printf("Word Count:%d\n",count);
    return 0;
}
