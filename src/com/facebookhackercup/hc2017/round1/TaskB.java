package com.facebookhackercup.hc2017.round1;

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
        int t, n, r;
        Point[] pts;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			t = in.nextInt();

			for (int test = 1; test <= t; test++)
			{
				n = in.nextInt();
				r = in.nextInt();
				pts = new Point[n];

				Set<Integer> xSet, ySet;

				xSet = new HashSet<>();
				ySet = new HashSet<>();

				for (int i = 0; i < n; i++)
				{
					pts[i] = new Point(i, in.nextInt(), in.nextInt());
					xSet.add(pts[i].x);
					ySet.add(pts[i].y);
				}

				int tot = xSet.size() * ySet.size();
				Set<Integer>[] allSquares = new Set[tot];
				int ctr = 0;
				int max = 0;

				for (int x : xSet)
				{
					for (int y : ySet)
					{
						allSquares[ctr] = find(x, y);
						max = Math.max(max, allSquares[ctr++].size());
					}
				}

				for (int i = 0; i < ctr; i++)
					for (int j = i + 1; j < ctr; j++)
						max = Math.max(max, findIntersection(allSquares[i], allSquares[j]));

				out.println("Case #" + test + ": " + max);
			}
		}

		Set<Integer> find(int x, int y)
		{
			Set<Integer> inside = new HashSet<>();

			for (Point pt : pts)
			{
				if (x <= pt.x && pt.x <= x + r && y <= pt.y && pt.y <= y + r)
					inside.add(pt.ind);
			}

			return inside;
		}

		int findIntersection(Set<Integer> a, Set<Integer> b)
		{
			Set<Integer> intersection = new HashSet<>();

			intersection.addAll(a);
			intersection.addAll(b);

			return intersection.size();
		}

		class Point
		{
			int ind, x, y;

			Point(int ind, int x, int y)
			{
				this.ind = ind;
				this.x = x;
				this.y = y;
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
