#include <bits/stdc++.h>
using namespace std;

#define ff first
#define ss second

const int inf = (int) 1e9;
const int lim = (int) 1e6 + 5;
int t, r, c;
char mm[1005][1005];
int dist[lim];
int num[1005][1005];
bool vis[lim];
vector<pair<int, int> > adj[lim];
deque<int> deq;

int zeroOneBFS(int dest)
{
	for (int i = 1; i <= dest; i++)
		dist[i] = inf;

	deq.clear();
	deq.push_front(0);

	while (deq.size() > 0)
	{
		int curr = deq.front();

		deq.pop_front();
		vis[curr] = false;

		for (auto p : adj[curr])
		{
			if (dist[curr] + p.ss < dist[p.ff])
			{
				dist[p.ff] = dist[curr] + p.ss;

				if (!vis[p.ff])
				{
					if (p.ss == 0)
						deq.push_front(p.ff);
					else
						deq.push_back(p.ff);

					vis[p.ff] = true;
				}
			}
		}
	}

	return dist[dest];
}

int main()
{
	scanf("%d", &t);

	while (t--)
	{
		scanf("%d %d", &r, &c);

		for (int i = 0; i < r; i++)
			scanf("%s", mm[i]);

		int ctr = 0;

		for (int i = 0; i < r; i++)
			for (int j = 0; j < c; j++)
				num[i][j] = ctr++;

		for (int i = 0; i < ctr; i++)
			adj[i].clear();

		for (int i = 0; i < r; i++)
		{
			for (int j = 0; j < c; j++)
			{
				// right.
				if (j < c - 1)
				{
					if (mm[i][j] == mm[i][j + 1])
					{
						adj[num[i][j]].push_back(make_pair(num[i][j + 1], 0));
						adj[num[i][j + 1]].push_back(make_pair(num[i][j], 0));
					}
					else
					{
						adj[num[i][j]].push_back(make_pair(num[i][j + 1], 1));
						adj[num[i][j + 1]].push_back(make_pair(num[i][j], 1));
					}
				}

				// down.
				if (i < r - 1)
				{
					if (mm[i][j] == mm[i + 1][j])
					{
						adj[num[i][j]].push_back(make_pair(num[i + 1][j], 0));
						adj[num[i + 1][j]].push_back(make_pair(num[i][j], 0));
					}
					else
					{
						adj[num[i][j]].push_back(make_pair(num[i + 1][j], 1));
						adj[num[i + 1][j]].push_back(make_pair(num[i][j], 1));
					}
				}
			}
		}

		printf("%d\n", zeroOneBFS(ctr - 1));
	}

	return 0;
}
