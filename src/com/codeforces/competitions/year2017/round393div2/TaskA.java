package com.codeforces.competitions.year2017.round393div2;

import java.io.*;
import java.util.*;

public final class TaskA
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
        int m, d, arr[];
        InputReader in;
        PrintWriter out;

		void solve()
		{
			arr = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
			m = in.nextInt() - 1;
			d = in.nextInt() - 1;

			int[][] x = new int[6][7];
			int ctr = 1;

			for (int i = d; i < 7; i++)
			{
				x[0][i] = ctr++;
				arr[m]--;
			}

			for (int i = 1; i < 6 && arr[m] > 0; i++)
			{
				for (int j = 0; j < 7 && arr[m] > 0; j++)
				{
					x[i][j] = ctr++;
					arr[m]--;
				}
			}

			if (x[5][0] > 0)
				out.println(6);
			else if (x[4][0] > 0)
				out.println(5);
			else
				out.println(4);
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
