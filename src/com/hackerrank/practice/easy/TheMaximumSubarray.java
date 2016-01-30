package com.hackerrank.practice.easy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public class TheMaximumSubarray
{
	static int t, n, array[];
	static InputReader reader;
	static OutputWriter writer;
	
	public static void main(String args[])
	{
		TheMaximumSubarray subarray = new TheMaximumSubarray();
		
		reader = subarray.new InputReader(System.in);
		writer = subarray.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	static void getAttributes()
	{
		int positiveSum;
		
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			n = reader.nextInt();
			
			array = new int[n];
			
			positiveSum = 0;
			
			for (int j = 0; j < n; j++)
			{
				array[j] = reader.nextInt();
				
				if (array[j] >= 0)
					positiveSum += array[j];
			}
			
			if (positiveSum == 0)
			{
				int max = array[0];
				
				for (int j = 1; j < n; j++)
					if (array[j] > max)
						max = array[j];
				
				positiveSum = max;
			}
			
			writer.println(contiguousMaxSum() + " " + positiveSum);
		}
	}
	
	static int contiguousMaxSum()
	{
		int currentSum, maxSum, value;
		
		currentSum = value = 0;
		maxSum = Integer.MIN_VALUE;
		
		for (int i = 0; i < n; i++)
		{
			value = currentSum + array[i];
			
			if (value > 0)
				currentSum = value;
			else
				currentSum = 0;
			
			if (currentSum > maxSum)
				maxSum = currentSum;
		}
		
		if (maxSum == 0)
		{
			int max = Integer.MIN_VALUE;
			
			for (int i = 0; i < n; i++)
			{
				if (array[i] == 0)
					return 0;
				else
				{
					if (array[i] > max)
						max = array[i];
				}
			}
			
			return max;
		}
		else
			return maxSum;
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
