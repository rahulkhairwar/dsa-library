package com.a2oj.groupcontests.jan17_2017;

import java.io.*;
import java.util.*;

public final class LittlePonyAndHarmonyChest
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
		static final int INFINITY = (int) 1e9;
		int n;
		int[] a, b, primes, factorSet, min;
		int[][] dp;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			a = new int[n];
			b = new int[n];
			primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59};
			factorSet = new int[60];

			for (int i = 0; i < n; i++)
				a[i] = in.nextInt();

			int pCnt = primes.length;
			int maxSize = 1 << pCnt;

			for (int i = 1; i < 60; i++)
				for (int j = 0; j < pCnt; j++)
					if (i % primes[j] == 0)
						factorSet[i] |= 1 << j;

			min = new int[n];
			dp = new int[n][maxSize];
			Arrays.fill(min, INFINITY);

			for (int i = 0; i < n; i++)
				Arrays.fill(dp[i], INFINITY);

			int val, mask, curr;

			val = find(0, 0);
			mask = curr = 0;

			for (int i = 0; i < n; i++)
			{
				for (int j = 1; j < 60; j++)
				{
					if ((mask & factorSet[j]) != 0)
						continue;

					if (Math.abs(a[i] - j) + curr + find(i + 1, mask | factorSet[j]) == val)
					{
						b[i] = j;
						curr += Math.abs(a[i] - j);
						mask |= factorSet[j];

						break;
					}
				}
			}

			for (int x : b)
				out.print(x + " ");
		}

		int find(int ind, int mask)
		{
			if (ind == n)
				return 0;

			if (dp[ind][mask] != INFINITY)
				return dp[ind][mask];

			for (int i = 1; i < 60; i++)
			{
				if ((factorSet[i] & mask) == 0)
				{
					int x = Math.abs(a[ind] - i) + find(ind + 1, mask | factorSet[i]);

					if (x < dp[ind][mask])
						dp[ind][mask] = x;
				}
			}

			return dp[ind][mask];
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
