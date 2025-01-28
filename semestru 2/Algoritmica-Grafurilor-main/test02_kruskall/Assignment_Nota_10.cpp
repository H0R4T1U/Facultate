#include <iostream>
#include <fstream>
using namespace std;
ifstream fin("../in.txt");
struct muchie{int x,y,c;} u[2000], sol[2000];

void Kruskal() {
    int n, m, L[2000];
    fin >> n >> m;
    int i, j;
    for (i = 1; i <= m; i++)
        // citim muchii
        fin >> u[i].x >> u[i].y >> u[i].c;
    // luam muchiile de cost minim

    for (i = 1; i < m; i++)
        for (j = i + 1; j < m; j++)
            if (u[i].c > u[j].c) {
                muchie aux = u[i];
                u[i] = u[j];
                u[j] = aux;
            }

    for (i = 1; i <= n; i++) L[i] = i;
    int cost = 0, k = 0;
    i = 1;
    // construim arborele de acoperire
    while(k<n-1){
        int arbx=L[u[i].x], arby=L[u[i].y];
        if(arbx!=arby){
            // adaugam cea mai micha muchie la solutie si crestem costu
            sol[++k]=u[i]; cost+=u[i].c;
            for(j=1; j<=n; j++)
                // refacem lista de muchii astfel incat toate ce duc la y sa treaca prin x ul adaugat la sol
                if(L[j]==arby) L[j]=arbx;
        }
        i++;
    }
    cout<<cost<<'\n';
    for(i=1; i<=k; i++) cout<<sol[i].x<<" "<<sol[i].y<<'\n';
}

int main(){
    Kruskal();
    return 0;
}