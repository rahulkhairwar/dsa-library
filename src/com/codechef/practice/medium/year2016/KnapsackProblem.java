package com.codechef.practice.medium.year2016;

import java.io.*;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.TreeSet;

/**
 * Created by rahulkhairwar on 02/02/16.
 */
public class KnapsackProblem
{
	static int n, w, c;
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

		Item[] items = new Item[n];
		TreeSet<Item> one, two;

		one = new TreeSet<Item>(new Comparator<Item>()
		{
			@Override
			public int compare(Item o1, Item o2)
			{
				if (o2.value <= o1.value)
					return -1;

				return 1;
			}
		});

		two = new TreeSet<Item>(new Comparator<Item>()
		{
			@Override
			public int compare(Item o1, Item o2)
			{
				if (o2.value <= o1.value)
					return -1;

				return 1;
			}
		});

		int totalCapacity = 0;

		for (int i = 0; i < n; i++)
		{
			items[i] = new Item(in.nextInt(), in.nextInt());
			totalCapacity += items[i].weight;

			if (items[i].weight == 1)
				one.add(items[i]);
			else
				two.add(items[i]);
		}

		int currCapacity;
		long[] answer = new long[totalCapacity + 1];

		answer[0] = 0;

		for (currCapacity = 1; currCapacity <= totalCapacity; currCapacity += 2)
		{
			Item oneFirst, oneSecond, twoFirst;
			long currValue = 0;

			oneFirst = oneSecond = twoFirst = null;

			if (one.size() > 0)
				oneFirst = one.pollFirst();

			if (one.size() > 0)
				oneSecond = one.pollFirst();

			if (two.size() > 0)
				twoFirst = two.pollFirst();

			if (oneFirst != null)
			{
				currValue = oneFirst.value;

				answer[currCapacity] = answer[currCapacity - 1] + currValue;
			}

/*			if (oneFirst != null)
				one.add(oneFirst);*/

			if (currCapacity + 1 > totalCapacity)
				break;

			if (oneSecond != null && twoFirst != null)
			{
				if (twoFirst.value > currValue + oneSecond.value)
				{
					answer[currCapacity + 1] = answer[currCapacity - 1] + twoFirst.value;
					one.add(oneSecond);
					one.add(oneFirst);
				}
				else
				{
					answer[currCapacity + 1] = answer[currCapacity] + oneSecond.value;
					two.add(twoFirst);
				}
			}
			else if (oneSecond != null)
			{
				answer[currCapacity + 1] = answer[currCapacity] + oneSecond.value;
			}
			else if (twoFirst != null)
			{
				answer[currCapacity + 1] = answer[currCapacity - 1] + twoFirst.value;
			}
		}

		for (int i = 1; i <= totalCapacity; i++)
			out.print(answer[i] + " ");
	}

	static class Item
	{
		int weight;
		long value;

		public Item(int weight, long value)
		{
			this.weight = weight;
			this.value = value;
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

5
1 1
2 2
2 3
2 4
2 5

14
1 5
1 4
1 3
1 2
1 3
1 1
1 1
2 6
2 5
2 5
2 4
2 3
2 2
2 1

 */
