package com.facebookhackercup.hc2017.round1;

import java.io.*;
import java.util.*;

public final class TaskA
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
    	static final long INFINITY = (long) 1e14;
        int t, n, m;
        int[] squares;
        long[][] cost, dp;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			t = in.nextInt();

			for (int test = 1; test <= t; test++)
			{
				n = in.nextInt();
				m = in.nextInt();
				cost = new long[n][m + 1];
				squares = new int[n + 1];
				dp = new long[n + 1][n + 1];

				for (int i = 0; i < n; i++)
					for (int j = 1; j <= m; j++)
						cost[i][j] = in.nextInt();

				for (int i = 0; i < n; i++)
					Arrays.sort(cost[i]);

				for (int i = 0; i < n; i++)
				{
					// making the costs cumulative.
					for (int j = 2; j <= m; j++)
						cost[i][j] += cost[i][j - 1];
				}

				for (int i = 1; i <= n; i++)
					squares[i] = i * i;

				for (int i = 0; i <= n; i++)
					Arrays.fill(dp[i], INFINITY);

				out.println("Case #" + test + ": " + find(0, n));
			}
		}

		long find(int day, int toBuy)
		{
			if (day == n - 1)
			{
				if (toBuy > m)
					return INFINITY;

				return cost[day][toBuy] + squares[toBuy];
			}

			if (toBuy == 0)
				return 0;

			if (dp[day][toBuy] != INFINITY)
				return dp[day][toBuy];

			int bought = n - toBuy;
			int start = bought > day ? 0 : 1;
			int lim = Math.min(m, toBuy);
			long min = INFINITY;

			// now, we have an option to buy [0, 1, 2,...., min(m, toBuy)] number of pies on this day.
			for (int i = start; i <= lim; i++)
				min = Math.min(min, cost[day][i] + squares[i] + find(day + 1, toBuy - i));

			return dp[day][toBuy] = min;
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

/*

1
5 5
1000 1000 1000 1000 1000
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1

*/
