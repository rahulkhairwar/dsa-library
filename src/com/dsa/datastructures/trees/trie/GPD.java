package com.dsa.datastructures.trees.trie;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public final class GPD
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
		int n, q, total;
		int[] val;
		Node[] trie;
		List<Integer>[] adj;
		Map<Integer, Integer> num;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			int lim = (int) 3e5 + 5;
			n = in.nextInt();    // no. of nodes
			q = in.nextInt();    // no. of queries
			val = new int[lim];    // encryption key array
			trie = new Node[lim];    // trie node array
			adj = new List[lim];    // adjacency list
			num = new HashMap<>();    // map to store (nodeId->idInTrieArray) pairs

			for (int i = 0; i < lim; i++)
				adj[i] = new ArrayList<>();

			getNum(0);    // root
			val[0] = in.nextInt();    // root's encryption key

			for (int i = 1; i < n; i++)
			{
				int u = getNum(0);
				int v = getNum(0);

				val[u] = in.nextInt();
				adj[u].add(v);
				adj[v].add(u);
			}

			dfs(0, -1);

			int lastAnswer = 0;
			int fullMask = (int) ((1L << 31) - 1);

			while (q-- > 0)
			{
				int t = in.nextInt() ^ lastAnswer;

				if (t == 0)
				{
					int u = getNum(lastAnswer);
					int v = getNum(lastAnswer);
					int x = in.nextInt() ^ lastAnswer;

					trie[v] = insert(trie[u], x);
				}
				else
				{
					int u = getNum(lastAnswer);
					int x = in.nextInt() ^ lastAnswer;
					int minAns = getMin(trie[u], x);
					int maxAns = getMin(trie[u], x ^ fullMask) ^ fullMask;

					lastAnswer = minAns ^ maxAns;
					out.println(minAns + " " + maxAns);
				}
			}
		}

		Node insert(Node node, int x)
		{
			if (node.depth == -1)
				return node;

			Node q = node.copy();

			int c = (x >> node.depth) & 1;

			if (q.child[c] == null)
				q.child[c] = new Node(q.depth - 1);

			q.child[c] = insert(q.child[c], x);

			return q;
		}

		int getMin(Node p, int x)
		{
			int ans = 0;

			for (int i = 30; i >= 0; i--)
			{
				int c = (x >> i) & 1;

				if (p.child[c] == null)
				{
					c ^= 1;
					ans += 1 << i;
				}

				p = p.child[c];
			}

			return ans;
		}

		void dfs(int node, int last)
		{
			if (last == -1)
				trie[node] = new Node(30);
			else
				trie[node] = trie[last];

			trie[node] = insert(trie[node], val[node]);

			for (int i = 0; i < adj[node].size(); i++)
				if (adj[node].get(i) != last)
					dfs(adj[node].get(i), node);
		}

		int getNum(int lastAnswer)
		{
			int x = in.nextInt() ^ lastAnswer;

			num.computeIfAbsent(x, value -> ++total);

			return num.get(x) - 1;
		}

		class Node
		{
			int depth;
			Node[] child;

			Node copy()
			{
				Node node = new Node(depth);

				node.child[0] = child[0];
				node.child[1] = child[1];

				return node;
			}

			Node(int depth)
			{
				this.depth = depth;
				child = new Node[2];
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
				}
				catch (IOException e)
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
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

}

/*

6 4
1 2
5 1 3
2 1 4
3 2 5
4 2 1
6 3 3
1 4 2
6 0 12 0
7 12 7
4 0 7

*/
