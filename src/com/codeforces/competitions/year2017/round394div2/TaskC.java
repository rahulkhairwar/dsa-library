package com.codeforces.competitions.year2017.round394div2;

import java.io.*;
import java.util.*;

public final class TaskC
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
    	final int inf = (int) 1e6;
        int n, m;
        char[][] s;
        int[][] nearest;
        int[][][][] dp;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			s = new char[n][];
			nearest = new int[n][3];	// 0 = letter, 1 = digit, 2 = symbol
			dp = new int[n][2][2][2];

			for (int i = 0; i < n; i++)
			{
				s[i] = in.next().toCharArray();
				Arrays.fill(nearest[i], inf);
			}

			for (int i = 0; i < n; i++)
				for (int j = 0; j < 2; j++)
					for (int k = 0; k < 2; k++)
						Arrays.fill(dp[i][j][k], -1);

			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < m; j++)
				{
					if (Character.isLowerCase(s[i][j]))
						nearest[i][0] = CMath.min(nearest[i][0], j, m - j);
					else if (Character.isDigit(s[i][j]))
						nearest[i][1] = CMath.min(nearest[i][1], j, m - j);
					else
						nearest[i][2] = CMath.min(nearest[i][2], j, m - j);
				}
			}

			out.println(find(0, 0, 0, 0));
		}

		int find(int ind, int let, int dig, int sym)
		{
			if (let == 1 && dig == 1 && sym == 1)
				return 0;

			if (ind == n)
				return inf;

			if (dp[ind][let][dig][sym] != -1)
				return dp[ind][let][dig][sym];

			int min = inf;

			if (let == 0)
			{
				if (nearest[ind][0] == inf)
					min = Math.min(min, find(ind + 1, 0, dig, sym));
				else
					min = CMath.min(min, nearest[ind][0] + find(ind + 1, 1, dig, sym), find(ind + 1, 0, dig, sym));
			}

			if (dig == 0)
			{
				if (nearest[ind][1] == inf)
					min = Math.min(min, find(ind + 1, let, 0, sym));
				else
					min = CMath.min(min, nearest[ind][1] + find(ind + 1, let, 1, sym), find(ind + 1, let, 0, sym));
			}

			if (sym == 0)
			{
				if (nearest[ind][2] == inf)
					min = Math.min(min, find(ind + 1, let, dig, 0));
				else
					min = CMath.min(min, nearest[ind][2] + find(ind + 1, let, dig, 1), find(ind + 1, let, dig, 0));
			}

			return dp[ind][let][dig][sym] = min;
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
