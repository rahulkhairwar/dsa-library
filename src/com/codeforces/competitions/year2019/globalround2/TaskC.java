package com.codeforces.competitions.year2019.globalround2;

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
		int n, m;
		int[][] a, b;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			a = new int[n][m];
			b = new int[n][m];

			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++)
					a[i][j] = in.nextInt();

			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++)
					b[i][j] = in.nextInt();

			int[] rows, cols;

			rows = new int[n];
			cols = new int[m];

			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < m; j++)
				{
					if (a[i][j] != b[i][j])
					{
						rows[i]++;
						cols[j]++;
					}
				}
			}

			for (int i = 0; i < n; i++)
			{
				if (rows[i] % 2 == 1)
				{
					out.println("No");

					return;
				}
			}

			for (int i = 0; i < m; i++)
			{
				if (cols[i] % 2 == 1)
				{
					out.println("No");

					return;
				}
			}

			out.println("Yes");
		}

		Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
		{
			solve();
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

		public boolean isNewLine(int c)
		{
			return c == '\n';
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

		InputReader(InputStream stream)
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

