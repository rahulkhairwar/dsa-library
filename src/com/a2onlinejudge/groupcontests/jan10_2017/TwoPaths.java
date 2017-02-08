package com.a2onlinejudge.groupcontests.jan10_2017;

import java.awt.*;
import java.io.*;
import java.util.*;

public final class TwoPaths
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
        boolean[][] adj;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			adj = new boolean[n][n];

			for (int i = 1; i < n; i++)
			{
				int u, v;

				u = in.nextInt() - 1;
				v = in.nextInt() - 1;
				adj[u][v] = adj[v][u] = true;
			}

			int max = 0;

			for (int i = 0; i < n; i++)
			{
				for (int j = i + 1; j < n; j++)
				{
					if (adj[i][j])
					{
						Point a, b;

						adj[i][j] = adj[j][i] = false;
						a = findBest(i, -1);
						b = findBest(j, -1);
						max = CMath.max(max, a.x * b.x, a.x * b.y, a.y * b.x, a.y * b.y);
						adj[i][j] = adj[j][i] = true;
					}
				}
			}

			out.println(max);
		}

		Point findBest(int node, int par)
		{
			// x stores max length of 2 combined children, y of the max length child.
			Point max = new Point(0, 0);
			PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());

			for (int i = 0; i < n; i++)
			{
				if (i == par)
					continue;

				if (adj[node][i])
				{
					Point pt = findBest(i, node);

					max.x = Math.max(max.x, pt.x);
					queue.add(pt.y);
				}
			}

			if (queue.size() == 0)
				return max;

			if (queue.size() == 1)
				max.y = 1 + queue.poll();
			else
			{
				int tempX = max.y = 1 + queue.poll();

				tempX += 1 + queue.poll();
				max.x = Math.max(max.x, tempX);
			}

			return max;
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
		static int max(int... arr)
		{
			int max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

	}

}
