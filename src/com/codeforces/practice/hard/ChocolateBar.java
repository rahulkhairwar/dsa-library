package com.codeforces.practice.hard;

import java.io.*;
import java.util.*;

public final class ChocolateBar
{
	public static void main(String[] args)
	{
		new ChocolateBar(System.in, System.out);
	}

	private static class Solver
	{
		static final long INF = (long) 1e9;
		int t, n, m, k;
		long[][][] dp;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();
			dp = new long[31][31][51];

			for (int i = 0; i < 31; i++)
				for (int j = 0; j < 31; j++)
					Arrays.fill(dp[i][j], -1);

			while (t-- > 0)
			{
				n = in.nextInt();
				m = in.nextInt();
				k = in.nextInt();
				out.println(find(n, m, k));
			}
		}

		long find(int len, int bre, int needed)
		{
			if (needed == 0 || needed == len * bre)
				return 0;

			if (dp[len][bre][needed] != -1)
				return dp[len][bre][needed];

			long ans = INF;

			for (int i = 1; i < len; i++)
				for (int j = Math.min(needed, i * bre); j >= 0; j--)
					if ((len - i) * bre >= needed - j)
						ans = Math.min(ans, bre * bre + find(i, bre, j) + find(len - i, bre, needed - j));

			for (int i = 1; i < bre; i++)
				for (int j = Math.min(needed, i * len); j >= 0; j--)
					if (len * (bre - i) >= needed - j)
						ans = Math.min(ans, len * len + find(len, i, j) + find(len, bre - i, needed - j));

			return dp[len][bre][needed] = ans;
		}

		Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	private static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		int read()
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

		int nextInt()
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

		boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		void close()
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

		InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	ChocolateBar(InputStream inputStream, OutputStream outputStream)
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