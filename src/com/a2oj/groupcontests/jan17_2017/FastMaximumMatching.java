package com.a2oj.groupcontests.jan17_2017;

import java.io.*;
import java.util.*;

/**
 * Question <a href="http://www.spoj.com/problems/MATCHING/">link</a>.
 */
public final class FastMaximumMatching
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
    	int v, e, n, m;
    	int[] dist, pair;
    	List<Integer>[] adj;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			v = n + m + 1;
			e = in.nextInt();
			dist = new int[v];
			pair = new int[v];
			adj = new List[v];

			for (int i = 0; i < v; i++)
				adj[i] = new ArrayList<>();

			for (int i = 0; i < e; i++)
			{
				int fr, to;

				fr = in.nextInt();
				to = in.nextInt() + n;
				adj[fr].add(to);
				adj[to].add(fr);
			}

			int matching = 0;

			while (bfs())
			{
				for (int i = 1; i <= n; i++)
				{
					if (pair[i] == NIL && dfs(i))
						matching++;
				}
			}

			out.println(matching);
		}

		boolean bfs()
		{
			Queue<Integer> queue = new LinkedList<>();

			for (int i = 1; i <= n; i++)
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
						pair[u] = v;
						pair[v] = u;

						return true;
					}
				}

				dist[u] = INF;

				return false;
			}

			return true;
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

/*

5 5 10
1 2
2 1
1 1
4 1
2 5
3 3
3 4
4 5
5 2
5 4

*/
