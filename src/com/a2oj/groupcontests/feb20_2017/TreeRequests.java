package com.a2oj.groupcontests.feb20_2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * This solution didn't pass the time limit. Even the best java solutions for this question took about 3 times of
 * that of the best C++ solutions. So, had to convert the code to C++. Code in the same folder in the file named
 * TreeRequests.cpp.
 */
public final class TreeRequests
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
		int n, q;
		int[] vals;
		String s;
		Node[] nodes;
		List<Integer>[] lists;
		Query[] queries;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			q = in.nextInt();
			vals = new int[n];
			nodes = new Node[n];
			lists = new List[n];
			queries = new Query[q];

			for (int i = 0; i < n; i++)
			{
				nodes[i] = new Node();
				lists[i] = new LinkedList<>();
			}

			for (int i = 1; i < n; i++)
				nodes[in.nextInt() - 1].adj.add(i);

			s = in.next();

			for (int i = 0; i < n; i++)
				vals[i] = 1 << (s.charAt(i) - 'a');

			for (int i = 0; i < q; i++)
			{
				int v = in.nextInt() - 1;
				int h = in.nextInt();

				queries[i] = new Query(h);
				lists[v].add(i);
			}

			dfs(0, 1);

			for (int i = 0; i < q; i++)
			{
				if (queries[i].poss)
					out.println("Yes");
				else
					out.println("No");
			}
		}

		Node dfs(int node, int depth)
		{
			Node a = nodes[node];

			a.depth = depth;

			for (int x : a.adj)
			{
				Node b = dfs(x, depth + 1);

				if (a.map.size() < b.map.size())
				{
					Node temp = a;

					a = b;
					b = temp;
				}

				a.merge(b);
			}

			int x = vals[node];

			a.put(depth, x);

			for (Integer i : lists[node])
				queries[i].poss = isPalindromePossible(a.map.get(queries[i].depth));

			return a;
		}

		boolean isPalindromePossible(Integer val)
		{
			return val == null || Integer.bitCount(val) < 2;
		}

		class Node
		{
			int depth;
			List<Integer> adj;
			Map<Integer, Integer> map;

			void put(int key, int val)
			{
				Integer prev = map.getOrDefault(key, 0);

				map.put(key, prev ^ val);
			}

			void merge(Node other)
			{
				for (Map.Entry<Integer, Integer> entry : other.map.entrySet())
					put(entry.getKey(), entry.getValue());
			}

			Node()
			{
				adj = new LinkedList<>();
				map = new HashMap<>();
			}

		}

		class Query
		{
			int depth;
			boolean poss;

			Query( int depth)
			{
				this.depth = depth;
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

		public String next()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			StringBuilder res = new StringBuilder();

			do
			{
				res.appendCodePoint(c);

				c = read();
			} while (!isSpaceChar(c));

			return res.toString();
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
