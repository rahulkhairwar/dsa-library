package com.codechef.practice.easy.year2015.may;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class JohnnyAndTheBeanstalk
{
	private static int t, k;
	private static InputReader reader;
	private static OutputWriter writer;

	public static void main(String args[])
	{
		JohnnyAndTheBeanstalk beanstalk = new JohnnyAndTheBeanstalk();

		reader = beanstalk.new InputReader(System.in);
		writer = beanstalk.new OutputWriter(System.out);

		getAttributes();

		writer.flush();

		reader.close();
		writer.close();
	}

	public static void getAttributes()
	{
		boolean result;
		long currentNumberOfLeaves, currentNumberOfStems;

		t = reader.nextInt();

		for (int i = 0; i < t; i++)
		{
			k = reader.nextInt();

			result = true;
			currentNumberOfLeaves = reader.nextInt();

			currentNumberOfStems = 1 - currentNumberOfLeaves;
			currentNumberOfStems *= 2;
			
			int j = 1;
			
			if (currentNumberOfStems < 0)
			{
				result = false;
				
				while (j < k)
				{
					reader.nextLong();

					j++;
				}
			}

			for (; j < k;)
			{
				currentNumberOfLeaves = reader.nextInt();
				j++;

				currentNumberOfStems -= currentNumberOfLeaves;
				
				if (currentNumberOfStems < 0)
				{
					result = false;

					while (j < k)
					{
						reader.nextLong();

						j++;
					}
				}

				currentNumberOfStems *= 2;
			}

			if (result && currentNumberOfStems == 0)
				writer.println("Yes");
			else
				writer.println("No");
		}
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

6
3
0 1 2
3
0 0 3
5
0 0 1 0 4
5
0 1 1 0 4
5
0 1 0 0 4
5
0 1 0 0 8

*/
