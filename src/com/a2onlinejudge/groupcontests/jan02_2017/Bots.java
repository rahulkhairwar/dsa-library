package com.a2onlinejudge.groupcontests.jan02_2017;

import java.io.*;
import java.util.*;

/**
 * Question <a href="http://codeforces.com/problemset/problem/575/H">link</a>.
 */
public final class Bots
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
        InputReader in;
        PrintWriter out;

		void solve()
		{
			long mod = (long) 1e9 + 7;
			int n = in.nextInt();
			long[] fact, invFact;

			fact = new long[n << 1];
			invFact = new long[n << 1];
			fact[0] = 1;

			for (int i = 1; i < n << 1; i++)
				fact[i] = (int) CMath.mod(fact[i - 1] * i, mod);

			invFact[(n << 1) - 1] = CMath.moduloInverse(fact[(n << 1) - 1], mod);

			for (int i = (n << 1) - 1; i > 0; i--)
				invFact[i - 1] = CMath.mod(invFact[i] * i, mod);

			long ans = CMath.modPower(2, n + 1, mod) - 1;
			long curr = ans + 1;

			for (int i = 0; i < n; i++)
			{
				long rem = CMath.mod(2 * CMath.mod(fact[n + i] * CMath.mod(invFact[n] * invFact[i], mod), mod), mod);

				curr -= rem;
				ans = CMath.mod(ans + curr, mod);
				curr = CMath.mod(curr << 1, mod);
			}

			while (ans < 0)
				ans += mod;

			out.println(ans);
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
