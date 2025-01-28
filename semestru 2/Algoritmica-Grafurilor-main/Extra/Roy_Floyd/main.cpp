#include <iostream>
#include <fstream>
#define INF  1000
using namespace std;

ifstream fin("../a.in");
int n,m,a[100][100];
void roy_floyd(){
    for(int k = 1;k<=n;k++){
        for(int i = 1;i<=n;i++){
            for(int j = 1; j<=n;j++){
                if(a[i][j] > a[i][k]+a[k][j]){
                    a[i][j] = a[k][j];
                }
            }
        }
    }
}
int main() {
    fin>>n>>m;
    for(int i = 1; i<=n;i++){
        for(int j = 1;j<=n;j++){
            if(i != j ){
                a[i][j] = INF;
            }
        }
    }
    for(int i = 1; i<=m;i++){
        int x,y,w;
        fin>>x>>y>>w;
        a[x][y] = w;
    }
    cout<<"drumurile Initiale:"<<endl;
    for(int i = 1;i<=n;i++){
        for(int j = 1;j<=n;j++){
            if(a[i][j] != INF ){
                cout<<a[i][j]<<" ";
            }else{
                cout<<"INF"<<" ";
            }

        }
        cout<<endl;
    }
    roy_floyd();
    cout<<"drumurile Finale:"<<endl;
    for(int i = 1;i<=n;i++){
        for(int j = 1;j<=n;j++){
            if(a[i][j] != INF ){
                cout<<a[i][j]<<" ";
            }else{
                cout<<"INF"<<" ";
            }
        }
        cout<<endl;
    }
    return 0;
}
