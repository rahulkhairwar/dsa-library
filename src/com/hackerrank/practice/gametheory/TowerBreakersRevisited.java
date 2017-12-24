package com.hackerrank.practice.gametheory;

import java.io.*;
import java.util.*;

public final class TowerBreakersRevisited
{
	public static void main(String[] args)
	{
		new TowerBreakersRevisited(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int t, n;
		int[] primeFactorsCnt;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			pre();
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();

				int xor = 0;

				for (int i = 0; i < n; i++)
					xor ^= primeFactorsCnt[in.nextInt()];

				if (xor != 0)
					out.println(1);
				else
					out.println(2);
			}
		}

		void pre()
		{
			int lim = (int) 1e6 + 5;
			int curr = 2;

			primeFactorsCnt = new int[lim];
			primeFactorsCnt[2] = 1;

			while ((curr << 1) < lim)
			{
				int temp = curr << 1;
				int cnt = 0;

				while (temp % 2 == 0)
				{
					temp >>= 1;
					cnt++;
				}

				primeFactorsCnt[curr << 1] = cnt;
				curr++;
			}

			for (int i = 3; i < lim; i += 2)
			{
				if (primeFactorsCnt[i] > 0)
					continue;

				primeFactorsCnt[i] = 1;
				curr = i + i;

				while (curr < lim)
				{
					int temp = curr;
					int cnt = 0;

					while (temp % i == 0)
					{
						temp /= i;
						cnt++;
					}

					primeFactorsCnt[curr] += cnt;
					curr += i;
				}
			}
		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override public void run()
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

	public TowerBreakersRevisited(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TowerBreakersRevisited", 1 << 29);

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
