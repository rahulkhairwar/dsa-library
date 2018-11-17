package com.codeforces.practice.medium;

import java.io.*;
import java.util.*;

public final class Polygons
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
        Point[] one, two;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			one = new Point[n];

			for (int i = 0; i < n; i++)
				one[i] = new Point(in.nextInt(), in.nextInt());

			Stack<Point> hullOne = getConvexHull(one, n);
			System.out.println("hullOne : " + hullOne);

			m = in.nextInt();
			two = new Point[n + m];

			for (int i = 0; i < n; i++)
				two[i] = one[i];

			for (int i = 0; i < m; i++)
				two[n + i] = new Point(in.nextInt(), in.nextInt());

			Stack<Point> hullTwo = getConvexHull(two, n + m);
			System.out.println("hullTwo : " + hullTwo);

			if (hullOne.size() != hullTwo.size())
				out.println("NO");
			else
			{
				while (hullOne.size() > 0)
				{
					if (!hullOne.pop().equals(hullTwo.pop()))
					{
						out.println("NO");

						return;
					}
				}

				out.println("YES");
			}

			Point a, b, c;

			a = new Point(10, 10);
			b = new Point(-10, 0);
			c = new Point(-10, 10);
//			System.out.println("a : " + a + ", b : " + b + ", c : " + c);
//			System.out.println("a->b->c ccw : " + counterClockWise(a, b, c));
		}

		Stack<Point> getConvexHull(Point[] points, int len)
		{
			Stack<Point> hull = new Stack<>();

			Arrays.sort(points, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					return Long.compare(o1.y, o2.y);
				}
			});

			hull.push(points[0]);

			for (int i = 1; i < len; i++)
				points[i].polarAngle = points[0].findPolarAngle(points[i]);

			Arrays.sort(points, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					if (o1.polarAngle == o2.polarAngle)
					{
/*						if (o1.y == o2.y)
							return Long.compare(o2.x, o1.x);

						return Long.compare(o1.y, o2.y);*/

//						return Long.compare(o2.y, o1.y);

						if (o1.x == o2.x)
							return Long.compare(o2.y, o1.y);

						return Long.compare(o1.x, o2.x);
//						return Long.compare(o2.x, o1.x);
					}

					return Double.compare(o1.polarAngle, o2.polarAngle);
				}
			});

/*			int ctr = 1;

			while (ctr < len && points[ctr].equals(points[0]))
				ctr++;

			if (ctr == len)
				return hull;

			int ctr2 = ctr + 1;

			while (ctr2 < len && counterClockWise(points[0], points[ctr], points[ctr2]) == 0)
				ctr2++;

			hull.push(points[ctr2 - 1]);*/

			hull.push(points[1]);

			int ctr2 = 2;

			System.out.println("pts :");
			for (int i = 0; i < len; i++)
				System.out.println("i : " + i + ", pt[i] : " + points[i]);

			for (int i = ctr2; i < len; i++)
			{
				Point top = hull.pop();
				long ccw;

//				System.out.println("\tpeek : " + hull.peek() + ", top : " + top + ", pts[i] : " + points[i] + ", ccw "
//						+ ": " + counterClockWise(hull.peek(), top, points[i]));
				while ((ccw = counterClockWise(hull.peek(), top, points[i])) < 0)
				{
					top = hull.pop();
//					System.out.println("\t**peek : " + hull.peek() + ", top : " + top + ", pts[i] : " + points[i] + ", "
//							+ "ccw : " + ccw);
				}

				hull.push(top);
				hull.push(points[i]);
			}

			return hull;
		}

		long counterClockWise(Point a, Point b, Point c)
		{
			long xx = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);

			if (xx < 0)
				return -1;
			else if (xx > 0)
				return 1;

			return 0;
		}

		class Point
		{
			long x, y;
			double polarAngle;

			double findPolarAngle(Point a)
			{
				return Math.atan2(a.y - y, a.x - x) * 180.0 /*/ Math.PI*/;
			}

			Point(long x, long y)
			{
				this.x = x;
				this.y = y;
			}

			@Override public boolean equals(Object obj)
			{
				Point pt = (Point) obj;

				return x == pt.x && y == pt.y;
			}

			@Override public String toString()
			{
				return "(" + x + ", " + y + ") => " + polarAngle;
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

        public boolean isSpaceChar(int c)
        {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
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

/*

4
-10 -10
-10 10
10 10
10 -10
3
-10 0
1 5
2 2

3
-1000000000 0
1000000000 1
1000000000 -2
3
-999999999 0
999999999 0
999999999 -1

3
-100 0
100 1
100 -2
3
-99 0
99 0
99 -1


*/
