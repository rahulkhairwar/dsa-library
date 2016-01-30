package com.codeforces.competitions.year2015.apriltojune.round299div2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public class Second
{
	private static int a[];
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		Second second = new Second();
		
		reader = second.new InputReader(System.in);
		writer = second.new OutputWriter(System.out);
		
		find();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	
	public static void find()
	{
		a = new int[1024];
		
		a[0] = 4;
		a[1] = 7;
		
		int count = 2;
		
		for (int i = 0; i < 510; i++)
		{
			a[count++] = a[i] * 10 + 4;
			a[count++] = a[i] * 10 + 7;
		}

		int num = reader.nextInt();
		
		for (int i = 0; i < 1022; i++)
			if (num == a[i])
			{
				writer.println(i + 1);
				
				break;
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
