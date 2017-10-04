package com.codeforces.practice.hard.year2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public final class MemoryAndScores
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
		static final long mod = (long) 1e9 + 7;
		int a, b, k, t;
		long[][] dpA, dpB;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			a = in.nextInt();
			b = in.nextInt();
			k = in.nextInt();
			t = in.nextInt();
			a += k * t;
			b += k * t;

			int limA = a + ((k * t) << 1) + 5;
			int limB = b + ((k * t) << 1) + 5;
			int[] minA = new int[t + 1];
			int[] minB = new int[t + 1];
			int[] maxA = new int[t + 1];
			int[] maxB = new int[t + 1];

			dpA = new long[t + 1][limA];
			dpB = new long[t + 1][limB];
			dpA[0][a] = dpB[0][b] = 1;
			minA[0] = maxA[0] = a;
			minB[0] = maxB[0] = b;

			for (int i = 0; i < t; i++)
			{
				for (int j = minA[i]; j <= maxA[i]; j++)
				{
					if (dpA[i][j] != 0)
					{
						int x = Math.max(0, j - k);

						dpA[i + 1][x] = (dpA[i + 1][x] + dpA[i][j]) % mod;
						dpA[i + 1][j + k + 1] = (dpA[i + 1][j + k + 1] - dpA[i][j]) % mod;
						minA[i + 1] = Math.min(minA[i + 1], x);
						maxA[i + 1] = j + k + 1;
					}
				}

				for (int j = 1; j < limA; j++)
					dpA[i + 1][j] = (dpA[i + 1][j] + dpA[i + 1][j - 1]) % mod;

				for (int j = minB[i]; j <= maxB[i]; j++)
				{
					if (dpB[i][j] != 0)
					{
						int x = Math.max(0, j - k);

						dpB[i + 1][x] = (dpB[i + 1][x] + dpB[i][j]) % mod;
						dpB[i + 1][j + k + 1] = (dpB[i + 1][j + k + 1] - dpB[i][j]) % mod;
						minB[i + 1] = Math.min(minB[i + 1], x);
						maxB[i + 1] = j + k + 1;
					}
				}

				for (int j = 1; j < limB; j++)
					dpB[i + 1][j] = (dpB[i + 1][j] + dpB[i + 1][j - 1]) % mod;
			}

			for (int i = 1; i < limB; i++)
				dpB[t][i] = (dpB[t][i] + dpB[t][i - 1]) % mod;

			long cnt = 0;
			int x = limB - 1;

			for (int i = 1; i < limA; i++)
			{
				if (i - 1 < limB)
					cnt = (cnt + ((dpA[t][i] * dpB[t][i - 1]) % mod)) % mod;
				else
					cnt = (cnt + ((dpA[t][i] * dpB[t][x]) % mod)) % mod;
			}

			while (cnt < 0)
				cnt += mod;

			out.println(cnt);
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
