package com.hackerearth.competitions.acmicpcpracticecontest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class Third
{
	static int n, ar[];
	static InputReader reader;
	static OutputWriter writer;
	
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
	
	static void getAttributes()
	{
		n = reader.nextInt();
		
		ar = reader.nextIntArray(n);
		
		int maxi = 0;
		
		for (int i = 0; i < n; i++)
			if (ar[i] > maxi)
				maxi = ar[i];
		
		int val = n * maxi;
		
		int currCount, maxCount, max[];
		int finCount = 0;
		
		currCount = maxCount = 0;
		max = new int[1000000];
		
		int test = 0;
		
		for (int i = 2; i <= val; i++)
		{
			currCount = 0;
			
			for (int j = 0; j < n; j++)
			{
				test = sum(j, i);
				
				if (test >= 0)
				{
					j = test;
					currCount++;
				}
				else if (test == -1)
					continue;
				else
					break;
			}
			
			if (currCount > maxCount)
			{
				maxCount = currCount;
				finCount = 0;
				max[finCount++] = i;
			}
			else if (currCount == maxCount)
				max[finCount++] = i;
		}
		
		int oneCount = 0;
		
		for (int i = 0; i < n; i++)
			if (ar[i] == 1)
				oneCount++;
		
		if (oneCount > maxCount)
		{
			writer.println(oneCount);
			writer.println(1);
		}
		else if (oneCount == maxCount)
		{
			writer.println(oneCount);
			
			writer.print(1 + " ");
			
			for (int i = 0; i < finCount; i++)
				writer.print(max[i] + " ");
		}
		else
		{
			writer.println(maxCount);

			for (int i = 0; i < finCount; i++)
				writer.print(max[i] + " ");
		}
	}
	
	// ret >= 0 => ∑ar [ind, ret] = x
	// ret == -1 => ∑ar [ind, someIndex] > x
	// ret == -2 => ∑ar [ind, n - 1] < x
	static int sum(int ind, int x)
	{
		int s = 0;
		
		for (int i = ind; i < n; i++)
		{
			s += ar[i];
			
			if (s == x)
				return i;
			else if (s > x)
				return -1;
		}
		
		return -2;
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
		
		public int[] nextIntArray(int arraySize)
		{
			int array[] = new int[arraySize];
			
			for (int i = 0; i < arraySize; i++)
				array[i] = nextInt();
			
			return array;
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
		
		public long[] nextLongArray(int arraySize)
		{
			long array[] = new long[arraySize];
			
			for (int i = 0; i < arraySize; i++)
				array[i] = nextLong();
			
			return array;
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
		
		public String nextLine()
		{
			int c = read();
			
			StringBuilder result = new StringBuilder();
			
			do
			{
				result.appendCodePoint(c);
				
				c = read();
			} while (!isNewLine(c));
			
			return result.toString();
		}
		
		public boolean isNewLine(int c)
		{
			return c == '\n';
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

/*


6
11 5 6 2 8 10

6
1 1 1 1 2 2

6
2 2 2 1 1 1

*/
