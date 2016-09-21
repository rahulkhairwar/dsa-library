#include<bits/stdc++.h>
using namespace std;

FILE *fin = freopen("easy.in", "r", stdin);
FILE *fout = freopen("easy.out", "w", stdout);

int main()
{
	char a[1005];

	cin>>a;

	bool neg = false;

	for (int i = 0; a[i]; i++)
	{
		if (a[i] == '-')
		{
			neg = true;
			cout << a[i];

			continue;
		}

		if (a[i] == '+')
			neg = false;

		if (neg)
		{
			if (a[i-1] == '-')
				cout << a[i];
			else if (a[i] == '0')
				cout << "+0";
			else if (a[i] != '0')
			{
				cout << "+" << a[i];
				neg = false;
			}
		}
		else
			cout << a[i];
	}

	cout << '\n';

	return 0;
}