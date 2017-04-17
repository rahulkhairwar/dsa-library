#include <bits/stdc++.h>
#include <math.h>
#include <unordered_map>

using namespace std;

struct Node
{
	int ind, deg, wt;
	vector<int> adj;
};

struct Query
{
	int ind, block, left, right, lca;
};

Node *createNode(int ind, int wt)
{
	Node *node = new Node();

	node->ind = ind;
	node->wt = wt;

	return node;
}

int sqrtN;

Query *createQuery(int ind, int left, int right)
{
	Query *query = new Query();

	query->ind = ind;
	query->left = left;
	query->right = right;
	query->block = left / sqrtN;

	return query;
}

const int MAXN = (int) 40000 + 5;
const int MAXQ = (int) 100000 + 5;
int n, q, timee, curr, maxx, uniqueCount;
int depth[MAXN], euler[MAXN << 1], first[MAXN],
		entry[MAXN], exitt[MAXN], entryExit[MAXN << 1],
		cnt[MAXN], vis[MAXN], lg[MAXN << 1];
int rmq[25][MAXN << 1];
bool addNext[MAXQ << 1];
Node *nodes[MAXN];
Query *queries[MAXQ << 1];

void compress()
{
	int wts[n];
	unordered_map<int, int> um;

	for (int i = 0; i < n; i++)
		wts[i] = nodes[i]->wt;

	sort(wts, wts + n);
	curr = 1;

	for (int i = 0; i < n; i++)
		if (!um[wts[i]])
			um[wts[i]] = curr++;

	maxx = curr + 1;

	for (int i = 0; i < n; i++)
		nodes[i]->wt = um[nodes[i]->wt];
}

void dfs(int node, int par, int dep)
{
	depth[node] = dep;
	euler[curr] = node;
	first[node] = curr++;
	entry[node] = timee;
	entryExit[timee++] = node;

	for (auto x : nodes[node]->adj)
	{
		if (x == par)
			continue;

		dfs(x, node, dep + 1);
		euler[curr++] = node;
	}

	exitt[node] = timee;
	entryExit[timee++] = node;
}

bool cmp(Query *a, Query *b)
{
	if (a->block == b->block)
		return a->right < b->right;

	return a->block < b->block;
}

void add(int ind)
{
	int x = nodes[entryExit[ind]]->wt;

	vis[entryExit[ind]]++;

	if (vis[entryExit[ind]] == 1)
	{
		cnt[x]++;

		if (cnt[x] == 1)
			uniqueCount++;
	} else if (vis[entryExit[ind]] == 2)
	{
		cnt[x]--;

		if (cnt[x] == 0)
			uniqueCount--;
	}
}

void remove(int ind)
{
	int x = nodes[entryExit[ind]]->wt;

	vis[entryExit[ind]]--;

	if (vis[entryExit[ind]] == 0)
	{
		cnt[x]--;

		if (cnt[x] == 0)
			uniqueCount--;
	} else if (vis[entryExit[ind]] == 1)
	{
		cnt[x]++;

		if (cnt[x] == 1)
			uniqueCount++;
	}
}

void compute()
{
    int lim = (n << 1) - 1;

    for (int i = 2; i <= lim; i++)
        lg[i] = lg[i >> 1] + 1;

    for (int i = 0; i < lim; i++)
        rmq[0][i] = euler[i];

    for (int i = 1; (1 << i) < lim; i++)
    {
        for (int j = 0; (1 << i) + j <= lim; j++)
        {
            int x = rmq[i - 1][j];
            int y = rmq[i - 1][(1 << (i - 1)) + j];

            rmq[i][j] = depth[x] <= depth[y] ? x : y;
        }
    }
}

int getLCA(int u, int v)
{
//	printf("\tgetLCA, u : %d, v : %d, v-u : %d\n", u, v, v - u);
    int k = lg[v - u];
    int x = rmq[k][u];
    int y = rmq[k][v - (1 << k) + 1];
//	printf("\tgetLCA, u : %d, v : %d, k : %d, x : %d, y : %d\n", u, v, k, x, y);

    return depth[x] <= depth[y] ? x : y;
}

int main()
{
	scanf("%d %d", &n, &q);
	sqrtN = (int) sqrt(n);

	for (int i = 0; i < n; i++)
	{
		int wt;

		scanf("%d", &wt);
		nodes[i] = createNode(i, wt);
        vis[i] = 0;
	}

    int xx[n], yy[n];

	for (int i = 1; i < n; i++)
	{
		int u, v;

		scanf("%d %d", &u, &v);
		u--, v--;
        nodes[u]->deg++;
        nodes[v]->deg++;
        xx[i] = u;
        yy[i] = v;
//        nodes[u]->adj.push_back(v);
//        nodes[v]->adj.push_back(u);
	}

    for (int i = 0; i < n; i++)
    {
        nodes[i]->adj.resize(nodes[i]->deg);
        nodes[i]->deg = 0;
    }

    for (int i = 1; i < n; i++)
    {
        int u = xx[i];
        int v = yy[i];

        nodes[u]->adj[nodes[u]->deg] = v;
        nodes[u]->deg++;
        nodes[v]->adj[nodes[v]->deg] = u;
        nodes[v]->deg++;
    }

	compress();

	curr = 0;
	dfs(0, -1, 0);
    curr = 0;
    compute();

    curr = 0;
    uniqueCount = 0;
    int qCount = 0;

	for (int i = 0; i < q; i++)
	{
		int l, r;

		scanf("%d %d", &l, &r);
		l--, r--;

		if (first[l] > first[r])
		{
			int temp = l;

			l = r;
			r = temp;
		}

//		if (i % 1000 == 0)
//			printf("i : %d\n", i);

        int lca = getLCA(first[l], first[r]);

//        printf("i : %d, l : %d, r : %d, lca : %d, qCount : %d\n", i, l, r, lca, qCount);

		if (lca == l)
        {
            queries[qCount] = createQuery(qCount, entry[l], entry[r]);
            qCount++;
        }
		else
		{
//			printf("\tl : %d, r : %d\n", l, r);
//			printf("\texit[l] : %d, entry[r] : %d\n", exitt[l], entry[r]);
//			int zz;

//			scanf("%d", &zz);
			queries[qCount] = createQuery(qCount, exitt[l], entry[r]);
//			printf("\tquery part 1 == NULL ? %c\n", (queries[qCount] == NULL ? 'T' : 'F'));
			queries[qCount]->lca = lca;
			addNext[qCount] = true;
            qCount++;
//			printf("\tcreated query part 1\n");
			queries[qCount] = createQuery(qCount, entry[lca], entry[lca]);
            qCount++;
//			printf("\tcreated query part 2\n");
		}
	}

	printf("Queries taken as input.\n");


/*    for (int i = 0; i < qCount; i++)
    {
        if (queries[i] != NULL)
            printf("i : %d, ind : %d, l : %d, r : %d, block : %d\n", i, queries[i]->ind, queries[i]->left, queries[i]->right, queries[i]->block);
    }*/

	sort(queries, queries + curr, cmp);

	printf("queries sorted\n");

	int ans[curr];

	for (int i = 0; i <= maxx; i++)
		cnt[i] = 0;

    printf("starting mo's loop.\n");
    uniqueCount = 0;
    int left = 0;
    int right = -1;

	for (int i = 0; i < qCount; i++)
	{

		if (i % 1000 == 0)
        	printf("i : %d, q[i] => l : %d, r : %d\n", i, queries[i]->left, queries[i]->right);

		while (left > queries[i]->left)
		{
//            printf("i : %d, l : %d\n", i, left);
			left--;
			add(left);
		}

		while (right < queries[i]->right)
		{
//            printf("i : %d, r : %d\n", i, right);
			right++;
			add(right);
		}

		while (left < queries[i]->left)
		{
			remove(left);
			left++;
		}

		while (right > queries[i]->right)
		{
			remove(right);
			right--;
		}

		ans[queries[i]->ind] = uniqueCount;

		if (addNext[queries[i]->ind] && cnt[nodes[queries[i]->lca]->wt] > 0)
			ans[queries[i]->ind]--;
	}

	for (int i = 0; i < qCount; i++)
	{
		int x = ans[i];

		if (addNext[i])
			x += ans[++i];

		printf("%d\n", x);
	}

    printf("out of printing loop.");

	return 0;
}