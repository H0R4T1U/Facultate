#include <iostream>
#include <fstream>
#define INF 100000
using namespace std;
int mat_ad[100][100];
int n,m,s;
int d[100];


bool bellman(int nod_start) {
    int i,j,k;
    for (i = 1; i <= n; i++)
    {
        d[i] = INF; // Presupunem ca toate distantele sunt infinit

    }
    d[nod_start]= 0; // Distanta de la inceput la inceput e 0


    for (i = 1; i < n; i++) {
        for (j = 1; j <= n; j++)
            for (k = 1; k <= n; k++)
            {
                if (mat_ad[j][k] != 0 && mat_ad[j][k] != INF ) { // Daca exista o muchie intre j si k
                    if (d[k] > d[j] + mat_ad[j][k]) {
                        d[k] = d[j] + mat_ad[j][k];
                    }
                }
            }
    }
    for (j = 1; j <= n; j++)
        for (k = 1; k <= n; k++)
        {
            if (mat_ad[j][k] != 0 && d[k] > d[j] + mat_ad[j][k]) { // Daca mai exista o relaxare atunci inseamna ca avem ciclu infinit
                return false;
            }
        }
    return true;

}
int main() {

    ifstream fin("../a.in");
    ofstream fout("../a.out");
    fin>>n>>m>>s;
    for(int i = 1; i<=n;i++){
        for(int j = 1;j<=n;j++){
            mat_ad[i][j] = INF;
        }
    }
    for(int i =1;i<=m;i++){
        int x,y,w;
        fin>>x>>y>>w;
        mat_ad[x][y] =w;
    }
    bellman(s);
    for(int i = 1; i <=n;i++){
        cout<<d[i]<<" ";
    }
    return 0;
}
