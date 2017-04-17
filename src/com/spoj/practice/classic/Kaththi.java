package com.spoj.practice.classic;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Java solution : TLE.
 * <br />Same solution in C++(Kaththi.cpp in the same folder) : Accepted.
 * <br />SPOJ _/\_
 */
class Kaththi
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
    	static final int inf = (int) 1e9;
        int t, r, c;
        int[] dist;
        int[][] num;
        boolean[] vis;
        List<Point>[] adj;
        Deque<Integer> deque;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			int lim = (int) 1e6 + 5;

			t = in.nextInt();
			dist = new int[lim];
			num = new int[1005][1005];
			vis = new boolean[lim];
			adj = new List[lim];
			deque = new LinkedList<>();

			for (int i = 0; i < lim; i++)
				adj[i] = new ArrayList<>();

			String[] map = new String[1005];

			while (t-- > 0)
			{
				r = in.nextInt();
				c = in.nextInt();

				for (int i = 0; i < r; i++)
					map[i] = in.nextLine();

				int ctr = 0;

				for (int i = 0; i < r; i++)
					for (int j = 0; j < c; j++)
						num[i][j] = ctr++;

				for (int i = 0; i < ctr; i++)
					adj[i].clear();

				for (int i = 0; i < r; i++)
				{
					for (int j = 0; j < c; j++)
					{
						// right.
						if (j < c - 1)
						{
							if (map[i].charAt(j) == map[i].charAt(j + 1))
							{
								adj[num[i][j]].add(new Point(num[i][j + 1], 0));
								adj[num[i][j + 1]].add(new Point(num[i][j], 0));
							}
							else
							{
								adj[num[i][j]].add(new Point(num[i][j + 1], 1));
								adj[num[i][j + 1]].add(new Point(num[i][j], 1));
							}
						}

						// down.
						if (i < r - 1)
						{
							if (map[i].charAt(j) == map[i + 1].charAt(j))
							{
								adj[num[i][j]].add(new Point(num[i + 1][j], 0));
								adj[num[i + 1][j]].add(new Point(num[i][j], 0));
							}
							else
							{
								adj[num[i][j]].add(new Point(num[i + 1][j], 1));
								adj[num[i + 1][j]].add(new Point(num[i][j], 1));
							}
						}
					}
				}

				out.println(zeroOneBFS(ctr - 1));
			}
		}

		int zeroOneBFS(int dest)
		{
			for (int i = 1; i <= dest; i++)
			{
				dist[i] = inf;
				vis[i] = false;
			}

			deque.clear();
			deque.add(0);

			while (deque.size() > 0)
			{
				int curr = deque.poll();

				vis[curr] = false;

				for (Point p : adj[curr])
				{
					if (dist[curr] + p.y < dist[p.x])
					{
						dist[p.x] = dist[curr] + p.y;

						if (p.y == 0)
							deque.addFirst(p.x);
						else
							deque.addLast(p.x);

						vis[p.x] = true;
					}
				}
			}

			return dist[dest];
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

        public String nextLine()
        {
            int c = read();

            StringBuilder result = new StringBuilder();

            do
            {
                result.appendCodePoint(c);

                c = read();
            } while (!isNewLine(c));

            return result.toString();
        }

        public boolean isNewLine(int c)
        {
            return c == '\n';
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

/*

4
2 2
aa
aa
2 3
abc
def
6 6
akaccc
aaacfc
amdfcc
aokhdd
zyxwdp
zyxwdd
5 5
abbbc
abacc
aaacc
aefci
cdgdd

*/
