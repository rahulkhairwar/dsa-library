package com.codeforces.competitions.year2017.round393div2;

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
        int n, cycles;
        int[] p, b;
        boolean[] vis;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			p = new int[n];
			b = new int[n];
			vis = new boolean[n];

			for (int i = 0; i < n; i++)
				p[i] = in.nextInt() - 1;

			for (int i = 0; i < n; i++)
				b[i] = in.nextInt();

			for (int i = 0; i < n; i++)
				if (!vis[i])
					dfs(i);

			int ans = 0;
			int ones = 0;

			for (int i = 0; i < n; i++)
				if (b[i] == 1)
					ones++;

			if (ones % 2 == 0)
				ans++;

			if (cycles == 1)
				out.println(ans);
			else
				out.println(ans + cycles);
		}

		void dfs(int node)
		{
			if (vis[node])
			{
				cycles++;

				return;
			}

			vis[node] = true;
			dfs(p[node]);
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
