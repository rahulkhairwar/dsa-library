#include <bits/stdc++.h>
using namespace std;

long long max1[100000];
long long pos1[100000];
long long max2[100000];
long long pos2[100000];

FILE *fin = freopen("generators.in","r",stdin);
FILE *fout = freopen("generators.out","w",stdout);

int main()
{
	std::ios_base::sync_with_stdio(false);

	long long n,k;
	bool no = true;

	cin>>n>>k;

	for (int i = 0; i < n; i++)
	{
		long long x, a, b, c;

		cin >> x >> a >> b >> c;
		max1[i] = x;
		pos1[i] = 0;
		max2[i] = -1;
		pos2[i] = -1;

		set<long long> s;
		long long ctr = 0;

		s.insert(x);

		while (s.find((a * x + b) % c) == s.end())
		{
			ctr++;
			s.insert((a * x + b) % c);
			x = (a * x + b) % c;

			if (max1[i] < x)
			{
				long long temp = max1[i];
				long long temppos = pos1[i];

				max1[i] = x;
				pos1[i] = ctr;

				if ((max1[i] - temp) % k != 0 && max2[i] < temp)
				{
					max2[i] = temp;
					pos2[i] = temppos;
					no = false;
				}
			}
			else if (max2[i] < x && (max1[i] - x) % k != 0)
			{
				max2[i] = x;
				pos2[i] = ctr;
				no = false;
			}
		}
	}

	if (no)
		cout << -1;
	else
	{
		long long sum =0;

		for (long long i = 0; i < n; i++)
			sum += max1[i];

		if (sum % k != 0)
		{
			cout << sum << "\n";

			for (long long i = 0; i < n; i++)
					cout << pos1[i] << " ";
		}
		else
		{
			long long ch = -1;
			long long mdiff = 100000;

			for (long long i = 0; i < n; i++)
			{
				if (max2[i] != -1 && mdiff > max1[i] - max2[i])
				{
					mdiff = max1[i] - max2[i];
					//cout<<mdiff<<endl;
					ch = i;
				}
			}

			sum -= mdiff;
			cout << sum << "\n";

			for (long long i = 0; i < n; i++)
			{
				if (i != ch)
					cout << pos1[i] << " ";
				else
					cout << pos2[i] << " ";
			}
		}
	}

	return 0;
}