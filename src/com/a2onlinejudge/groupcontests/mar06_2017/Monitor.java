package com.a2onlinejudge.groupcontests.mar06_2017;

import java.io.*;
import java.util.*;

public final class Monitor
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
        long a, b, x, y;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			a = in.nextLong();
			b = in.nextLong();
			x = in.nextLong();
			y = in.nextLong();

			long gcd = CMath.gcd(x, y);

			x /= gcd;
			y /= gcd;

			long min = Math.min(a / x, b / y);

			out.println((x * min) + " " + (y * min));
		}

		void debug(Object... o)
		{
			System.err.println(Arrays.deepToString(o));
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

	static class CMath
	{
		static long gcd(long a, long b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

	}

}

/*

800 600 4 3

1920 1200 16 9

1 1 1 2

186329049 1221011622 90104472 1769702163

*/
