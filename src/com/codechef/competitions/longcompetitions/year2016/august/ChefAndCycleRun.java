package com.codechef.competitions.longcompetitions.year2016.august;

import java.io.*;
import java.util.*;

class ChefAndCycleRun
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver(in, out);
		solver.solve();

		out.flush();

		in.close();
		out.close();
	}

	static class Solver
	{
		int t, n;
		long[] cost;
		InputReader in;
		OutputWriter out;

		public Solver(InputReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
		}

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				cost = in.nextLongArray(n);

				int start, end;

				start = in.nextInt() - 1;
				end = in.nextInt() - 1;

				if (n == 2)
				{
					out.println(Math.min(2 * cost[0] + cost[1], Math.min(cost[0] + 2 * cost[1], Math.min(cost[0],
							cost[1]))));

					continue;
				}

				long rightStartToEnd, leftStartToEnd;

				rightStartToEnd = leftStartToEnd = 0;

				for (int i = start; i < end; i++)
					rightStartToEnd += cost[i];

				for (int i = start; ; )
				{
					if (i == 0)
						i = n;

					leftStartToEnd += cost[i - 1];
					i--;

					if (i == end)
						break;
				}

				long[] rightCum = new long[n];
				long[] rightMinCum = new long[n];

				rightCum[end] = 0;
				rightMinCum[end] = 0;

				for (int i = end - 1; i >= start; i--)
				{
					rightCum[i] = rightCum[i + 1] + cost[i];
					rightMinCum[i] = Math.min(rightCum[i], rightMinCum[i + 1]);
				}

				long leftCum = 0;
				long leftMinCum = 0;
				long min = rightMinCum[start];

				for (int i = start + 1; i <= end; i++)
				{
					leftCum += cost[i - 1];
					leftMinCum = Math.min(leftMinCum, leftCum);
					min = Math.min(min, leftMinCum + rightMinCum[(i + 1) % n]);
				}

				long[] mirrorLeftCum = new long[n];
				long[] mirrorLeftMinCum = new long[n];

				mirrorLeftCum[end] = 0;
				mirrorLeftMinCum[end] = 0;

				for (int i = end + 1;;)
				{
					if (i == n)
					{
						i = 0;

						if (i == start)
						{
							mirrorLeftCum[0] = mirrorLeftCum[n - 1] + cost[n - 1];
							mirrorLeftMinCum[0] = Math.min(mirrorLeftCum[0], mirrorLeftMinCum[n - 1]);

							break;
						}
					}

					mirrorLeftCum[i] = mirrorLeftCum[i > 0 ? i - 1 : n - 1] + cost[i > 0 ? i - 1 : n - 1];
					mirrorLeftMinCum[i] = Math.min(mirrorLeftCum[i], mirrorLeftMinCum[i > 0 ? i - 1 : n - 1]);

					i++;

					if (i == start)
					{
						mirrorLeftCum[i] = mirrorLeftCum[i > 0 ? i - 1 : n - 1] + cost[i > 0 ? i - 1 : n - 1];
						mirrorLeftMinCum[i] = Math.min(mirrorLeftCum[i], mirrorLeftMinCum[i > 0 ? i - 1 : n - 1]);

						break;
					}
				}

				long mirrorRightCum = 0;
				long mirrorRightMinCum = 0;
				long mirrorMin = mirrorLeftMinCum[start];

				for (int i = start - 1; i != end; i--)
				{
					if (i == -1)
					{
						i = n - 1;

						if (i == end)
						{
							mirrorRightCum += cost[i];
							mirrorRightMinCum = Math.min(mirrorRightMinCum, mirrorRightCum);
							mirrorMin = Math.min(mirrorMin, mirrorRightMinCum + mirrorLeftMinCum[n - 2]);

							break;
						}
					}

					mirrorRightCum += cost[i];
					mirrorRightMinCum = Math.min(mirrorRightMinCum, mirrorRightCum);
					mirrorMin = Math.min(mirrorMin, mirrorRightMinCum + mirrorLeftMinCum[i > 0 ? i - 1 : n - 1]);
				}

				out.println(Math.min(leftStartToEnd + 2L * min, rightStartToEnd + 2L * mirrorMin));
			}
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

	static class OutputWriter
	{
		private PrintWriter writer;

		public OutputWriter(OutputStream stream)
		{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
		}

		public OutputWriter(Writer writer)
		{
			this.writer = new PrintWriter(writer);
		}

		public void println(int x)
		{
			writer.println(x);
		}

		public void print(int x)
		{
			writer.print(x);
		}

		public void println(char x)
		{
			writer.println(x);
		}

		public void print(char x)
		{
			writer.print(x);
		}

		public void println(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i] + " ");
		}

		public void println(long x)
		{
			writer.println(x);
		}

		public void print(long x)
		{
			writer.print(x);
		}

		public void println(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i]);
		}

		public void println(float num)
		{
			writer.println(num);
		}

		public void print(float num)
		{
			writer.print(num);
		}

		public void println(double num)
		{
			writer.println(num);
		}

		public void print(double num)
		{
			writer.print(num);
		}

		public void println(String s)
		{
			writer.println(s);
		}

		public void print(String s)
		{
			writer.print(s);
		}

		public void println()
		{
			writer.println();
		}

		public void printSpace()
		{
			writer.print(" ");
		}

		public void printf(String format, Object args)
		{
			writer.printf(format, args);
		}

		public void flush()
		{
			writer.flush();
		}

		public void close()
		{
			writer.close();
		}

	}

}

/*

1
4
1 2 1 1
1 3

1
5
2 -500 -200 100 1000
2 5

1
8
1 -200 -100 -200 -500 -200 -100 100
2 7

1
8
100 -200 -100 -200 -500 -200 -100 100
2 7

1
8
100 -200 -100 -200 -500 -200 -100 100
7 8

1
2
-100 -1000
1 2

1
3
-100 100 -1000
1 2

*/
