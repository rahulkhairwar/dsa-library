package com.codeforces.competitions.year2017.round399div1_2;

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
        long n, l, r;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextLong();
			l = in.nextLong();
			r = in.nextLong();

			long size = getSize(n);

			out.println(count(n, 0, 1, size));
		}

		long count(long x, int level, long left, long right)
		{
			if (left > r || right < l)
				return 0;

			long size = getSize(x);

			if (size == 1)
			{
				if (left >= l && right <= r)
					return x == 1 ? 1 : 0;

				return 0;
			}

			long ll = count(x / 2, level + 1, left, left + size / 2 - 1);
			long rr = count(x / 2, level + 1, left + size / 2 + 1, right);
			long mid = count(x % 2, level + 1, left + size / 2, left + size / 2);

			return ll + mid + rr;
		}

		long getSize(long x)
		{
			if (x < 2)
				return 1;

			return 2 * getSize(x >> 1) + 1;
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
