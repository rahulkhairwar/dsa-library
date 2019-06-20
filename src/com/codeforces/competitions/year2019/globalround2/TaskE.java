package com.codeforces.competitions.year2019.globalround2;

import java.io.*;
import java.util.*;

public class TaskE
{
	public static void main(String[] args)
	{
		new TaskE(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n;
		long[] counts;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			counts = new long[n];

			for (int i = 0; i < n; i++)
				counts[i] = in.nextInt();

			long remaining = 0;
			long ans = 0;

			for (int i = 0; i < n; i++)
			{
				if (remaining > 0)
				{
					long cnt = counts[i] / 2;
					long min = Math.min(cnt, remaining);

					remaining -= min;
					counts[i] -= min << 1;
					ans += min;
				}

				long eq = counts[i] / 3;

				counts[i] -= eq * 3;
				ans += eq;
				remaining += counts[i];
			}

			out.println(ans);
		}

		Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
		{
			solve();
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

		public boolean isNewLine(int c)
		{
			return c == '\n';
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

		InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	public TaskE(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskE", 1 << 29);

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
