package com.a2onlinejudge.groupcontests.feb08_2017;

import java.io.*;
import java.util.*;

public final class EternalVictory
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
        List<Integer>[] adj;
        Edge[] edges;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			adj = new List[n];
			edges = new Edge[(n << 1) - 2];

			for (int i = 0; i < n; i++)
				adj[i] = new ArrayList<>();

			int ctr = 0;

			for (int i = 1; i < n; i++)
			{
				int u, v, wt;

				u = in.nextInt() - 1;
				v = in.nextInt() - 1;
				wt = in.nextInt();
				edges[ctr] = new Edge(v, wt);
				adj[u].add(ctr++);
				edges[ctr] = new Edge(u, wt);
				adj[v].add(ctr++);
			}

			long[] ans = dfs(0, -1);

			out.println(ans[0] - ans[1]);
		}

		long[] dfs(int node, int par)
		{
			long max = 0;
			long maxDepth = 0;
			long tot = 0;

			for (int x : adj[node])
			{
				if (edges[x].to == par)
					continue;

				long[] wts = dfs(edges[x].to, node);

				// if the trip isn't ended in this node's subtree
				wts[0] += edges[x].wt + edges[x].wt;
				// if the trip is ended in this node's subtree
				wts[1] += edges[x].wt;
				max = Math.max(max, wts[0]);
				maxDepth = Math.max(maxDepth, wts[1]);
				tot += wts[0];
			}

			return new long[]{tot, maxDepth};
		}

		class Edge
		{
			int to, wt;

			public Edge(int to, int wt)
			{
				this.to = to;
				this.wt = wt;
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
