#include<bits/stdc++.h>
using namespace std;

FILE *fin = freopen("alex.in", "r", stdin);
FILE *fout = freopen("alex.out", "w", stdout);

int main()
{
	double m,n;

	cin>>n>>m;

	double ans = 0;

	if(n / 3 <= m)
		ans = max(ans, n / 3);
	else
		ans = max(ans, m);

	if(m / 3 <= n)
		ans = max(ans, m / 3);
	else
		ans = max(ans, n);

	if(n <= m)
		ans = max(ans, n/2);


	if(m <= n)
		ans = max(ans, m / 2);


	printf("%.12f\n", ans);

	return 0;
}
