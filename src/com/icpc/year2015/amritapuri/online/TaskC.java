package com.icpc.year2015.amritapuri.online;

import java.io.*;
import java.util.*;

class TaskC
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		int t, n, k;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				k = in.nextInt();

				if (k > n / 2)
				{
					out.println(-1);

					continue;
				}

				if (k == 0)
				{
					for (int i = 0; i < n; i++)
						out.print(i + 1 + " ");

					out.println();

					continue;
				}

				// all blocks won't be of size k.
				int mod = n % k;
				int count = n / k + 1;
				int[][] blocks = new int[count][k];
				boolean[][] changed = new boolean[count][k];
				int counter = 1;

				outer:
				for (int i = 0; i < count; i++)
				{
					for (int j = 0; j < k; j++)
					{
						blocks[i][j] = counter++;

						if (counter > n)
							break outer;
					}
				}

				for (int i = 0; i < count; i += 2)
				{
					if (i + 1 == count)
						break;

					for (int j = 0; j < k; j++)
					{
						if (blocks[i + 1][j] == 0)
							break;

						int temp = blocks[i][j];

						blocks[i][j] = blocks[i + 1][j];
						blocks[i + 1][j] = temp;
						changed[i][j] = changed[i + 1][j] = true;
					}
				}

				if (count % 2 == 1)
				{
					for (int i = 0; i < k; i++)
					{
						if (blocks[count - 1][i] == 0)
							break;

						int temp = blocks[count - 1][i];

						blocks[count - 1][i] = blocks[count - 2][i];
						blocks[count - 2][i] = temp;
						changed[count - 1][i] = changed[count - 2][i] = true;
					}
				}

				for (int i = 1; i < count; i++)
				{
					for (int j = 0; j < k; j++)
					{
						if (blocks[i][j] == 0)
							continue;

						if (!changed[i][j])
						{
							int temp = blocks[i - 1][j];

							blocks[i - 1][j] = blocks[i][j];
							blocks[i][j] = temp;
							changed[i - 1][j] = changed[i][j] = true;
						}
					}
				}

				if (count % 2 == 1)
				{
					for (int i = mod; i < k; i++)
						blocks[count - 2][i] -= mod;

					for (int i = 0; i < mod; i++)
						blocks[count - 1][i] += k - mod;
				}

				for (int i = 0; i < count; i++)
				{
					for (int j = 0; j < k; j++)
					{
						if (blocks[i][j] == 0)
							break;

						out.print(blocks[i][j] + " ");
					}
				}

				out.println();
			}
		}

		public Solver(InputReader in, OutputWriter out)
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

25
2 2
7 3
8 2
1 1
5 1
15 4
15 6
13 3
13 4
21 2
20 3
20 6
20 5
20 4
30 0
30 1
30 2
30 3
30 4
30 5
30 6
30 7
30 8
30 9
30 10
answers :
2 2 => -1
7 3 => 4 5 6 7 2 3 1
8 2 => 3 4 1 2 7 8 5 6
1 1 => -1
5 1 => 2 1 4 5 3
15 4 => 5 6 7 8 1 2 3 12 13 14 15 4 9 10 11
15 6 => 7 8 9 10 11 12 13 14 15 1 2 3 4 5 6
13 3 => 4 5 6 1 2 3 10 11 12 13 7 8 9
13 4 => 5 6 7 8 1 10 11 12 13 2 3 4 9
21 2 => 3 4 1 2 7 8 5 6 11 12 9 10 15 16 13 14 19 20 21 17 18
20 3 => 4 5 6 1 2 3 10 11 12 7 8 9 16 17 18 19 20 13 14 15
20 6 => 7 8 9 10 11 12 1 2 15 16 17 18 19 20 3 4 5 6 13 14
20 5 => 6 7 8 9 10 1 2 3 4 5 16 17 18 19 20 11 12 13 14 15
20 4 => 5 6 7 8 1 2 3 4 13 14 15 16 17 18 19 20 9 10 11 12
30 0 => 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30
30 1 => 2 1 4 3 6 5 8 7 10 9 12 11 14 13 16 15 18 17 20 19 22 21 24 23 26 25 28 27 30 29
30 2 => 3 4 1 2 7 8 5 6 11 12 9 10 15 16 13 14 19 20 17 18 23 24 21 22 27 28 29 30 25 26
30 3 => 4 5 6 1 2 3 10 11 12 7 8 9 16 17 18 13 14 15 22 23 24 19 20 21 28 29 30 25 26 27
30 4 => 5 6 7 8 1 2 3 4 13 14 15 16 9 10 11 12 21 22 23 24 17 18 27 28 29 30 19 20 25 26
30 5 => 6 7 8 9 10 1 2 3 4 5 16 17 18 19 20 11 12 13 14 15 26 27 28 29 30 21 22 23 24 25
30 6 => 7 8 9 10 11 12 1 2 3 4 5 6 19 20 21 22 23 24 25 26 27 28 29 30 13 14 15 16 17 18
30 7 => 8 9 10 11 12 13 14 1 2 3 4 5 6 7 22 23 24 25 26 27 28 29 30 15 16 17 18 19 20 21
30 8 => 9 10 11 12 13 14 15 16 1 2 3 4 5 6 23 24 25 26 27 28 29 30 7 8 17 18 19 20 21 22
30 9 => 10 11 12 13 14 15 16 17 18 1 2 3 22 23 24 25 26 27 28 29 30 4 5 6 7 8 9 19 20 21
30 10 => 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 1 2 3 4 5 6 7 8 9 10

*/
