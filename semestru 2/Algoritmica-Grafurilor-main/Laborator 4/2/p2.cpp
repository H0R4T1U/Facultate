#include <iostream>
#include <fstream>
#include <deque>
#include <vector>
using namespace std;
int l,n;
deque<int> c;
ifstream fisier("2.txt");
struct nod{
    int pre;
};
nod v[100];

int get_min(){ // gaseste
    bool fr[c.size() + 1] = {0};
    for(auto &i: c)
        fr[i] = 1;

    for(int i=0;i <= c.size();i++)
        if(fr[i] == 0) return i;
    return -1;
}

void decodare(){
    for(int i=0;i<l;i++){
        int y = get_min();
        int x = c.front();
        cout << y << " " << x << endl;
        v[y].pre = x;
        c.pop_front();
        c.push_back(y);
    }
}

int main(){
    fisier >> l; // citim nr valori din codarea prufer
    n = l+1;
    for(int i=0;i<l;i++){
        int x;
        fisier >> x;
        c.push_back(x); // adaugam valorile
    }

    for(int i=0;i<n;i++)
        v[i].pre = -1;

    decodare();

    cout << n << endl;
    for(int i=0;i<n;i++)
        cout << v[i].pre << " ";

    return 0;
}