package com.codeforces.competitions.year2017.round452div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class TaskD
{
	public static void main(String[] args)
	{
		new TaskD(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		long n;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();

			long maxN = n + n - 1;

			if (n < 5)
				out.println(n * (n - 1) / 2);
			else if (areAllNines(maxN))
				out.println(1);
			else
			{
				long ans = 0;
				long numberOfNines = ("" + maxN).length() - 1;
				long num = (long) (Math.pow(10, numberOfNines)) - 1;

				while (num <= maxN)
				{
					if (num <= n)
						ans += num / 2;
					else
						ans += (n - num / 2);

					num += Math.pow(10, numberOfNines);
				}

				out.println(ans);
			}
		}

		boolean areAllNines(long num)
		{
			String s = "" + num;

			for (char ch : s.toCharArray())
				if (ch != '9')
					return false;

			return true;
		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
		{
			try
			{
				solve();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

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

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	public TaskD(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskD", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			in.close();
			out.flush();
			out.close();
		}
	}

}

