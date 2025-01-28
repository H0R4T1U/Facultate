#include <iostream>
#include <fstream>
#define INF 100000
using namespace std;
int mat_ad[100][100];
int n,m,s;
int v[100],d[100];
void dijkstra(int s){
    for(int i =1; i<= n;i++){
        v[i] = 0;
        d[i] = mat_ad[s][i];
    }
    v[s] = 1;
    d[s] = 0;
    for(int k = 1; k <=n;k++){
        int min_dist = INF;
        int pmin = -1;
        for(int i = 1;i<=n;i++){ // partea greedy
            if(v[i] == 0 && d[i] < min_dist){
                min_dist  = d[i];
                pmin = i; //gaseste cea mai micap ondere
            }
        }
        if(pmin > -1){
            v[pmin] = 1;
            for(int i =1; i <=n;i++){
                if(v[i] == 0 && d[i] > d[pmin] + mat_ad[pmin][i]){
                    d[i] = d[pmin]+mat_ad[pmin][i];
                }
            }
        }
    }
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
    dijkstra(s);
    for(int i = 1; i <=n;i++){
        cout<<d[i]<<" ";
    }
    return 0;
}
