package com.codeforces.competitions.year2017.round393div2;

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
        int n, m, k;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			k = in.nextInt();

			if (n == 1)
			{
				out.println(m);

				return;
			}

			if (n == 2)
			{
				// tot - 2, 2 - 1, 3 - 2, ....
				int ans = 1;

				m -= 2;

				if (m > 0)
				{
					m--;
					ans++;
				}

				ans += m / 2;
				out.println(ans);

				return;
			}

			if (n == 3)
			{
				if (k == 1 || k == 3)
				{
					int ans = 1;

					m -= 3;

					if (m > 0)
					{
						m--;
						ans++;
					}

					if (m > 1)
					{
						m -= 2;
						ans++;
					}

					ans += m / 3;
					out.println(ans);

					return;
				}
				else
				{
					int ans = 1;

					m -= 3;

					if (m > 0)
					{
						m--;
						ans++;
					}

					ans += m / 3;
					out.println(ans);

					return;
				}
			}

			int cnt = 1;
			int tot = m;

			tot -= n;

			if (tot > 0)
			{
				tot--;
				cnt++;
			}

			int ctr = 1;
			boolean ll, rr;

			ll = rr = false;

			while (true)
			{
				int left = Math.min(k - 1, ctr);
				int right = Math.min(n - k, ctr);

				if (left + right + 1 > tot)
					break;

				if (k - 1 == ctr)
				{
					ll = true;

					if (n - k == ctr)
						rr = true;

					break;
				}

				if (n - k == ctr)
				{
					rr = true;

					break;
				}

				tot -= left + right + 1;
				cnt++;
				ctr++;
			}

			if (ll && rr && tot > 0)
			{
				int left = k - 1;
				int right = n - k;
				int req = left + right + 1;

				cnt += tot / req;
			}
			else if (ll)
			{
				while (true)
				{
					int left = k - 1;
					int right = Math.min(n - k, ctr);

					if (left + right + 1 > tot)
						break;

					if (n - k == ctr)
						break;

					tot -= left + right + 1;
					cnt++;
					ctr++;
				}
			}
			else if (rr)
			{
				while (true)
				{
					int left = Math.min(k - 1, ctr);
					int right = n - k;

					if (left + right + 1 > tot)
						break;

					if (k - 1 == ctr)
						break;

					tot -= left + right + 1;
					cnt++;
					ctr++;
				}
			}

			int left = k - 1;
			int right = n - k;
			int req = left + right + 1;

			cnt += tot / req;
			out.println(cnt);
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
