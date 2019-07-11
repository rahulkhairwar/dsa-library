package com.a2oj.ladder.CodeforcesDiv2D;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Problem5
{
	public static void main(String[] args)
	{
		new Problem5(System.in, System.out);
	}

	private static class Solver implements Runnable
	{
		static final int MOD = (int) 1e9 + 7;
		int n, k;
		long[][] dp;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			k = in.nextInt();
			dp = new long[n + 1][k + 1];

			for (int i = 0; i <= n; i++)
				Arrays.fill(dp[i], -1);

			long ans = 0;

			for (int i = 1; i <= n; i++)
				ans = CMath.mod(ans + find(i, k - 1), MOD);

			out.println(ans);
		}

		long find(int prev, int rem)
		{
			if (prev > n)
				return 0;

			if (rem == 0)
				return dp[prev][rem] = 1;

			if (dp[prev][rem] != -1)
				return dp[prev][rem];

			long ans = 0;
			int ctr = 1;

			while (true)
			{
				if (prev * ctr > n)
					break;

				ans = CMath.mod(ans + find(prev * ctr, rem - 1), MOD);
				ctr++;
			}

			return dp[prev][rem] = ans;
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

	private Problem5(InputStream inputStream, OutputStream outputStream)
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
