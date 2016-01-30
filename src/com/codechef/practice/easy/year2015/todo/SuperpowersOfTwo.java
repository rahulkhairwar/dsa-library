package com.codechef.practice.easy.year2015.todo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class SuperpowersOfTwo
{
	private static int t, n;
	private static long mod, superpowerArray[];
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		SuperpowersOfTwo two = new SuperpowersOfTwo();
		
		reader = two.new InputReader(System.in);
		writer = two.new OutputWriter(System.out);
		
		binary(600000);
		
		//getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		mod = 1000000007;
		
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			
		}
	}
	
	public static void binary(int number)
	{
		if (number == 0)
		{
			writer.println(0);
			
			return;
		}
		else if (number == 1)
		{
			writer.println(1);
			
			return;
		}
		
		while (number > 1)
		{
			writer.print(number % 2);
			
			number /= 2;
		}
	}
	
	public static void findSuperpowers()
	{
		superpowerArray = new long[1049000];
		
		superpowerArray[0] = 1;
		superpowerArray[1] = 4;
		
		int count, binary;
		
		count = 2;
		binary = 1;
		
		for (int i = 1; i < 524289; i++)
		{
			binary *= 10;
			
			// wrong!!
			superpowerArray[count++] = ((long) Math.pow(2, Math.pow(2, binary)) % mod);
					
			binary += 1;
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
