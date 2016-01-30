package com.codeforces.competitions.year2015.julytoseptember.round317div2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public final class Second
{
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		Second second = new Second();
		
		reader = second.new InputReader(System.in);
		writer = second.new OutputWriter(System.out);
		
		try
		{
			getAttributes();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes() throws IOException
	{
		int n, s, price, quantity;
		String dir;
		
		Book buy[], sell[];
		
		buy = new Book[100006];
		sell = new Book[100006];
		
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		
		String nS = bReader.readLine();
		String nSA[] = nS.split(" ");
		
		n = Integer.parseInt(nSA[0]);
		s = Integer.parseInt(nSA[1]);
		
		String line, tok[];
		
		for (int i = 0; i < n; i++)
		{
			line = bReader.readLine();
			
			tok = line.split(" ");

			dir = tok[0];
			price = Integer.parseInt(tok[1]);
			quantity = Integer.parseInt(tok[2]);
			
			if (dir.charAt(0) == 'B')
			{
				if (buy[price] != null)
					buy[price].quantity += quantity;
				else
					buy[price] = new Book(price, quantity);
			}
			else
			{
				if (sell[price] != null)
					sell[price].quantity += quantity;
				else
					sell[price] = new Book(price, quantity);
			}
		}
		
		int i, countS;
		Book finS[] = new Book[n];
		
		i = countS = 0;
		
		while (i < 100006)
		{
			if (sell[i] != null)
			{
				finS[countS++] = sell[i];
			}
			
			i++;
		}
		
		int j, countB;
		Book finB[] = new Book[n];
		
		j = countB = 0;
		
		while (j < 100006)
		{
			if (buy[j] != null)
			{
				finB[countB++] = buy[j];
			}
			
			j++;
		}
		
		if (countS >= s)
			for (int k = s - 1; k >= 0; k--)
				writer.println("S " + finS[k].price + " " + finS[k].quantity);
		else
			for (int k = countS - 1; k >= 0; k--)
				writer.println("S " + finS[k].price + " " + finS[k].quantity);
				
		if (countB >= s)
			for (int k = countB - 1; k >= countB - s; k--)
				writer.println("B " + finB[k].price + " " + finB[k].quantity);
		else
			for (int k = countB - 1; k >= 0; k--)
				writer.println("B " + finB[k].price + " " + finB[k].quantity);
	}
	
	static class Book
	{
		int price, quantity;
		
		public Book(int price, int quantity)
		{
			this.price = price;
			this.quantity = quantity;
		}

		@Override
		public String toString()
		{
			return "Book [price=" + price + ", quantity=" + quantity + "]";
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
		
		public String nextLine()
		{
			int c = read();

			StringBuilder result = new StringBuilder();

			do
			{
				result.appendCodePoint(c);
				
				c = read();
			} while (c != '\n');

			return result.toString();
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

/*

6 2
B 10 3
S 50 2
S 40 1
S 50 6
B 20 4
B 25 10


8 2
B 10 3
S 50 2
S 51 2
S 45 2
S 40 1
S 50 6
B 20 4
B 25 10

*/
