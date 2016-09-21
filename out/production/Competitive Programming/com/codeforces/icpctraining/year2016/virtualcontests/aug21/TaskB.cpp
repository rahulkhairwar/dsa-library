#include<bits/stdc++.h>
using namespace std;

FILE *fin = freopen("black.in", "r", stdin);
FILE *fout = freopen("black.out", "w", stdout);

int main ()
{
	int b, w;
	cin >> b >> w;

	char B, W;

	if(b >= w)
	{
		B = '@';
		W = '.';
	}
	else
	{
		B = '.';
		W = '@';
	}

	int x = min(b, w);
	int m;

	if(b == w)
		m = 2;
	else
		m = 4;

	if (b == w)
		cout << b + w << " " << m << '\n';
	else
		cout << b + w + 1 << " " << m << '\n';

	for (int i = 0; i < x * 2; i++)
	{
		for (int j = 0; j < m; j++)
		{
			if (i % 2 == 0)
				cout << B;
			else
				cout << W;
		}

		cout << '\n';
	}

	x = abs(b - w);

	for (int i = 0; i < x; i++)
	{
		for (int j = 0; j < m; j++)
		{
			if (i % 2 == 0 && j == 1)
				cout << B;
			else if (i % 2 == 1 && j == 2)
				cout << B;
			else
				cout << W;
		}

		cout << '\n';
	}

	if (w != b)
	{
		for (int i = 0; i < m; i++)
			cout << W;
	}

	return 0;
}