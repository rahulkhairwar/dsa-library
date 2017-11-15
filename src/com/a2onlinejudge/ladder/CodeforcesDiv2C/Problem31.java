package com.a2onlinejudge.ladder.CodeforcesDiv2C;

import java.io.*;
import java.util.*;

public final class Problem31
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
		out.flush();
		in.close();
		out.close();
	}

	static class Solver
	{
		InputReader in;
		PrintWriter out;

		void solve()
		{
			String dir = in.next();
			int len = dir.length();
			int[] ans = new int[len];
			int left, right;

			left = 0;
			right = len - 1;

			for (int i = 0; i < len; i++)
			{
				if (dir.charAt(i) == 'l')
					ans[right--] = i + 1;
				else
					ans[left++] = i + 1;
			}

			for (int i = 0; i < len; i++)
				out.println(ans[i]);
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
				} catch (IOException e)
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
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

}
