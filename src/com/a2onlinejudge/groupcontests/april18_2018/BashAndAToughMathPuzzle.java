package com.a2onlinejudge.groupcontests.april18_2018;

import java.io.*;
import java.util.*;

public final class BashAndAToughMathPuzzle
{
	public static void main(String[] args)
	{
		new BashAndAToughMathPuzzle(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, q;
		int[] arr, tree;
		boolean poss;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			arr = in.nextIntArray(n);
			tree = new int[n << 2];
			build(1, 0, n - 1);
			q = in.nextInt();

			for (int i = 0; i < q; i++)
			{
				int type = in.nextInt();

				if (type == 1)
				{
					int left = in.nextInt() - 1;
					int right = in.nextInt() - 1;
					int target = in.nextInt();

					poss = true;
					query(1, 0, n - 1, left, right, true, target);

					if (poss)
						out.println("YES");
					else
						out.println("NO");
				}
				else
				{
					int ind = in.nextInt() - 1;
					int val = in.nextInt();

					update(1, 0, n - 1, ind, ind, val);
				}
			}
		}

		int query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, boolean doSplit, int target)
		{
			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return 0;

			if (doSplit && treeStart >= treeEnd)
				return 0;

			if (!doSplit && treeStart >= rangeStart && treeEnd <= rangeEnd)
				return tree[node];

			int mid = treeStart + treeEnd >> 1;
			int left = node << 1;
			int right = left + 1;
			int l = query(left, treeStart, mid, rangeStart, rangeEnd, false, target);
			int r = query(right, mid + 1, treeEnd, rangeStart, rangeEnd, false, target);

			if (!doSplit)
				return CMath.gcd(l, r);

			if (l % target != 0 && r % target != 0)
				poss = false;
			else if (l % target != 0)
				query(left, treeStart, mid, rangeStart, rangeEnd, true, target);
			else if (r % target != 0)
				query(right, mid + 1, treeEnd, rangeStart, rangeEnd, true, target);

			return 0;
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
			tree[node] = CMath.gcd(tree[left], tree[right]);
		}

		void update(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, int updateValue)
		{
			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return;

			if (treeStart == treeEnd)
			{
				tree[node] = updateValue;

				return;
			}

			int mid = treeStart + treeEnd >> 1;
			int left = node << 1;
			int right = left + 1;

			update(left, treeStart, mid, rangeStart, rangeEnd, updateValue);
			update(right, mid + 1, treeEnd, rangeStart, rangeEnd, updateValue);
			tree[node] = CMath.gcd(tree[left], tree[right]);
		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override public void run()
		{
			try
			{
				solve();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
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

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	static class CMath
	{
		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

	}

	public BashAndAToughMathPuzzle(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "BashAndAToughMathPuzzle", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			in.close();
			out.flush();
			out.close();
		}
	}

}
