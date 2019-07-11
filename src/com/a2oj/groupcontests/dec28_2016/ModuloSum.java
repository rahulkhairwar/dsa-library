package com.a2oj.groupcontests.dec28_2016;

import java.io.*;
import java.util.*;

/**
 * Question <a href="http://codeforces.com/problemset/problem/577/B">link</a>.
 */
public final class ModuloSum
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
    	boolean possible;
        int n, m;
        int[] arr, cnt;
        boolean[][] dp;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			arr = new int[n];
			cnt = new int[m];

			for (int i = 0; i < n; i++)
			{
				arr[i] = in.nextInt() % m;
				cnt[arr[i]]++;

				if (arr[i] == 0)
					possible = true;
			}

			if (possible || n > m)
				out.println("YES");
			else
			{
				dp = new boolean[n + 1][m];

				for (int i = 1; i <= n; i++)
				{
					dp[i][arr[i - 1]] = true;

					for (int j = 0; j < m; j++)
					{
						if (dp[i - 1][j])
							dp[i][j] = dp[i][(j + arr[i - 1]) % m] = true;
					}
				}

				for (int i = 0; i < m; i++)
					if (dp[n][i] && i % m == 0)
					{
						out.println("YES");

						return;
					}

				out.println("NO");
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
