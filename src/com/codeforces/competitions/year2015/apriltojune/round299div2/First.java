package com.codeforces.competitions.year2015.apriltojune.round299div2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public class First
{
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		First first = new First();
		
		reader = first.new InputReader(System.in);
		writer = first.new OutputWriter(System.out);
		
/*		for (int i = 0; i < 100; i++)
		{
			find(i);
		}*/
		
		// find(11);
		
		find();
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void find()
	{
		int s = reader.nextInt();
		int ones, tens;
		String fW, sW;
		
		ones = s % 10;
		tens = s / 10;
		fW = sW = "";
		
		if (ones == 0)
		{
			if (tens == 0)
				sW = "zero";
			else if (tens == 1)
				sW = "ten";
			else
				sW = "";
		}
		else if (tens == 1)
		{
			if (ones == 1)
				fW = "eleven";
			else if (ones == 2)
				fW = "twelve";
			else if (ones == 3)
				fW = "thirteen";
			else if (ones == 4)
				fW = "fourteen";
			else if (ones == 5)
				fW = "fifteen";
			else if (ones == 6)
				fW = "sixteen";
			else if (ones == 7)
				fW = "seventeen";
			else if (ones == 8)
				fW = "eighteen";
			else
				fW = "nineteen";
			
			// System.out.println("fW : " + fW);
		}
		else
			sW = oWord(ones);
		
		if (tens == 0)
			fW =""; 
		else if (tens != 1)
			fW = tWord(tens);
		
		if (fW == "")
			writer.println(sW);
		else if (sW == "")
			writer.println(fW);
		else
			writer.println(fW + "-" + sW);
	}
	
	public static String oWord(int n)
	{
		if (n == 1)
			return "one";
		else if (n == 2)
			return "two";
		else if (n == 3)
			return "three";
		else if (n == 4)
			return "four";
		else if (n == 5)
			return "five";
		else if (n == 6)
			return "six";
		else if (n == 7)
			return "seven";
		else if (n == 8)
			return "eight";
		else
			return "nine";
	}
	
	public static String tWord(int n)
	{
		if (n == 1)
			return "ten";
		else if (n == 2)
			return "twenty";
		else if (n == 3)
			return "thirty";
		else if (n == 4)
			return "forty";
		else if (n == 5)
			return "fifty";
		else if (n == 6)
			return "sixty";
		else if (n == 7)
			return "seventy";
		else if (n == 8)
			return "eighty";
		else
			return "ninety";
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
