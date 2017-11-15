package com.a2onlinejudge.ladder.CodeforcesDiv2D;

import java.io.*;
import java.util.*;

public class Problem8
{
	public static void main(String[] args)
	{
		new Problem8(System.in, System.out);
	}

	private static class Solver implements Runnable
	{
		static final int MOD = (int) 1e8;
		int n1, n2, k1, k2;
		long[][][][] dp;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n1 = in.nextInt();
			n2 = in.nextInt();
			k1 = in.nextInt();
			k2 = in.nextInt();
			dp = new long[n1 + 1][n2 + 1][k1 + 1][k2 + 1];

			for (int i = 0; i <= n1; i++)
				for (int j = 0; j <= n2; j++)
					for (int k = 0; k <= k1; k++)
						Arrays.fill(dp[i][j][k], -1);

			out.println(find(n1, n2, 0, 0));
		}

		long find(int rem1, int rem2, int cont1, int cont2)
		{
			if (rem1 == 0 && rem2 == 0)
				return dp[0][0][cont1][cont2] = 1;

			if (dp[rem1][rem2][cont1][cont2] != -1)
				return dp[rem1][rem2][cont1][cont2];

			long ans = 0;

			if (cont2 < k2 && rem2 > 0)
				ans = CMath.mod(ans + find(rem1, rem2 - 1, 0, cont2 + 1), MOD);

			if (cont1 < k1 && rem1 > 0)
				ans = CMath.mod(ans + find(rem1 - 1, rem2, cont1 + 1, 0), MOD);

			return dp[rem1][rem2][cont1][cont2] = ans;
		}

		Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
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

	private static class CMath
	{
		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

	private Problem8(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "Solver", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		in.close();
		out.flush();
		out.close();
	}

}
