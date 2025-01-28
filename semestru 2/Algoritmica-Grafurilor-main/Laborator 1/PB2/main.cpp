#include <iostream>

#include <fstream>

using namespace std;

ifstream in("data.in");

int m, n, i, j, ad[101][101], nr, grad, ok, st, dr, x[101],v[101], k, adiac[101][101];

int main()

{

	in >> n;

	for (i = 1; i <= n; i++)

		for (j = 1; j <= n; j++)

			in >> ad[i][j];

	// graf regular = toate nodurile au acelasi grad

	cout << "Nodurile care sunt izolate: ";

	for (i = 1; i <= n; i++)

	{

		nr = 0;

		for (j = 1; j <= n; j++)

			nr += ad[i][j];

		if (nr == 0) cout << i << ' ';

	}

	cout << endl;



	grad = 0;

	for (j = 1; j <= n; j++)

	{

		grad = grad + ad[1][j];

	}



	ok = 1;

	for (i = 2; i <= n; i++)

	{

		nr = 0;

		for (j = 1; j <= n; j++)

			nr = nr + ad[i][j];

		if (nr != grad) { ok = 0; cout << "Graful nu este regular";  break; }



	}

	if (ok == 1) cout << "Graful este regular";



	in >> m;

	for (i = 1; i <= m; i++)

		for (j = 1; j <= m; j++)

			in >> adiac[i][j];



	// matricea distantelor

	for (int k = 1; k <= m; k++)

		for (int i = 1; i <= m; i++)

			for (int j = 1; j <= m; j++)

					if (adiac[i][j] > adiac[i][k] + adiac[k][j])

						adiac[i][j] = adiac[i][k] + adiac[k][j];



	cout << "\nMatricea distantelor este:\n";

	for (int i = 1; i <= m; i++)

	{

		for (int j = 1; j <= m; j++)

			cout << adiac[i][j] << ' ';

	cout << '\n';

	}



	//conexitate

	st = dr = 1;

	x[1] = 1;

	v[1] = 1;

	while (st <= dr)

	{

		k = x[st];

		for (i = 1; i <= n; i++)

			if (v[i] == 0 && ad[k][i] == 1)

			{

				v[i] = 1;

				x[++dr] = i;

			}

		st++;

	}

	if (dr == n) cout << "Graful este conex";

	else cout << "Graful nu este conex";





	return 0;

}

