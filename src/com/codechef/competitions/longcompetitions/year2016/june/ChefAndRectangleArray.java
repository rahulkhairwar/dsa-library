package com.codechef.competitions.longcompetitions.year2016.june;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by rahulkhairwar on 09/06/16.
 */
class ChefAndRectangleArray
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver(in, out);

		solver.solve(1);

		out.flush();

		in.close();
		out.close();
	}

	static class Solver
	{
		int rows, columns, q, a, b, arr[][];
		int[] log;
		int[][] sum;
		int[][][][] rmq;
		InputReader in;
		OutputWriter out;

		void solve(int testNumber)
		{
			rows = in.nextInt();
			columns = in.nextInt();

			if (rows <= 50 && columns <= 50)
			{
				solve2();

				return;
			}

			arr = new int[rows][columns];

			for (int i = 0; i < rows; i++)
				for (int j = 0; j < columns; j++)
					arr[i][j] = in.nextInt();

			buildSparseMatrix(rows, columns);
			buildSumTable(rows, columns);

			q = in.nextInt();

			while (q-- > 0)
			{
				a = in.nextInt();
				b = in.nextInt();

				if (a == 1 && b == 1)
				{
					out.println(0);

					continue;
				}

				int sum, min, max, aIntoB;

				min = Integer.MAX_VALUE;
				aIntoB = a * b;

				for (int i = 0; i + a <= rows; i++)
				{
					for (int currColumn = 0; currColumn + b <= columns; currColumn++)
					{
						sum = findSum(i, currColumn, i + a - 1, currColumn + b - 1);
						max = query(i, currColumn, i + a - 1, currColumn + b - 1);
						min = Math.min(min, max * aIntoB - sum);
					}
				}

				out.println(min);
			}
		}

		void buildSparseMatrix(int n, int m)
		{
			log = new int[1001];

			for (int i = 2; i <= 1000; i++)
				log[i] = log[i >> 1] + 1;

			rmq = new int[log[n] + 1][log[m] + 1][n + 1][m + 1];

			for (int i = 0; (1 << i) <= n; i++)
			{
				for (int j = 0; (1 << j) <= m; j++)
				{
					for (int x = 0; x + (1 << i) - 1 < n; x++)
					{
						for (int y = 0; y + (1 << j) - 1 < m; y++)
						{
							if (i == 0 && j == 0)
								rmq[i][j][x][y] = arr[x][y];
							else if (i == 0)
								rmq[i][j][x][y] = max(rmq[i][j - 1][x][y], rmq[i][j - 1][x][(y + (1 << (j - 1)))]);
							else if (j == 0)
								rmq[i][j][x][y] = max(rmq[i - 1][j][x][y], rmq[i - 1][j][(x + (1 << (i - 1)))][y]);
							else
								rmq[i][j][x][y] =
										max(rmq[i - 1][j - 1][x][y], rmq[i - 1][j - 1][(x + (1 << (i - 1)))][y],
												rmq[i - 1][j - 1][x][(y + (1 << (j - 1)))],
												rmq[i - 1][j - 1][(x + (1 << (i - 1)))][(y + (1 << (j - 1)))]);
						}
					}
				}
			}
		}

		int query(int x, int y, int x1, int y1)
		{
			int k, l, ans;

			k = log[x1 - x + 1];
			l = log[y1 - y + 1];
			ans = max(4, rmq[k][l][x][y], rmq[k][l][x1 - (1 << k) + 1][y], rmq[k][l][x][y1 - (1 << l) + 1],
					rmq[k][l][x1 - (1 << k) + 1][y1 - (1 << l) + 1]);

			return ans;
		}

		void buildSumTable(int rows, int columns)
		{
			sum = new int[rows][columns];

			sum[0][0] = arr[0][0];

			for (int i = 1; i < rows; i++)
				sum[i][0] = sum[i - 1][0] + arr[i][0];

			for (int i = 1; i < columns; i++)
				sum[0][i] = sum[0][i - 1] + arr[0][i];

			for (int i = 1; i < rows; i++)
				for (int j = 1; j < columns; j++)
					sum[i][j] = arr[i][j] + sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1];
		}

		int findSum(int x1, int y1, int x2, int y2)
		{
			return sum[x2][y2] - (x1 > 0 ? sum[x1 - 1][y2] : 0) - (y1 > 0 ? sum[x2][y1 - 1] : 0) + (x1 > 0 && y1 > 0 ?
					sum[x1 - 1][y1 - 1] : 0);
		}

		static int max(int... integers)
		{
			int maxValue = -1;

			for (int i : integers)
				maxValue = Math.max(maxValue, i);

			return maxValue;
		}

		void solve2()
		{
			arr = new int[rows][columns];

			for (int i = 0; i < rows; i++)
				for (int j = 0; j < columns; j++)
					arr[i][j] = in.nextInt();

			buildSparseMatrix2(rows, columns);
			buildSumTable2(rows, columns);

			q = in.nextInt();

			while (q-- > 0)
			{
				a = in.nextInt();
				b = in.nextInt();

				if (a == 1 && b == 1)
				{
					out.println(0);

					continue;
				}

				int sum, min, max, aIntoB;

				min = Integer.MAX_VALUE;
				aIntoB = a * b;

				for (int i = 0; i + a <= rows; i++)
				{
					for (int currColumn = 0; currColumn + b <= columns; currColumn++)
					{
						sum = findSum2(i, currColumn, i + a - 1, currColumn + b - 1);
						max = query2(i, currColumn, i + a - 1, currColumn + b - 1);
						min = Math.min(min, max * aIntoB - sum);
					}
				}

				out.println(min);
			}
		}

		void buildSparseMatrix2(int n, int m)
		{
			log = new int[1001];

			for (int i = 2; i <= 1000; i++)
				log[i] = log[i >> 1] + 1;

			rmq = new int[n + 1][m + 1][log[n] + 1][log[m] + 1];

			for (int i = 0; (1 << i) <= n; i++)
			{
				for (int j = 0; (1 << j) <= m; j++)
				{
					for (int x = 0; x + (1 << i) - 1 < n; x++)
					{
						for (int y = 0; y + (1 << j) - 1 < m; y++)
						{
							if (i == 0 && j == 0)
								rmq[x][y][i][j] = arr[x][y];
							else if (i == 0)
								rmq[x][y][i][j] = max(rmq[x][y][i][j - 1], rmq[x][y + (1 << (j - 1))][i][j - 1]);
							else if (j == 0)
								rmq[x][y][i][j] = max(rmq[x][y][i - 1][j], rmq[x + (1 << (i - 1))][y][i - 1][j]);
							else
								rmq[x][y][i][j] = max(rmq[x][y][i - 1][j - 1], rmq[x + (1 << (i - 1))][y][i - 1][j - 1],
										rmq[x][y + (1 << (j - 1))][i - 1][j - 1], rmq[x + (1 << (i - 1))][y + (1 << (j - 1))
												][i - 1][j - 1]);
						}
					}
				}
			}
		}

		int query2(int x, int y, int x1, int y1)
		{
			int k, l, ans;

			k = log[x1 - x + 1];
			l = log[y1 - y + 1];
			ans = max(rmq[x][y][k][l], rmq[x1 - (1 << k) + 1][y][k][l], rmq[x][y1 - (1 << l) + 1][k][l], rmq[x1 - (1 << k)
					+ 1][y1 - (1 << l) + 1][k][l]);

			return ans;
		}

		void buildSumTable2(int rows, int columns)
		{
			sum = new int[rows][columns];

			sum[0][0] = arr[0][0];

			for (int i = 1; i < rows; i++)
				sum[i][0] = sum[i - 1][0] + arr[i][0];

			for (int i = 1; i < columns; i++)
				sum[0][i] = sum[0][i - 1] + arr[0][i];

			for (int i = 1; i < rows; i++)
				for (int j = 1; j < columns; j++)
					sum[i][j] = arr[i][j] + sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1];
		}

		int findSum2(int x1, int y1, int x2, int y2)
		{
			return sum[x2][y2] - (x1 > 0 ? sum[x1 - 1][y2] : 0) - (y1 > 0 ? sum[x2][y1 - 1] : 0) + (x1 > 0 && y1 > 0 ?
					sum[x1 - 1][y1 - 1] : 0);
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
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					stream)));
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

		public void flush()
		{
			writer.flush();
		}

		public void close()
		{
			writer.close();
		}

	}

	static class CMath
	{
		static long power(long number, long power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			if (power == 1)
				return number;

			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}

		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);
			else
				return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

	}

}

/*

3 4
1 8 3 4
5 2 3 1
3 6 2 2
1
2 2

 */
