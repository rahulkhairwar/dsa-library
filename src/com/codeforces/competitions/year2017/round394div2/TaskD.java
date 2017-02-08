package com.codeforces.competitions.year2017.round394div2;

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
    	final long inf = (long) 1e18;
        int n, l, r;
		long min, max;
		int[] a;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			l = in.nextInt();
			r = in.nextInt();
			min = inf;
			max = -inf;
			a = new int[n];

			for (int i = 0; i < n; i++)
				a[i] = in.nextInt();

			for (int i = 0; i < n; i++)
			{
				a[i] += in.nextInt();
				min = Math.min(min, a[i]);
				max = Math.max(max, a[i]);
			}

			long low = (long) -1e9;
			long high = (long) 1e9;

			while (low <= high)
			{
				long mid = low + high >> 1;
				int check = check(mid);

				if (check == 0)
				{
					for (int i = 0; i < n; i++)
						out.print(a[i] + mid + " ");

					return;
				}
				else if (check < 0)
					low = mid + 1;
				else
					high = mid - 1;
			}

			out.println(-1);
		}

		int check(long x)
		{
			long low = min + x;
			long high = max + x;

			if (l <= low && high <= r)
				return 0;

			if (low < l)
				return -1;

			return 1;
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
