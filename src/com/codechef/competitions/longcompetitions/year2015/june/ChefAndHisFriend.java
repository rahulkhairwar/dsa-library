package com.codechef.competitions.longcompetitions.year2015.june;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class ChefAndHisFriend
{
	private static int t, waitingTimeChef, waitingTimeYuri, arrivalTimeLimitChef, arrivalTimeLimitYuri;
	private static InputReader reader;
	private static OutputWriter writer;

	public static void main(String[] args)
	{
		ChefAndHisFriend friend = new ChefAndHisFriend();
		
		reader = friend.new InputReader(System.in);
		writer = friend.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			arrivalTimeLimitChef = reader.nextInt();
			arrivalTimeLimitYuri = reader.nextInt();
			waitingTimeChef = reader.nextInt();
			waitingTimeYuri = reader.nextInt();
			
			if (waitingTimeChef == 0 && waitingTimeYuri == 0)
			{
				int smaller, bigger;
				
				if (arrivalTimeLimitChef < arrivalTimeLimitYuri)
				{
					smaller = arrivalTimeLimitChef;
					bigger = arrivalTimeLimitYuri;
				}
				else
				{
					smaller = arrivalTimeLimitYuri;
					bigger = arrivalTimeLimitChef;
				}
				
				// writer.println((double) smaller / ((double) bigger * Math.pow(10, 6)));
				System.out.printf("%.10f\n", (double) smaller / ((double) bigger * Math.pow(10, 6)));
			}
			else
			{
				int smaller, bigger;
				boolean chefSmaller;
				
				if (arrivalTimeLimitChef < arrivalTimeLimitYuri)
				{
					smaller = arrivalTimeLimitChef;
					bigger = arrivalTimeLimitYuri;
					
					chefSmaller = true;
				}
				else
				{
					smaller = arrivalTimeLimitYuri;
					bigger = arrivalTimeLimitChef;
					
					chefSmaller = false;
				}
				
				if (chefSmaller)
					System.out.printf("%.10f\n", (double) (smaller + waitingTimeChef) / ((double) (bigger + waitingTimeYuri)/* * Math.pow(10, 6)*/));
				else
					System.out.printf("%.10f\n", (double) (smaller + waitingTimeYuri) / ((double) (bigger + waitingTimeChef)/* * Math.pow(10, 6)*/));
			}
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
		
		public void println(double x)
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
		
		public void print(double x)
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
