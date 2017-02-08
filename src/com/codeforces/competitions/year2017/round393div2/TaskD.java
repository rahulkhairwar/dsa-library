package com.codeforces.competitions.year2017.round393div2;

import java.io.*;
import java.util.*;

public final class TaskD
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
        int n;
        int[] time, dp;
        TreeSet<Point> set;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			time = new int[n];
			dp = new int[n];
			set = new TreeSet<>(new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					return Integer.compare(o1.time, o2.time);
				}
			});

			for (int i = 0; i < n; i++)
			{
				time[i] = in.nextInt();
				set.add(new Point(i, time[i]));
			}

			dp[0] = 20;

			for (int i = 1; i < n; i++)
			{
				// could have reached current trip by either buying 1 ticket for it,
				// or by buying a 90-minute ticket < 90 minutes ago,
				// or by buying a 1440-minute ticket < 1440 minutes ago.
				int x, y, z, ind;

				x = 20 + dp[i - 1];
				ind = set.tailSet(new Point(-1, time[i] - 90 + 1)).first().ind;
				y = 50 + (ind > 0 ? dp[ind - 1] : 0);
				ind = set.tailSet(new Point(-1, time[i] - 1440 + 1)).first().ind;
				z = 120 + (ind > 0 ? dp[ind - 1] : 0);
				dp[i] = CMath.min(x, y, z);
			}

			for (int i = 0; i < n; i++)
				out.println(i > 0 ? dp[i] - dp[i - 1] : dp[i]);
		}

		class Point
		{
			int ind, time;

			Point(int ind, int time)
			{
				this.ind = ind;
				this.time = time;
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

	static class CMath
	{
		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

	}

}
