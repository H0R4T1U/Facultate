#include <stdio.h>
#include <stdlib.h>


int main(int argc, char** argv){
    int linii,coloane;
    FILE* f;
    int** matrix;
    f = fopen(argv[1],"r");
    fscanf(f,"%d %d",&linii,&coloane);
    matrix = malloc(linii*sizeof(int*));
    for(int i = 0; i < linii;i++){
        matrix[i] = malloc(coloane*sizeof(int));
        for(int j = 0; j < coloane; j++){
            fscanf(f,"%d",&matrix[i][j]);
        }
    }
    fclose(f);
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