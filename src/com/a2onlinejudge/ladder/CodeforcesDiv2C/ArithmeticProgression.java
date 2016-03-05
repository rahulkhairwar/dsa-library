package com.a2onlinejudge.ladder.CodeforcesDiv2C;

import java.io.*;
import java.util.*;

/**
 * Created by rahulkhairwar on 03/03/16.
 */
public final class ArithmeticProgression
{
	static int n, arr[];
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

		arr = new int[n];

		for (int i = 0; i < n; i++)
			arr[i] = in.nextInt();

		Arrays.sort(arr);

		if (n == 1)
		{
			out.println(-1);

			return;
		}
		else if (n == 2)
		{
			int d = arr[1] - arr[0];

			if (d >= 2 && d % 2 == 0)
			{
				out.println(3);
				out.println((arr[0] - d) + " " + ((arr[1] + arr[0]) / 2) + " " + (arr[1] + d));
			}
			else if (d == 0)
			{
				out.println(1);
				out.println(arr[0]);
			}
			else
			{
				out.println(2);
				out.println((arr[0] - d) + " " + (arr[1] + d));
			}

			return;
		}

		Map<Integer, Integer> map = new HashMap<>(n);
		int size;

		for (int i = 1; i < n; i++)
		{
			int diff = arr[i] - arr[i - 1];

			if (map.containsKey(diff))
				map.put(diff, map.get(diff) + 1);
			else
				map.put(diff, 1);
		}

		size = map.size();

		if (size > 2)
		{
			out.println(0);

			return;
		}
		else if (size == 1)
		{
			Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
			int d = iterator.next().getKey();

			if (d == 0)
			{
				out.println(1);
				out.println(arr[0]);

				return;
			}

			out.println(2);
			out.println((arr[0] - d) + " " + (arr[n - 1] + d));

			return;
		}
		else
		{
			Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
			Map.Entry<Integer, Integer> first, second;
			int firstD, secondD, firstDCount, secondDCount;

			first = iterator.next();
			second = iterator.next();
			firstD = first.getKey();
			secondD = second.getKey();
			firstDCount = first.getValue();
			secondDCount = second.getValue();

			if (firstDCount > 1 && secondDCount > 1)
			{
				out.println(0);

				return;
			}
			else
			{
				int pos = 0;
				int d;

				if (firstDCount >= 1 && secondDCount == 1)
					d = firstD;
				else
				{
					d = secondD;
					secondD = firstD;
				}

				for (int i = 1; i < n; i++)
				{
					if (arr[i] - arr[i - 1] == secondD)
					{
						pos = i - 1;

						break;
					}
				}

				int middle = (arr[pos] + arr[pos + 1]) / 2;

				if (middle - arr[pos] == d && arr[pos + 1] - middle == d)
				{
					out.println(1);
					out.println(middle);

					return;
				}
				else
					out.println(0);
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

4
1 3 5 9

4
1 2 5 6

4
1 2 6 7

2
1 3

2
1 4

3
1 4 2

 */
