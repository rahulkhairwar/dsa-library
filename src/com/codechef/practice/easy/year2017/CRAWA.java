package com.codechef.practice.easy.year2017;

import java.io.*;
import java.util.*;

public final class CRAWA
{
	public static void main(String[] args)
	{
		new CRAWA(System.in, System.out);
	}

	static class Solver
	{
        int t;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				int x = in.nextInt();
				int y = in.nextInt();

				if ((x == 0 && y == 0) || checkRight(x, y) || checkTop(x, y) || checkLeft(x, y) || checkBottom(x, y))
					out.println("YES");
				else
					out.println("NO");
			}
		}

		boolean checkRight(int x, int y)
		{
			if (x < 0 || x % 2 == 0)
				return false;

			int upper = x + 1;
			int lower = x - 1;

			return -lower <= y && y <= upper;
		}

		boolean checkTop(int x, int y)
		{
			if (y < 2 || y % 2 == 1)
				return false;

			int right = y - 1;

			return -y <= x && x <= right;
		}

		boolean checkLeft(int x, int y)
		{
			if (x > -2 || x % 2 == -1)
				return false;

			return x <= y && y <= -x;
		}

		boolean checkBottom(int x, int y)
		{
			if (y > -2 || y % 2 == -1)
				return false;

			int right = -y + 1;

			return y <= x && x <= right;
		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
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

	public CRAWA(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Solver solver = new Solver(in, out);

		try
		{
			solver.solve();
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		out.flush();
		out.close();
	}

}
