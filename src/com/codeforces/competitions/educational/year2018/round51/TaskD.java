package com.codeforces.competitions.educational.year2018.round51;

import java.io.*;
import java.util.*;

public class TaskD
{
	public static void main(String[] args)
	{
		new TaskD(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final long MOD = 998244353;
		int n, k;
		long[][][] dp;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			k = in.nextInt();
			dp = new long[n][k + 1][4];

			for (int i = 0; i < n; i++)
				for (int j = 0; j <= k; j++)
					Arrays.fill(dp[i][j], -1);

			for (int i = 0; i < 4; i++)
				dp[0][0][i] = 0;

			if (k >= 1)
				dp[0][1][0] = dp[0][1][3] = 1;

			if (k >= 2)
				dp[0][2][1] = dp[0][2][2] = 1;

			long ans = 0;

			for (int i = 0; i < 4; i++)
				ans = CMath.mod(ans + find(n - 1, k, i), MOD);

			out.println(ans);
		}

		long find(int ind, int comp, int mask)
		{
			if (ind == -1 || comp <= 0)
				return 0;

			if (dp[ind][comp][mask] != -1)
				return dp[ind][comp][mask];

			long cnt;

			// same no. of components at ind - 1
			if (mask == 0)
				cnt = CMath.mod(MOD, find(ind - 1, comp, 0), find(ind - 1, comp, 1), find(ind - 1, comp, 2));
			else if (mask == 1)
				cnt = CMath.mod(find(ind - 1, comp, 1), MOD);
			else if (mask == 2)
				cnt = CMath.mod(find(ind - 1, comp, 2), MOD);
			else
				cnt = CMath.mod(MOD, find(ind - 1, comp, 1), find(ind - 1, comp, 2), find(ind - 1, comp, 3));

			// comp - 1 no. of components at ind - 1
			if (mask == 0)
				cnt = CMath.mod(cnt + find(ind - 1, comp - 1, 3), MOD);
			else if (mask == 1)
				cnt = CMath.mod(MOD, cnt, find(ind - 1, comp - 1, 0), find(ind - 1, comp - 1, 3));
			else if (mask == 2)
				cnt = CMath.mod(MOD, cnt, find(ind - 1, comp - 1, 0), find(ind - 1, comp - 1, 3));
			else if (mask == 3)
				cnt = CMath.mod(cnt + find(ind - 1, comp - 1, 0), MOD);

			// comp - 2 no. of components at ind - 1
			if (mask == 1)
				cnt = CMath.mod(cnt + find(ind - 1, comp - 2, 2), MOD);
			else if (mask == 2)
				cnt = CMath.mod(cnt + find(ind - 1, comp - 2, 1), MOD);

			return dp[ind][comp][mask] = cnt;
		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override public void run()
		{
			try
			{
				solve();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
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
			return number % mod;
		}

		static long mod(long mod, long... numbers)
		{
			long modSum = numbers[0];

			for (int i = 1; i < numbers.length; i++)
				modSum = mod(modSum + numbers[i], mod);

			return modSum;
		}

	}

	public TaskD(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskD", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			in.close();
			out.flush();
			out.close();
		}
	}

}
