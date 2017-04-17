package com.a2onlinejudge.groupcontests.feb14_2017;

import java.io.*;
import java.util.*;

public final class DimaAndBacteria
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
    	static int inf = (int) 1e9;
        int n, m, k;
        List<Edge>[] zeroWeight;
        int[] start, end, type, dist;
		boolean[] vis;
		int[][] minDist;
		Deque<Integer> deque;
        InputReader in;
        PrintWriter out;

        void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			k = in.nextInt();
			zeroWeight = new List[n];
			start = new int[n];
			end = new int[n];
			type = new int[n];
			dist = new int[n];
			vis = new boolean[n];
			minDist = new int[k][k];
			deque = new LinkedList<>();

			int[] cnt = new int[k];
			int ctr = 0;

			for (int i = 0; i < k; i++)
			{
				cnt[i] = in.nextInt();
				start[i] = ctr;
				end[i] = start[i] + cnt[i] - 1;
				Arrays.fill(minDist[i], inf);

				int temp = cnt[i];

				while (temp-- > 0)
					type[ctr++] = i;
			}

			for (int i = 0; i < n; i++)
				zeroWeight[i] = new ArrayList<>();

			for (int i = 0; i < m; i++)
			{
				int u, v, wt;

				u = in.nextInt() - 1;
				v = in.nextInt() - 1;
				wt = in.nextInt();

				if (type[u] != type[v])
				{
					minDist[type[u]][type[v]] = Math.min(minDist[type[u]][type[v]], wt);
					minDist[type[v]][type[u]] = Math.min(minDist[type[v]][type[u]], wt);
				}

				if (wt == 0)
				{
					zeroWeight[u].add(new Edge(u, v, 0));
					zeroWeight[v].add(new Edge(v, u, 0));
				}
			}

			for (int i = 0; i < k; i++)
				minDist[i][i] = 0;

			boolean correct = true;

			for (int i = 0; i < k; i++)
			{
				zeroOneBFS(start[i]);

				for (int j = start[i]; j <= end[i]; j++)
				{
					if (dist[j] != 0)
					{
						correct = false;

						break;
					}
				}
			}

			if (!correct)
				out.println("No");
			else
			{
				out.println("Yes");
				floydWarshall(k);

				for (int i = 0; i < k; i++, out.println())
				{
					for (int j = 0; j < k; j++)
					{
						if (minDist[i][j] == inf)
							out.print(-1 + " ");
						else
							out.print(minDist[i][j] + " ");
					}
				}
			}
		}

		void zeroOneBFS(int src)
		{
			Arrays.fill(vis, false);
			Arrays.fill(dist, inf);
			dist[src] = 0;
			deque.clear();
			deque.add(src);

			while (deque.size() > 0)
			{
				int curr = deque.poll();

				vis[curr] = false;

				for (Edge edge : zeroWeight[curr])
				{
					if (dist[curr] + edge.wt < dist[edge.to])
					{
						dist[edge.to] = dist[curr] + edge.wt;

						if (!vis[edge.to])
						{
							if (edge.wt == 0)
								deque.addFirst(edge.to);
							else
								deque.addLast(edge.to);

							vis[edge.to] = true;
						}
					}
				}
			}
		}

		void floydWarshall(int v)
		{
			for (int k = 0; k < v; k++)
			{
				for (int i = 0; i < v; i++)
				{
					for (int j = 0; j < v; j++)
					{
						if (minDist[i][k] + minDist[k][j] < minDist[i][j])
							minDist[i][j] = minDist[i][k] + minDist[k][j];
					}
				}
			}
		}

		class Edge
		{
			int fr, to, wt;

			public Edge(int fr, int to, int wt)
			{
				this.fr = fr;
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
