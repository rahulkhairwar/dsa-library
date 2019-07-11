package com.a2oj.groupcontests.feb08_2017;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public final class CircularRMQ
{
	public static void main(String[] args)
	{
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(System.out);
			Solver solver = new Solver(in, out);

			solver.solve();
			in.close();
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	static class Solver
	{
		final long INFINITY = (long) 1e18;
		int n, q;
		long[] arr, tree, lazy;
		BufferedReader in;
		PrintWriter out;

		public Solver(BufferedReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		void solve() throws IOException
		{
			n = Integer.parseInt(in.readLine());
			arr = new long[n];
			tree = new long[n << 2];
			lazy = new long[n << 2];

			String[] tok = in.readLine().split(" ");

			for (int i = 0; i < n; i++)
				arr[i] = Integer.parseInt(tok[i]);

			build(1, 0, n - 1);
			q = Integer.parseInt(in.readLine());

			while (q-- > 0)
			{
				tok = in.readLine().split(" ");
				int l = Integer.parseInt(tok[0]);
				int r = Integer.parseInt(tok[1]);

				if (tok.length == 3)
				{
					// update op.
					if (l > r)
					{
						int val = Integer.parseInt(tok[2]);

						update(1, 0, n - 1, 0, r, val);
						update(1, 0, n - 1, l, n - 1, val);
					}
					else
						update(1, 0, n - 1, l, r, Integer.parseInt(tok[2]));
				}
				else
				{
					// min query op.
					if (l > r)
						out.println(Math.min(query(1, 0, n - 1, 0, r), query(1, 0, n - 1, l, n - 1)));
					else
						out.println(query(1, 0, n - 1, l, r));
				}
			}
		}

		void build(int node, int treeStart, int treeEnd)
		{
			if (treeStart == treeEnd)
			{
				tree[node] = arr[treeStart];

				return;
			}

			int mid = treeStart + treeEnd >> 1;
			int left = node << 1;
			int right = left + 1;

			build(left, treeStart, mid);
			build(right, mid + 1, treeEnd);
			tree[node] = Math.min(tree[left], tree[right]);
		}

		long query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			push(node, treeStart, treeEnd);

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return INFINITY;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return tree[node];

			int mid = treeStart + treeEnd >> 1;

			return Math.min(query(node << 1, treeStart, mid, rangeStart, rangeEnd),
					query((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd));
		}

		void update(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, int val)
		{
			push(node, treeStart, treeEnd);

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			{
				if (treeStart != treeEnd)
				{
					lazy[node << 1] += val;
					lazy[(node << 1) + 1] += val;
				}

				tree[node] += val;

				return;
			}

			int mid = treeStart + treeEnd >> 1;
			int left = node << 1;
			int right = left + 1;

			update(left, treeStart, mid, rangeStart, rangeEnd, val);
			update(right, mid + 1, treeEnd, rangeStart, rangeEnd, val);
			tree[node] = Math.min(tree[left], tree[right]);
		}

		void push(int node, int treeStart, int treeEnd)
		{
			if (lazy[node] != 0)
			{
				if (treeStart != treeEnd)
				{
					lazy[node << 1] += lazy[node];
					lazy[(node << 1) + 1] += lazy[node];
				}

				tree[node] += lazy[node];
				lazy[node] = 0;
			}
		}

	}

}
