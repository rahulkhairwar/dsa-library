package com.codeforces.competitions.educational.year2017.round21;

import java.io.*;
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
        int n, w;
        Cup[] cups;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			w = in.nextInt();
			cups = new Cup[n];

			for (int i = 0; i < n; i++)
				cups[i] = new Cup(i, in.nextInt());

			Arrays.sort(cups, new Comparator<Cup>()
			{
				@Override public int compare(Cup o1, Cup o2)
				{
					return Integer.compare(o1.cap, o2.cap);
				}
			});

			for (int i = 0; i < n; i++)
			{
				if ((cups[i].cap & 1) == 1)
				{
					int temp = cups[i].cap / 2 + 1;

					w -= temp;
					cups[i].ans = temp;
				}
				else
				{
					int temp = cups[i].cap / 2;

					w -= temp;
					cups[i].ans = temp;
				}
			}

			if (w > 0)
			{
				for (int i = n - 1; i >= 0; i--)
				{
					int req = cups[i].cap - cups[i].ans;

					if (req <= w)
					{
						cups[i].ans += req;
						w -= req;
					}
					else
					{
						cups[i].ans += w;
						w = 0;
					}
				}
			}

			if (w != 0)
				out.println(-1);
			else
			{
				Arrays.sort(cups, new Comparator<Cup>()
				{
					@Override public int compare(Cup o1, Cup o2)
					{
						return Integer.compare(o1.ind, o2.ind);
					}
				});

				for (int i = 0; i < n; i++)
					out.print(cups[i].ans + " ");
			}
		}

        public Solver(InputReader in, PrintWriter out)
        {
        	this.in = in;
        	this.out = out;
        }

	}

	static class Cup
	{
		int ind, cap, ans;

		public Cup(int ind, int cap)
		{
			this.ind = ind;
			this.cap = cap;
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
