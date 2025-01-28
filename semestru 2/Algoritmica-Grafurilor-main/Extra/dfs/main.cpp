#include <iostream>
#include <fstream>
using namespace std;
int a[100][100];
int v[100];

int n,m;
void dfs(int nod){
    v[nod] = 1;
    for(int i = 1; i<= n;i++){
        if(v[i] == 0 && a[nod][i]){
            dfs(i);
        }
    }
}
int main() {
    ifstream fin("../a.in");

    fin>>n;
    fin>>m;
    for(int i =1 ;i <=m;i++){
        int x,y;
        fin >> x >>y;
        a[x][y]=a[y][x]=1;
    }
    dfs(1);
    for(int i =1 ; i<n;i++){
        if(v[i]){
            cout<<i<<" ";
        }
    }

    std::cout << "Hello, World!" << std::endl;
    return 0;
}
