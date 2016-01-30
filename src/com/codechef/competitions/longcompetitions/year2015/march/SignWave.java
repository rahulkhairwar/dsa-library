package com.codechef.competitions.longcompetitions.year2015.march;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class SignWave
{
	private static int t, s, c, k;
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		SignWave signWave = new SignWave();
		
		reader = signWave.new InputReader(System.in);
		writer = signWave.new OutputWriter(System.out);
		
		getAttributes();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			s = reader.nextInt();
			c = reader.nextInt();
			k = reader.nextInt();
			
			countPoints();
		}
	}
	
	public static void countPoints()
	{
		long count = 0;

		if (k == 1)
		{
			if (s == 0)
			{
				count = count + 0;

				for (int i = 1; i <= c; i++)
					count = count + powerOfTwo(i);
			}
			else
			{
				count = powerOfTwo(s) + 1;

				for (int i = s; i <= c; i++)
					count = count + powerOfTwo(i);
			}
		}
		else
		{
			if (s == 0 || s == 1)
				count = 0;
			else if (k >= s + 1)
				count = 0;
			else if (c >= s - k + 1)
			{
				count = count + powerOfTwo(s - k + 2) + 1;
			}
			else
				count = count + powerOfTwo(s - k + 1) + 1;
		}
		
		// writer.print(count);
		System.out.println(count);
	}

	public static long powerOfTwo(int p)
	{
		if (p <= 0)
			return 0;
		else
			return (long) Math.pow(2, p);
	}

	public static int findCountForS()
	{
		return 0;
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

		public void print(int x)
		{
			writer.print(x);
		}

		public void printSpace()
		{
			writer.print(" ");
		}

		public void close()
		{
			writer.close();
		}

	}

}

/*

13
2 0 1
3 5 1
3 2 1
5 4 1
5 7 1
2 0 2
3 2 3
4 4 2
9 5 6
7 3 2
1 2 2
1 4 6
3 2 5
3 2 4

3 2 3
3 0 3
*/
