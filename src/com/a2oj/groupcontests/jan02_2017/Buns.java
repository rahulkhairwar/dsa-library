package com.a2oj.groupcontests.jan02_2017;

import java.io.*;
import java.util.*;

/**
 * Question <a href="http://codeforces.com/problemset/problem/106/C">link</a>.
 */
public final class Buns
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
        int n, m, dough0, profit0;
        int[] stuffing, reqStuffing, reqDough, profit;
        int[][][] dp;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			dough0 = in.nextInt();
			profit0 = in.nextInt();
			stuffing = new int[m];
			reqStuffing = new int[m];
			reqDough = new int[m];
			profit = new int[m];
			dp = new int[m][n + 1][101];

			for (int i = 0; i < m; i++)
			{
				stuffing[i] = in.nextInt();
				reqStuffing[i] = in.nextInt();
				reqDough[i] = in.nextInt();
				profit[i] = in.nextInt();
			}

			for (int i = 0; i < m; i++)
				for (int j = 0; j <= n; j++)
					Arrays.fill(dp[i][j], -1);

			out.println(find(0, n, stuffing[0]));
		}

		int find(int ind, int remDough, int remStuffing)
		{
			if (dp[ind][remDough][remStuffing] != -1)
				return dp[ind][remDough][remStuffing];

			int max = 0;

			// making a bun using this stuffing.
			if (remDough >= reqDough[ind] && remStuffing >= reqStuffing[ind])
				max = profit[ind] + find(ind, remDough - reqDough[ind], remStuffing - reqStuffing[ind]);

			// make a bun without using this stuffing.
			if (remDough >= dough0)
				max = Math.max(max, profit0 + find(ind, remDough - dough0, remStuffing));

			// don't use this stuffing.
			if (ind < m - 1)
				max = Math.max(max, find(ind + 1, remDough, stuffing[ind + 1]));

			dp[ind][remDough][remStuffing] = max;

			return max;
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
