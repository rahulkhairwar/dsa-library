package com.hackerearth.competitions.hack_a_heart;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class TaskC implements Runnable
{
	public static void main(String[] args)
	{
		try
		{
			Thread thread = new Thread(null, new TaskC(), "TaskC", 1 << 29);

			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	@Override public void run()
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
		final int INF = (int) 1e8;
		int n, q, ctr;
		int[] justice, maxJustice, euler, first, depth, tree;
		List<Integer>[] adj;
		InputReader in;
		PrintWriter out;

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		void solve()
		{
			n = in.nextInt();
			justice = in.nextIntArray(n);
			maxJustice = new int[n];
			euler = new int[2 * n - 1];
			first = new int[n];
			depth = new int[n];
			adj = new List[n];

			for (int i = 0; i < n; i++)
				adj[i] = new ArrayList<>();

			for (int i = 1; i < n; i++)
			{
				int par = in.nextInt() - 1;

				adj[par].add(i);
			}

			int size = 2 * n - 1;

			Arrays.fill(first, (int) 1e9);
			dfs(0, 0, 0);
			tree = new int[size << 2];
			build(1, 0, size - 1);
			q = in.nextInt();

			while (q-- > 0)
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

				out.println(maxJustice[euler[x]] + 1);
			}
		}

		void dfs(int node, int dep, int maxJ)
		{
			if (justice[node] > justice[maxJ])
				maxJ = node;

			maxJustice[node] = maxJ;
			euler[ctr] = node;
			first[node] = ctr++;
			depth[node] = dep;

			for (int x : adj[node])
			{
				dfs(x, dep + 1, maxJ);
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
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

}
