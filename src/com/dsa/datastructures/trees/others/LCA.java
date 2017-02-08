package com.dsa.datastructures.trees.others;

import java.io.*;
import java.util.*;

/**
 * An implementation to find the Lowest Common Ancester of any 2 vertices of a rooted tree.
 * <br />Running time : O(V logV) for preprocessing, and then O(logV) for each query.
 */
public final class LCA
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
		final int INF = (int) 1e9;
		int v, queries, ctr;
        int[] euler, first, depth, tree;
        List<Integer>[] adj;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			v = in.nextInt();
			euler = new int[2 * v - 1];
			first = new int[v];
			depth = new int[v];
			adj = new List[v];

			for (int i = 0; i < v; i++)
				adj[i] = new ArrayList<>();

			for (int i = 1; i < v; i++)
			{
				int par = in.nextInt() - 1;

				adj[par].add(i);
			}

			int size = 2 * v - 1;

			tree = new int[size << 2];
			Arrays.fill(first, -1);
			dfs(0, 0);
			build(1, 0, size - 1);
			queries = in.nextInt();

			while (queries-- > 0)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				if (first[v] < first[u])
				{
					int temp = u;

					u = v;
					v = temp;
				}

				int x = query(1, 0, size - 1, first[u], first[v]);

				System.out.println("LCA of " + u + " and " + v + " is : " + euler[x]);
			}
		}

		void dfs(int node, int dep)
		{
			euler[ctr] = node;
			first[node] = ctr++;
			depth[node] = dep;

			for (int x : adj[node])
			{
				dfs(x, dep + 1);
				euler[ctr++] = node;
			}
		}

		void build(int node, int treeStart, int treeEnd)
		{
			if (treeStart == treeEnd)
			{
				tree[node] = treeStart;

				return;
			}

			int mid, left, right;

			mid = treeStart + treeEnd >> 1;
			build(node << 1, treeStart, mid);
			build((node << 1) + 1, mid + 1, treeEnd);
			left = tree[node << 1];
			right = tree[(node << 1) + 1];

			if (depth[euler[left]] < depth[euler[right]])
				tree[node] = left;
			else
				tree[node] = right;
		}

		int query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return INF;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return tree[node];

			int mid, left, right;

			mid = treeStart + treeEnd >> 1;
			left = query(node << 1, treeStart, mid, rangeStart, rangeEnd);
			right = query((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			if (left == INF)
				return right;

			if (right == INF)
				return left;

			if (depth[euler[left]] < depth[euler[right]])
				return left;

			return right;
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

5
1 1 2 2
2
4 5
5 3
: 2, 1

28
1 1 1 1 2 6 6 8 8 8 2 2 13 14 15 10 3 4 5 5 5 22 22 5 4 18 18
10
14 28
14 12
23 24
26 19
12 13
7 24
7 17
11 17
16 15
17 10
: 0, 1, 21, 3, 1, 0, 5, 7, 14, 9

*/
