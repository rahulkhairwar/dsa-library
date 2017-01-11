const ll MAX = 1000000007;
std::vector<ll> fact;
void initFact(int maximum) {
	fact.resize(maximum + 1, 0);
	fact[0] = 1;
	for (ll i = 1; i <= maximum; i++)

		fact[i] = ((fact[i - 1] % MAX) * (i % MAX)) % MAX;
}
inline ll mod(ll i) {
	return (i % MAX + MAX) % MAX;
}
ll powa(ll a, ll p) {
	if (p == 1)
		return mod(a);
	if (p == 0)
		return 1;
	ll ans = (powa(a, p / 2) % MAX);
	if (p % 2 == 0)
		return ((ans % MAX) * (ans % MAX)) % (MAX);
	else {
		ans = ((ans % MAX) * (ans % MAX)) % (MAX);
		ans = ((ans % MAX) * (a % MAX)) % (MAX);
		return ans;
	}
}
ll modInv(ll a) {
	a = mod(a);
	return powa(a, MAX - 2) % MAX;
}
ll atomic_nCr(ll n, ll k) {
	if (n < k)
		return 0;
	if (k == 0 || k == n)
		return 1;
	if (k == 1 || k == n - 1)
		return n;
	ll f = fact[n];
	ll s = ((fact[k] % MAX) * (fact[n - k] % MAX)) % MAX;
	return ((f % MAX) * (modInv(s) % MAX)) % MAX;
}

// Probably Lucas Theorem.
ll nCr(ll n, ll k) {
	ll ans = 1;
	while (n > 0 || k > 0) {
		ll temp = atomic_nCr(n % MAX, k % MAX);
		ans = ((ans % MAX) * (temp % MAX)) % MAX;
		n /= MAX;
		k /= MAX;
	}
	return ans;
}

std::vector<sf::Vector2f>
getConvexHull(const std::vector<sf::Vector2f>&points, std::vector<unsigned>&indexes)
{
	std::vector < sf::Vector2f > hull;
	for (unsigned i = 0; i < points.size(); i++)
		indexes.push_back(i);
	sf::Vector2f pivot = getPivot(points);
	hull.push_back(pivot);
	std::sort (indexes.begin(), indexes.end(), [pivot, points](unsigned a, unsigned &b) -> bool {
		return !turn(points[a], points[b], pivot);
	});
	hull.push_back(pivot);
	hull.push_back(points[indexes[0]]);
	for (unsigned i = 1; i < points.size() - 1; i++)
	{
		sf::Vector2f A = hull[hull.size() - 1] - hull[hull.size() - 2];
		sf::Vector2f B = points[indexes[i]] - hull[hull.size() - 1];
		while ((A.x * B.y - A.y * B.x) > 0.0f)
		{
			hull.pop_back();
			A = hull[hull.size() - 1] - hull[hull.size() - 2];
			B = points[indexes[i]] - hull[hull.size() - 1];
		}
		hull.push_back(points[indexes[i]]);
	} return hull;
}

// Adjacency list implementation of Dinic's blocking flow algorithm.
// This is very fast in practice, and only loses to push-relabel flow.
//
// Running time:
//     O(|V|^2 |E|)
//
// INPUT:
//     - graph, constructed using AddEdge()
//     - source and sink
//
// OUTPUT:
//     - maximum flow value
//     - To obtain actual flow values, look at edges with capacity > 0
//       (zero capacity edges are residual edges).

typedef long
long LL;

struct Edge
{
	int u, v;
	LL cap, flow;
	Edge()
	{}

	Edge( int u, int v, LL cap):u(u), v(v), cap(cap), flow(0) {}
};

struct Dinic
{
	int N;
	vector<Edge> E;
	vector<vector<int>> g;
	vector<int> d, pt;
	Dinic( int N):N(N), E(0), g(N), d(N), pt(N) {}

	void AddEdge(int u, int v, LL cap)
	{
		if (u != v)
		{
			E.emplace_back(Edge(u, v, cap));
			g[u].emplace_back(E.size() - 1);
			E.emplace_back(Edge(v, u, 0));
			g[v].emplace_back(E.size() - 1);
		}
	}

	bool BFS(int S, int T)
	{
		queue<int> q ({S});
		fill(d.begin(), d.end(), N + 1);
		d[S] = 0;
		while (!q.empty())
		{
			int u = q.front();
			q.pop();
			if (u == T)
				break;
			for (int k : g[u])
			{
				Edge & e = E[k];
				if (e.flow < e.cap && d[e.v] > d[e.u] + 1)
				{
					d[e.v] = d[e.u] + 1;
					q.emplace(e.v);
				}
			}
		}
		return d[T] != N + 1;
	}

	LL DFS(int u, int T, LL flow =-1)
	{
		if (u == T || flow == 0)
			return flow;
		for (int &i = pt[u]; i<g[u].size(); ++i){
			Edge & e = E[g[u][i]];
			Edge & oe = E[g[u][i] ^ 1];
			if (d[e.v] == d[e.u] + 1)
			{
				LL amt = e.cap - e.flow;
				if (flow != -1 && amt > flow)
					amt = flow;
				if (LL pushed = DFS(e.v, T, amt)){
					e.flow += pushed;
					oe.flow -= pushed;
					return pushed;
				}
			}
		} return 0;
	}

	LL MaxFlow(int S, int T)
	{
		LL total = 0;
		while (BFS(S, T))
		{
			fill(pt.begin(), pt.end(), 0);
			while (LL flow = DFS(S, T))
			total += flow;
		} return total;
	}
};

// BEGIN CUT
// The following code solves SPOJ problem #4110: Fast Maximum Flow (FASTFLOW)

int main()
{
	int N,E;
	scanf("%d%d",&N,&E);
	Dinic dinic(N);
	for(int i=0;i<E; i++)
	{
		int u,v;
		LL cap;
		scanf("%d%d%lld",&u,&v,&cap);
		dinic.AddEdge(u-1,v-1,cap);
		dinic.AddEdge(v-1,u-1,cap);
	}
	printf("%lld\n",dinic.MaxFlow(0,N-1));
	return 0;
}