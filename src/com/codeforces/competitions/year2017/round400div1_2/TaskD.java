package com.codeforces.competitions.year2017.round400div1_2;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public final class TaskD
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
        int n, m;
        int[] status, ctr, col;
        int[][] adj;
        boolean[] vis;
        InputReader in;
        PrintWriter out;

        void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			status = in.nextIntArray(n);
			ctr = new int[n];
			col = new int[m];
			adj = new int[n][2];

			for (int i = 0; i < m; i++)
			{
				int sz = in.nextInt();

				while (sz-- > 0)
				{
					int x = in.nextInt() - 1;

					adj[x][ctr[x]++] = i;
				}
			}

			if (isBipartite())
				out.println("YES");
			else
				out.println("NO");
		}

		boolean isBipartite()
		{
			List<Point>[] graph = new List[m];

			for (int i = 0; i < m; i++)
				graph[i] = new ArrayList<>();

			for (int i = 0; i < n; i++)
			{
				graph[adj[i][0]].add(new Point(adj[i][1], status[i]));
				graph[adj[i][1]].add(new Point(adj[i][0], status[i]));
			}

			vis = new boolean[m];

			boolean valid = true;

			for (int i = 0; i < m; i++)
				if (!vis[i])
					valid &= dfs(graph, i, 1);

			for (int i = 0; i < n; i++)
			{
				if (status[i] == 1 && col[adj[i][0]] != col[adj[i][1]])
					return false;
				else if (status[i] == 0 && col[adj[i][0]] == col[adj[i][1]])
					return false;
			}

			return valid;
		}

		boolean dfs(List<Point>[] graph, int node, int c)
		{
			vis[node] = true;
			col[node] = c;

			boolean isBipartite = true;

			for (Point pt : graph[node])
			{
				int x = pt.y == 0 ? c % 2 + 1 : c;

				if (!vis[pt.x])
					isBipartite &= dfs(graph, pt.x, x);
				else
				{
					if (col[pt.x] != x)
						return false;
				}
			}

			return isBipartite;
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

        public int[] nextIntArray(int arraySize)
        {
            int array[] = new int[arraySize];

            for (int i = 0; i < arraySize; i++)
                array[i] = nextInt();

            return array;
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
