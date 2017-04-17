package com.codeforces.competitions.year2017.round404div2;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

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
		int n, m;
		Point[] a, b;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			a = new Point[n];

			for (int i = 0; i < n; i++)
				a[i] = new Point(in.nextInt(), in.nextInt());

			m = in.nextInt();
			b = new Point[m];

			for (int i = 0; i < m; i++)
				b[i] = new Point(in.nextInt(), in.nextInt());

			Arrays.sort(a, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					return Integer.compare(o1.y, o2.y);
				}
			});

			Arrays.sort(b, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					return Integer.compare(o1.x, o2.x);
				}
			});

			long max = Math.max(0, b[m - 1].x - a[0].y);

			Arrays.sort(a, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					return Integer.compare(o1.x, o2.x);
				}
			});

			Arrays.sort(b, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					return Integer.compare(o1.y, o2.y);
				}
			});

			max = Math.max(max, a[n - 1].x - b[0].y);
			out.println(max);
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

}
