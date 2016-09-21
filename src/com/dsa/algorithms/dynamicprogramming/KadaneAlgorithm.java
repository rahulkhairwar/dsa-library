package com.dsa.algorithms.dynamicprogramming;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

/**
 * Kadane's Algorithm can find the maximum possible sum of contiguous elements
 * of an array in O(n).
 */
public class KadaneAlgorithm
{
	static InputReader reader;
	static OutputWriter writer;

	public static void main(String[] args)
	{
		KadaneAlgorithm kadane = new KadaneAlgorithm();

		reader = kadane.new InputReader(System.in);
		writer = kadane.new OutputWriter(System.out);

		getAttributes();

		writer.flush();

		reader.close();
		writer.close();
	}

	static void getAttributes()
	{
		System.out.println("Enter the size of the array : ");

		int size, array[], answer[];

		size = reader.nextInt();
		array = new int[size];

		System.out.println("Enter the array : ");

		for (int i = 0; i < size; i++)
			array[i] = reader.nextInt();

		answer = findMaxSum(array, size);

		writer.println("The maximum sum of contiguous elements in the array is : "
				+ answer[0]
				+ " starting at index "
				+ answer[1]
				+ " and ending at index " + answer[2]);
	}

	/**
	 * Finds the maximum sum of contiguous elements in the array using Kandane's
	 * Algorithm.
	 *
	 * @param array the array whose maximum sum of contiguous elements need to be
	 *              found.
	 * @param size  the size of the array.
	 * @return an array of size 3, having : <br/>
	 * maximum sum of contiguous elements at index 0,<br/>
	 * the left index of the contiguous sub-array at index 1, and<br/>
	 * the right index of the contiguous sub-array at index 2.
	 */
	static int[] findMaxSum(int array[], int size)
	{
		int value, currentSum, currentIndex, maxSum, maxSumLeftIndex, maxSumRightIndex;

		currentSum = maxSum = 0;
		currentIndex = maxSumLeftIndex = maxSumRightIndex = -1;

		for (int i = 0; i < size; i++)
		{
			value = currentSum + array[i];

			if (value > 0)
			{
				if (currentSum == 0)
					currentIndex = i;

				currentSum = value;
			}
			else
				currentSum = 0;

			if (currentSum > maxSum)
			{
				maxSum = currentSum;
				maxSumLeftIndex = currentIndex;
				maxSumRightIndex = i;
			}
		}

		return new int[]
				{maxSum, maxSumLeftIndex, maxSumRightIndex};
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
			}
			while (!isSpaceChar(c));

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
			}
			while (!isSpaceChar(c));

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
			}
			while (!isSpaceChar(c));

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

8
1 -3 5 -2 9 -8 -6 4

9
1 -3 5 -2 9 -8 -6 4 100

*/
