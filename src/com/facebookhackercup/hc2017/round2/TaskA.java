package com.facebookhackercup.hc2017.round2;

import java.io.*;
import java.util.*;

/**
 * Not sure if this is correct.
 * Initially submitted code was wrong.
 */
class TaskA
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
        int t, n, m, k;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			t = in.nextInt();

			for (int test = 1; test <= t; test++)
			{
				n = in.nextInt();
				m = in.nextInt();
				k = in.nextInt();

				int req = k + 1;
				int min = Math.min(n, m);
/*
				if (req >= min)
				{
					out.println("Case #" + test + ": " + -1);

					continue;
				}*/

				if (2 * k + 3 > Math.max(n, m))
				{
					out.println("Case #" + test + ": " + -1);

					continue;
				}

				int ans = ((min + k - 1) / k);

				System.out.println("ans : " + ans);

				if (Math.max(k, 3) + k + 1 <= Math.max(n, m) && 3 * k <= min)
				{
//					System.out.println("hh");
					if (k == 1)
						ans = Math.min(ans, 5);
					else if (k == 2)
						ans = Math.min(ans, 4);
					else
					{
//						System.out.println("hhh");
						ans = Math.min(ans, 4);
					}
				}

				out.println("Case #" + test + ": " + ans);
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

/*

1
20 20 4

1
6 19 7

*/
