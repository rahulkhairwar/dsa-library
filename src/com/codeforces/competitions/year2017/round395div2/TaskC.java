package com.codeforces.competitions.year2017.round395div2;

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
		int n, col[];
		List<Integer>[] adj;
		boolean[] valid, start;
		boolean poss;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			col = new int[n];
			adj = new ArrayList[n];
			valid = new boolean[n];
			start = new boolean[n];

			for (int i = 0; i < n; i++)
				adj[i] = new ArrayList<>();

			for (int i = 1; i < n; i++)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				adj[u].add(v);
				adj[v].add(u);
			}

			col = in.nextIntArray(n);
			Arrays.fill(valid, true);
			dfs(0, new HashMap<>(), -1);

			int cnt = 0;
			int at = 0;

			for (int i = 0; i < n; i++)
			{
				if (start[i])
				{
					cnt++;
					at = i;
				}
			}

			if (cnt > 1)
				out.println("NO");
			else
			{
				poss = true;
				Map<Integer, Integer> map = dfs2(at, -1, at);

				if (!poss || map.size() > 1)
					out.println("NO");
				else
					out.println("YES\n" + (at + 1));
			}
		}

		void dfs(int node, Map<Integer, Integer> map, int par)
		{
			for (int x : adj[node])
			{
				if (x == par)
					continue;

				Map<Integer, Integer> mm = new HashMap<>();

				if (node == 0)
					dfs(x, mm, node);
				else
					dfs(x, mm, node);

				for (Map.Entry<Integer, Integer> entry : mm.entrySet())
				{
					Integer cnt = map.get(entry.getKey());

					if (cnt == null)
						map.put(entry.getKey(), entry.getValue());
					else
						map.put(entry.getKey(), cnt + entry.getValue());
				}
			}

			if (node != 0)
			{
				Integer cnt = map.get(col[node]);

				if (cnt == null)
					map.put(col[node], 1);
				else
					map.put(col[node], cnt + 1);
			}

			for (int x : adj[node])
			{
				if (x == par)
					continue;

				if (node == 0)
				{
					if (!valid[x])
						valid[node] = false;
				}
				else
				{
					if (!valid[x])
						valid[node] = false;
				}
			}

			if (valid[node])
			{
				if (map.size() > 1)
				{
					start[node] = true;
					valid[node] = false;
				}
			}
		}

		Map<Integer, Integer> dfs2(int node, int par, int root)
		{
			Map<Integer, Integer> map = new HashMap<>();

			for (int x : adj[node])
			{
				if (x == par)
					continue;

				Map<Integer, Integer> mm = dfs2(x, node, root);

				if (mm.size() > 1)
					poss = false;

				if (node != root)
				{
					for (Map.Entry<Integer, Integer> entry : mm.entrySet())
					{
						Integer cnt = map.get(entry.getKey());

						if (cnt == null)
							map.put(entry.getKey(), entry.getValue());
						else
							map.put(entry.getKey(), cnt + entry.getValue());
					}
				}
			}

			if (node != root)
			{
				Integer cnt = map.get(col[node]);

				if (cnt == null)
					map.put(col[node], 1);
				else
					map.put(col[node], cnt + 1);
			}

			if (map.size() > 1)
				poss = false;

			return map;
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

		public int[] nextIntArray(int arraySize)
		{
			int array[] = new int[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextInt();

			return array;
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
