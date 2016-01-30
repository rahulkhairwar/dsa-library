package com.codechef.practice.easy.year2015.may;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.InputMismatchException;

class DiscrepanciesInTheVotersList
{
	private static int m, n, p;
	private static int mArray[], nArray[], pArray[], finalArray[];
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		DiscrepanciesInTheVotersList list = new DiscrepanciesInTheVotersList();
		
		reader = list.new InputReader(System.in);
		writer = list.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		StringBuilder result = new StringBuilder();
		int count = 0;
		
		m = reader.nextInt();
		n = reader.nextInt();
		p = reader.nextInt();
		
		mArray = new int[m];
		nArray = new int[n];
		pArray = new int[p];
		finalArray = new int[m + n + p];
		
		for (int i = 0; i < m; i++)
			mArray[i] = reader.nextInt();
		
		for (int i = 0; i < n; i++)
		{
			nArray[i] = reader.nextInt();

			if (binarySearch(nArray[i], 1))
				finalArray[count++] = nArray[i];
		}
		
		for (int i = 0; i < p; i++)
		{
			pArray[i] = reader.nextInt();
			
			if (binarySearch(pArray[i], 1) || binarySearch(pArray[i], 2))
				finalArray[count++] = pArray[i];
		}
		
		if (count != 0)
		{
			int previous, temp, i;

			previous = finalArray[0];
			temp = count;
			i = 1;
			
			Arrays.sort(finalArray, 0, count);
			result.append(finalArray[0] + "\n");

			while (finalArray[i] == previous)
			{
				i++;
				count--;
			}

			for (; i < temp;)
			{
				previous = finalArray[i];
				result.append(finalArray[i] + "\n");

				while (finalArray[i] == previous)
				{
					i++;
					count--;
				}

				count++;
			}
		}
		
		writer.println(count);
		writer.println(result.toString());
	}
	
	public static boolean binarySearch(int searchElement, int searchArray)
	{
		int first, last, middle;
		
		first = 0;
		
		if (searchArray == 1)
		{
			last = m - 1;
			
			while (first <= last)
			{
				middle = (last + first) / 2;
				
				if (mArray[middle] == searchElement)
					return true;
				
				if (mArray[middle] > searchElement)
					last = middle - 1;
				else if (mArray[middle] < searchElement)
					first = middle + 1;
			}
		}
		
		else if (searchArray == 2)
		{
			last = n - 1;
			
			while (first <= last)
			{
				middle = (last + first) / 2;
				
				if (nArray[middle] == searchElement)
					return true;
				
				if (nArray[middle] > searchElement)
					last = middle - 1;
				else if (nArray[middle] < searchElement)
					first = middle + 1;
			}
		}
		
		return false;
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

		public void println(long x)
		{
			writer.println(x);
		}

		public void print(long x)
		{
			writer.print(x);
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

10 8 10
1 2 3 4 5 6 7 8 9 20
4 5 6 7 8 9 12 20
20 45 78 79 80 81 82 83 84 85
----

10 12 14
1 2 4 5 6 7 8 9 10 20
21 22 23 24 25 26 27 28 29 30 31 32
40 41 42 43 44 45 46 47 48 49 50 51 52 53
----

5 5 5
4 5 6 7 8
4 5 6 7 8
4 5 6 7 8
----

5 6 6
1 2 3 4 5
1 2 3 4 5 6
1 2 3 4 5 6
----

5 6 5
1 2 3 4 5
1 2 3 4 5 6
1 2 3 4 5
----

6 5 5
1 2 3 4 5 6
1 2 3 4 5
1 2 3 4 5
----

6 6 5
1 2 3 4 5 6
1 2 3 4 5 6
1 2 3 4 5
----

*/
