package com.codeforces.competitions.year2015.julytoseptember.round314div2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public final class First
{
	static int n, arr[]; 
	static InputReader reader;
	static OutputWriter writer;
	
	public static void main(String[] args)
	{
		First first = new First();
		
		reader = first.new InputReader(System.in);
		writer = first.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	static void getAttributes()
	{
		n = reader.nextInt();
		
		arr = new int[n];
		
		for (int i = 0; i < n; i++)
			arr[i] = reader.nextInt();
		
		for (int i = 0; i < n; i++)
		{
			int ans[] = minMax(i, 0, i - 1, i + 1, n - 1);
			
			writer.println(ans[0] + " " + ans[1]);
		}
	}
	
	static int[] minMax(int i, int lM, int l, int r, int rM)
	{
		int ans[] = new int[2];
		
		if (i == 0)
		{
			ans[0] = arr[i + 1] - arr[i];
			ans[1] = arr[n - 1] - arr[i];
			
			return ans;
		}
		if (i == n - 1)
		{
			ans[0] = arr[i] - arr[i - 1];
			ans[1] = arr[i] - arr[0];
			
			return ans;
		}
		
		if (arr[i] - arr[lM] > arr[rM] - arr[i])
			ans[1] = arr[i] - arr[lM];
		else
			ans[1] = arr[rM] - arr[i];
		
		if (arr[i] - arr[l] < arr[r] - arr[i])
			ans[0] = arr[i] - arr[l];
		else
			ans[0] = arr[r] - arr[i];
		
		return ans;
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

		public void print(int x)
		{
			writer.print(x);
		}

		public void print(long x)
		{
			writer.println(x);
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
