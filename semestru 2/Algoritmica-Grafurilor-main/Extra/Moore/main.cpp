#include <iostream>
#include <fstream>
#define INF 1000
using namespace std;
ifstream in("../graf.txt");
void Moore(int nod, int n, int a[100][100], int l[])
{
    int q[100],st,dr,k;
    st=dr=1;
    q[st] = nod;
    l[nod] = 0;
    for(int i = 1;i<=n;i++){
        if(i != nod){
            l[i] = INF;
        }

    }
    while(st<=dr){
        k = q[st];
        for(int i = 1; i<=n;i++){
            if(l[i] == INF && a[k][i]){
                l[i] = l[k]+1;
                q[++dr] = i;
            }
        }
        st++;
    }

}
void citesteGraf(int &nrNoduri, int &nodStart, int matAd[100][100])
{
    int x, y;
    in >> nrNoduri >> nodStart;
    while (in >> x >> y)
    {
        matAd[x][y] = matAd[y][x] = 1;
    }
}
int main()
{
    int nrNoduri, nodStart, matAd[100][100], l[100];
    citesteGraf(nrNoduri, nodStart, matAd);
    Moore(nodStart, nrNoduri, matAd, l);

    cout << "Distante minime de la nodul " << nodStart << ":\n";
    for (int i = 1; i <= nrNoduri; i++)
        if (l[i] == INF)
            cout << "Nodul " << i << ": nu este accesibil\n";
        else
            cout << "Nodul " << i << ": " << l[i]<<"\n";



    return 0;
}
