package com.codeforces.competitions.year2015.apriltojune.round299div2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public class Third
{
	private static long n, a, b, l, t, m, s[], mIT, diff;
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		Third third = new Third();
		
		reader = third.new InputReader(System.in);
		writer = third.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		a = reader.nextLong();
		b = reader.nextLong();
		n = reader.nextLong();
		
		s = new long[100000];
		
		int result = 0;
		
		findSums();
		
		for (int i = 0; i < n; i++)
		{
			l = reader.nextLong();
			t = reader.nextLong();
			m = reader.nextLong();
			
			mIT = m * t;
			
			if (m == 0 || t == 0)
			{
				writer.println(-1);
				
				continue;
			}
			
			if (m == 1)
				diff = s[(int) (l - 1)];
			else
				diff = s[(int) (m - 2)] - s[(int) (l - 1)];
			
			// if (mIT <)
			
			for (int j = 0/*(int) (l - 1)*/; j < m; j++)
			{
				diff = sL((l - 1) + j);
			}
		}
	}
	
	public static long sL(long l)
	{
		return (a + (l - 1) * b);
	}
	
	public static int binarySearch(long search)
	{
		long first, last, middle;
		
		first  = 0;
	    last   = (n - 1);
	    middle = (first + last)/2;
	 
	    while(first <= last)
	    {
	      if (s[(int) middle] < search)
	      {
	    	  if (s[(int) (middle + 1)] > search)
	    		  return (int) middle;
	    	  
	    	  first = middle + 1;
	      }
	      else if ( s[(int) middle] == search ) 
	    	  break;
	      else
	      {
	    	  if (s[(int) (middle - 1)] < search)
	    		  return (int) (middle - 1);
	    	  
	    	  last = middle - 1;
	      }
	 
	      middle = (first + last)/2;
	   }
	    
		return -1;
	}
	
	public static void findSums()
	{
		s[0] = a;
		
		for (int i = 0; i < 100000; i++)
			s[i] = s[i - 1] + b;
	}
	
	public static void sum()
	{
		
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
