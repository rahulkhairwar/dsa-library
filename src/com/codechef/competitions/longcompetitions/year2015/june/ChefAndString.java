package com.codechef.competitions.longcompetitions.year2015.june;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;

class ChefAndString
{
	private static int t, length, questions, k, mod;
	private static int factorials[], answer[];
	private static String s;
	private static HashMap<String, Integer> substrings;
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		ChefAndString chef = new ChefAndString();
		
		reader = chef.new InputReader(System.in);
		writer = chef.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		Collection<Integer> values = new ArrayList<Integer>();
		
		t = reader.nextInt();
		mod = 1000000007;
		
		findFactorials();
		
		// System.out.println(Arrays.toString(factorials));
		
		for (int i = 0; i < t; i++)
		{
			length = reader.nextInt();
			questions = reader.nextInt();
			
			s = reader.next();
			
			find();
			
			// Iterator<HashMap<String, Integer>> it = substrings.
			
			int temp, size, ans;
			
			for (int j = 0; j < questions; j++)
			{
				k = reader.nextInt();
				
				values = substrings.values();
				size = values.size();
				ans = 0;
				
				// for (int l = 0; l < size; l++)
				{
					Iterator<Integer> iterator = values.iterator();
					
					while (iterator.hasNext())
					{
						if ((temp = iterator.next()) >= k)
						{
							ans += (factorials[temp] / (factorials[k] * factorials[temp - k]));
						}
					}
				}
				
				writer.println(ans);
			}
		}
	}
	
	public static void findFactorials()
	{
		factorials = new int[10];
		
		factorials[0] = 1;
		factorials[1] = 1;
		
		for (int i = 2; i < 10; i++)
			factorials[i] = factorials[i - 1] * i;
	}
	
	public static void find()
	{
		int count;
		Integer value;
		String substring;
		
		length = s.length();
		count = 1;
		
		substrings = new HashMap<String, Integer>(); 
		
		for (int i = 0; i < length; i++)
		{
			for (int j = 0; j < length - count + 1; j++)
			{
				substring = s.substring(j, j + count);
				
				// System.out.println("curr subS : " + substring);
				
				value = substrings.get(substring);
				
				if (value != null)
					substrings.replace(substring, value + 1);
				else
					substrings.put(substring, 1);
			}
			
			count++;
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
