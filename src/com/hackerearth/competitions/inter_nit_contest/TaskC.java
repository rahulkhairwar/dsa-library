package com.hackerearth.competitions.inter_nit_contest;

import java.io.*;
import java.util.*;

public final class TaskC
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
		static final long INFINITY = Long.MAX_VALUE >> 1;
		int v, e;
		long cnt;
		long[] dist;
		int[] par, parEdge;
		boolean[] vis;
		List<Integer>[] adj;
		Edge[] edges;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			v = in.nextInt();
			e = in.nextInt();
			dist = new long[v];
			par = new int[v];
			parEdge = new int[v];
			vis = new boolean[v];
			adj = new List[v];
			edges = new Edge[e];

			for (int i = 0; i < v; i++)
				adj[i] = new ArrayList<>();

			long tot = 0;

			for (int i = 0; i < e; i++)
			{
				int fr, to, wt;

				fr = in.nextInt() - 1;
				to = in.nextInt() - 1;
				wt = in.nextInt();
				tot += wt;
				edges[i] = new Edge(fr, to, wt);
				adj[fr].add(i);
				adj[to].add(i);
			}

			dijkstra();
			mark(0);

			for (int i = 0; i < e; i++)
			{
				if (edges[i].changed)
					continue;

				long d1, d2, ext, cand1, cand2;

				d1 = dist[edges[i].from];
				d2 = dist[edges[i].to];
				ext = edges[i].weight;

				if (d1 + ext == d2)
					cand1 = 0;
				else
				{
					cand1 = d1 + ext - d2;
					cand1 = Math.min(cand1, ext);
				}

				if (d2 + ext == d1)
					cand2 = 0;
				else
				{
					cand2 = d2 + ext - d1;
					cand2 = Math.min(cand2, ext);
				}

				cnt += Math.min(cand1, cand2);
			}

			out.println(tot - cnt);
		}

		// ok.
		void mark(int node)
		{
			for (int x : adj[node])
			{
				int to = node == edges[x].to ? edges[x].from : edges[x].to;

				if (node == par[to] && !edges[x].changed)
				{
					edges[x].changed = true;
					mark(node == edges[x].to ? edges[x].from : edges[x].to);
				}
			}
		}

		// ok.
		void dijkstra()
		{
			Arrays.fill(dist, INFINITY);
			Arrays.fill(par, -1);
			dist[0] = 0;

			PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>()
			{
				@Override public int compare(Integer o1, Integer o2)
				{
					return Long.compare(dist[o1], dist[o2]);
				}
			});

			queue.add(0);

			while (queue.size() > 0)
			{
				int curr = queue.poll();

				for (int x : adj[curr])
				{
					int to, wt;

					to = edges[x].to == curr ? edges[x].from : edges[x].to;
					wt = edges[x].weight;

					if (dist[curr] + wt < dist[to])
					{
						dist[to] = dist[curr] + wt;
						par[to] = curr;
						parEdge[to] = x;
						queue.add(to);
					}
				}
			}
		}

		class Edge
		{
			int from, to, weight;
			boolean changed;

			Edge(int from, int to, int weight)
			{
				this.from = from;
				this.to = to;
				this.weight = weight;
			}

			@Override public String toString()
			{
				return String.format("(%d -> %d) :: %d", from, to, weight);
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

/*

3 3
1 2 5
2 3 10
1 3 5
: 10

5 8
5 4 17
3 4 2
3 5 14
1 3 13
2 5 11
4 2 20
1 4 14
5 1 10
: 63

*/
