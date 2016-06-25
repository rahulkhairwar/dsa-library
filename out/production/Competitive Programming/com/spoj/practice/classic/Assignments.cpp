#include <iostream>
using namespace std;

#define ll long long

ll MAX = (1 << 20) + 5;

int t, n;
ll dp[(1 << 20) + 5];
int preferences[20][20];

int countBits(ll mask)
{
	int count = 0;

	while (mask != 0)
	{
		if ((mask & 1) == 1)
			count++;

		mask >>= 1;
	}

	return count;
}

ll countValue(ll mask)
{
	if (dp[mask] != -1)
		return dp[mask];

	int index = countBits(mask);

	if (index == n)
		return 1;

	ll count = 0;
	ll temp = 1;

	for (int i = 0; i < n; i++)
	{
		if (preferences[index][i] && (mask & temp) == 0)
			count += countValue(mask | temp);

		temp <<= 1;
	}

	dp[mask] = count;

	return count;
}

int main()
{
	cin >> t;

	while (t--)
	{
		cin >> n;

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				cin >> preferences[i][j];

		for (int i = 0; i < MAX; i++)
			dp[i] = -1;

		cout << countValue(0) << endl;
	}

	return 0;
}