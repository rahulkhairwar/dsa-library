#include <bits/stdc++.h>
#include <unordered_map>
using namespace std;

#define ll long long

struct Query
{
	int depth;
	bool poss;
};

struct Node
{
	int depth;
	vector<int> adj;
	unordered_map<int, int> mp;
};

void put(Node* node, int key, int val)
{
	node->mp[key] ^= val;
}

void merge(Node* a, Node* b)
{
	for (unordered_map<int, int>::iterator entry = b->mp.begin(); entry != b->mp.end(); entry++)
		put(a, entry->first, entry->second);
}

Query* createQuery(int depth)
{
	Query* query = new Query();

	query->depth = depth;
	query->poss = false;

	return query;
}

const int MAX = (int) 5e5 + 5;
int n, q;
int vals[MAX];
char s[MAX];
Node* nodes[MAX];
vector<int> lists[MAX];
Query* queries[MAX];

bool isPalindromePossible(int val)
{
	return val == 0 || __builtin_popcount(val) < 2;
}

Node* dfs(int node, int depth)
{
	Node* a = nodes[node];

	a->depth = depth;

	for (auto x : a->adj)
	{
		Node* b = dfs(x, depth + 1);

		if (a->mp.size() < b->mp.size())
		{
			Node* temp = a;

			a = b;
			b = temp;
		}

		merge(a, b);
	}

	int x = vals[node];

	put(a, depth, x);

	for (auto i : lists[node])
		queries[i]->poss = isPalindromePossible(a->mp[queries[i]->depth]);

	return a;
}

int main()
{
	scanf("%d %d", &n, &q);

	for (int i = 0; i < n; i++)
	{
		lists[i].clear();
		vals[i] = 0;
		nodes[i] = new Node();
	}

	for (int i = 1; i < n; i++)
	{
		int par;

		scanf("%d", &par);
		par--;
		nodes[par]->adj.push_back(i);
	}

	scanf("%s", s);

	for (int i = 0; i < n; i++)
		vals[i] = 1 << (s[i] - 'a');

	for (int i = 0; i < q; i++)
	{
		int v, h;

		scanf("%d %d", &v, &h);
		v--;
		queries[i] = createQuery(h);
		lists[v].push_back(i);
	}

	dfs(0, 1);

	for (int i = 0; i < q; i++)
	{
		if (queries[i]->poss)
			printf("Yes\n");
		else
			printf("No\n");
	}

	return 0;
}