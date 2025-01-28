#include <iostream>
#include <fstream>
using namespace std;
int a[100][100];
int v[100];
int queue[100];
int n,m;
int bfs(int start){
    int k,st,dr;
    st=dr=1;
    queue[st] = start;
    v[start] = 1;
    while(st<=dr){
        k = queue[st];
        for(int i = 1; i<=n;i++){
            if(v[i] == 0 && a[k][i]){
                v[i] = 1;
                queue[++dr] = i;
            }
        }
        st++;
    }
    return dr;
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
    cout<<"Noduri Vizitate din nodul 1:"<<bfs(1)<<endl;
    for(int i =1 ; i<n;i++){
        if(v[i]){
            cout<<i<<" ";
        }
    }

    std::cout << "Hello, World!" << std::endl;
    return 0;
}
