package com.spoj.practice.classic;

import java.io.*;
import java.util.*;

class GSS1
{
	public static void main(String[] args)
	{
		IR in = new IR(System.in);
		Sl sl = new Sl(in);
		sl.sl();
	}

	static class Sl
	{
		int INF = (int) 1e6, n, m;
		int[] arr;
		Node[] tree;
		Node InfNode = new Node(-INF, -INF, -INF, -INF);
		IR in;
		PrintWriter out = new PrintWriter(System.out);

		void sl()
		{
			n = in.nI();
			arr = new int[n];
			for (int i = 0; i < n; i++)
				arr[i] = in.nI();
			m = in.nI();
			tree = new Node[n << 2];
			build(1, 0, n - 1);
			for (int i = 0; i < m; i++)
			{
				int left, right;
				left = in.nI() - 1;
				right = in.nI() - 1;

				out.println(query(1, 0, n - 1, left, right).ans);
			}

			out.flush();
		}

		void build(int node, int treeStart, int treeEnd)
		{
			if (treeStart == treeEnd)
			{
				tree[node] = new Node(arr[treeStart], arr[treeStart], arr[treeStart], arr[treeStart]);

				return;
			}

			int mid = treeStart + treeEnd >> 1;
			Node left, right;
			long ans, sum, prefix, suffix;

			build(node << 1, treeStart, mid);
			build((node << 1) + 1, mid + 1, treeEnd);
			left = tree[node << 1];
			right = tree[(node << 1) + 1];
			ans = Math.max(left.ans, Math.max(right.ans, Math.max(left.suffix + right.prefix, Math.max(left.sum + right
					.prefix, left.suffix + right.sum))));
			sum = left.sum + right.sum;
			prefix = Math.max(left.prefix, left.sum + right.prefix);
			suffix = Math.max(right.suffix, left.suffix + right.sum);
			tree[node] = new Node(ans, sum, prefix, suffix);
		}

		Node query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return InfNode;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return tree[node];

			int mid = treeStart + treeEnd >> 1;
			Node left, right;

			left = query(node << 1, treeStart, mid, rangeStart, rangeEnd);
			right = query((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			return merge(left, right);
		}

		Node merge(Node left, Node right)
		{
			long ans, sum, prefix, suffix;

			sum = left.sum + right.sum;
			prefix = Math.max(left.prefix, left.sum + right.prefix);
			suffix = Math.max(right.suffix, left.suffix + right.sum);
			ans = Math.max(prefix, Math.max(suffix, Math.max(left.ans, Math.max(right.ans, left.suffix + right
					.prefix))));

			return new Node(ans, sum, prefix, suffix);
		}

		class Node
		{
			long ans, sum, prefix, suffix;

			public Node(long ans, long sum, long prefix, long suffix)
			{
				this.ans = ans;
				this.sum = sum;
				this.prefix = prefix;
				this.suffix = suffix;
			}

		}

		public Sl(IR in)
		{
			this.in = in;
		}
	}

	static class IR
	{
		private InputStream st;
		private byte[] b = new byte[1024];
		private int cC, nC;

		public IR(InputStream stream)
		{
			this.st = stream;
		}

		public int rd()
		{
			if (nC == -1)
				throw new InputMismatchException();

			if (cC >= nC)
			{
				cC = 0;
				try
				{
					nC = st.read(b);
				}
				catch (IOException e)
				{
					throw new InputMismatchException();
				}
				if (nC <= 0)
					return -1;
			}

			return b[cC++];
		}

		public int nI()
		{
			int c = rd();

			while (iSC(c))
				c = rd();

			int sgn = 1;

			if (c == '-')
			{
				sgn = -1;
				c = rd();
			}

			int res = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				res *= 10;
				res += c & 15;

				c = rd();
			} while (!iSC(c));

			return res * sgn;
		}

		public boolean iSC(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public void close()
		{
			try
			{
				st.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}

/*

5
-1 2 3 -5 8
5
1 5
1 3
3 5
4 5
5 5

8
-1 5 10 -1 20 30 -1 -2
1
1 8

*/
