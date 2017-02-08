package com.hackerrank.competitions.year2017.codeagon;

import java.io.*;
import java.util.*;

public final class TaskD
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
		int q, n, m;
		int[] og;
		boolean[] vis, need;
		Set<Integer>[] adj, rev;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			int lim = (int) 2e4 + 5;

			q = in.nextInt();
			og = new int[lim];
			vis = new boolean[lim];
			need = new boolean[lim];
			adj = new Set[lim];
			rev = new Set[lim];

			while (q-- > 0)
			{
				n = in.nextInt();
				m = in.nextInt();

				for (int i = 0; i < n; i++)
				{
					og[i] = 0;
					vis[i] = need[i] = false;
					adj[i] = new HashSet<>();
					rev[i] = new HashSet<>();
				}

				for (int i = 0; i < n; i++)
				{
					int cnt = in.nextInt();

					while (cnt-- > 0)
					{
						int par = in.nextInt() - 1;

						og[i]++;
						adj[i].add(par);
						rev[par].add(i);
					}
				}

				for (int i = 0; i < m; i++)
				{
					int x = in.nextInt() - 1;

					if (!vis[x])
						dfs(x);
				}

				PriorityQueue<Integer> queue = new PriorityQueue<>(n, new Comparator<Integer>()
				{
					@Override public int compare(Integer o1, Integer o2)
					{
						if (og[o1] == og[o2])
							return Integer.compare(o1, o2);

						return Integer.compare(og[o1], og[o2]);
					}
				});

				for (int i = 0; i < n; i++)
					if (need[i] && og[i] == 0)
						queue.add(i);

				List<Integer> ans = new ArrayList<>();

				while (true)
				{
					int x = queue.poll();

					ans.add(x + 1);

					for (int to : rev[x])
					{
						if (!need[to])
							continue;

						og[to]--;

						if (og[to] == 0)
							queue.add(to);
					}

					if (queue.size() == 0)
						break;
				}

				out.println(ans.size());

				for (int x : ans)
					out.print(x + " ");

				out.println();
			}
		}

		void dfs(int node)
		{
			if (vis[node])
				return;

			vis[node] = true;
			need[node] = true;

			for (int x : adj[node])
				dfs(x);
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
