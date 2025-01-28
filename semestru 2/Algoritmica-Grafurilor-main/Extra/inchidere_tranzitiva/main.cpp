#include <iostream>
#include <fstream>
using namespace std;
ifstream fin("../a.in");

int n,m,a[100][100];
void inchidere_tranzitiva(){
    for(int k = 1; k<=n;k++ ){
        for(int i = 1; i<=n;i++){
            for(int j = 1; j<=n;j++){
                if(a[i][j] == 0){
                    a[i][j] = a[i][k] & a[k][j];
                }
            }
        }
    }
}
int main() {
    fin>>n>>m;
    for(int i = 1; i<=m;i++){
        int x,y;
        fin>>x>>y;
        a[x][y] = 1;
    }
    cout<<"Matricea de adiacenta"<<endl;
    for(int i =1;i<=n;i++){
        for(int j =1;j<=n;j++){
            cout<<a[i][j]<<" ";
        }
        cout<<endl;
    }
    inchidere_tranzitiva();
    cout<<"Matricea inchiderii tranzitive"<<endl;
    for(int i =1;i<=n;i++){
        for(int j =1;j<=n;j++){
            cout<<a[i][j]<<" ";
        }
        cout<<endl;
    }
    std::cout << "Hello, World!" << std::endl;
    return 0;
}
