#include <iostream>
#include <fstream>
using namespace std;
ifstream fin("../a.in");

int n,m,a[100][100],l[100][100],in[100][100];
int main() {
    fin>>n>>m;
    for(int i = 1; i <=m;i++){
        int x,y;
        fin>>x>>y;
        //matricea de adiacenta
        a[x][y] = a[y][x] = 1;
        //lista de adiacenta
        l[x][0]+=1;
        l[x][l[x][0]] = y;
        in[x][i] = in[y][i] = 1;//matricea de incidenta
    }
    cout<<"Matricea de Adiacenta"<<endl;
    for(int i = 1 ; i<=n;i++){
        for(int j = 1;j <=n;j++){
            cout<<a[i][j]<<" ";
        }
        cout<<endl;
    }
    cout<<endl<<"Lista de Adiacenta"<<endl;
    for(int i =1;i<=n;i++){
        cout<<i<<":";
        for(int j = 1;j<=l[i][0];j++){
            cout<<l[i][j]<<" ";
        }
        cout<<endl;
    }
    cout<<endl<<"Matricea de Incidenta"<<endl;
    for(int i = 1;i <=n;i++){
        for(int j = 1; j <=m;j++){
            cout<<in[i][j]<<" ";
        }
        cout<<endl;
    }
    return 0;
}
