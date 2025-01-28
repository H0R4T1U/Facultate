#include <iostream>
#include <fstream>
using namespace std;
ifstream in("../data.in");
int V, E, i, j, x, y, ad[101][101], list[101][101], inc[101][101];
int main()
{
    in >> V >> E;
    for (i = 1; i <= E; i++)
    {
        in >> x >> y;
        ad[x][y] = ad[y][x] = 1;
        list[x][0]++;
        list[y][0]++;
        list[x][list[x][0]] = y;
        list[y][list[y][0]] = x;
        inc[x][i] = inc[y][i] = 1;
    }

    cout << "Matricea de adiacenta a grafului este:\n";
    for (i = 1; i <= V; i++)
    {
        for (j = 1; j <= V; j++)
            cout << ad[i][j] << ' ';
        cout << '\n';
    }

    cout << "Lista de adiacenta a grafului este:\n";
    for (i = 1; i <= V; i++)
    {
        cout << i << ": ";
        for (j = 1; j <= list[i][0]; j++)
            cout << list[i][j] << ' ';
        cout << '\n';
    }

    cout << "Matricea de incidenta a grafului este:\n";
    for (i = 1; i <= V; i++)
    {
        for (j = 1; j <= E; j++)
            cout << inc[i][j] << ' ';
        cout << '\n';
    }

    return 0;
}