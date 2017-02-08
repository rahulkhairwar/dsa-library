package com.hackerrank.competitions.year2017.codeagon;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public final class TaskE
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
    	final int NIL = 0;
    	final int INF = (int) 1e9;
        int q, x, y;
        int[] pair, dist;
        Point[] e, p;
        List<Integer>[] adj;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			q = in.nextInt();

			while (q-- > 0)
			{
				x = in.nextInt();
				y = in.nextInt();
				e = new Point[x + 1];
				p = new Point[y + 1];
				adj = new List[x + y + 1];

				for (int i = 1; i <= x; i++)
					e[i] = new Point(in.nextInt(), in.nextInt());

				for (int i = 1; i <= y; i++)
					p[i] = new Point(in.nextInt(), in.nextInt());

				for (int i = 0; i < x + y + 1; i++)
					adj[i] = new ArrayList<>();

				for (int i = 1; i <= x; i++)
				{
					for (int j = 1; j <= y; j++)
					{
						if (poss(e[i], p[j]))
						{
							adj[i].add(x + j);
							adj[x + j].add(i);
						}
					}
				}

				out.println(hopcroftKarp());
			}
		}

		int hopcroftKarp()
		{
			pair = new int[x + y + 1];
			dist = new int[x + y + 1];

			int matching = 0;

			while (bfs())
			{
				for (int i = 1; i <= x; i++)
					if (pair[i] == NIL && dfs(i))
						matching++;
			}

			return matching;
		}

		boolean bfs()
		{
			Queue<Integer> queue = new LinkedList<>();

			for (int i = 1; i <= x; i++)
			{
				if (pair[i] == NIL)
				{
					dist[i] = 0;
					queue.add(i);
				}
				else
					dist[i] = INF;
			}

			dist[NIL] = INF;

			while (queue.size() > 0)
			{
				int u = queue.poll();

				if (dist[u] < dist[NIL])
				{
					for (int v : adj[u])
					{
						if (dist[pair[v]] == INF)
						{
							dist[pair[v]] = dist[u] + 1;
							queue.add(pair[v]);
						}
					}
				}
			}

			return dist[NIL] != INF;
		}

		boolean dfs(int u)
		{
			if (u != NIL)
			{
				for (int v : adj[u])
				{
					if (dist[pair[v]] == dist[u] + 1 && dfs(pair[v]))
					{
						pair[v] = u;
						pair[u] = v;

						return true;
					}
				}

				dist[u] = INF;

				return false;
			}

			return true;
		}

		boolean poss(Point a, Point b)
		{
			return a.x == b.x || a.y == b.y || (a.x + a.y) == (b.x + b.y) || (a.x - a.y) == (b.x - b.y);
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
