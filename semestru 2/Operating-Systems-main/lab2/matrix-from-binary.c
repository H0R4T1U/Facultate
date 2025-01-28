#include <stdio.h>
#include <stdlib.h>


int main(int argc, char** argv){
    int linii,coloane;
    FILE* bin;
    int** matrix;
    bin = fopen(argv[1],"r");
    fread(&linii,sizeof(int),1,bin);
    fread(&coloane,sizeof(int),1,bin);
    matrix = malloc(linii*sizeof(int*));
    for(int i = 0; i < linii;i++){
        matrix[i] = malloc(coloane*sizeof(int));
        for(int j = 0; j < coloane; j++){
            fread(&matrix[i][j],sizeof(int),1,bin);
        }
    }
    fclose(bin);

     for(int i =0; i<linii;i++){
        for(int j = 0; j<coloane;j++){
            printf("%3d",matrix[i][j]);
        }
        printf("\n");
    }
    
    for(int i = 0; i <linii;i++){
        free(matrix[i]);
    }
    free(matrix);

    return 0;
}
