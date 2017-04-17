package com.a2onlinejudge.groupcontests.feb20_2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;

public final class CeilTheCommander
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
		int n, size;
		int[] deg, x, y, sub, ans;
		int[][] adj;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			deg = new int[n];
			x = new int[n];
			y = new int[n];
			sub = new int[n];
			ans = new int[n];
			adj = new int[n][];

			for (int i = 1; i < n; i++)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				deg[u]++;
				deg[v]++;
				x[i] = u;
				y[i] = v;
			}

			for (int i = 0; i < n; i++)
			{
				adj[i] = new int[deg[i]];
				deg[i] = 0;
			}

			for (int i = 1; i < n; i++)
			{
				int u = x[i];
				int v = y[i];

				adj[u][deg[u]++] = v;
				adj[v][deg[v]++] = u;
			}

			Arrays.fill(ans, -1);
			decompose(0, -1, 'A');
			StringBuilder sb = new StringBuilder("");

			for (int x : ans)
				sb.append((char) x).append(" ");

			out.println(sb.toString());
		}

		int getSize(int node, int par)
		{
			sub[node] = 1;

			for (int x : adj[node])
			{
				if (x == par || ans[x] != -1)
					continue;

				getSize(x, node);
				sub[node] += sub[x];
			}

			return sub[node];
		}

		void decompose(int node, int par, int ch)
		{
			if (par == -1)
				size = getSize(node, par);

			for (int x : adj[node])
			{
				if (x != par && ans[x] == -1 && sub[x] > size / 2)
				{
					decompose(x, node, ch);

					return;
				}
			}

			ans[node] = ch;

			for (int x : adj[node])
				if (ans[x] == -1)
					decompose(x, -1, ch + 1);
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
