package com.codeforces.icpctraining.learning.dpontrees;

import java.io.*;
import java.util.*;

/**
 * Given a tree T with N nodes, calculate longest path between any two nodes(also known as diameter of tree).
 *
 * Have to check if this code actually works.
 */
public class Problem2
{
	public static void main(String[] args) throws IOException
	{
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);

		Solver solver = new Solver(in, out);
		solver.solve();

		out.flush();

		in.close();
		out.close();
	}

	static class Solver
	{
		int n;
		Node[] tree;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			tree = new Node[n];

			for (int i = 0; i < n; i++)
				tree[i] = new Node();

			for (int i = 1; i < n; i++)
				tree[in.nextInt()].adj.add(i);

			dfs(0);

			int max = 0;

			for (int i = 0; i < n; i++)
				max = Math.max(max, tree[i].crossMax);

			out.println(max);
		}

		void dfs(int node)
		{
			Node temp = tree[node];

			if (temp.adj.size() == 0)
				return;

			Iterator<Integer> iterator = temp.adj.iterator();
			int max, secondMax;

			max = secondMax = 0;

			while (iterator.hasNext())
			{
				int curr = iterator.next();

				dfs(curr);

				int dist = tree[curr].nonCrossMax;

				if (dist >= max)
				{
					secondMax = max;
					max = dist;
				}
				else if (dist > secondMax)
					secondMax = dist;
			}

			if (temp.adj.size() == 1)
				temp.crossMax = max + 1;
			else if (temp.adj.size() == 2)
				temp.crossMax = max + secondMax + 2;

			temp.nonCrossMax = max + 1;
		}

		class Node
		{
			int crossMax, nonCrossMax;
			List<Integer> adj;

			public Node()
			{
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

}

/*

10
0 1 1 2 4 2 0 7 8 7

 */
