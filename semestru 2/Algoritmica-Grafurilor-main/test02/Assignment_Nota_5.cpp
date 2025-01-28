#include <iostream>
#include <fstream>
#include <vector>
#include <set>
using namespace std;


int n;
vector <int> nr_succesori,parinte, k;
set <int> frunze;

int main()
{
    ifstream fin("../in.txt");
    ofstream fout("../out.txt");
    fin >> n;
    nr_succesori.resize(n + 1);
    parinte.resize(n + 1);
    for (int i = 0; i < n; i++)
    {
        fin >> parinte[i];
        if (parinte[i] != -1)
            nr_succesori[parinte[i]]++;
    }
    for (int i = 0; i < n; i++)
        if (nr_succesori[i] == 0)
            frunze.insert(i);
    while (!frunze.empty())
    {
        int f = *frunze.begin();
        frunze.erase(frunze.begin());
        if (parinte[f] != -1)
        {
            int pr = parinte[f];
            k.push_back(pr);
            nr_succesori[pr]--;
            if (nr_succesori[pr] == 0)
                frunze.insert(parinte[f]);
        }

    }
    fout << k.size() << endl;
    for (int i : k)
        fout << i << " ";
    fout << endl;
    fin.close();
    fout.close();
    return 0;

}