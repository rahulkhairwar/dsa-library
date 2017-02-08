package com.spoj.practice.classic;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class PrimeIntervals
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
        int t, l, r, lim;
        boolean[] isComp;
		List<Integer> primes;
		InputReader in;
        PrintWriter out;

		void solve()
		{
			pre();
			t = in.nextInt();
			isComp = new boolean[(int) 1e6 + 5];

			while (t-- > 0)
			{
				l = in.nextInt();
				r = in.nextInt();

				int size = r - l + 1;
				int sqrt = (int) Math.sqrt(r);
				// in isComp, 0 = l, 1 = l + 1, ...., r - l + 1 = r.

				for (int i = 0; i <= size; i++)
					isComp[i] = false;

				for (int p : primes)
				{
					if (p > sqrt)
						break;

					int curr = p * ((l + p - 1) / p);
					if (curr == p)
						curr += p;

					while (curr <= r)
					{
						isComp[curr - l] = true;
						curr += p;
					}
				}

				for (int i = l == 1 ? 1 : 0; i < size; i++)
					if (!isComp[i])
						out.println(i + l);

				out.println();
			}
		}

		void pre()
		{
			lim = (int) Math.sqrt(2147483647);
			isComp = new boolean[lim + 5];
			isComp[1] = true;
			primes = new ArrayList<>();
			primes.add(2);

			int curr = 2;

			while ((curr << 1) <= lim)
			{
				isComp[curr << 1] = true;
				curr++;
			}

			for (int i = 3; i <= lim; i += 2)
			{
				if (isComp[i])
					continue;

				primes.add(i);
				curr = i + i;

				while (curr <= lim)
				{
					isComp[curr] = true;
					curr += i;
				}
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
