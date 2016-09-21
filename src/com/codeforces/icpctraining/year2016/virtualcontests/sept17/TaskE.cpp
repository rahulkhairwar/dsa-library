#include<bits/stdc++.h>
using namespace std;

#define ll long long

FILE * fin = freopen("easy.in", "r", stdin);
FILE * fout = freopen("easy.out", "w", stdout);

int main ()
{
	int n, k;

	cin >> n >> k;

	ll sum = 0;
	deque<ll> a[ n];
	int c = 0;

	for (int i = 0; i < n; i++)
	{
		int sz;
		ll b;

		cin >> sz;
		c += sz;

		while (sz--)
		{
			cin >> b;
			a[i].push_back(b);
		}
	}

	int i = 0;

	while (k)
	{
		if (a[i].size())
		{
			ll b = a[i].front();
			a[i].pop_front();

			if (b >= sum)
			{
				sum += b;
				k--;
			}
		}
		else
		{
			sum += 50;
			k--;
		}

		i = (i + 1) % n;
	}

	cout << sum << '\n';

	return 0;
}