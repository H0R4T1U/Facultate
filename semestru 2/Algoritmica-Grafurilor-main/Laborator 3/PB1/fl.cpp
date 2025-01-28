#include <fstream>
using namespace std;
#define inf 1000000000

int a[10000][10000], n, m, d[10000];

void Bellman(int nod, ofstream& fout);

int main(int argc, char* argv[]) {
    ifstream fin(argv[1]);
    ofstream fout(argv[2]);
    fin >> n >> m;
    int nod;
    fin >> nod;

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            a[i][j] = 0;
        }
    }
    for (int j = 0; j < m; j++) {
        int x, y, c;
        fin >> x >> y >> c;
        a[x][y] = c;
    }
    Bellman(nod, fout);
    return 0;
}

void Bellman(int nod, ofstream& fout) {
    for (int i = 0; i < n; i++)
        d[i] = inf;
    d[nod] = 0;
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                if (a[j][k] != 0) {
                    if (d[k] > d[j] + a[j][k]) {
                        d[k] = d[j] + a[j][k];
                    }
                }
            }
        }
    }

    for (int i = 0; i < n; i++) {
        if (d[i] == inf) {
            fout << "INF ";
        } else {
            fout << d[i] << " ";
        }
    }
    fout << endl;
}
