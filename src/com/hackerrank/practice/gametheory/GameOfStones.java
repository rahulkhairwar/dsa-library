package com.hackerrank.practice.gametheory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class GameOfStones
{
	public static void main(String[] args)
	{
		new GameOfStones(System.in, System.out);
	}

	private static class Solver implements Runnable
	{
		int t, n;
		int[] grundy, moves;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();
			grundy = new int[105];
			Arrays.fill(grundy, -1);
			grundy[0] = grundy[1] = 0;
			moves = new int[]{2, 3, 5};

			for (int i = 2; i < 105; i++)
				find(i);

			while (t-- > 0)
			{
				n = in.nextInt();

				if (grundy[n] != 0)
					out.println("First");
				else
					out.println("Second");
			}
		}

		void find(int stones)
		{
			if (grundy[stones] != -1)
				return;

			Set<Integer> set = new HashSet<>();

			for (int m : moves)
				if (stones >= m)
					set.add(grundy[stones - m]);

			int ctr = 0;

			while (set.size() > 0)
			{
				if (!set.contains(ctr))
				{
					grundy[stones] = ctr;

					return;
				}

				ctr++;
			}

			grundy[stones] = ctr;
		}

		Solver(InputReader in, PrintWriter out)
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

	private static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		int read()
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

		int nextInt()
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

		boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		void close()
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

	private GameOfStones(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "Solver", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		in.close();
		out.flush();
		out.close();
	}

}
