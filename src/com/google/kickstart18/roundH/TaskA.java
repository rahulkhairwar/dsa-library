package com.google.kickstart18.roundH;

import java.io.*;
import java.util.*;

public final class TaskA
{
	public static void main(String[] args)
	{
		new TaskA(System.in, System.out);
	}

	static class Solver implements Runnable
	{
        int t, n, p;
        String[] seq;
        boolean[] take;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();

			for (int test = 1; test <= t; test++)
			{
				n = in.nextInt();
				p = in.nextInt();
				seq = new String[p];
				take = new boolean[p];
				Arrays.fill(take, true);

				for (int i = 0; i < p; i++)
					seq[i] = in.nextLine();

				for (int i = 0; i < p; i++)
				{
					if (!take[i])
						continue;

					for (int j = i + 1; j < p; j++)
					{
						if (isPrefixOf(seq[i], seq[j]))
							take[j] = false;
						else if (take[j])
						{
							if (isPrefixOf(seq[j], seq[i]))
							{
								take[i] = false;

								break;
							}
						}
					}
				}

				long tot = (1L << n);

				for (int i = 0; i < p; i++)
				{
					if (!take[i])
						continue;

					long x = (1L << (n - seq[i].length()));

					tot -= x;
				}

				out.printf("Case #%d: %d\n", test, tot);
			}
		}

		boolean isPrefixOf(String a, String b)
		{
			int aLen = a.length();
			int bLen = b.length();

			if (aLen > bLen)
				return false;

			for (int i = 0; i < aLen; i++)
			{
				if (a.charAt(i) != b.charAt(i))
					return false;
			}

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

	public TaskA(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskA", 1 << 29);

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
