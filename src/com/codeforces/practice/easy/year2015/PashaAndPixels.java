package com.codeforces.practice.easy.year2015;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public final class PashaAndPixels
{
	private static int n, m, k, row, column, array[][];
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		PashaAndPixels pixels = new PashaAndPixels();
		
		reader = pixels.new InputReader(System.in);
		writer = pixels.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		boolean result;
		
		n = reader.nextInt();
		m = reader.nextInt();
		k = reader.nextInt();
		
		if (n == 1 || m == 1)
		{
			writer.println(0);
			
			return;
		}
		
		array = new int[n + 1][m + 1];
		
		for (int i = 0; i < k; i++)
		{
			row = reader.nextInt();
			column = reader.nextInt();
			
			result = fill(row, column);
			
			if (result) // if a 2x2 square matrix is found
			{
				writer.println(i + 1);
				
				return;
			}
		}
		
		writer.println(0);
	}
	
	public static boolean fill(int rowNumber, int columnNumber)
	{
		array[rowNumber][columnNumber] = 1;
		
		if (rowNumber == 0)
		{
			if (columnNumber == 0)
				return checkDownRight(rowNumber, columnNumber);
			
			if (columnNumber == m)
				return checkDownLeft(rowNumber, columnNumber);
			
			if (checkDownLeft(rowNumber, columnNumber))
				return true;
			
			if (checkDownRight(rowNumber, columnNumber))
				return true;
		}
		else if (rowNumber == n)
		{
			if (columnNumber == 0)
				return checkUpRight(rowNumber, columnNumber);
			
			if (columnNumber == m)
				return checkUpLeft(rowNumber, columnNumber);
			
			if (checkUpLeft(rowNumber, columnNumber))
				return true;
			
			if (checkUpRight(rowNumber, columnNumber))
				return true;
		}
		else
			if (checkUpLeft(rowNumber, columnNumber)
					|| checkUpRight(rowNumber, columnNumber)
					|| checkDownLeft(rowNumber, columnNumber)
					|| checkDownRight(rowNumber, columnNumber))
				return true;
		
		return false;
	}
	
	public static boolean checkDownLeft(int rowNumber, int columnNumber)
	{
		if (columnNumber == 0)
			return false;
		
		int sum = array[rowNumber][columnNumber - 1]
				+ array[rowNumber][columnNumber]
				+ array[rowNumber + 1][columnNumber]
				+ array[rowNumber + 1][columnNumber - 1];
		
		if (sum == 4)
			return true;
		
		return false;
	}
	
	public static boolean checkDownRight(int rowNumber, int columnNumber)
	{
		if (columnNumber == m)
			return false;
		
		int sum = array[rowNumber][columnNumber]
				+ array[rowNumber][columnNumber + 1]
				+ array[rowNumber + 1][columnNumber + 1]
				+ array[rowNumber + 1][columnNumber];
		
		if (sum == 4)
			return true;
		
		return false;
	}
	
	public static boolean checkUpLeft(int rowNumber, int columnNumber)
	{
		if (columnNumber == 0)
			return false;
		
		int sum = array[rowNumber - 1][columnNumber - 1]
				+ array[rowNumber - 1][columnNumber]
				+ array[rowNumber][columnNumber]
				+ array[rowNumber][columnNumber - 1];
		
		if (sum == 4)
			return true;
		
		return false;
	}
	
	public static boolean checkUpRight(int rowNumber, int columnNumber)
	{
		if (columnNumber == m)
			return false;
		
		int sum = array[rowNumber - 1][columnNumber]
				+ array[rowNumber - 1][columnNumber + 1]
				+ array[rowNumber][columnNumber + 1]
				+ array[rowNumber][columnNumber];
		
		if (sum == 4)
			return true;
		
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
