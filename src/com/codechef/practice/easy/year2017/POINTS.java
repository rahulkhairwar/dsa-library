package com.codechef.practice.easy.year2017;

import java.awt.*;
import java.io.*;
import java.util.*;

public final class POINTS
{
	public static void main(String[] args)
	{
		new POINTS(System.in, System.out);
	}

	static class Solver
	{
        int t, n;
        Point[] pts;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				pts = new Point[n];

				for (int i = 0; i < n; i++)
					pts[i] = new Point(in.nextInt(), in.nextInt());

				Arrays.sort(pts, new Comparator<Point>()
				{
					@Override public int compare(Point o1, Point o2)
					{
						if (o1.x == o2.x)
							return Integer.compare(o2.y, o1.y);

						return Integer.compare(o1.x, o2.x);
					}
				});

				double dist = 0;

				for (int i = 1; i < n; i++)
					dist += pts[i - 1].distance(pts[i]);

				out.printf("%.2f\n", dist);
			}
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

	private POINTS(InputStream inputStream, OutputStream outputStream)
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
