package com.codeforces.practice.hard;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

/**
 * Couldn't get this java solution accepted, but got the C++ version of the same accepted, with some I/O optimizations.
 */
public class PowerfulArray
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
		int n, t, sqrtN;
		int[] count;
		long sum;
		long[] arr, squares;
		Query[] queries;
		InputReader in;
		OutputWriter out;

		public Solver(InputReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
		}

		void solve(int testNumber)
		{
			n = in.nextInt();
			t = in.nextInt();
			sqrtN = (int) Math.sqrt(n);

			arr = new long[n + 1];
			count = new int[(int) (1e6 + 5)];

			prepareSquares();

			for (int i = 0; i < n; i++)
			{
				arr[i] = in.nextInt();
				sum -= (arr[i] * squares[count[(int) arr[i]]]);
				count[(int) arr[i]]++;
				sum += (arr[i] * squares[count[(int) arr[i]]]);
			}

			queries = new Query[t];

			for (int i = 0; i < t; i++)
			{
				int left, right;

				left = in.nextInt() - 1;
				right = in.nextInt() - 1;

				queries[i] = new Query(i, left, right);
			}

			Arrays.sort(queries, new Comparator<Query>()
			{
				@Override public int compare(Query o1, Query o2)
				{
					if (o1.blockNumber == o2.blockNumber)
						return Integer.compare(o1.right, o2.right);

					return Integer.compare(o1.blockNumber, o2.blockNumber);
				}
			});

			int left, right;
			long[] answer;

			left = 0;
			right = n - 1;
			answer = new long[t];

			for (int i = 0; i < t; i++)
			{
				while (left > queries[i].left)
				{
					left--;
					add(arr[left]);
				}

				while (right < queries[i].right)
				{
					right++;
					add(arr[right]);
				}

				while (left < queries[i].left)
				{
					delete(arr[left]);
					left++;
				}

				while (right > queries[i].right)
				{
					delete(arr[right]);
					right--;
				}

				answer[queries[i].queryNumber] = sum;
			}

			for (int i = 0; i < t; i++)
				out.println(answer[i]);
		}

		private void prepareSquares()
		{
			squares = new long[(int) (2 * 1e5 + 5)];

			for (long i = 1; i < 2 * 1e5 + 5; i++)
				squares[(int) i] = i * i;
		}

		void add(long num)
		{
			sum -= num * squares[count[(int) num]];
			count[(int) num]++;
			sum += num * squares[count[(int) num]];
		}

		void delete(long num)
		{
			sum -= num * squares[count[(int) num]];
			count[(int) num]--;
			sum += num * squares[count[(int) num]];
		}

		class Query
		{
			int queryNumber, left, right, blockNumber;

			public Query(int queryNumber, int left, int right)
			{
				this.queryNumber = queryNumber;
				this.left = left;
				this.right = right;
				blockNumber = left / sqrtN;
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

	}

}

/*

test 14 :

9 10
6 2 3 1 4 8 5 9 8
3 9
6 6
9 9
9 9
9 9
2 3
3 9
3 8
6 9
5 6

*/
