package com.codechef.competitions.longcompetitions.year2015.july;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class Na2aAndLuckyStone
{
	private static int n;
	private static long a;
	private static InputReader reader;
	private static OutputWriter writer;

	public static void main(String[] args)
	{
		Na2aAndLuckyStone stone = new Na2aAndLuckyStone();

		reader = stone.new InputReader(System.in);
		writer = stone.new OutputWriter(System.out);

		getAttributes();

		writer.flush();

		reader.close();
		writer.close();
	}

	public static void getAttributes()
	{
		long powerOfFive, temp, answer;

		n = reader.nextInt();

		for (int i = 0; i < n; i++)
		{
			a = reader.nextLong();

			if (a % 10 == 0)
			{
				writer.println(a);

				continue;
			}

			powerOfFive = 0;
			temp = a;

			while (temp % 5 == 0)
			{
				powerOfFive++;
				temp /= 5;
			}
			
			if (powerOfFive > 0)
			{
				answer = powerOfFour(powerOfFive / 2);
				
				if (powerOfFive % 2 == 0)
					writer.println(a * answer);
				else
					writer.println(a * (answer * 4l));
			}
			else
				writer.println(a);
		}
	}
	
	public static void getAttributes2()
	{
		n = reader.nextInt();

		for (int i = 0; i < n; i++)
		{
			a = reader.nextLong();
			
			long answer = a;
			
			// fucking Codechef question authors!!
			// never explain anything properly!!
			if (answer % 10 == 0)
			{
				long temp;
				
				while (true)
				{
					temp = answer;
					
					while (temp % 10 == 0)
						temp /= 10;
					
					if (temp % 10 == 5)
						answer *= 4;
					else
						break;
				}
				
				writer.println(answer);
				
				continue;
			}
			
			long temp;
			
			while (true)
			{
				temp = answer;
				
				while (temp % 10 == 0)
					temp /= 10;
				
				if (temp % 10 == 5)
					answer *= 4;
				else
					break;
			}
			
			writer.println(answer);
		}
	}
	
	public static long powerOfFour(long power)
	{
		if (power == 0)
			return 1;
		
		if (power == 1)
			return 4;
		
		return 4 * powerOfFour(power - 1);
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
