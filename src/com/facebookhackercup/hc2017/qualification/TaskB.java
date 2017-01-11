package com.facebookhackercup.hc2017.qualification;

import java.io.*;
import java.util.*;

import static java.lang.System.out;

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
        int t, n;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			t = in.nextInt();

			for (int test = 1; test <= t; test++)
			{
				n = in.nextInt();

				PriorityQueue<Integer> asc, desc;

				asc = new PriorityQueue<>();
				desc = new PriorityQueue<>(Collections.reverseOrder());

				for (int i = 0; i < n; i++)
				{
					int x = in.nextInt();

					asc.add(x);
					desc.add(x);
				}

				int cnt = 0;

				while (desc.size() > 0)
				{
					int top = desc.poll();
					int ctr = 1;

					asc.remove(top);

					while (top * ctr < 50)
					{
						if (desc.size() == 0)
							break;

						int x = asc.poll();

						desc.remove(x);
						ctr++;
					}

					if (top * ctr >= 50)
						cnt++;
				}

				out.println("Case #" + test + ": " + cnt);
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
