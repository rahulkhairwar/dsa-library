package com.codechef.practice.easy.year2015.march;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class SumsInATriangle
{
	private static int t, n;
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		SumsInATriangle triangle = new SumsInATriangle();
		
		reader = triangle.new InputReader(System.in);
		writer = triangle.new OutputWriter(System.out);
		
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
			getAttributes2();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
/*	public static void getAttributes()
	{
		MyArray oldArray, currentArray;
		int max;
		
		n = reader.nextInt();
		
		oldArray = new MyArray(n);
		currentArray = new MyArray(n);
		
		max = oldArray.a[0] = reader.nextInt();
		
		for (int i = 1; i < n - 1; i++)
		{
			currentArray.a[0] = oldArray.a[0] + reader.nextInt();

			for (int j = 1; j < i; j++)
				currentArray.a[j] = Math.max(oldArray.a[j], oldArray.a[j - 1])
						+ reader.nextInt();

			currentArray.a[i] = oldArray.a[i - 1] + reader.nextInt();

			oldArray.a = currentArray.a;
			currentArray.a = new int[n];
		}

		if (n > 1)
		{
			currentArray.a[0] = oldArray.a[0] + reader.nextInt();
			
			if (currentArray.a[0] > max)
				max = currentArray.a[0];
			
			for (int j = 1; j < n - 1; j++)
			{
				currentArray.a[j] = Math.max(oldArray.a[j], oldArray.a[j - 1])
						+ reader.nextInt();
				
				if (currentArray.a[j] > max)
					max = currentArray.a[j];
			}

			currentArray.a[n - 1] = oldArray.a[n - 2] + reader.nextInt();

			if (currentArray.a[n - 1] > max)
				max = currentArray.a[n - 1];
		}
		
		writer.println(max);
	}*/
	
	public static void getAttributes2()
	{
		int max, a[][];
		
		n = reader.nextInt();
		
		a = new int[n][n];
		
		max = a[0][0] = reader.nextInt();
		
		for (int i = 1; i < n - 1; i++)
		{
			a[i][0] = a[i - 1][0] + reader.nextInt();
			
			for (int j = 1; j < i; j++)
				a[i][j] = max(a[i - 1][j - 1], a[i - 1][j]) + reader.nextInt();
			
			a[i][i] = a[i - 1][i - 1] + reader.nextInt();
		}
		
		if (n > 1)
		{
			a[n - 1][0] = a[n - 2][0] + reader.nextInt();
			
			if (a[n - 1][0] > max)
				max = a[n - 1][0];
			
			for (int j = 1; j < n - 1; j++)
			{
				a[n - 1][j] = max(a[n - 2][j - 1], a[n - 2][j]) + reader.nextInt();
				
				if (a[n - 1][j] > max)
					max = a[n - 1][j];
			}
			
			a[n - 1][n - 1] = a[n - 2][n - 2] + reader.nextInt();
			
			if (a[n - 1][n - 1] > max)
				max = a[n - 1][n - 1];
		}
		
		writer.println(max);
	}
	
	public static int max(int a, int b)
	{
		if (a >= b)
			return a;
		else
			return b;
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

class MyArray
{
	int a[];
	
	public MyArray(int size)
	{
		a = new int[size];
	}
	
}

/*

3
3
1
2 6
8 5 10
4
1
2 6
8 5 10
13 2 3 6
5
1
2 6
8 5 10
13 2 3 6
19 21 8 7 11

answer : 17 24 45
 */
