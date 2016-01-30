package com.codechef.practice.beginner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class ChefAndGift
{
	private static int t, n;
	private static InputReader reader;
	private static OutputWriter writer;

	public static void main(String[] args)
	{
		ChefAndGift chefAndGift = new ChefAndGift();
		
		reader = chefAndGift.new InputReader(System.in);
		writer = chefAndGift.new OutputWriter(System.out);
		
		start();
		
		reader.close();
		writer.close();
	}
	
	public static void start()
	{
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			n = reader.nextInt();
			
			if (n == 1)
				System.out.println(reader.nextInt());
			else if (n == 2)
				System.out.println(min(reader.nextInt(), reader.nextInt()));
			else
			{
				int p, q, min, max, a[];
				
				a = new int[6];
				
				p = reader.nextInt();
				q = reader.nextInt();
				
				min = min2(p, q);
				max = max2(p, q);
				
/*				System.out.println("Currently the array looks like : ");
				printArray(a, 6);
				System.out.println("\nand min : " + min + " and max : " + max);*/
				
				for (int j = 2; j < n; j++)
				{
					p = reader.nextInt();
					
					a[0] = min + p;
					a[1] = min - p;
					a[2] = min * p;
					a[3] = max + p;
					a[4] = max - p;
					a[5] = max * p;
					
					min = findMinFromArray(a, 6);
					max = findMaxFromArray(a, 6);
					
/*					System.out.println("Currently the array looks like : ");
					printArray(a, 6);
					System.out.println("and min : " + min + " and max : " + max);*/
				}
				
				System.out.println(min);
			}
		}
	}

	public static int min(int a, int b)
	{
		if (a < 0 ^ b < 0)
		{
			if (absolute(a) == 1 && absolute(b) == 1)
				return -2;
			else
				return a * b;
		}
		else if (a < 0  && b < 0)
			return a + b;
		
		int add, sub, mul;
		
		add = a + b;
		sub = a - b;
		mul = a * b;
		
		if (add <= sub && add <= mul)
			return add;
		else if (sub <= add && sub <= mul)
			return sub;
		else
			return mul;
	}
	
	public static int max(int a, int b)
	{
		if (a < 0 ^ b < 0)
			return absolute(absolute(a) + absolute(b));
		else if (a < 0 && b < 0)
			return a * b;
		
		int add, sub, mul;
		
		add = a + b;
		sub = a - b;
		mul = a * b;
		
		if (add >= sub && add >= mul)
			return add;
		else if (sub >= add && sub >= mul)
			return sub;
		else
			return mul;
	}
	
	public static int min2(int a, int b)
	{
		int add, sub, mul;
		
		add = a + b;
		sub = a - b;
		mul = a * b;
		
		if (add <= sub && add <= mul)
			return add;
		else if (sub <= add && sub <= mul)
			return sub;
		else
			return mul;
	}
	
	public static int max2(int a, int b)
	{
		int add, sub, mul;
		
		add = a + b;
		sub = a - b;
		mul = a * b;
		
		if (add >= sub && add >= mul)
			return add;
		else if (sub >= add && sub >= mul)
			return sub;
		else
			return mul;
	}
	
	public static int findMinFromArray(int a[], int size)
	{
		int minimum = a[0];
		
		for (int i = 1; i < size; i++)
			if (a[i] < minimum)
				minimum = a[i];
		
		return minimum;
	}
	
	public static int findMaxFromArray(int a[], int size)
	{
		int maximum = a[0];
		
		for (int i = 1; i < size; i++)
			if (a[i] > maximum)
				maximum = a[i];
		
		return maximum;
	}
	
	public static int absolute(int a)
	{
		if (a >= 0)
			return a;
		else
			return -a;
	}

	public static void printArray(int a[], int size)
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

/*

10
3
1 2 3
1
9
5
1 4 -3 2 -7
3
4 3 -8
3
-5 8 -2
-----------
3
-1 1 1
3
1 -1 1
3
1 1 -1
3
-1 -1 1
3
-1 1 -1
3
1 -1 -1
3
-1 -1 -1

answers : -4 9 -126 -96
-3 -2 

*/