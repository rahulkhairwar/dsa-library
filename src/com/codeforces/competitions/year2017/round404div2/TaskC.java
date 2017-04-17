package com.codeforces.competitions.year2017.round404div2;

import java.io.*;
import java.math.BigInteger;
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
        long n, m;
        BigInteger bn, bm, rem;
        InputReader in;
        PrintWriter out;

        void solve()
		{
			n = in.nextLong();
			m = in.nextLong();
			bn = BigInteger.valueOf(n);
			bm = BigInteger.valueOf(m);
			rem = BigInteger.valueOf(n - m);	// after day m.

			if (n == 1)
				out.println(1);
			else if (n <= m)
				out.println(n);
			else
			{
				BigInteger f = find(rem);

				out.println(f.add(bm));
			}
		}

		BigInteger find(BigInteger rem)
		{
			BigInteger low, high, mid;

			low = BigInteger.ONE;
			high = BigInteger.valueOf((long) 1e18).multiply(BigInteger.valueOf((long) 1e18));

			while (low.compareTo(high) <= 0)
			{
				mid = low.add(high).divide(BigInteger.valueOf(2));

				BigInteger val = get(mid);

				if (val.compareTo(rem) >= 0)
				{
					if (get(mid.subtract(BigInteger.ONE)).compareTo(rem) < 0)
						return mid;

					high = mid.subtract(BigInteger.ONE);
				}
				else
					low = mid.add(BigInteger.ONE);
			}

			return BigInteger.ONE;
		}

		BigInteger get(BigInteger b)
		{
			return b.multiply(b.add(BigInteger.ONE)).divide(BigInteger.valueOf(2));
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
