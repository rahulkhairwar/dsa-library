// $-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$ //
//                                      ashlamps08                                         //
// $-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$-$ //

#include <bits/stdc++.h>

using namespace std;

template <typename T>
void scanInt(T &i) {
	i = 0;
	int c;
	while ((c = getchar_unlocked()) != EOF && !(c <= '9' && c >= '0'))
		;
	i = c - '0';
	while ((c = getchar_unlocked()) != EOF && c <= '9' && c >= '0')
		i = (i << 1) + (i << 3) + (c - '0');
}

#define ll long long
#define ld long double
#define pii pair<int, int>
#define pll pair<ll, ll>
#define pdd pair<ld, ld>
#define sf(a) scanf("%d", &a)
#define pf(a) scanf("%d", a)
//#define int ll

const int inf = 1e9 + 7;
const int N = 123456;

class fenwick {
  public:
	int bit[N];

	fenwick() {
		fill(bit, bit + N, 0);
	}

	void add(int ind, int val) {

		while (ind < N) {

			bit[ind] += val;
			ind += ind & -ind;
		}

	}

	int fetch(int ind) {
		int res = 0;

		while (ind > 0) {

			res += bit[ind];
			ind -= ind & -ind;
		}

		return res;
	}
};

struct line {
	int x1;
	int x2;
	int y1;
	int y2;

	int type;
	int ind;
};

inline bool comp1(const line &a, const line &b) {
	if (a.x1 < b.x1)
		return true;
	if (a.x1 > b.x1)
		return false;
	return a.type == 1;
}

inline bool comp2(const line &a, const line &b) {
	if (a.y2 < b.y2)
		return true;
	if (a.y2 > b.y2)
		return false;
	return a.type == 0;
}

int main() {



	int n, a, b, c, d;

	scanInt(n);

	line l[n];
	map<pii, int> m;
	int minx = 1, miny = 1;

	for (int i = 0; i < n; i++) {
		scanInt(a);
		scanInt(b);
		scanInt(c);
		scanInt(d);

		if (a == c) {
			l[i].x1 = l[i].x2 = a;
			l[i].type = 0;
			if (b > d)
				l[i].y1 = b, l[i].y2 = d;
			else
				l[i].y1 = d, l[i].y2 = b;
		}
		else {
			l[i].y1 = l[i].y2 = b;
			l[i].type = 1;
			if (a < c)
				l[i].x1 = a, l[i].x2 = c;
			else
				l[i].x1 = c, l[i].x2 = a;
		}

		m[{l[i].x1, l[i].y1}]++;
		m[{l[i].x2, l[i].y2}]++;

		l[i].ind = i;
	}

	sort(l, l + n, comp1);

	int ans[n];
	ll count = 0;
	fill(ans, ans + n, 0);

	fenwick f1;
	vector<int> rightend[N];

	minx = l[0].x1;

	for (int i = minx, j = 0; i < N && j < n;) {
		bool flag = false;

		if (l[j].x1 > i)
			flag = true;



		if (!flag) {
			if (l[j].type == 1) {

				f1.add(l[j].y1, 1);
				rightend[l[j].x2].push_back(l[j].y2);
				j++;
			}
			else {

				int temp = f1.fetch(l[j].y1) - f1.fetch(l[j].y2 - 1);
				ans[l[j].ind] = temp;

				j++;
			}
		}

		if (j < n && l[j].x1 > i) {
			while (rightend[i].size() != 0) {
				int aa = rightend[i].back();

				f1.add(aa, -1);
				rightend[i].pop_back();
			}
		}

		if(flag)
			i++;
	}

	sort(l, l + n, comp2);

	fenwick f2;
	vector<int> upend[N];

	miny = l[0].y2;

	for (int i = miny, j = 0; j < n && i < N;) {
		bool flag = false;

		if (l[j].y2 > i) {
			flag = true;
		}

		if (!flag) {

			if (l[j].type == 0) {
				f2.add(l[j].x2, 1);
				upend[l[j].y1].push_back(l[j].x1);
				j++;
			}
			else {
				int temp = f2.fetch(l[j].x2) - f2.fetch(l[j].x1 - 1);
				ans[l[j].ind] = temp;
				j++;
			}
		}

		if (j < n && l[j].y2 > i) {
			while (upend[i].size() != 0) {
				int aa = upend[i].back();
				f2.add(aa, -1);
				upend[i].pop_back();
			}
		}

		if (flag)
			i++;
	}

	for (int i = 0; i < n; i++) {

		ans[l[i].ind] -= (m[{l[i].x1, l[i].y1}] + m[{l[i].x2, l[i].y2}] - 2);
		count += (ll)ans[l[i].ind];
	}

	printf("%lld\n", count >> 1);

	for (int i = 0; i < n; i++)
		printf("%d ", ans[i]);
	printf("\n");
	return 0;
}
/*
8
5 5 2 5
5 3 10 3
5 1 5 11
5 6 12 6
5 8 11 8
7 6 7 8
8 3 8 6
9 8 9 3

2
5 5 2 5
5 5 5 9


*/
