package com.a2onlinejudge.groupcontests.jan10_2017;

import java.io.*;
import java.util.*;

public final class ValidSets
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
		static final int mod = (int) 1e9 + 7;
        int d, n;
        boolean[][] vis;
        Node[] nodes;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			d = in.nextInt();
			n = in.nextInt();
			vis = new boolean[n][n];
			nodes = new Node[n];

			for (int i = 0; i < n; i++)
				nodes[i] = new Node(in.nextInt());

			for (int i = 1; i < n; i++)
			{
				int u, v;

				u = in.nextInt() - 1;
				v = in.nextInt() - 1;
				nodes[u].adj.add(v);
				nodes[v].adj.add(u);
			}

			for (int i = 0; i < n; i++)
				nodes[i].cnt = dfs(i, -1, i);

			long ans = 0;

			for (int i = 0; i < n; i++)
				ans = CMath.mod(ans + nodes[i].cnt, mod);

			out.println(ans);
		}

		long dfs(int node, int par, int start)
		{
			long cnt = 1;

			for (int x : nodes[node].adj)
			{
				if (x == par)
					continue;

				if (nodes[x].val >= nodes[start].val && nodes[x].val - nodes[start].val <= d && !vis[x][start])
				{
					cnt = CMath.mod(cnt * (dfs(x, node, start) + 1), mod);
					vis[start][x] = true;
				}
			}

			return cnt;
		}

		class Node
		{
			int val;
			long cnt;
			List<Integer> adj;

			public Node(int val)
			{
				this.val = val;
				adj = new ArrayList<>();
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

	static class CMath
	{
		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

}
