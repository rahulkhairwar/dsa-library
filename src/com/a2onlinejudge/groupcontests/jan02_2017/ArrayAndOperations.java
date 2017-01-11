package com.a2onlinejudge.groupcontests.jan02_2017;

import java.io.*;
import java.util.*;

public final class ArrayAndOperations
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		static final int INFINITY = (int) 1e9;
		int n, m, v, s, t, flow, maxFlow;
		Set<Integer> primes;
		Map<Integer, int[]> map;
		int[] arr, par, parEdge;
		boolean[][] aa;
		Edge[] edges;
		List<Integer>[] adj;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			v = n + 2;
			s = 0;
			t = n + 1;
			arr = new int[n + 1];
			par = new int[v];
			parEdge = new int[v];
			edges = new Edge[10 * m + 10 * n];
			aa = new boolean[v][v];
			adj = new List[v];
			map = new HashMap<>();

			for (int i = 1; i <= n; i++)
				arr[i] = in.nextInt();

			findPrimes();

			for (int i = 0; i < n + 2; i++)
				adj[i] = new ArrayList<>();

			for (int i = 0; i < m; i++)
			{
				int u, v;

				u = in.nextInt();
				v = in.nextInt();
				aa[u][v] = aa[v][u] = true;
			}

			for (int p : primes)
				solveForPrime(p);

			out.println(maxFlow);
		}

		void findPrimes()
		{
			primes = new HashSet<>();

			for (int i = 1; i <= n; i++)
			{
				int sqrt = (int) Math.sqrt(arr[i]);
				int temp = arr[i];

				for (int j = 2; j <= sqrt; j++)
				{
					int cnt = 0;

					while (temp % j == 0)
					{
						temp /= j;
						cnt++;
					}

					if (cnt > 0)
					{
						int[] xx = map.get(j);

						if (xx == null)
						{
							xx = new int[n + 1];
							map.put(j, xx);
							primes.add(j);
						}

						xx[i] = cnt;
					}

					if (temp == 1)
						break;
				}

				if (temp != 1)
				{
					int[] xx = map.get(temp);

					if (xx == null)
					{
						xx = new int[n + 1];
						map.put(temp, xx);
						primes.add(temp);
					}

					xx[i] = 1;
				}
			}
		}

		void solveForPrime(int prime)
		{
			int ctr = 0;

			for (int i = 1; i < v - 1; i += 2)
			{
				int xx = getCount(arr[i], prime);

				edges[ctr] = new Edge(i, xx, -1);
				adj[s].add(ctr++);
				edges[ctr] = new Edge(s, 0, -1);
				adj[i].add(ctr++);

				for (int j = 1; j < v - 1; j++)
				{
					if (aa[i][j])
					{
						// add an edge from i to j.
						edges[ctr] = new Edge(j, 0, -1);
						adj[i].add(ctr++);
						edges[ctr] = new Edge(i, 0, -1);
						adj[j].add(ctr++);
						assignWeight(edges[ctr - 2], prime);
					}
				}

				if (i + 1 < v - 1)
				{
					xx = getCount(arr[i + 1], prime);

					edges[ctr] = new Edge(t, xx, -1);
					adj[i + 1].add(ctr++);
					edges[ctr] = new Edge(i + 1, 0, -1);
					adj[t].add(ctr++);
				}
			}

			int[] dist = new int[v];

			while (true)
			{
				Queue<Integer> queue = new LinkedList<>();

				flow = 0;
				Arrays.fill(par, -1);
				Arrays.fill(parEdge, -1);
				Arrays.fill(dist, INFINITY);
				dist[s] = 0;
				queue.add(s);

				while (queue.size() > 0)
				{
					int curr = queue.poll();

					if (curr == t)
						break;    // breaking immediately if reached the sink.

					for (int ed : adj[curr])
					{
						int to = edges[ed].to;
						int weight = edges[ed].weight;

						if (weight > 0 && dist[to] == INFINITY)
						{
							dist[to] = dist[curr] + 1;
							queue.add(to);
							par[to] = curr;
							parEdge[to] = ed;
						}
					}
				}

				augment(t, INFINITY);

				if (flow == 0)
					break;

				maxFlow += flow;
			}
		}

		void assignWeight(Edge a, int prime)
		{
			a.weight = getCount(arr[a.to], prime);
		}

		void augment(int node, int minEdge)
		{
			if (node == s)
				flow = minEdge;
			else if (par[node] != -1)
			{
				int ed = parEdge[node];
				int weight = edges[ed].weight;
				int oppEdge = edges[ed].oppEdge;

				augment(par[node], Math.min(minEdge, weight));
				edges[ed].weight -= flow;
				edges[ed ^ 1].weight += flow;

				if (oppEdge >= 0)
					edges[oppEdge].weight -= flow;
			}
		}

		int getCount(int num, int prime)
		{
			int cnt = 0;

			while (num % prime == 0)
			{
				num /= prime;
				cnt++;
			}

			return cnt;
		}

		class Edge
		{
			int to, weight, oppEdge;

			public Edge(int to, int weight, int oppEdge)
			{
				this.to = to;
				this.weight = weight;
				this.oppEdge = oppEdge;
			}

			@Override public String toString()
			{
				return String.format("to : %d, wt : %d, oppEdge : %d", to, weight, oppEdge);
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

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

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

	}

}
