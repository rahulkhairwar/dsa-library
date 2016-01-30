package com.codechef.competitions.shortcompetitions.year2015.april.codequest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.InputMismatchException;

class MixSorting
{
	private static int t, numbers[];
	private static String s, newString;
	private static char chars[];
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		MixSorting sorting = new MixSorting();
		
		reader = sorting.new InputReader(System.in);
		writer = sorting.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		int length, numCount, charCount;
		t = reader.nextInt();
		
		numbers = new int[1000000];
		chars = new char[1000000];
		
		for (int i = 0; i < t; i++)
		{
			s = reader.next();
			
			length = s.length();
			numCount = charCount = 0;
			newString = "";
			
			for (int j = 0; j < length; j++)
			{
				if (s.charAt(j) < 65)
					numbers[numCount++] = s.charAt(j) - 48;
				else
					chars[charCount++] = s.charAt(j);
			}
			
/*			printIntArray(numbers, numCount);
			printCharArray(chars, charCount);
			*/
			Arrays.sort(numbers, 0, numCount);
			Arrays.sort(chars, 0, charCount);
/*			
			System.out.println("After sorting : **********************");
			
			printIntArray(numbers, numCount);
			printCharArray(chars, charCount);*/
			
			numCount = charCount = 0;
			
			for (int j = 0; j < length; j++)
			{
				// System.out.println("current char in s : " + s.charAt(j));
			// 	System.out.println("num[] : " + numbers[numCount] + " || chars[] : " + chars[charCount]);
				
				if (s.charAt(j) < 65)
					newString += (char) (numbers[numCount++] + 48);
				else
					newString += chars[charCount++];
			}
			
			writer.println(newString);
		}
		
		// writer.println(newString);
		// System.out.println(newString);
	}
	
	public static void printIntArray(int a[], int size)
	{
		for (int i = 0; i < size; i++)
			System.out.print(a[i] + " ");
		
		System.out.println();
	}
	
	public static void printCharArray(char a[], int size)
	{
		for (int i = 0; i < size; i++)
			System.out.print(a[i] + " ");
		
		System.out.println();
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

/*

5
js7657gg7673wjk98
986ho09uyrt6hgi21rd
uhui876340
674748748479765
iguyguygkiyd

*/
