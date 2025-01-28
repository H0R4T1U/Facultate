#include <iostream>
#include <fstream>
using namespace std;

// Variabile globale pentru a reține numărul de noduri (n), muchii (m), un contor pentru parcurgerea listei de noduri (p),
// o matrice de adiacență (a) și un vector pentru a reține parcurgerea Euleriană (L)
int n, m, p, a[100][100], L[100];

ifstream fisier("p3.txt");

void Euler(int k){
    // Parcurge toate nodurile vecine nodului k
    for(int i=0; i<=n; i++)
        // Dacă există o muchie între nodul k și nodul i
        if(a[k][i]){
            // Elimină muchia dintre nodul k și nodul i (pentru a evita să o mai parcurgem o dată)
            a[k][i]=a[i][k]=0;
            // Continuă parcurgerea recursivă de la nodul i
            Euler(i);
        }
    L[++p]=k;
}

int main(){
    int i, j;
    fisier>>n>>m;
    for(int K=1; K<=m; K++){
        fisier>>i>>j;
        a[i][j]=a[j][i]=1;
    }
    Euler(0);
    for(int i=1;i<p;i++) cout<<L[i]<<" ";
    return 0;
}
