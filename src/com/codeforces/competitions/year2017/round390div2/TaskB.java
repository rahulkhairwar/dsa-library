package com.codeforces.competitions.year2017.round390div2;

import java.io.*;
import java.util.*;

public final class TaskB
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		char[][] b;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			b = new char[4][];

			for (int i = 0; i < 4; i++)
				b[i] = in.next().toCharArray();

			boolean poss = false;

			for (int i = 0; i < 4; i++)
			{
				for (int j = 0; j < 4; j++)
				{
					if (b[i][j] == '.')
					{
						b[i][j] = 'x';
						poss |= possible();
						b[i][j] = '.';
					}
				}
			}

			if (poss)
				out.println("YES");
			else
				out.println("NO");
		}

		boolean possible()
		{
			boolean poss = equal(b[0][0], b[0][1], b[0][2]);

			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 2; j++)
					if (equal(b[i][j], b[i][j + 1], b[i][j + 2]))
						poss = true;

			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 2; j++)
					if (equal(b[j][i], b[j + 1][i], b[j + 2][i]))
						poss = true;

			poss |= equal(b[0][0], b[1][1], b[2][2]);
			poss |= equal(b[0][1], b[1][2], b[2][3]);
			poss |= equal(b[1][0], b[2][1], b[3][2]);
			poss |= equal(b[1][1], b[2][2], b[3][3]);

			poss |= equal(b[2][0], b[1][1], b[0][2]);
			poss |= equal(b[3][0], b[2][1], b[1][2]);
			poss |= equal(b[2][1], b[1][2], b[0][3]);
			poss |= equal(b[3][1], b[2][2], b[1][3]);

			return poss;
		}

		boolean equal(char a, char b, char c)
		{
			return a == b && a == c && a != '.';
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

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

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

		public String next()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			StringBuilder res = new StringBuilder();

			do
			{
				res.appendCodePoint(c);

				c = read();
			} while (!isSpaceChar(c));

			return res.toString();
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

	}

}

/*

xx..
.oo.
x...
oox.

*/
