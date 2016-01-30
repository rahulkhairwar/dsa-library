package com.hackerrank.practice.medium;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

public class DorseyThief
{
	static int n, x;
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
		n = in.nextInt();
		x = in.nextInt();
		
		Passenger[] all = new Passenger[n];
		int total = 0;
		
		for (int i = 0; i < n; i++)
		{
			int val, grams;
			val = in.nextInt();
			grams = in.nextInt();

			//all[i] = new Passenger(in.nextInt(), in.nextInt());
			if (grams <= x)
				all[total++] = new Passenger(val, grams);
		}
		
		/*long[][] dp = new long[n + 1][x];
		
		dp[0][0] = 0;
		*/
		
		Arrays.sort(all, new Comparator<Passenger>()
		{
			@Override
			public int compare(Passenger o1, Passenger o2)
			{
				return o1.grams - o2.grams;
			}
		});
		
		Select[][] dp = new Select[total + 1][x + 1];
		int maxJ = 0;
		
		dp[0][0] = new Select(0, true);
		
		for (int i = 1; i <= total; i++)
		{
			for (int j = 0; j <= maxJ; j++)
			{
				if (dp[i - 1][j] != null)
				{
					if (dp[i][j] == null)
						dp[i][j] = new Select(dp[i - 1][j].value, true);
					else
						dp[i][j] = new Select(Math.max(dp[i][j].value, dp[i - 1][j].value), true);

					if (j + all[i - 1].grams <= x)
					{
						if (dp[i][j + all[i - 1].grams] != null)
						{
							dp[i][j + all[i - 1].grams] = new Select(Math.max(
									dp[i][j + all[i - 1].grams].value,
									dp[i - 1][j].value + all[i - 1].value), true);
						}
						else
						{
							dp[i][j + all[i - 1].grams] = new Select(
									dp[i - 1][j].value + all[i - 1].value, true);
						}
						
						if (j + all[i - 1].grams > maxJ)
							maxJ = j + all[i - 1].grams;
					}
				}
			}
		}
		
/*		System.out.println("dp : ");
		
		for (int i = 0; i <= n; i++)
		{
			for (int j = 0; j <= x; j++)
			{
				if (dp[i][j] == null)
					System.out.print("---(-) ");
				else
					System.out.print(dp[i][j].value + "(" + j + ") ");
			}
			
			System.out.println();
		}
		
		System.out.println("n : " + n + ", x : " + x + ", dp[n][x].value : " + dp[n][x].value);*/
		
		if (dp[total][x] == null)
			out.println("Got caught!");
		else
			out.println(dp[total][x].value);
	}
	
	static class Passenger
	{
		int value, grams;
		
		public Passenger(int value, int grams)
		{
			this.value = value;
			this.grams = grams;
		}
	}

	static class Select
	{
		long value;
		boolean keep;
		
		public Select(long value, boolean keep)
		{
			this.value = value;
			this.keep = keep;
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

	static class CMath
	{
		static long power(long number, int power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;
			
			if (power == 1)
				return number;
			
			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}
		
		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}
		
	}

}
