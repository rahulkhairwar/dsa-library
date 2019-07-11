package com.a2oj.groupcontests.feb20_2017;

import java.io.*;
import java.util.*;

public class CountOnATree2
{
	public static void main(String[] args)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);

/*		try
		{
			in = new InputReader(new FileInputStream(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA "
					+ "Workspace/Competitive Programming/src/com/checker/input.txt")));
			out = new PrintWriter(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
					+ "Programming/src/com/checker/output.txt"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}*/

		Solver solver = new Solver(in, out);

		try
		{
			solver.solve();
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

	static class Solver
	{
		int n, q, sqrtN, eulerSize, time, curr, max, uniqueCount, maxN, maxQ;
		int[] depth, euler, first, entry, exit, entryExit, cnt, vis, log;
		int[][] dp, rmq;
		boolean[] addNext;
		Node[] nodes;
		Query[] queries;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			maxN = (int) 4e4 + 5;
			maxQ = (int) 1e5 + 5;
			depth = new int[maxN];
			euler = new int[maxN << 1];
			first = new int[maxN];
			entry = new int[maxN];
			exit = new int[maxN];
			entryExit = new int[(maxN << 1) + 1];
			vis = new int[maxN];
			addNext = new boolean[maxQ << 1];
			nodes = new Node[maxN];
			queries = new Query[maxQ << 1];
			dp = new int[19][maxN + 1];

			for (int i = 0; i < maxN; i++)
				nodes[i] = new Node(0, 0);

			pre();
			String s;

			while ((s = in.readLine()) != null)
			{
				String[] tok = s.split(" ");

				n = Integer.parseInt(tok[0]);
				q = Integer.parseInt(tok[1]);
				go();
			}
		}

		void go() throws IOException
		{
			sqrtN = (int) Math.sqrt(n);
			eulerSize = (n << 1) - 1;

			for (int i = n << 1; i >= 0; i--)
				euler[i] = entryExit[i] = 0;

			for (int i = q << 1; i >= 0; i--)
				addNext[i] = false;

			String[] tok = in.readLine().split(" ");

			for (int i = 0; i < n; i++)
			{
				depth[i] = first[i] = entry[i] = exit[i] = vis[i] = 0;
				nodes[i].ind = i;
				nodes[i].weight = Integer.parseInt(tok[i]);
				nodes[i].adj.clear();
			}

			for (int i = 1; i < n; i++)
			{
				tok = in.readLine().split(" ");
				int u = Integer.parseInt(tok[0]) - 1;
				int v = Integer.parseInt(tok[1]) - 1;

				nodes[u].adj.add(v);
				nodes[v].adj.add(u);
			}

			compress();
			curr = 0;
			dfs(0, -1, 0);
			curr = 0;
			compute();

			for (int i = 0; i < q; i++)
			{
				tok = in.readLine().split(" ");
				int l = Integer.parseInt(tok[0]) - 1;
				int r = Integer.parseInt(tok[1]) - 1;

				if (first[l] > first[r])
				{
					int temp = l;

					l = r;
					r = temp;
				}

				int lca = getLCA(first[l], first[r]);

				if (lca == l)
					queries[curr] = new Query(curr++, entry[l], entry[r]);
				else
				{
					queries[curr] = new Query(curr, exit[l], entry[r]);
					queries[curr].lca = lca;
					addNext[curr++] = true;
					queries[curr] = new Query(curr++, entry[lca], entry[lca]);
				}
			}

			Arrays.sort(queries, 0, curr, new Comparator<Query>()
			{
				@Override public int compare(Query o1, Query o2)
				{
					if (o1.block == o2.block)
						return Integer.compare(o1.right, o2.right);

					return Integer.compare(o1.block, o2.block);
				}
			});

			int left, right;
			int[] ans = new int[curr];

			left = 0;
			right = -1;
			cnt = new int[max];

			for (int i = 0; i < curr; i++)
			{
				while (left > queries[i].left)
				{
					left--;
					add(left);
				}

				while (right < queries[i].right)
				{
					right++;
					add(right);
				}

				while (left < queries[i].left)
				{
					remove(left);
					left++;
				}

				while (right > queries[i].right)
				{
					remove(right);
					right--;
				}

				ans[queries[i].ind] = uniqueCount;

				if (addNext[queries[i].ind] && cnt[nodes[queries[i].lca].weight] > 0)
					ans[queries[i].ind]--;
			}

			for (int i = 0; i < curr; i++)
			{
				int xx = ans[i];

				if (addNext[i])
					xx += ans[++i];

				out.println(xx);
			}
		}

		void dfs(int node, int par, int dep)
		{
			euler[curr] = node;
			first[node] = curr++;
			depth[node] = dep;
			entry[node] = time;
			entryExit[time++] = node;

			for (int x : nodes[node].adj)
			{
				if (x == par)
					continue;

				dfs(x, node, dep + 1);
				euler[curr++] = node;
			}

			exit[node] = time;
			entryExit[time++] = node;
		}

		void pre()
		{
			int lim = (maxN << 1) - 1;

			log = new int[lim + 1];

			for (int i = 2; i <= lim; i++)
				log[i] = log[i >> 1] + 1;

			rmq = new int[log[lim] + 1][lim];
		}

		void compute()
		{
			int lim = (n << 1) - 1;

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
			int k = log[v - u];
			int x = rmq[k][u];
			int y = rmq[k][v - (1 << k) + 1];

			return depth[x] <= depth[y] ? x : y;
		}

		void add(int ind)
		{
			int x = nodes[entryExit[ind]].weight;

			vis[entryExit[ind]]++;

			if (vis[entryExit[ind]] == 1)
			{
				cnt[x]++;

				if (cnt[x] == 1)
					uniqueCount++;
			}
			else if (vis[entryExit[ind]] == 2)
			{
				cnt[x]--;

				if (cnt[x] == 0)
					uniqueCount--;
			}
		}

		void remove(int ind)
		{
			int x = nodes[entryExit[ind]].weight;

			vis[entryExit[ind]]--;

			if (vis[entryExit[ind]] == 0)
			{
				cnt[x]--;

				if (cnt[x] == 0)
					uniqueCount--;
			}
			else if (vis[entryExit[ind]] == 1)
			{
				cnt[x]++;

				if (cnt[x] == 1)
					uniqueCount++;
			}
		}

		void compress()
		{
			int[] weights = new int[n];
			Map<Integer, Integer> map = new HashMap<>();

			for (int i = 0; i < n; i++)
				weights[i] = nodes[i].weight;

			Arrays.sort(weights);
			curr = 1;

			for (int i = 0; i < n; i++)
				if (!map.containsKey(weights[i]))
					map.put(weights[i], curr++);

			max = curr + 1;

			for (int i = 0; i < n; i++)
				nodes[i].weight = map.get(nodes[i].weight);
		}

		void debug(Object... o)
		{
			System.err.println(Arrays.deepToString(o));
		}

		class Node
		{
			int ind, deg, weight;
//			int[] adj;
			List<Integer> adj;

			Node(int ind, int weight)
			{
				this.ind = ind;
				this.weight = weight;
				adj = new ArrayList<>();
			}

		}

		class Query
		{
			int ind, block, left, right, lca;

			Query(int ind, int left, int right)
			{
				this.ind = ind;
				this.left = left;
				this.right = right;
				block = left / sqrtN;
			}

			@Override public String toString()
			{
				return String.format("ind : %d, l : %d, r : %d, block : %d", ind, left, right, block);
			}
		}

		public Solver(BufferedReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

}

/*

18 5
5 6 2 2 2 3 8 4 2 5 6 8 5 3 3 3 4 2
1 2
2 5
5 13
5 12
5 11
2 4
1 3
3 14
14 15
1 6
6 8
6 9
9 16
9 17
6 10
10 18
6 7
5 18
12 7
8 17
14 15
4 7

*/
