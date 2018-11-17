package com.codeforces.competitions.educational.year2018.round38;

import java.io.*;
import java.util.*;

public class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int t;
		long ansN, ansM;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();

			outer : while (t-- > 0)
			{
				long x = in.nextInt();
				long maxN = binarySearch(x);

				if (x == 0)
					out.println(1 + " " + 1);
				else
				{
					ansN = ansM = -1;

					for (long n = maxN, cnt = 0; n >= 1 && cnt <= 1e4; n--, cnt++)
					{
						if (poss(n, x))
						{
							out.println(n + " " + ansM);

							continue outer;
						}
					}

					out.println(-1);
				}
			}
		}

		boolean poss(long n, long x)
		{
			long low = 1;
			long high = n;

			while (low <= high)
			{
				long mid = low + high >> 1;
				long f = find(n, mid);

				if (f == x)
				{
					ansM = mid;

					return true;
				}
				else if (f > x)
					high = mid - 1;
				else
					low = mid + 1;
			}

			return false;
		}

		long binarySearch(long x)
		{
			long low = 1;
			long high = (long) 1e9;

			while (low <= high)
			{
				long mid = low + high >> 1;
				long min = find(mid, 2);

				if (min >= x)
				{
					if (mid == 1 || find(mid - 1, 2) < x)
						return mid;

					high = mid - 1;
				}
				else
					low = mid + 1;
			}

			return -1;
		}

		long find(long n, long m)
		{
			return n * n - (n / m) * (n / m);
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

	public TaskC(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskC", 1 << 29);

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

