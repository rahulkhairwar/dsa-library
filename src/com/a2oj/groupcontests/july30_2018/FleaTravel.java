package com.a2oj.groupcontests.july30_2018;

import java.io.*;
import java.util.*;

public final class FleaTravel
{
	public static void main(String[] args)
	{
		new FleaTravel(System.in, System.out);
	}

	static class Solver implements Runnable
	{
        int n;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();

			Set<Integer>[] sets = new Set[n];

			for (int i = 0; i < n; i++)
				sets[i] = new HashSet<>();

			int curr = 0;
			int currMinute = 1;
			int visitedCnt = 0;

			while (true)
			{
				if (sets[curr].contains(currMinute))
					break;

				if (sets[curr].size() == 0)
					visitedCnt++;

				sets[curr].add(currMinute);
				currMinute++;
				currMinute %= n;
				curr = (curr + currMinute) % n;

				if (visitedCnt == n)
					break;
			}

			if (visitedCnt == n)
				out.println("YES");
			else
				out.println("NO");
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

	public FleaTravel(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "FleaTravel", 1 << 29);

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
