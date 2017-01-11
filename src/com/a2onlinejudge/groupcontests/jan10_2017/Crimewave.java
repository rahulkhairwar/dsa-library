package com.a2onlinejudge.groupcontests.jan10_2017;

import java.io.*;
import java.util.*;

/**
 * Question <a href="https://uva.onlinejudge.org/index.php?option=onlinejudge&page=show_problem&problem=504">link</a>.
 */
public final class Crimewave
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
    	static final int INFINITY = (int) 1e9;
        int problems, r, c, b, v, s, t, flow, maxFlow;
        int[] par, parEdge;
        int[][] mat;
        boolean[][] isBank;
        Edge[] edges;
        List<Integer>[] adj;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			problems = in.nextInt();
			par = new int[3000];
			parEdge = new int[3000];
			mat = new int[55][55];
			isBank = new boolean[55][55];
			edges = new Edge[20000];
			adj = new List[3000];

			while (problems-- > 0)
			{
				r = in.nextInt();
				c = in.nextInt();
				b = in.nextInt();
				maxFlow = 0;

				int ctr = 1;

				for (int i = 0; i < r + 2; i++)
					for (int j = 0; j < c + 2; j++)
						mat[i][j] = ctr++;

				v = ctr + 2;
				s = 0;
				t = ctr + 1;
				ctr = 0;

				for (int i = 0; i < 55; i++)
					Arrays.fill(isBank[i], false);

				for (int i = 0; i < v; i++)
					adj[i] = new ArrayList<>();

				for (int i = 0; i < b; i++)
				{
					int u, v;

					u = in.nextInt();
					v = in.nextInt();
					isBank[u][v] = true;
				}

				for (int i = 1; i < r + 1; i++)
				{
					for (int j = 1; j < c + 1; j++)
					{
						if (isBank[i][j])
						{
							// up.
							if (i > 1 && !isBank[i - 1][j])
							{
								edges[ctr] = new Edge(mat[i - 1][j], 1);
								adj[mat[i][j]].add(ctr++);
								edges[ctr] = new Edge(mat[i][j], 0);
								adj[mat[i - 1][j]].add(ctr++);
							}

							// right.
							if (j < c && !isBank[i][j + 1])
							{
								edges[ctr] = new Edge(mat[i][j + 1], 1);
								adj[mat[i][j]].add(ctr++);
								edges[ctr] = new Edge(mat[i][j], 0);
								adj[mat[i][j + 1]].add(ctr++);
							}

							// down.
							if (i < r && !isBank[i + 1][j])
							{
								edges[ctr] = new Edge(mat[i + 1][j], 1);
								adj[mat[i][j]].add(ctr++);
								edges[ctr] = new Edge(mat[i][j], 0);
								adj[mat[i + 1][j]].add(ctr++);
							}

							// left.
							if (j > 1 && !isBank[i][j - 1])
							{
								edges[ctr] = new Edge(mat[i][j - 1], 1);
								adj[mat[i][j]].add(ctr++);
								edges[ctr] = new Edge(mat[i][j], 0);
								adj[mat[i][j - 1]].add(ctr++);
							}

							// adding edge from source to bank.
							edges[ctr] = new Edge(mat[i][j], 1);
							adj[s].add(ctr++);
							edges[ctr] = new Edge(s, 1);
							adj[mat[i][j]].add(ctr++);
						}
						else
						{
							// up.
							if (i > 1 && !isBank[i - 1][j])
							{
								edges[ctr] = new Edge(mat[i - 1][j], 1);
								adj[mat[i][j]].add(ctr++);
								edges[ctr] = new Edge(mat[i][j], 0);
								adj[mat[i - 1][j]].add(ctr++);
							}

							// right.
							if (j < c && !isBank[i][j + 1])
							{
								edges[ctr] = new Edge(mat[i][j + 1], 1);
								adj[mat[i][j]].add(ctr++);
								edges[ctr] = new Edge(mat[i][j], 0);
								adj[mat[i][j + 1]].add(ctr++);
							}

							// down.
							if (i < r && !isBank[i + 1][j])
							{
								edges[ctr] = new Edge(mat[i + 1][j], 1);
								adj[mat[i][j]].add(ctr++);
								edges[ctr] = new Edge(mat[i][j], 0);
								adj[mat[i + 1][j]].add(ctr++);
							}

							// left.
							if (j > 1 && !isBank[i][j - 1])
							{
								edges[ctr] = new Edge(mat[i][j - 1], 1);
								adj[mat[i][j]].add(ctr++);
								edges[ctr] = new Edge(mat[i][j], 0);
								adj[mat[i][j - 1]].add(ctr++);
							}
						}
					}
				}

				// adding edges from all edge nodes to sink.
				for (int i = 1; i <= r; i++)
				{
					edges[ctr] = new Edge(t, 1);
					adj[mat[i][1]].add(ctr++);
					edges[ctr] = new Edge(mat[i][1], 0);
					adj[t].add(ctr++);
					edges[ctr] = new Edge(t, 1);
					adj[mat[i][c]].add(ctr++);
					edges[ctr] = new Edge(mat[i][c], 0);
					adj[t].add(ctr++);
				}

				for (int i = 1; i <= c; i++)
				{
					edges[ctr] = new Edge(t, 1);
					adj[mat[1][i]].add(ctr++);
					edges[ctr] = new Edge(mat[1][i], 0);
					adj[t].add(ctr++);
					edges[ctr] = new Edge(t, 1);
					adj[mat[r][i]].add(ctr++);
					edges[ctr] = new Edge(mat[r][i], 0);
					adj[t].add(ctr++);
				}

				while (true)
				{
					int[] dist = new int[v];
					Queue<Integer> queue = new LinkedList<>();

					Arrays.fill(par, -1);
					Arrays.fill(parEdge, -1);
					Arrays.fill(dist, INFINITY);
					queue.add(s);
					dist[s] = 0;
					flow = 0;

					while (queue.size() > 0)
					{
						int curr = queue.poll();

						if (curr == t)
							break;	// breaking immediately if sink is reached.

						for (int ed : adj[curr])
						{
							int to, weight;

							to = edges[ed].to;
							weight = edges[ed].weight;

							if (weight > 0 && dist[to] == INFINITY)
							{
								dist[to] = dist[curr] + 1;
								queue.add(to);
								par[to] = curr;
								parEdge[to] = ed;
							}
						}
					}

					augment(t, INFINITY);

					if (flow == 0)
						break;

					maxFlow += flow;
				}

				if (maxFlow == b)
					out.println("possible");
				else
					out.println("not possible");
			}
		}

		void augment(int node, int minEdge)
		{
			if (node == s)
				flow = minEdge;
			else if (par[node] != -1)
			{
				int ed = parEdge[node];
				int weight = edges[ed].weight;

				augment(par[node], Math.min(minEdge, weight));
				edges[ed].weight -= flow;
				edges[ed ^ 1].weight += flow;
			}
		}

		class Edge
		{
			int to, weight;

			public Edge(int to, int weight)
			{
				this.to = to;
				this.weight = weight;
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

/*

2
6 6 10
4 1
3 2
4 2
5 2
3 4
4 4
5 4
3 6
4 6
5 6
5 5 5
3 2
2 3
3 3
4 3
3 4

*/
