package com.dsa.algorithms.dynamicprogramming;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public class MaxSum
{
	static InputReader reader;
	static OutputWriter writer;

	public static void main(String[] args)
	{
		MaxSum sum = new MaxSum();

		reader = sum.new InputReader(System.in);
		writer = sum.new OutputWriter(System.out);

		getAttributes();

		writer.flush();

		reader.close();
		writer.close();
	}

	static void getAttributes()
	{
		System.out.println("Enter the size of the array : ");

		int size, maxValue, array[];

		size = reader.nextInt();
		maxValue = Integer.MIN_VALUE;
		array = new int[size];

		System.out.println("Enter the array : ");

		for (int i = 0; i < size; i++)
		{
			array[i] = reader.nextInt();

			if (array[i] > maxValue)
				maxValue = array[i];
		}

		System.out.println("The maximum possible sum of any combination of "
				+ "elements of the entered array is : "
				+ findMaxSum(array, size, maxValue));
	}

	/**
	 * Finds the maximum possible sum of any combination of elements in the
	 * array using Dynamic Programming.
	 * 
	 * @param array
	 *            the array whose maximum possible sum of any combination of
	 *            elements needs to be found.
	 * @param size
	 *            the size of the array.
	 * @param maxValue
	 *            the maximum possible value of any element in the array.
	 * @return the maximum possible sum of any combination of elements of the
	 *         array.
	 */
	static int findMaxSum(int array[], int size, int maxValue)
	{
		int countPositive, positiveArray[];

		countPositive = 0;
		positiveArray = new int[size];

		for (int i = 0; i < size; i++)
			if (array[i] > 0)
				positiveArray[countPositive++] = array[i];

		// in most cases, the maximum value of any combination shall be
		// provided, and we might have to check if that particular maximum sum
		// is possible or not, or some other variation of this concept.
		boolean dp[][] = new boolean[countPositive + 1][countPositive
				* maxValue + 1];

		dp[0][0] = true;

		int temp = countPositive * maxValue;

		for (int i = 1; i <= countPositive; i++)
		{
			for (int j = 0; j <= temp; j++)
			{
				if (dp[i - 1][j])
				{
					dp[i][j] = true;
					dp[i][j + positiveArray[i - 1]] = true;
				}
			}
		}

		for (int i = temp; i >= 0; i--)
			if (dp[countPositive][i])
				return i;

		return -1;
	}

	class InputReader
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

	class OutputWriter
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

		public void println(long x)
		{
			writer.println(x);
		}

		public void print(int x)
		{
			writer.print(x);
		}

		public void print(long x)
		{
			writer.println(x);
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

/*

10
5 8 3 6 -8 -5 7 -1 9 1

*/