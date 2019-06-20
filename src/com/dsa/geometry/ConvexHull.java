package com.dsa.geometry;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Stack;

public class ConvexHull
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

	private static class Solver
	{
		int n;
		Point[] points;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			points = new Point[n];

			for (int i = 0; i < n; i++)
				points[i] = new Point(in.nextInt(), in.nextInt());

			System.out.println("The convex hull of the given points is :");

			for (Point p : getConvexHull())
				System.out.println(p.x + " " + p.y);
		}

		Stack<Point> getConvexHull()
		{
			Stack<Point> hull = new Stack<>();

			Arrays.sort(points, (o1, o2) -> {
				if (o1.y == o2.y)
					return Integer.compare(o1.x, o2.x);

				return Integer.compare(o1.y, o2.y);
			});

			hull.push(points[0]);

			for (int i = 1; i < n; i++)
				points[i].polarAngle = points[0].findPolarAngle(points[i]);

			Arrays.sort(points, 1, n, (o1, o2) -> {
				if (o1.polarAngle == o2.polarAngle)
					return Double.compare(dist(points[0], o1), dist(points[0], o2));

				return Double.compare(o1.polarAngle, o2.polarAngle);
			});

			int ctr = 1;

			while (ctr < n && points[ctr].equals(points[0]))
				ctr++;

			int ctr2 = ctr + 1;

			while (ctr2 < n && counterClockWise(points[0], points[ctr], points[ctr2]) == 0)
				ctr2++;

			hull.push(points[ctr2 - 1]);

			for (int i = ctr2; i < n; i++)
			{
				Point top = hull.pop();

				while (counterClockWise(hull.peek(), top, points[i]) <= 0)
					top = hull.pop();

				hull.push(top);
				hull.push(points[i]);
			}

			return hull;
		}

		double dist(Point a, Point b)
		{
			return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
		}

		int counterClockWise(Point a, Point b, Point c)
		{
			return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
		}

		Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	private static class Point
	{
		int x, y;
		double polarAngle;

		double findPolarAngle(Point a)
		{
			return Math.atan2(a.y - y, a.x - x) * 180.0 / Math.PI;
		}

		Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		@Override public String toString()
		{
			return "(" + x + ", " + y + ")";
		}

	}

	private static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		InputReader(InputStream stream)
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

	}

}

/*

13
2 -2
3 0
4 1
2 1
3 2
1 2
-1 3
-3 3
-4 3
-5 2
-4 0
-3 -2
0 -1

*/
