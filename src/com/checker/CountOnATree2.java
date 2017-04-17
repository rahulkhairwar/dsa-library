//package com.checker;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

class CountOnATree2
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
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

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		int n, q, sqrtN, eulerSize, time, curr, max, uniqueCount;
		int[] depth, euler, first, entry, exit, entryExit, cnt, vis, log;
		int[][] dp, rmq;
		boolean[] addNext;
		Node[] nodes;
		Query[] queries;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			q = in.nextInt();
			sqrtN = (int) Math.sqrt(n);
			eulerSize = (n << 1) - 1;
			depth = new int[n];
			euler = new int[eulerSize];
			first = new int[n];
			entry = new int[n];
			exit = new int[n];
			entryExit = new int[(n << 1) + 1];
			vis = new int[n];
			addNext = new boolean[q << 1];
			nodes = new Node[n];
			queries = new Query[q << 1];
			dp = new int[19][n + 1];

			for (int i = 0; i < n; i++)
				nodes[i] = new Node(i, in.nextInt());

			int[] x = new int[n];
			int[] y = new int[n];

			for (int i = 1; i < n; i++)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				nodes[u].deg++;
				nodes[v].deg++;
				x[i] = u;
				y[i] = v;
			}

			for (int i = 0; i < n; i++)
			{
				nodes[i].adj = new int[nodes[i].deg];
				nodes[i].deg = 0;
			}

			for (int i = 1; i < n; i++)
			{
				int u = x[i];
				int v = y[i];

				nodes[u].adj[nodes[u].deg++] = v;
				nodes[v].adj[nodes[v].deg++] = u;
			}

			compress();
			curr = 0;
			dfs(0, -1, 0);
			curr = 0;
			compute();

/*			System.out.println("euler : " + Arrays.toString(euler) + "\nfirst : " + Arrays.toString(first) + "\ndepth"
					+ " : " + Arrays.toString(depth) + "\nentry : " + Arrays.toString(entry) + "\nexit : " + Arrays
					.toString(exit) + "\nentryExit : " + Arrays.toString(entryExit));*/

			for (int i = 0; i < q; i++)
			{
				int l = in.nextInt() - 1;
				int r = in.nextInt() - 1;

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

/*			for (int i = 0; i < curr; i++)
				System.out.println("i : " + i + ", " + queries[i]);*/

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

//				System.out.println("q.ind : " + queries[i].ind + ", lca : " + queries[i].lca + ", wt : " +
//						nodes[queries[i].lca].weight);

				if (addNext[queries[i].ind] && cnt[nodes[queries[i].lca].weight] > 0)
					ans[queries[i].ind]--;

//				System.out.println("i : " + i + ", q.l : " + queries[i].left + ", q.r : " + queries[i].right + ", ans"
//						+ " : " + uniqueCount);
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

		void compute()
		{
			int lim = (n << 1) - 1;

			log = new int[lim + 1];

			for (int i = 2; i <= lim; i++)
				log[i] = log[i >> 1] + 1;

			rmq = new int[log[lim] + 1][lim];

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
			int[] adj;

			Node(int ind, int weight)
			{
				this.ind = ind;
				this.weight = weight;
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

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public int read()
		{
			if (numChars == -1)
				throw new InputMismatchException();

			if (curChar >= numChars)
			{
				curChar = 0;
				try
				{
					numChars = stream.read(buf);
				}
				catch (IOException e)
				{
					throw new InputMismatchException();
				}
				if (numChars <= 0)
					return -1;
			}

			return buf[curChar++];
		}

		public int nextInt()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sgn = 1;

			if (c == '-')
			{
				sgn = -1;
				c = read();
			}

			int res = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				res *= 10;
				res += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return res * sgn;
		}

		public boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public void close()
		{
			try
			{
				stream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		public InputReader(InputStream stream)
		{
			this.stream = stream;
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
