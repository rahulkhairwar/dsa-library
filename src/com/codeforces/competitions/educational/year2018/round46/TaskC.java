package com.codeforces.competitions.educational.year2018.round46;

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
		int n;
		Point[] pts;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			pts = new Point[n];

			for (int i = 0; i < n; i++)
				pts[i] = new Point(in.nextLong(), in.nextLong());

			int size = n << 1;
			Index[] indices = new Index[size];
			int ctr = 0;

			for (int i = 0; i < n; i++)
			{
				indices[ctr++] = new Index(pts[i].x, 1);
				indices[ctr++] = new Index(pts[i].y, 0);
			}

			Arrays.sort(indices, (o1, o2) -> {
				if (o1.x == o2.x)
					return Integer.compare(o2.isStart, o1.isStart);

				return Long.compare(o1.x, o2.x);
			});

			long[] ans = new long[n + 1];
			int open = 0;

			for (int i = 0; i < size; i++)
			{
				long segSize = (indices[i].x - 1) - (i > 0 ? indices[i - 1].x + 1 : indices[i].x - 1) + 1;

				ans[open] += segSize;

				if (indices[i].isStart == 1)
					open++;

				ans[open]++;

				if (indices[i].isStart == 0)
					open--;
			}

			for (int i = 1; i <= n; i++)
				out.print(ans[i] + " ");
		}

		class Index
		{
			long x;
			int isStart;

			Index(long x, int isStart)
			{
				this.x = x;
				this.isStart = isStart;
			}
		}

		class Point
		{
			long x, y;

			public Point(long x, long y)
			{
				this.x = x;
				this.y = y;
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

		public long nextLong()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sign = 1;

			if (c == '-')
			{
				sign = -1;

				c = read();
			}

			long result = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				result *= 10;
				result += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return result * sign;
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