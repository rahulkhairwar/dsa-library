package com.codechef.competitions.shortcompetitions.year2016.february.lunchtime;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by rahulkhairwar on 27/02/16.
 */
class Second
{
	static int t, n, k;
	static char[][] board;
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
		t = in.nextInt();

		outer : while (t-- > 0)
		{
			n = in.nextInt();
			k = in.nextInt();

			board = new char[n][n];

			for (int i = 0; i < n; i++)
				board[i] = in.next().toCharArray();

			String diagonal;

			for (int i = 0; i < n; i++)
			{
				diagonal = "";

				for (int j = i, k = 0; j < n && k < n; j++, k++)
				{
					diagonal += board[k][j];
				}

				boolean result = check(diagonal);

				if (result)
				{
					out.println("YES");

					continue outer;
				}
			}

			for (int i = 0; i < n - 1; i++)
			{
				diagonal = "";

				for (int j = i + 1, k = 0; j < n && k < n; j++, k++)
				{
					diagonal += board[j][k];
				}

				boolean result = check(diagonal);

				if (result)
				{
					out.println("YES");

					continue outer;
				}
			}

			for (int i = 0; i < n; i++)
			{
				diagonal = "";

				for (int j = n - 1 - i, k = 0; j >= 0 && k < n; j--, k++)
				{
					diagonal += board[j][k];
				}

				boolean result = check(diagonal);

				if (result)
				{
					out.println("YES");

					continue outer;
				}
			}

			for (int i = 0; i < n - 1; i++)
			{
				diagonal = "";

				for (int j = n - 1, k = i + 1; j >= 0 && k < n; j--, k++)
				{
					diagonal += board[j][k];
				}

				boolean result = check(diagonal);

				if (result)
				{
					out.println("YES");

					continue outer;
				}
			}

			String row;

			for (int i = 0; i < n; i++)
			{
				row = "";

				for (int j = 0; j < n; j++)
					row += board[i][j];

				boolean result = check(row);

				if (result)
				{
					out.println("YES");

					continue outer;
				}
			}

			String column;

			for (int i = 0; i < n; i++)
			{
				column = "";

				for (int j = 0; j < n; j++)
					column += board[j][i];

				boolean result = check(column);

				if (result)
				{
					out.println("YES");

					continue outer;
				}
			}

			out.println("NO");
		}
	}

	static boolean check(String s)
	{
		if (k == 1)
			return true;

		int[] count;
		char[] type;
		int len = s.length();

		if (len < k)
			return false;

		count = new int[len];
		type = new char[len];

		int i, counter;

		i = counter = 0;

		while (i < len)
		{
			int j = i;
			char curr = s.charAt(j);
			int currCount = 0;
			boolean entered = false;

			while (j < len && s.charAt(j) == curr)
			{
				j++;
				currCount++;
				entered = true;
			}

			count[counter] = currCount;
			type[counter++] = curr;

			i = j;

			if (!entered)
				i++;
		}

		if (counter == 2)
		{
			char fir, sec;
			int cFir, cSec;

			fir = type[0];
			sec = type[1];
			cFir = count[0];
			cSec = count[1];

			if (fir == 'X' && sec == '.' && cFir == k - 1)
				return true;

			if (fir == '.' && sec == 'X' && cSec == k - 1)
				return true;
		}

		for (int j = 0; j < counter - 2; j++)
		{
			char fir, sec, thi;
			int cFir, cSec, cThi;

			fir = type[j];
			sec = type[j + 1];
			thi = type[j + 2];
			cFir = count[j];
			cSec = count[j + 1];
			cThi = count[j + 2];

			if (fir == 'X' && sec == '.' && cFir == k - 1)
				return true;

			if (fir == '.' && sec == 'X' && cSec == k - 1)
				return true;

			if (sec == 'X' && thi == '.' && cSec == k - 1)
				return true;

			if (sec == '.' && thi == 'X' && cThi == k - 1)
				return true;

			if (fir == 'X' && sec == '.' && thi == 'X')
			{
				if (cSec > 1)
					continue;

				if (cFir + cThi + 1 >= k)
					return true;
			}
		}

		return false;
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

/*

1
5 5
XXX.O
XX.OO
X.OOO
OXOOX
OXOOO

1
5 5
XXX.O
XX.OO
X.OOO
OOOOX
.XXXX

1
10 5
XXX.OXXXXO
XX.OOOOOOO
X.OOOOOXXX
OOOOX....X
.XXXOOOOXO
.XXOOOOOOX
.XXOOOXXXO
.OXXXXOOXX
.OOOOXOOXO
.OOOOXXXXO

1
10 5
XXX.OXXXXO
XX.OOOOOOO
X.OOOOOXXX
OXOOX....X
.XXXOOOOXO
.XXOOOOOOX
.XXOOOXXXO
.OXXXXOOXX
.OOOOXOOXO
.OOOOXXXXO

1
10 5
XXX.OXXXXO
XX.OOOOOOO
XOOOOOOXXX
OXOOX....X
.XXXOOOOXO
.XOOXOOOOX
.XXXOOXXXO
.OXXXXOOXX
.XOOOXOOXO
.OOOOXXXXO

 */
