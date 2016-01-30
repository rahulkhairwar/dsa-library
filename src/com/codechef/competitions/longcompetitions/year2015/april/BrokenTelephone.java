package com.codechef.competitions.longcompetitions.year2015.april;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class BrokenTelephone
{
	private static int t, n, a;
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		BrokenTelephone telephone = new BrokenTelephone();
		
		reader = telephone.new InputReader(System.in);
		writer = telephone.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			n = reader.nextInt();
			
			calculate();
		}
	}
	
	public static void calculate()
	{
		int initial, previous, secondLast;
		long count;
		
		initial = previous = secondLast = a = reader.nextInt();
		count = 0;
		
		a = reader.nextInt();

/*		if (a != previous)
		{
			count++;
			
			if (a != initial)
				count++;
		}
		
		previous = a;
		
		for (int j = 2; j < n; j++)
		{
			a = reader.nextInt();

			if (a != previous)
			{
				count++;
				
				if (a != secondLast)
					count++;
			}
			
			// System.out.println("current a : " + a + " count : " + count + " previous : " + previous + " secondLast : " + secondLast);
			secondLast = previous;
			previous = a;
		}*/
		
		for (int j = 1; j < n; j++)
		{
			a = reader.nextInt();
			
			if (a != previous)
				count += 2;
		}
		
		writer.println(count);
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

2
20
4 4 4 4 5 9 5 9 9 9 9 4 4 6 5 1 6 4 5 6
5
4 4 4 4 5

*/