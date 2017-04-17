package com.codeforces.practice.medium.year2017;

import java.io.*;
import java.util.*;

public final class BankHacking
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
    	static final int INF = (int) 1e9 + 5;
        int n;
        int[] strengths, neighbourCnt, temp, maxNeighbour;
        long max;
        List<Integer>[] adj;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			strengths = in.nextIntArray(n);
			neighbourCnt = new int[n];
			temp = new int[n];
			maxNeighbour = new int[n];
			adj = new List[n];
			Arrays.fill(maxNeighbour, -INF);

			for (int i = 0; i < n; i++)
				adj[i] = new ArrayList<>();

			for (int i = 1; i < n; i++)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				adj[u].add(v);
				adj[v].add(u);
				neighbourCnt[u]++;
				neighbourCnt[v]++;
				temp[u]++;
				temp[v]++;
				maxNeighbour[u] = Math.max(maxNeighbour[u], strengths[v]);
				maxNeighbour[v] = Math.max(maxNeighbour[v], strengths[u]);
			}

			max = -INF;

			int root = 0;

			for (int i = 1; i < n; i++)
			{
				if (strengths[i] > strengths[root])
					root = i;
			}

			dfs(root, -1, 0);

			out.println(max);

/*			TreeSet<Integer> set = new TreeSet<>(new Comparator<Integer>()
			{
				@Override public int compare(Integer o1, Integer o2)
				{
					if (strengths[o1] == strengths[o2])
						return Integer.compare(neighbourCnt[o1] - temp[o1], neighbourCnt[o2] - temp[o2]);

					return Integer.compare(strengths[o2], strengths[o1]);
				}
			});

			for (int i = 0; i < n; i++)
				set.add(i);

			long req = -INF;

			for (int i = 0; i < n; i++)
			{
				int curr = set.pollFirst();
//				System.out.println("removed : " + (curr + 1) + " with strength : " + strengths[curr]);

				boolean hasOfflineNeighbour = (i < 1);

				if (neighbourCnt[curr] != temp[curr])
					hasOfflineNeighbour = true;

				if (!hasOfflineNeighbour)
				{
				}

				for (int x : adj[curr])
				{
					boolean removed = set.remove(x);

					strengths[x]++;

					if (removed)
						set.add(x);

					temp[x]--;
				}

				req = Math.max(req, strengths[curr]);
			}

			out.println(req);*/
		}

		void dfs(int node, int par, int level)
		{
			if (level == 1)
				strengths[node]++;
			else if (level > 1)
				strengths[node] += 2;

			max = Math.max(max, strengths[node]);

			for (int x : adj[node])
			{
				if (x != par)
				{
//					strengths[x]++;
					dfs(x, node, level + 1);
				}
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
