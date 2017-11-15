package com.a2onlinejudge.ladder.CodeforcesDiv2C;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Problem32
{
	public static void main(String[] args)
	{
		new Problem32(System.in, System.out);
	}

	private static class Solver implements Runnable
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
				pts[i] = new Point(i, in.nextInt());

			Arrays.sort(pts, new Comparator<Point>()
			{
				@Override
				public int compare(Point o1, Point o2)
				{
					return Integer.compare(o1.y, o2.y);
				}
			});

			List<Integer> listA = new ArrayList<>();
			List<Integer> listB = new ArrayList<>();
			long sumA = 0;
			long sumB = 0;

			for (int i = 0; i < n; i += 2)
			{
				sumA += pts[i].y;
				listA.add(pts[i].x);

				if (i + 1 == n)
				{
					sumA -= pts[i].y;
					listA.remove(listA.size() - 1);
				}
				else
				{
					sumB += pts[i + 1].y;
					listB.add(pts[i + 1].x);
				}
			}

			if (n % 2 == 1)
			{
				int extra = pts[n - 1].y;

				if (sumA + extra - sumB <= extra)
					listA.add(pts[n - 1].x);
				else
					listB.add(pts[n - 1].x);
			}

			out.println(listA.size());

			for (int x : listA)
				out.print(x + 1 + " ");

			out.println("\n" + listB.size());

			for (int x : listB)
				out.print(x + 1 + " ");
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

	private Problem32(InputStream inputStream, OutputStream outputStream)
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
