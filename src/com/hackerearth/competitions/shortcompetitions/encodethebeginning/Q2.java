package com.hackerearth.competitions.shortcompetitions.encodethebeginning;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class Q2
{
	static int t, n, arr[];
	static String a, b;
	static InputReader in;
	static OutputWriter out;
	
	public static void main(String[] args)
	{
		in = new InputReader(System.in);
		out = new OutputWriter(System.out);
		
		solve();
		
		out.flush();
		
		in.close();
		out.close();
	}
	
	static void solve()
	{
		t = in.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			a = in.next();
			b = in.next();
			
			int oneToZero, zeroToOne, qsnToZero, qsnToOne;
			int zero, one, qsn;
			int length = b.length();
			
			zero = one = qsn = 0;
			oneToZero = zeroToOne = qsnToZero = qsnToOne = 0;
			
			for (int j = 0; j < length; j++)
			{
				if (a.charAt(j) == '0')
					zero++;
				else if (a.charAt(j) == '1')
					one++;
				else
					qsn++;
			}
			
			int reqZero, reqOne;
			
			reqZero = reqOne = 0;
			
			for (int j = 0; j < length; j++)
			{
				if (b.charAt(j) == '0')
					reqZero++;
				else
					reqOne++;
			}
			
			if (reqOne < one)
			{
				out.println("Case " + (i + 1) + ": " + -1);
				
				continue;
			}
			
			for (int j = 0; j < length; j++)
			{
				if (b.charAt(j) == '0' && a.charAt(j) == '1')
					oneToZero++;
				else if (b.charAt(j) == '1' && a.charAt(j) == '0')
					zeroToOne++;
				else if (a.charAt(j) == '?' && b.charAt(j) == '0')
					qsnToZero++;
				else if (a.charAt(j) == '?' && b.charAt(j) == '1')
					qsnToOne++;
			}
			
//			System.out.println("onetozero : " + oneToZero + ", zto1 : " + zeroToOne + ", qsntozero : " + qsnToZero + ", qto1 : " + qsnToOne);
			int min = Math.min(oneToZero, zeroToOne);
			
			int total = min;
			
			oneToZero -= min;
			zeroToOne -= min;
			
/*			total += qsn;
			qsn = 0;*/
//			System.out.println("tot : " + total);
			if (oneToZero > 0)
			{
				if (qsnToZero + qsnToOne >= oneToZero)
				{
					total += (2 * oneToZero);
					qsnToZero -= oneToZero;
					oneToZero = 0;
					total += (qsnToOne + qsnToZero);
					
					out.println("Case " + (i + 1) + ": " + total);
				}
				else
					out.println("Case " + (i + 1) + ": " + -1);
			}
			else
			{
				total += qsnToOne;
				total += qsnToZero;
				total += zeroToOne;
				
				out.println("Case " + (i + 1) + ": " + total);
			}
		}
	}

	static class InputReader
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

	static class OutputWriter
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

5
00111
00110
00?11
00110
0001 0100
000? 0100
?0101
11110

*/
