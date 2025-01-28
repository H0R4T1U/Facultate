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

    FILE* bin;
    bin = fopen(argv[2],"wb");
    fwrite(&linii,sizeof(int),1,bin);
    fwrite(&coloane,sizeof(int),1,bin);
    for(int i =0;i<linii;i++){
        for(int j = 0;j<coloane;j++){
            fwrite(&matrix[i][j],sizeof(int),1,bin);
        }
    }
    fclose(bin);
    for(int i = 0; i <linii;i++){
        free(matrix[i]);
    }
    free(matrix);

    return 0;
}