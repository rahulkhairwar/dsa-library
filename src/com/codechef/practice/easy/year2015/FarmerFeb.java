package com.codechef.practice.easy.year2015;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class FarmerFeb
{
	private static int t, x, y;
	private static int numberOfPrimes, primes[];
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		FarmerFeb farmer = new FarmerFeb();
		
		reader = farmer.new InputReader(System.in);
		writer = farmer.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		t = reader.nextInt();
		
		findPrimes();
		
		for (int i = 0; i < t; i++)
		{
			x = reader.nextInt();
			y = reader.nextInt();
			
			if (x + y >= 1999)
				writer.println(2003 - x - y);
			else
				writer.println(binarySearch(x + y) - x - y);
		}
	}
	
	public static void findPrimes()
	{
		primes = new int[310];
		
		primes[0] = 2;
		numberOfPrimes = 1;
		
		for (int i = 3; i < 2005; i++)
			if (isPrime(i))
				primes[numberOfPrimes++] = i;
	}
	
	public static boolean isPrime(int number)
	{
		int sqrt = (int) Math.sqrt(number);
		
		for (int i = 0; i < numberOfPrimes && primes[i] <= sqrt; i++)
			if (number % primes[i] == 0)
				return false;
		
		return true;
	}
	
	public static int binarySearch(int firstPrimeAfter)
	{
		int first, last, middle;
		
		first = 0;
		last = numberOfPrimes - 1;
		
		while (first <= last)
		{
			middle = (first + last) / 2;
			
			if (primes[middle] > firstPrimeAfter && primes[middle - 1] <= firstPrimeAfter)
				return primes[middle];
			
			if (primes[middle] > firstPrimeAfter)
				last = middle - 1;
			else
				first = middle + 1;
		}
		
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
