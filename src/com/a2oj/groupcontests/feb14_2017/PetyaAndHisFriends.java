package com.a2oj.groupcontests.feb14_2017;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public final class PetyaAndHisFriends
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
        int n;
        List<Integer> primes;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			pre();
			n = in.nextInt();

			if (n == 2)
				out.println(-1);
			else
			{
				BigInteger[] ans = new BigInteger[n];

				for (int i = 0; i < n; i++)
					ans[i] = BigInteger.ONE;

				for (int i = 0; i < n; i++)
				{
					for (int j = 0; j < n; j++)
					{
						if (i == j)
							continue;

						ans[j] = ans[j].multiply(BigInteger.valueOf(primes.get(i)));
					}
				}

				for (int i = 0; i < n; i++)
					out.println(ans[i].toString());
			}
		}

		void pre()
		{
			primes = new ArrayList<>();

			int lim = 250;
			boolean[] isComposite = new boolean[lim + 5];
			int curr = 2;

			isComposite[1] = true;
			primes.add(2);

			while ((curr << 1) <= lim)
			{
				isComposite[curr << 1] = true;
				curr++;
			}

			for (int i = 3; i <= lim; i += 2)
			{
				if (isComposite[i])
					continue;

				primes.add(i);
				curr = i + i;

				while (curr <= lim)
				{
					isComposite[curr] = true;
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
