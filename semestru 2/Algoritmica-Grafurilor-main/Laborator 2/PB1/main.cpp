#include <iostream>
#include <fstream>

using namespace std;
int a[10][10];
int viz[10];
int n,m;
ifstream fin("../data.in");
void dfs(int a[10][10],int nod_curent) {
    viz[nod_curent] = 1;
    for(int i =1;i <=n;i++) {
        if(a[nod_curent][i] == 1 && viz[i] == 0) {
            dfs(a,i);
        }
    }
}
int main() {
    int x,y;
    fin>>n>>m;
    for(int i = 1; i<=m;i++) {
        fin>>x>>y;
        a[x][y]=a[y][x]=1;
    }
    dfs(a,1);
    cout<<"Noduri Vizitate:";
    for(int i = 1; i <=n;i++) {
        if(viz[i]) {
            cout<<i<<" ";
        }
    }
    return 0;
}
