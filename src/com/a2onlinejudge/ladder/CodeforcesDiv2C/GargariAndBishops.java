package com.a2onlinejudge.ladder.CodeforcesDiv2C;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

/**
 * Created by rahulkhairwar on 24/02/16.
 */
public final class GargariAndBishops
{
	static int n;
	static int[][] board;
	static InputReader in;
	static OutputWriter out;

	public static void main(String[] args)
	{
		in = new InputReader(System.in);
		out = new OutputWriter(System.out);

		solve();

		out.flush();

		in.close();
		out.close();
	}

	static void solve()
	{
		n = in.nextInt();

		board = new int[n][n];

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				board[i][j] = in.nextInt();

		long[] bottomToTop = new long[2 * n - 1];
		int count = 0;

		for (int i = 0; i < n; i++)
		{
			long total = 0;

			for (int row = i, column = 0; row >= 0 && column < n; row--, column++)
				total += board[row][column];

			bottomToTop[count++] = total;
		}

		for (int i = 1; i < n; i++)
		{
			long total = 0;

			for (int row = n - 1, column = i; row >= 0 && column < n; row--, column++)
				total += board[row][column];

			bottomToTop[count++] = total;
		}

		long[][] sum = new long[n][n];

		for (int i = 0; i < n; i++)
		{
			long total = 0;

			for (int row = n - 1 - i, column = 0; row < n && column < n; row++, column++)
				total += board[row][column];

			for (int row = n - 1 - i, column = 0; row < n && column < n; row++, column++)
				sum[row][column] = total;
		}

		for (int i = 1; i < n; i++)
		{
			long total = 0;

			for (int row = 0, column = i; row < n && column < n; row++, column++)
				total += board[row][column];

			for (int row = 0, column = i; row < n && column < n; row++, column++)
				sum[row][column] = total;
		}

		Cross[] allCrosses = new Cross[n * n];

		count = 0;

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				allCrosses[count++] = new Cross(i, j, sum[i][j] + bottomToTop[i + j] - board[i][j]);

		Arrays.sort(allCrosses, new Comparator<Cross>()
		{
			@Override
			public int compare(Cross o1, Cross o2)
			{
				return Long.compare(o2.sum, o1.sum);
			}
		});

		Cross first, second, third, fourth;

		first = allCrosses[0];
		third = allCrosses[1];

		int i = 1;

		while (true)
		{
			if (Math.abs(first.row - allCrosses[i].row) % 2 == 0)
			{
				if (Math.abs(first.column - allCrosses[i].column) % 2 == 1)
				{
					second = allCrosses[i];

					break;
				}
			}
			else
			{
				if (Math.abs(first.column - allCrosses[i].column) % 2 == 0)
				{
					second = allCrosses[i];

					break;
				}
			}

			i++;
		}

		if (third.row == second.row && third.column == second.column)
		{
			out.println(first.sum + second.sum);
			out.println((first.row + 1) + " " + (first.column + 1) + " " + (second.row + 1) + " " + (second.column +
					1));
		}
		else
		{
			i = 2;

			while (true)
			{
				if (Math.abs(third.row - allCrosses[i].row) % 2 == 0)
				{
					if (Math.abs(third.column - allCrosses[i].column) % 2 == 1)
					{
						fourth = allCrosses[i];

						break;
					}
				}
				else
				{
					if (Math.abs(third.column - allCrosses[i].column) % 2 == 0)
					{
						fourth = allCrosses[i];

						break;
					}
				}

				i++;
			}

			if (first.sum + second.sum > third.sum + fourth.sum)
			{
				out.println(first.sum + second.sum);
				out.println((first.row + 1) + " " + (first.column + 1) + " " + (second.row + 1) + " " + (second.column +
						1));
			}
			else
			{
				out.println(third.sum + fourth.sum);
				out.println((third.row + 1) + " " + (third.column + 1) + " " + (fourth.row + 1) + " " + (fourth
						.column + 1));
			}
		}
	}

	static class Cross
	{
		int row, column;
		long sum;

		public Cross(int row, int column, long sum)
		{
			this.row = row;
			this.column = column;
			this.sum = sum;
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
				} catch (IOException e)
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
			} catch (IOException e)
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
		static long power(long number, int power)
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

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

}
