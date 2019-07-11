package com.a2oj.groupcontests.dec28_2016;

import java.io.*;
import java.util.*;

public final class KefaAndDishes
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
		int n, m, k;
		int[] sat;
		int[][] extra;
		long[][] dp;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			k = in.nextInt();
			sat = new int[n + 1];
			extra = new int[n + 1][n + 1];    // extra[i][j] = satisfaction gained if j was eaten right before i.
			dp = new long[1 << n][n + 1];

			for (int i = 1; i <= n; i++)
				sat[i] = in.nextInt();

			for (int i = 0; i < k; i++)
			{
				int x, y, c;

				x = in.nextInt();
				y = in.nextInt();
				c = in.nextInt();
				extra[y][x] = c;
			}

			for (int i = 0; i < 1 << n; i++)
				Arrays.fill(dp[i], -1);

			out.println(find(0, 0));
		}

		long find(int mask, int prev)
		{
			if (mask == (1 << n))
				return 0;

			if (dp[mask][prev] != -1)
				return dp[mask][prev];

			if (Integer.bitCount(mask) == m)
				return dp[mask][prev] = 0;

			// can either eat this dish or not eat it.
			// eating this dish :
			long eat = 0;

			for (int i = 0; i < n; i++)
			{
				if ((mask & (1 << i)) == 0)    // this dish isn't eaten yet.
				{
					long temp = find(mask | (1 << i), i + 1) + sat[i + 1];

					if (prev != -1)
						temp += extra[i + 1][prev];

					eat = Math.max(eat, temp);
				}
			}

			// not eating this dish :
			long dontEat = 0;

			for (int i = 0; i < n; i++)
				if ((mask & (1 << i)) == 0)
					dontEat = Math.max(dontEat, find(mask | (1 << i), prev));

			return dp[mask][prev] = Math.max(eat, dontEat);
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

		public int[] nextIntArray(int arraySize)
		{
			int array[] = new int[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextInt();

			return array;
		}

		public long nextLong()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sign = 1;

			if (c == '-')
			{
				sign = -1;

				c = read();
			}

			long result = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				result *= 10;
				result += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return result * sign;
		}

		public long[] nextLongArray(int arraySize)
		{
			long array[] = new long[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextLong();

			return array;
		}

		public float nextFloat()
		{
			float result, div;
			byte c;

			result = 0;
			div = 1;
			c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean isNegative = (c == '-');

			if (isNegative)
				c = (byte) read();

			do
			{
				result = result * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					result += (c - '0') / (div *= 10);

			if (isNegative)
				return -result;

			return result;
		}

		public double nextDouble()
		{
			double ret = 0, div = 1;
			byte c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean neg = (c == '-');

			if (neg)
				c = (byte) read();

			do
			{
				ret = ret * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);

			if (neg)
				return -ret;

			return ret;
		}

		public String next()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			StringBuilder res = new StringBuilder();

			do
			{
				res.appendCodePoint(c);

				c = read();
			} while (!isSpaceChar(c));

			return res.toString();
		}

		public boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public String nextLine()
		{
			int c = read();

			StringBuilder result = new StringBuilder();

			do
			{
				result.appendCodePoint(c);

				c = read();
			} while (!isNewLine(c));

			return result.toString();
		}

		public boolean isNewLine(int c)
		{
			return c == '\n';
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
