package com.codechef.competitions.longcompetitions.year2015.march;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.InputMismatchException;

class ChefAndProblems
{
	private static int n, m, k, l, r;
	private static int a[];
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		ChefAndProblems chefAndProblems = new ChefAndProblems();
		
		reader = chefAndProblems.new InputReader(System.in);
		writer = chefAndProblems.new OutputWriter(System.out);
		
		getAttributes();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		n = reader.nextInt();
		m = reader.nextInt();
		k = reader.nextInt();
		
		a = new int[n];
		
		for (int i = 0; i < n; i++)
			a[i] = reader.nextInt();
		
		for (int i = 0; i < k; i++)
		{
			l = reader.nextInt();
			r = reader.nextInt();
			
			if (l == r)
			{
				System.out.println(0);
				
				continue;
			}
			
			find();
		}
	}
	
	public static void find()
	{
		int maxDiff, countElementsInB, q, b[];
		
		maxDiff = 0;
		b = new int[n];
		countElementsInB = 0;
		
		b = Arrays.copyOf(a, n);
		System.out.println("Initially b[] : ");
		printArray(b, n);
		
		System.out.println("\nfor l : " + l + " and r : " + r);

		for (int j = l; j < r - 1; j++)
		{
			if (a[l - 1] == a[j])
			{
				q = j - l + 1;

				if (q > maxDiff)
					maxDiff = q;
			}
			else
				b[countElementsInB++] = a[j];
		}

		System.out.print("Currently the b[] array looks like : ");
		printArray(b, countElementsInB);
		
		System.out.print("Currently the a[] array looks like : ");
		printArray(a, n);

		for (int i = l; i < r - 2; i++)
		{
			countElementsInB = 0;
			
			System.out.println("currently i : " + i);
			
			for (int j = i + 1; j < r - 1; j++)
			{
				if (b[i] == b[j])
				{
					q = j - i;
					
					if (q > maxDiff)
						maxDiff = q;
				}
				else
					b[countElementsInB++] = b[j];
			}
			
			System.out.print("Currently the b[] array looks like : ");
			printArray(b, countElementsInB);
			System.out.print("Currently the a[] array looks like : ");
			printArray(a, n);
		}
		
		System.out.println(maxDiff);
	}
	
	public static void printArray(int array[], int size)
	{
		for (int i = 0; i < size; i++)
			System.out.print(array[i] + " ");
		
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

		public void close()
		{
			writer.close();
		}

	}

}
