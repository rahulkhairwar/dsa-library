package com.a2onlinejudge.ladder.CodeforcesDiv2D;

import java.io.*;
import java.util.*;

public class Problem3
{
	public static void main(String[] args)
	{
		new Problem3(System.in, System.out);
	}

	private static class Solver implements Runnable
	{
		int n, m, total;
		int[][] values;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			m = in.nextInt();
			total = 1 << n;
			values = new int[n + 1][];
			values[0] = new int[1 << n];

			for (int i = 0; i < total; i++)
				values[0][i] = in.nextInt();

			createTree(1);

			for (int i = 0; i < m; i++)
			{
				int ind = in.nextInt() - 1;

				values[0][ind] = in.nextInt();
				update(1, ind);
				out.println(values[n][0]);
			}
		}

		void createTree(int level)
		{
			if (level == n + 1)
				return;

			int size = total / (1 << (level - 1));
			int ctr = 0;

			values[level] = new int[size >> 1];

			for (int i = 0; i < size; i += 2)
			{
				if (level % 2 == 1) // OR.
					values[level][ctr++] = values[level - 1][i] | values[level - 1][i + 1];
				else    // XOR.
					values[level][ctr++] = values[level - 1][i] ^ values[level - 1][i + 1];
			}

			createTree(level + 1);
		}

		void update(int level, int prev)
		{
			if (level == n + 1)
				return;

			int other = (prev % 2 == 1 ? prev - 1 : prev + 1);
			int updateAt = prev >> 1;

			if (level % 2 == 1)
				values[level][updateAt] = values[level - 1][prev] | values[level - 1][other];
			else
				values[level][updateAt] = values[level - 1][prev] ^ values[level - 1][other];

			update(level + 1, updateAt);
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

	private Problem3(InputStream inputStream, OutputStream outputStream)
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
