#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <string.h>
#include <unistd.h>

int main(){
	int a2b[2];
	pipe(a2b);
	if(fork() == 0){
		close(a2b[1]);
		char ch[1];
		int len = 0;
		while(1){
			char word[20];
			if(read(a2b[0],ch,sizeof(char)) <= 0){
				break;
			}
			read(a2b[0],&len,sizeof(int));
			read(a2b[0],word,sizeof(char)*len);
			for(int i = 0; i<= len;i++){
				if(i == len){
					printf("%1s %s -1\n",ch,word);
					break;
				}
				if(word[i]==ch[0]){
					printf("%1s %s %d\n",ch,word,i);
					break;
				}
			}
			
		}
		close(a2b[0]);
		exit(0);
	}

	FILE* fd = fopen("cuvinte.txt","r");
	size_t len = 0;
	char ch[1];
	char word[30];
	char* buffer;
	while(getline(&buffer,&len,fd) != EOF){
		sscanf(buffer,"%1s %s",ch,word);
		int x = strlen(word);
		write(a2b[1],ch,sizeof(char));
		write(a2b[1],&x,sizeof(int));
		write(a2b[1],word,sizeof(char)*x);
	}
	close(a2b[1]);
	wait(0);
	exit(0);

}
