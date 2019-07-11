package com.a2oj.groupcontests.feb14_2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public final class Cycles
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
		InputReader in;
		PrintWriter out;

		void solve()
		{
			int k = in.nextInt();
			boolean[][] adj = new boolean[105][105];
			int n = 2;

			adj[0][1] = adj[1][0] = true;

			for (int i = 2; k > 0; i++)
			{
				int deg = n;

				while (deg * (deg - 1) / 2 > k)
					deg--;

				k -= deg * (deg - 1) / 2;
				n++;

				for (int j = 0; j < deg; j++)
					adj[i][j] = adj[j][i] = true;
			}

			out.println(n);

			StringBuilder ans = new StringBuilder();

			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < n; j++)
					ans.append(adj[i][j] ? '1' : '0');

				ans.append('\n');
			}

			out.println(ans);
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
