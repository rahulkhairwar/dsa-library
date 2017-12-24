package com.hackerrank.practice.gametheory;

import java.io.*;
import java.util.*;

public final class TowerBreakersAgain
{
	public static void main(String[] args)
	{
		new TowerBreakersAgain(System.in, System.out);
	}

	static class Solver implements Runnable
	{
        int t, n;
        int[] grundy;
        List<Integer>[] divisors;
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
					xor ^= grundy[in.nextInt()];

				if (xor != 0)
					out.println(1);
				else
					out.println(2);
			}
		}

		void pre()
		{
			int lim = (int) 1e5 + 5;

			grundy = new int[lim];
			Arrays.fill(grundy, -1);
			grundy[1] = 0;
			grundy[2] = 1;
			divisors = new List[lim];

			for (int i = 2; i < lim; i++)
				divisors[i] = new ArrayList<>();

			for (int i = 2; i < lim; i++)
			{
				int curr = i;

				while (curr < lim)
				{
					divisors[curr].add(i);
					curr += i;
				}
			}

			for (int i = 3; i < lim; i++)
				find(i);
		}

		void find(int x)
		{
			if (grundy[x] != -1)
				return;

			Set<Integer> set = new HashSet<>();

			for (int div : divisors[x])
			{
				find(x / div);

				if (div % 2 == 1)
					set.add(grundy[x / div]);
				else
					set.add(0);
			}

			int ctr = 0;

			while (set.contains(ctr))
				ctr++;

			grundy[x] = ctr;
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

	public TowerBreakersAgain(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TowerBreakersAgain", 1 << 29);

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
