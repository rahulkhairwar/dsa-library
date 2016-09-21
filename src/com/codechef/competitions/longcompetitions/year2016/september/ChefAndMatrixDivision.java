package com.codechef.competitions.longcompetitions.year2016.september;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

class ChefAndMatrixDivision
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
		int n, p;
		long min = Long.MAX_VALUE;
		long[][] mat, sum;
		int[] ansR, ansC;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			p = in.nextInt();
			mat = new long[n][n];
			sum = new long[n][n];

			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					mat[i][j] = in.nextInt();

			for (int i = 0; i < n; i++)
				sum[0][i] = mat[0][i];

			for (int i = 0; i < n; i++)
				sum[i][0] = mat[i][0];

			for (int i = 1; i < n; i++)
				for (int j = 1; j < n; j++)
					sum[i][j] = mat[i][j] + sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1];

			for (int i = 0; i < 1000; i++)
				divide();

			for (int i = 1; i < p; i++)
				out.print(ansR[i] + " ");

			out.println();

			for (int i = 1; i < p; i++)
				out.print(ansC[i] + " ");

//            out.println(sum);
		}

		void divide()
		{
			int[] rows, cols;
			Set<Integer> set = new HashSet<>();

			rows = new int[p];
			cols = new int[p];

			while (set.size() < p - 1)
			{
				int rand = (int) (Math.random() * (n - 2)) + 1;

				if (set.contains(rand))
					continue;

				rows[set.size()] = rand;
				set.add(rand);
			}

			rows[p - 1] = 0;
			set = new HashSet<>();

			while (set.size() < p - 1)
			{
				int rand = (int) (Math.random() * (n - 2)) + 1;

				if (set.contains(rand))
					continue;

				cols[set.size()] = rand;
				set.add(rand);
			}

			cols[p - 1] = 0;
			Arrays.sort(rows);
			Arrays.sort(cols);

/*            for (int i = 1; i < p; i++)
                out.print(rows[i] + " ");
            out.println();

            for (int i = 1; i < p; i++)
                out.print(cols[i] + " ");*/

			long max = 0;

			for (int i = 1; i < p; i++)
			{
				for (int j = 1; j < p; j++)
				{
					// i = current row, j = current column
					// so, current rect = [(x-prev row, y-prev row), (x-curr row, y-curr col)]
					max = Math.max(max, rectangleSum(rows[i - 1], cols[j - 1], rows[i], cols[j]));
				}
			}

			if (max < min)
			{
				ansR = rows;
				ansC = cols;
			}
		}

		long rectangleSum(int x1, int y1, int x2, int y2)
		{
			return sum[x2][y2] - sum[x1][y2] - sum[x2][y1] + sum[x1][y1];
		}

		class Rectangle
		{
			int x1, y1, x2, y2;

			public Rectangle(int x1, int y1, int x2, int y2)
			{
				this.x1 = x1;
				this.y1 = y1;
				this.x2 = x2;
				this.y2 = y2;
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

	static class CMath
	{
		static long gcd(long a, long b)
		{
			if (b == 0)
				return a;

			return gcd(b, a % b);
		}

		static long lcm(long... arr)
		{
			int len = arr.length;
			long lcm = arr[0];

			for (int i = 1; i < len; i++)
				lcm = (lcm * arr[i]) / gcd(lcm, arr[i]);

			return lcm;
		}

	}

}
