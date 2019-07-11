package com.a2oj.groupcontests.jan17_2017;

import java.awt.*;
import java.io.*;
import java.util.*;

public final class GeraldAndGiantChess
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
    	final long mod = (long) 1e9 + 7;
        int h, w, n;
        long[] fact, invFact;
        Point[] pts;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			pre();
			h = in.nextInt();
			w = in.nextInt();
			n = in.nextInt();
			pts = new Point[n + 1];

			for (int i = 0; i < n; i++)
				pts[i] = new Point(in.nextInt(), in.nextInt());

			pts[n] = new Point(h, w);
			Arrays.sort(pts, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					if (o1.x == o2.x)
						return Integer.compare(o1.y, o2.y);

					return Integer.compare(o1.x, o2.x);
				}
			});

			long[] ans = new long[n + 1];

			for (int i = 0; i <= n; i++)
			{
				long tot = nCR(pts[i].x + pts[i].y - 2, pts[i].x - 1);
				long remove = 0;

				for (int j = 0; j < i; j++)
				{
					if (pts[i].y < pts[j].y)
						continue;

					remove += CMath.mod(nCR(pts[i].x - pts[j].x + pts[i].y - pts[j].y, pts[i].x - pts[j].x) * ans[j],
							mod);
					remove = CMath.mod(remove, mod);
				}

				ans[i] = tot - remove;

				while (ans[i] < 0)
					ans[i] += mod;
			}

			out.println(ans[n]);
		}

		long nCR(int n, int r)
		{
			return CMath.mod(CMath.mod(fact[n] * invFact[r], mod) * invFact[n - r], mod);
		}

		void pre()
		{
			fact = new long[(int) 2e5 + 5];
			invFact = new long[(int) 2e5 + 5];
			fact[0] = 1;

			for (int i = 1; i <= 2e5; i++)
				fact[i] = CMath.mod(fact[i - 1] * i, mod);

			invFact[(int) 2e5] = CMath.moduloInverse(fact[(int) 2e5], mod);

			for (int i = (int) 2e5; i > 0; i--)
				invFact[i - 1] = CMath.mod(invFact[i] * i, mod);
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
