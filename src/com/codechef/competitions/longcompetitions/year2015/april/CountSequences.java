package com.codechef.competitions.longcompetitions.year2015.april;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class CountSequences
{
	private static int t, n, left, right;
	private static long previous, current, sum, r, mod, inverse[];
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		CountSequences sequences = new CountSequences();
		
		reader = sequences.new InputReader(System.in);
		writer = sequences.new OutputWriter(System.out);
		
		getAttributes();
		
/*		mod = 1000003;
		System.out.println(powerWithMod(3, 1000001));*/
		
/*		mod = 1000003;
		System.out.println(power(2, 5));
		System.out.println(power(2, 8));
		System.out.println(power(3, 5));
		System.out.println(power(5, 10));
		System.out.println(9765625 % 1000003);
		System.out.println(power(2, 10));*/
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		t = reader.nextInt();
		
		mod = 1000003;
		inverse = new long[1000004];
		
		findInverse();
		
		for (int i = 0; i < t; i++)
		{
			n = reader.nextInt();
			left = reader.nextInt();
			right = reader.nextInt();
			
			r = right - left + 1;
			
			calculate();
		}
	}
	
	public static void calculate()
	{
		sum = previous = r % mod;
		
		for (long i = 1; i < n; i++)
		{
			// current = (((previous * ((r + i) % mod))% mod) * power(i + 1, mod - 2)) % mod;
			
			current = (((previous * ((r + i) % mod))% mod) * inverse[(int) (i + 1)]) % mod;
			
			if (current == 0)
				break;
			else
				sum = (sum + current) % mod;
			
			previous = current;
		}
		
		writer.println(sum);
	}
	
	public static void findInverse()
	{
		inverse[1] = 1;
		
		for (int i = 2; i < 1000004; i++)
			inverse[i] = power(i, 1000001) % 1000001;
	}
	
	public static long power(long num, long power)
	{
		if (power == 1)
			return num;
		
		long p;
		
		if (power % 2 == 0)
			p = 1;
		else
			p = num;

		return (long) ((p * (Math.pow(power(num, power / 2) % mod, 2) % mod)) % mod);
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

3
1 4 5
2 4 5
3 4 5

5
5 45 59
92734 3974 9374
23980 2783 20983
827 29873 297398
2 4 5

*/