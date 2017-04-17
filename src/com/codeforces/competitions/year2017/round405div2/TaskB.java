package com.codeforces.competitions.year2017.round405div2;

import java.io.*;
import java.util.*;

public final class TaskB
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
        Node[] nodes;
        boolean[] vis;
        Set<Integer>[] adj;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			nodes = new Node[n];
			vis = new boolean[n];
			adj = new Set[n];

			for (int i = 0; i < n; i++)
			{
				adj[i] = new HashSet<>();
				nodes[i] = new Node(i);
			}

			for (int i = 0; i < m; i++)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				union(nodes[u], nodes[v]);
				adj[u].add(v);
				adj[v].add(u);
			}

			Set<Integer> set = new HashSet<>();

			for (int i = 0; i < n; i++)
				set.add(findParent(nodes[i]).key);

			boolean poss = true;

			for (int x : set)
			{
				long cnt = dfs(x);
				long size = nodes[x].size;

				poss &= (cnt / 2 == size * (size - 1) / 2);
			}

			if (poss)
				out.println("YES");
			else
				out.println("NO");
		}

		long dfs(int node)
		{
			long cnt = adj[node].size();

			vis[node] = true;

			for (int x : adj[node])
				if (!vis[x])
					cnt += dfs(x);

			return cnt;
		}

		void union(Node one, Node two)
		{
			Node parentOne, parentTwo;

			parentOne = findParent(one);
			parentTwo = findParent(two);

			if (parentOne.parent.key == parentTwo.parent.key)
				return;

			if (parentOne.height > parentTwo.height)
			{
				parentTwo.parent = parentOne;
				parentOne.size += parentTwo.size;
			}
			else if (parentOne.height < parentTwo.height)
			{
				parentOne.parent = parentTwo;
				parentTwo.size += parentOne.size;
			}
			else
			{
				parentTwo.parent = parentOne;
				parentOne.size += parentTwo.size;
				parentOne.height++;
			}
		}

		Node findParent(Node temp)
		{
			if (temp.key == temp.parent.key)
				return temp;

			return temp.parent = findParent(temp.parent);
		}

		class Node
		{
			int key, height, size;
			Node parent;

			public Node(int key)
			{
				this.key = key;
				size = 1;
				height = 0;
				parent = this;
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
