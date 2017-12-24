package com.codeforces.competitions.educational.year2017.round33;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public class TaskD
{
	public static void main(String[] args)
	{
		new TaskD(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, d;
		long[] days, maxWeCanAdd;
		boolean[] check;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			d = in.nextInt();
			days = new long[n];
			maxWeCanAdd = new long[n];
			check = new boolean[n];

			for (int i = 0; i < n; i++)
			{
				days[i] = in.nextInt();

				if (days[i] == 0)
					check[i] = true;
			}

			for (int i = 1; i < n; i++)
				days[i] += days[i - 1];

			maxWeCanAdd[n - 1] = d - days[n - 1];

			for (int i = n - 2; i >= 0; i--)
				maxWeCanAdd[i] = Math.min(maxWeCanAdd[i + 1], d - days[i]);

			int cnt = 0;
			long add = 0;
			boolean poss = true;

			for (int i = 0; i < n; i++)
			{
				if (days[i] > d)
				{
					poss = false;

					break;
				}

				if (!check[i])
					continue;

				if (days[i] + add >= 0)
					continue;

				long req = -(days[i] + add);

				if (req > maxWeCanAdd[i] - add)
				{
					poss = false;

					break;
				}

				add += maxWeCanAdd[i] - add;
				cnt++;
			}

			if (!poss)
				out.println(-1);
			else
				out.println(cnt);
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

