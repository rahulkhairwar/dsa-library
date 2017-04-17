package com.codeforces.competitions.year2017.round399div1_2;

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
        int n, k, x;
        int[][] map;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			k = in.nextInt();
			x = in.nextInt();
			map = new int[20][n];

			for (int i = 0; i < n; i++)
				map[0][i] = in.nextInt();

			Arrays.sort(map[0]);

			if (k == 0)
			{
				out.println(map[0][n - 1] + " " + map[0][0]);

				return;
			}

			int ctr = 1;
			int cycle, start;

			outer : while (true)
			{
				for (int i = 0; i < n; i += 2)
					map[ctr][i] = map[ctr - 1][i] ^ x;

				for (int i = 1; i < n; i += 2)
					map[ctr][i] = map[ctr - 1][i];

				Arrays.sort(map[ctr]);

				for (int i = 0; i < ctr; i++)
				{
					boolean match = true;

					for (int j = 0; j < n; j++)
					{
						if (map[i][j] != map[ctr][j])
						{
							match = false;

							break;
						}
					}

					if (match)
					{
						cycle = ctr - i;
						start = i;

						break outer;
					}
				}

				ctr++;
			}

			k -= start;
			k %= cycle;
			out.println(map[start + k][n - 1] + " " + map[start + k][0]);
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
