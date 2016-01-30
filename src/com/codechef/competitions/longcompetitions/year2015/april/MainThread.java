package com.codechef.competitions.longcompetitions.year2015.april;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class MainThread extends Thread
{
	private int n, c, q, i[], a[], b[], r[];
	private int qa, qb, count;
	private static InputReader reader;
	private static OutputWriter writer;

	public static void main(String[] args)
	{
		MainThread thread = new MainThread();
		
		reader = thread.new InputReader(System.in);
		writer = thread.new OutputWriter(System.out);
		
		thread.run();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}

	@Override
	public void run()
	{
		MainThread main1 = new MainThread();

		main1.mainFunction();
		main1.waitTime();
		main1.rank();
		main1.startCycle();
	}

	public void mainFunction()
	{
		n = reader.nextInt();
		c = reader.nextInt();

		i = new int[n];

		for (int j = 0; j < n; j++)
			i[j] = reader.nextInt();

		q = reader.nextInt();

		for (int j = 0; j < q; j++)
		{
			//q1[j] = reader.nextInt();
			//q2[j] = reader.nextInt();
			
			qa = reader.nextInt();
			qb = reader.nextInt();
			
			startCycle();
		}
	}

	public void startCycle()
	{
/*		for (int j = 0; j < q; j++)
		{
			if (q1[j] == 1)
				writer.println(getWaitTime(q2[j] - 1));
			else if (q1[j] == 2)
				writer.println(getRank(q2[j] - 1));
		}*/
		
		if (qa == 1)
			writer.println(getWaitTime(qb - 1));
		else if (qb == 2)
			writer.println(getRank(qb - 1));
	}

	public void waitTime()
	{
/*		int count;

		a = new int[n];

		if (i[0] < c)
			count = i[0];
		else
			count = c;

		a[0] = 0;

		for (int j = 1; j < n; j++)
		{
			a[j] = count;

			if (i[j] < c)
				count = count + i[j];
			else
				count = count + c;
		}*/
		
		//int count;
		
		// if ()
	}

	public void rank()
	{
		b = new int[n];
		r = new int[n];

		int count = 0;

		while (count < n)
		{
			for (int j = 0; j < n; j++)
			{
				if (i[j] == 0)
					continue;

				if (i[j] <= c)
				{
					count++;

					r[j] = count;
					i[j] = 0;
				}
				else
					i[j] = i[j] - c;
			}
		}
	}

	public int getWaitTime(int k)
	{
		return a[k];
	}

	public int getRank(int k)
	{
		return r[k];
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
