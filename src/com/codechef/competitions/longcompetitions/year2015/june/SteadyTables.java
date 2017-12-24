package com.codechef.competitions.longcompetitions.year2015.june;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

class SteadyTables
{
	private static int t, rows, columns;
	private static long mod, c[][], answer[][];
	private static InputReader reader;
	private static PrintWriter out;
	
	public static void main(String[] args)
	{
		SteadyTables tables = new SteadyTables();
		
		reader = tables.new InputReader(System.in);
		out = new PrintWriter(System.out);
		getAttributes();
		out.flush();
		reader.close();
		out.close();
	}
	
	public static void getAttributes()
	{
		t = reader.nextInt();
		mod = 1000000000;	// 10^9
		precalculateCombinations();
		
		for (int i = 0; i < t; i++)
		{
			rows = reader.nextInt();
			columns = reader.nextInt();
			out.println(solve());
		}
	}
	
	public static void precalculateCombinations()
	{
		c = new long[4100][4100];
		
		for (int i = 0; i <= 4000; i++)
			c[i][0] = 1;
		
		for (int i = 1; i <= 4000; i++)
			for (int j = 1; j <= i; j++)
				c[i][j] = (c[i - 1][j - 1] + c[i - 1][j]) % mod;
	}
	
	public static long solve()
	{
		answer = new long[rows + 1][columns + 1];
		answer[0][0] = 1;
		
		for (int i = 1; i <= rows; i++)
		{
			int s = 0;
			
			for (int j = 0; j <= columns; j++)
			{
				s += answer[i - 1][j];
				
				if (s >= mod)
					s -= mod;
				
				answer[i][j] = (s * c[j + columns - 1][columns - 1]) % mod;
			}
		}
		
		int result = 0;
		
		for (int i = 0; i <= columns; i++)
		{
			result += answer[rows][i];
			
			if (result >= mod)
				result -= mod;
		}
		
		return result;
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

}
