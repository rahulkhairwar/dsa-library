#include <bits/stdc++.h>
using namespace std;

FILE *fin = freopen("hash.in", "r", stdin);
FILE *fout = freopen("hash.out", "w", stdout);

int main()
{
	int k;
	string a = "";

	cin >> k;

	for (int i = 0; i < k; i++)
		a += 'a';

	cout << a << endl;

	int count = 1;

	for (int i = 0; i < k - 1; i++)
	{
		a[i] = 'b';
		a[i + 1] = 'B';

		cout << a << endl;

		a[i] = 'a';
		a[i + 1] = 'a';
	}

	return 0;
}