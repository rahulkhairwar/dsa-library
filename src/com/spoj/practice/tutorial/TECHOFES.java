package com.spoj.practice.tutorial;

import java.io.*;
import java.util.*;

class TECHOFES
{
	public static void main(String[] args)
	{
		new TECHOFES(System.in, System.out);
	}

	static class Solver
	{
		int t, n, m, sz;
		int[] cost, pop, dp;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				m = in.nextInt();
				sz = (1 << n) + 1;
				cost = new int[n];
				pop = new int[n];
				dp = new int[sz];

				for (int i = 0; i < n; i++)
				{
					cost[i] = in.nextInt();
					pop[i] = in.nextInt();
				}

				Arrays.fill(dp, -1);
				out.println(find(0, m));
			}
		}

		int find(int mask, int money)
		{
			if (Integer.bitCount(mask) == n)
				return 0;

			if (dp[mask] != -1)
				return dp[mask];

			for (int i = 0; i < n; i++)
				if ((mask & (1 << i)) == 0 && cost[i] <= money)
					dp[mask] = Math.max(dp[mask], pop[i] + find(mask | (1 << i), money - cost[i]));

			if (dp[mask] == -1)
				dp[mask] = 0;

			return dp[mask];
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

	private TECHOFES(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
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

}
