package com.hackerrank.practice.easy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public class Knapsack
{
	static int t, n, k, array[], valid[];
	static InputReader reader;
	static OutputWriter writer;
	
	public static void main(String args[])
	{
		Knapsack knapsack = new Knapsack();
		
		reader = knapsack.new InputReader(System.in);
		writer = knapsack.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	static void getAttributes()
	{
		int validCount;
		boolean maxPossible;
		
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			n = reader.nextInt();
			k = reader.nextInt();
			
			array = new int[n];
			valid = new int[n];
			
			validCount = 0;
			maxPossible = false;
			
			for (int j = 0; j < n; j++)
			{
				array[j] = reader.nextInt();
				
				if (array[j] < k)
					valid[validCount++] = array[j];
				
				if (array[j] == k)
					maxPossible = true;
				else if (array[j] == 1)
					maxPossible = true;
			}
			
			if (maxPossible)
			{
				writer.println(k);
				
				continue;
			}
			
			writer.println(findMaxSum(valid, validCount));
		}
	}
	
	static int findMaxSum(int valid[], int length)
	{
		boolean dp[][] = new boolean[length + 1][k + 1];
		
		dp[0][0] = true;
		
		for (int i = 1; i <= length; i++)
		{
			for (int j = 0; j <= k; j++)
			{
				if (dp[i - 1][j])
				{
					dp[i][j] = true;
					
					if (j + valid[i - 1] < k)
						dp[i][j + valid[i - 1]] = true;
					else if (j + valid[i - 1] == k)
						return k;
				}
			}
		}
		
		if (dp[length][1])
			return k;
		
		for (int i = 1; i <= k / 2; i++)
		{
			if (dp[length][i])
			{
				int temp = 2;
				
				while (i * temp <= k)
				{
					dp[length][i * temp] = true;
					
					temp++;
				}
			}
		}
		
/*		System.out.println("Will print the dp array : ");
		
		for (int i = 0; i <= length; i++)
		{
			for (int j = 0; j <= k; j++)
				System.out.print((dp[i][j] ? "t " : "f ") + "");
			
			System.out.println();
		}*/
		
		for (int i = k; i >= 0; i--)
			if (dp[length][i])
				return i;
		
		return -1;
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
		
		public float nextFloat() // problematic
		{
			float result, div;
			byte c;

			result = 0;
			div = 1;
			c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean isNegative = (c == '-');

			if (isNegative)
				c = (byte) read();

			do
			{
				result = result * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					result += (c - '0') / (div *= 10);

			if (isNegative)
				return -result;

			return result;
		}

		public double nextDouble() // not completely accurate
		{
			double ret = 0, div = 1;
			byte c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean neg = (c == '-');

			if (neg)
				c = (byte) read();

			do
			{
				ret = ret * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);

			if (neg)
				return -ret;

			return ret;
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
		
		public void println(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}
		
		public void print(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i] + " ");
		}

		public void println(long x)
		{
			writer.println(x);
		}

		public void print(long x)
		{
			writer.print(x);
		}
		
		public void println(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}
		
		public void print(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i]);
		}
		
		public void println(float num)
		{
			writer.println(num);
		}
		
		public void print(float num)
		{
			writer.print(num);
		}
		
		public void println(double num)
		{
			writer.println(num);
		}
		
		public void print(double num)
		{
			writer.print(num);
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
