package com.hackerearth.practice.codemonk.dynamicprogramming1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.InputMismatchException;
 
/**
 * I used this trick to solve the question : 
 * {@link 
 * https://www.hackerearth.com/code-monk-dynamic-programming/algorithm/samu-and-shopping/}
 * I was initially getting StackOverflowError for bigger inputs since the stack size in
 * java is less compared to C and C++, but after allocating the stack size for the thread
 * it worked perfectly fine.
 */
class SamuAndShopping implements Runnable
{
	static int t, n;
	static int shops[][], dp[][];
	static InputReader in;
	static OutputWriter out;

	public static void main(String[] args) throws InterruptedException
	{
		in = new InputReader(System.in);
		out = new OutputWriter(System.out);

		t = in.nextInt();

		for (int i = 0; i < t; i++)
		{
			Thread t = new Thread(null, new SamuAndShopping(),
					"SamuAndShopping", 1 << 28);
			t.start();
			t.join();
		}

		out.flush();

		in.close();
		out.close();
	}

	public SamuAndShopping()
	{
		n = in.nextInt();

		shops = new int[n][3];

		for (int j = 0; j < n; j++)
		{
			shops[j][0] = in.nextInt();
			shops[j][1] = in.nextInt();
			shops[j][2] = in.nextInt();
		}

	}

	@Override
	public void run()
	{
		if (n == 1)
		{
			out.println(Math.min(shops[0][0],
					Math.min(shops[0][1], shops[0][2])));

			return;
		}

		dp = new int[n][3];

		for (int j = 0; j < n; j++)
			Arrays.fill(dp[j], -1);

		long min = Math.min(
				findBest(1, 0) + shops[0][0],
				Math.min(findBest(1, 1) + shops[0][1], findBest(1, 2)
						+ shops[0][2]));

		out.println(min);
	}

	static int findBest(int currIndex, int previousTaken)
	{
		int plusOne, plusTwo;

		if (dp[currIndex][(previousTaken + 1) % 3] != -1
				&& dp[currIndex][(previousTaken + 2) % 3] != -1)
			return Math.min(dp[currIndex][(previousTaken + 1) % 3],
					dp[currIndex][(previousTaken + 2) % 3]);

		if (currIndex == n - 1)
		{
			plusOne = shops[currIndex][(previousTaken + 1) % 3];
			plusTwo = shops[currIndex][(previousTaken + 2) % 3];

			dp[currIndex][(previousTaken + 1) % 3] = plusOne;
			dp[currIndex][(previousTaken + 2) % 3] = plusTwo;

			return Math.min(plusOne, plusTwo);
		}

		plusOne = shops[currIndex][(previousTaken + 1) % 3]
				+ findBest(currIndex + 1, (previousTaken + 1) % 3);
		plusTwo = shops[currIndex][(previousTaken + 2) % 3]
				+ findBest(currIndex + 1, (previousTaken + 2) % 3);

		dp[currIndex][(previousTaken + 1) % 3] = plusOne;
		dp[currIndex][(previousTaken + 2) % 3] = plusTwo;

		return Math.min(plusOne, plusTwo);
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

}