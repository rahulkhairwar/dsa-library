package com.hackerearth.competitions.hack_a_heart.tiebreaker;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

class TaskB
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
		final long mod = (long) 1e9 + 7;
		int n, m, lim;
		boolean[] isComposite;
		int[][] mat;
		long[][] ways;
		boolean reached;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			pre();
			n = in.nextInt();
			m = in.nextInt();
			mat = new int[n][m];
			ways = new long[n][m];

			for (int i = 0; i < n; i++)
				Arrays.fill(ways[i], -1);

			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++)
					mat[i][j] = in.nextInt();

			dfs(0, 0);
			out.println(ways[0][0]);

			if (ways[0][0] != 0)
				printPath(0, 0);
		}

		void printPath(int x, int y)
		{
			out.println(x + 1 + " " + (y + 1));

			if (x == n - 1 && y == m - 1)
			{
				reached = true;

				return;
			}

			if (x < n - 1 && y < m - 1)
			{
				if (ways[x + 1][y + 1] > 0)
				{
					printPath(x + 1, y + 1);

					return;
				}
			}

			if (x < n - 1)
			{
				if (ways[x + 1][y] > 0)
				{
					printPath(x + 1, y);

					return;
				}
			}

			if (y < m - 1 && ways[x][y + 1] > 0)
				printPath(x, y + 1);
		}

		long dfs(int x, int y)
		{
			if (x < 0 || x >= n || y < 0 || y >= m)
				return 0;

			if (ways[x][y] != -1)
				return ways[x][y];

			if (isComposite[mat[x][y]])
				return ways[x][y] = 0;

			if (x == n - 1 && y == m - 1)
			{
				if (isComposite[mat[x][y]])
					return ways[x][y] = 0;

				return ways[x][y] = 1;
			}

			long ans = CMath.mod(dfs(x + 1, y) + dfs(x, y + 1), mod);

			return ways[x][y] = CMath.mod(ans + dfs(x + 1, y + 1), mod);
		}

		void pre()
		{
			lim = (int) 1e5;
			isComposite = new boolean[lim + 5];
			isComposite[1] = true;

			int curr = 2;

			while ((curr << 1) <= lim)
			{
				isComposite[curr << 1] = true;
				curr++;
			}

			for (int i = 3; i <= lim; i += 2)
			{
				if (isComposite[i])
					continue;

				curr = i + i;

				while (curr <= lim)
				{
					isComposite[curr] = true;
					curr += i;
				}
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

	static class CMath
	{
		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

}
