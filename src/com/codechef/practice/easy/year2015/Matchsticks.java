package com.codechef.practice.easy.year2015;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

class Matchsticks
{
	static int n, q, left, right, bTime[], logTable[];
	static Interval rmq[][];
	static InputReader reader;

	public static void main(String[] args)
	{
		Matchsticks matchsticks = new Matchsticks();

		reader = matchsticks.new InputReader(System.in);

		getAttributes();

		reader.close();
	}
	
	static void getAttributes()
	{
		n = reader.nextInt();

		bTime = new int[n];

		for (int i = 0; i < n; i++)
			bTime[i] = reader.nextInt();

		preCompute();

		q = reader.nextInt();

		for (int i = 0; i < q; i++)
		{
			left = reader.nextInt();
			right = reader.nextInt();

			findBurningTime(left, right);
		}
	}

	static void preCompute()
	{
		logTable = new int[n + 1];

		for (int i = 2; i <= n; i++)
			logTable[i] = logTable[i >> 1] + 1;

		rmq = new Interval[logTable[n] + 1][n];

		for (int i = 0; i < n; i++)
			rmq[0][i] = new Interval(i, i);

		int minX, minY, maxX, maxY, min, max;

		for (int i = 1; (1 << i) < n; i++)
		{
			for (int j = 0; (1 << i) + j <= n; j++)
			{
				minX = rmq[i - 1][j].min;
				minY = rmq[i - 1][(1 << i - 1) + j].min;

				min = bTime[minX] <= bTime[minY] ? minX : minY;

				maxX = rmq[i - 1][j].max;
				maxY = rmq[i - 1][(1 << i - 1) + j].max;

				max = bTime[maxX] >= bTime[maxY] ? maxX : maxY;

				rmq[i][j] = new Interval(min, max);
			}
		}
	}
	
	static int findMinPos(int rSI, int rEI)
	{
		int k, x, y;

		k = logTable[rEI - rSI];
		x = rmq[k][rSI].min;
		y = rmq[k][rEI - (1 << k) + 1].min;

		return bTime[x] <= bTime[y] ? x : y;
	}

	static int findMaxPos(int rSI, int rEI)
	{
		int k, x, y;

		k = logTable[rEI - rSI];
		x = rmq[k][rSI].max;
		y = rmq[k][rEI - (1 << k) + 1].max;

		return bTime[x] >= bTime[y] ? x : y;
	}

	static void findBurningTime(int left, int right)
	{
		int minInRange, maxLeft, maxRight, maxInRange;
		double maxLeftInRange, decider;

		minInRange = bTime[findMinPos(left, right)];

		if (left == 0)
			maxLeft = -1;
		else
			maxLeft = bTime[findMaxPos(0, left - 1)];

		if (right == n - 1)
			maxRight = -1;
		else
			maxRight = bTime[findMaxPos(right + 1, n - 1)];

		maxInRange = bTime[findMaxPos(left, right)];
		maxLeftInRange = (maxInRange - minInRange) / 2.0;

		decider = Math.max(maxLeftInRange, Math.max(maxLeft, maxRight));

		System.out.printf("%.1f\n", ((double) minInRange + decider));
	}

	static class Interval
	{
		// max and min elements' array positions and not the elements
		// themselves.
		int min, max;

		public Interval(int min, int max)
		{
			this.min = min;
			this.max = max;
		}

	}
	
	class InputReader
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

		public float nextFloat() // problematic
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

		public double nextDouble() // not completely accurate
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

/*

20
5 2 8 3 4 2 9 8 6 8 2 5 3 10 1 5 8 15 20 6
1
0 19

20
5 2 8 3 4 2 9 8 6 8 2 5 3 10 1 5 8 15 20 6
5
10 15
7 13
0 19
12 17
4 10

20
5 2 8 3 4 2 9 8 6 8 2 5 3 10 1 5 8 15 2 6
5
10 15
7 13
0 19 12 17 4 10

*/
