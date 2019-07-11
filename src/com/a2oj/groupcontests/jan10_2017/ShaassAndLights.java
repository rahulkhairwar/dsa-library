package com.a2oj.groupcontests.jan10_2017;

import java.io.*;
import java.util.*;

public final class ShaassAndLights
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
    	static final long mod = (long) 1e9 + 7;
        int n, m, arr[];
        long[] fact, invFact;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			arr = new int[n];

			for (int i = 0; i < m; i++)
				arr[in.nextInt() - 1] = 1;

			List<Group> groups = new ArrayList<>();

			int currLeft = 0;
			int currRight;

			while (currLeft < n)
			{
				while (currLeft < n && arr[currLeft] == 1)
					currLeft++;

				if (currLeft == n)
					break;

				currRight = currLeft + 1;

				while (currRight < n && arr[currRight] == 0)
					currRight++;

				groups.add(new Group(currLeft, currRight - 1));
				currLeft = currRight + 1;
			}

			int tot = 0;
			long prod = 1;

			for (Group g : groups)
			{
				tot += g.size;

				if (g.isDual)
					prod = CMath.mod(prod * CMath.modPower(2, g.size - 1, mod), mod);
			}

			compute(tot + 1);
			prod = CMath.mod(prod * fact[tot], mod);

			for (Group g : groups)
				prod = CMath.mod(prod * invFact[g.size], mod);

			out.println(prod);
		}

		void compute(int tot)
		{
			fact = new long[tot];
			invFact = new long[tot];
			fact[0] = 1;

			for (int i = 1; i < tot; i++)
				fact[i] = CMath.mod(fact[i - 1] * i, mod);

			invFact[tot - 1] = CMath.moduloInverse(fact[tot - 1], mod);

			for (int i = tot - 1; i > 0; i--)
				invFact[i - 1] = CMath.mod(invFact[i] * i, mod);
		}

		class Group
		{
			int left, right, size;
			boolean isDual;

			Group(int left, int right)
			{
				this.left = left;
				this.right = right;
				size = right - left + 1;

				if (left > 0 && arr[left - 1] == 1 && right < n - 1 && arr[right + 1] == 1)
					isDual = true;
			}

			@Override public String toString()
			{
				return String.format("l : %d, r : %d, d ? : %s", left, right, isDual ? "t" : "f");
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

	static class CMath
	{
		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);
			else
				return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

}

/*

1 0 0 0 0 1
6 2
1 6
: 8

1 0 0 0 0 0 1
7 2
1 7
: 16

1 0 0 0 1 0 0 0 0 1
10 3
1 5 10
: 1120

1 0 0 0 1 0 0 0 0 1 0 0 0 1
14 4
1 5 10 14
: 537600

1 0 0 0 0
5 1
1
: 1

0 0 0 0 1 0 0 0 1
9 2
5 9
: 140

0 0 1 0 0 1
6 2
3 6
: 12

0 0 1 0 0 0 1
7 2
3 7
: 40

0 0 0 1 0 0 1
7 2
4 7
: 20

0 0 0 1 0 0 0 1
8 2
4 8
: 80

36 24
1 7 8 10 11 12 13 14 15 16 17 19 21 22 25 26 27 28 29 30 31 32 35 36

*/
