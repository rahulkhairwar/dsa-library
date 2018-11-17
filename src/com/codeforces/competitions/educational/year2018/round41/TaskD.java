package com.codeforces.competitions.educational.year2018.round41;

import java.awt.*;
import java.io.*;
import java.util.*;

public class TaskD
{
	public static void main(String[] args)
	{
		new TaskD(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n;
		Point[] points;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			points = new Point[n];

			for (int i = 0; i < n; i++)
				points[i] = new Point(in.nextInt(), in.nextInt());

			Arrays.sort(points, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					if (o1.y == o2.y)
						return Long.compare(o1.x, o2.x);

					return Long.compare(o1.y, o2.y);
				}
			});

			for (int i = 1; i < n; i++)
				points[i].polarAngle = points[0].findPolarAngle(points[i]);

			for (int i = 0; i < n; i++)
				System.out.println("i : " + i + ", pts[i] : " + points[i] + ", pA : " + points[i].polarAngle);

			Set<Double> set = new HashSet<>();

			for (int i = 1; i < n; i++)
				set.add(points[i].polarAngle);

			System.out.println("set : " + set);

			if (set.size() <= 2)
				out.println("YES");
			else
				out.println("NO");
		}

		void solve2() throws IOException
		{
			n = in.nextInt();
			points = new Point[n];

			for (int i = 0; i < n; i++)
				points[i] = new Point(in.nextInt(), in.nextInt());

			if (n <= 4)
			{
				out.println("YES");

				return;
			}

//			System.out.println("The convex hull of the given points is :");

			Stack<Point> convexHull = getConvexHull();
//			System.out.println("cH : " + convexHull);

//			System.out.println("size : " + convexHull.size());

			if (convexHull.size() > 4)
				out.println("NO");
			else
			{
				Set<Point> set = new HashSet<>();

				for (Point pt : convexHull)
					set.add(pt);

				Point[] hull = new Point[convexHull.size()];
				int ctr = 0;

				for (Point pt : convexHull)
					hull[ctr++] = pt;

				System.out.println("cH : " + Arrays.toString(hull));

				boolean[][] check = new boolean[4][4];

				for (Point pt : points)
				{
					boolean l01 = liesOnAB(pt, hull[0], hull[1]);
					boolean l12 = liesOnAB(pt, hull[1], hull[2]);
					boolean l23 = liesOnAB(pt, hull[2], hull[3]);
					boolean l30 = liesOnAB(pt, hull[3], hull[0]);

					if (l01)
						check[0][1] = true;
					if (l12)
						check[1][2] = true;
					if (l23)
						check[2][3] = true;
					if (l30)
						check[3][0] = true;

					if (!l01 && !l12 && !l23 && !l30)
					{
						System.out.println("pt not on line : " + pt);
						out.println("NO");

						return;
					}
				}

				int cnt = 0;

				for (int i = 0; i < 4; i++)
					for (int j = 0; j < 4; j++)
						if (check[i][j])
							cnt++;

				if (cnt > 2)
					out.println("NO");
				else
					out.println("YES");


/*				Point prev = convexHull.pop();
				Point curr = convexHull.pop();
				int sides = 0;
				Point[] lines = new Point[8];
				int ctr = 0;

				for (Point pt : points)
				{
					if (set.contains(pt))
						continue;

					System.out.println("cH doesn't contain : " + pt);

					if (pt.distance(prev) + curr.distance(pt) == prev.distance(curr))
					{
						sides++;
						System.out.println("pr : " + prev + ", cr : " + curr);
						lines[ctr++] = prev;
						lines[ctr++] = curr;
						prev = curr;

						if (convexHull.size() > 0)
							curr = convexHull.pop();
					}
				}*/

/*				if (sides <= 2)
				{
					for (int i = 0; i < n; i++)
					{
						if (!valid(points[i], lines))
						{
							out.println("NO");

							return;
						}
					}

					out.println("YES");
				}*/
			}

/*			for (Point p : getConvexHull())
				System.out.println(p.x + " " + p.y);*/
		}
/*
		boolean valid(Point pt, Point[] lines)
		{
			System.out.println("lines.len : " + lines.length);
			System.out.println("lines : " + Arrays.deepToString(lines));

			long distAB = lines[0].distance(lines[1]);

			if (pt.distance(lines[0]) + lines[1].distance(pt) == distAB)
				return true;

			if (lines.length < 4)
				return false;

			long distCD = lines[2].distance(lines[3]);

			return pt.distance(lines[2]) + lines[3].distance(pt) == distCD;
		}*/

		boolean liesOnAB(Point pt, Point a, Point b)
		{
			double distAB = a.distance(b);

			System.out.println("pt : " + pt + ", a : " + a + ", b : " + b + ",\n..ab : " + distAB);
			System.out.println("aPTB : " + (b.distance(pt) + pt.distance(a)));

			return b.distance(pt) + pt.distance(a) == distAB;
		}

		Stack<Point> getConvexHull()
		{
			Stack<Point> hull = new Stack<>();

			Arrays.sort(points, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					if (o1.y == o2.y)
						return Long.compare(o1.x, o2.x);

					return Long.compare(o1.y, o2.y);
				}
			});

			hull.push(points[0]);

			for (int i = 1; i < n; i++)
				points[i].polarAngle = points[0].findPolarAngle(points[i]);

			Arrays.sort(points, 1, n, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					if (o1.polarAngle == o2.polarAngle)
						return Double.compare(dist(points[0], o1), dist(points[0], o2));

					return Double.compare(o1.polarAngle, o2.polarAngle);
				}
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

		Stack<Point> getOtherConvexHull()
		{
			Stack<Point> hull = new Stack<>();

			Arrays.sort(points, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					if (o1.y == o2.y)
						return Long.compare(o1.x, o2.x);

					return Long.compare(o1.y, o2.y);
				}
			});

			hull.push(points[0]);

			for (int i = 1; i < n; i++)
				points[i].polarAngle = points[0].findPolarAngle(points[i]);

			Arrays.sort(points, 1, n, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					if (o1.polarAngle == o2.polarAngle)
						return Double.compare(dist(points[0], o1), dist(points[0], o2));

					return Double.compare(o1.polarAngle, o2.polarAngle);
				}
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

				while (counterClockWise(hull.peek(), top, points[i]) < 0)
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

		long counterClockWise(Point a, Point b, Point c)
		{
			return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
		}

		private static class Point
		{
			long x, y;
			double polarAngle;

			double findPolarAngle(Point a)
			{
				return Math.atan2(a.y - y, a.x - x) * 180.0 / Math.PI;
			}

			Point(long x, long y)
			{
				this.x = x;
				this.y = y;
			}

			public double distance(Point pt)
			{
				return Math.sqrt((x - pt.x) * (x - pt.x) + (y - pt.y) * (y - pt.y));
			}

			@Override public boolean equals(Object obj)
			{
				Point pt = (Point) obj;

				return x == pt.x && y == pt.y;
			}

			@Override public int hashCode()
			{
				return Objects.hash(x, y);
			}

			@Override public String toString()
			{
				return "(" + x + ", " + y + ")";
			}

		}

		void debug(Object... o)
		{
			System.err.println(Arrays.deepToString(o));
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

		public int[] nextIntArray(int arraySize)
		{
			int array[] = new int[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextInt();

			return array;
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

		public long[] nextLongArray(int arraySize)
		{
			long array[] = new long[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextLong();

			return array;
		}

		public float nextFloat()
		{
			float result, div;
			byte c;

			result = 0;
			div = 1;
			c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean isNegative = (c == '-');

			if (isNegative)
				c = (byte) read();

			do
			{
				result = result * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					result += (c - '0') / (div *= 10);

			if (isNegative)
				return -result;

			return result;
		}

		public double nextDouble()
		{
			double ret = 0, div = 1;
			byte c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean neg = (c == '-');

			if (neg)
				c = (byte) read();

			do
			{
				ret = ret * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);

			if (neg)
				return -ret;

			return ret;
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

		public String nextLine()
		{
			int c = read();

			StringBuilder result = new StringBuilder();

			do
			{
				result.appendCodePoint(c);

				c = read();
			} while (!isNewLine(c));

			return result.toString();
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

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	static class CMath
	{
		static long power(long number, long power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			if (power == 1)
				return number;

			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}

		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);
			else
				return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

		static long min(long... arr)
		{
			long min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static long max(long... arr)
		{
			long max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static int max(int... arr)
		{
			int max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

	}

	static class Utils
	{
		static boolean nextPermutation(int[] arr)
		{
			for (int a = arr.length - 2; a >= 0; --a)
			{
				if (arr[a] < arr[a + 1])
				{
					for (int b = arr.length - 1; ; --b)
					{
						if (arr[b] > arr[a])
						{
							int t = arr[a];

							arr[a] = arr[b];
							arr[b] = t;

							for (++a, b = arr.length - 1; a < b; ++a, --b)
							{
								t = arr[a];
								arr[a] = arr[b];
								arr[b] = t;
							}

							return true;
						}
					}
				}
			}

			return false;
		}

	}

	public TaskD(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskD", 1 << 29);

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

