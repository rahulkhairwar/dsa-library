package com.a2oj.groupcontests.feb08_2017;

import java.io.*;
import java.util.*;

public final class RectangularGame
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
        int n, lim;
        int[] spf;
        List<Integer> primes;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			pre();
			n = in.nextInt();

			int ans = 0;

			while (n > 1)
			{
				int x = getSPF(n);

				ans += n;
				n /= x;

				if (n == 1)
					break;
			}

			out.println(ans + 1);
		}

		void pre()
		{
			lim = (int) Math.sqrt(1e9);
			spf = new int[lim + 5];
			spf[2] = 2;
			primes = new ArrayList<>();
			primes.add(2);

			int curr = 2;

			while ((curr << 1) <= lim)
			{
				spf[curr << 1] = 2;
				curr++;
			}

			for (int i = 3; i <= lim; i += 2)
			{
				if (spf[i] != 0)
					continue;

				spf[i] = i;
				primes.add(i);
				curr = i + i;

				while (curr <= lim)
				{
					if (spf[curr] == 0)
						spf[curr] = i;

					curr += i;
				}
			}
		}

		int getSPF(int x)
		{
			if (x <= lim)
				return spf[x];

			int sqrt = (int) Math.sqrt(x);

			for (int p : primes)
			{
				if (p > sqrt)
					return x;

				if (x % p == 0)
					return p;
			}

			return x;
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
