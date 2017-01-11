package com.a2onlinejudge.groupcontests.jan02_2017;

import java.io.*;
import java.util.*;

/**
 * Question <a href="http://codeforces.com/problemset/problem/571/B">link</a>.
 */
public final class Minimization
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
        int n, k;
        int[] arr;
        int[][] dp;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			k = in.nextInt();
			arr = new int[n];

			for (int i = 0; i < n; i++)
				arr[i] = in.nextInt();

			int large, small;

			large = n % k;
			small = k - large;
			dp = new int[small + 1][large + 1];
			Arrays.sort(arr);

			for (int i = 0; i <= small; i++)
				Arrays.fill(dp[i], Integer.MAX_VALUE);

			dp[0][0] = 0;

			for (int i = 0; i <= small; i++)
			{
				for (int j = 0; j <= large; j++)
				{
					// dp[i][j] = minimum answer using i small groups and j large groups.
					int used = i * (n / k) + j * (n / k + 1);

					// if this state was reached from the state with 1 less small group.
					if (i > 0)
						dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + arr[used - 1] - arr[used - n / k]);

					// if this state was reached from the state with 1 less large group.
					if (j > 0)
						dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + arr[used - 1] - arr[used - n / k - 1]);
				}
			}

			out.println(dp[small][large]);
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
