#include <iostream>
#include <fstream>
using namespace std;
#define inf 1000000000

int a[10000][10000], n, m, d[10000];


void Bellman(int nod,ofstream& fout);
int main(int argc, char* argv[]) {
    cout << "Hello, World!" <<endl;
    ifstream fin(argv[1]);
    ofstream fout(argv[2]);
    fin>>n>>m;
    int nod; fin>>nod;
    for(int j=1; j<=m; j++){
        int x, y, c;
        fin>>x>>y>>c;
        a[x][y]=c;
    }
    Bellman(nod,fout);

    return 0;
}

void Bellman(int nod,ofstream& fout){

    for(int i=0; i<n; i++)
        d[i]=inf;
    d[nod]=0;
    for(int i=0; i<n; i++)
        for(int j=0; j<n; j++)
            if(a[i][j])
                if(d[j]>d[i]+a[i][j])
                    d[j]=d[i]+a[i][j];
    for(int i=0; i<n; i++)
        if(d[i] == inf) {
            fout<<"INF ";
        }else {
            fout<<d[i]<<" ";

        }
    fout<<endl;

}
