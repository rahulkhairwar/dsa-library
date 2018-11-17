package com.codeforces.competitions.year2018.round520div2;

import java.io.*;
import java.util.*;

public class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final long MOD = (long) 1e9 + 7;
		int n, q;
		char[] s;
		int[] arr;
		long[] tree;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			n = Integer.parseInt(tok[0]);
			q = Integer.parseInt(tok[1]);
			s = in.readLine().toCharArray();
			arr = new int[n];
			tree = new long[n << 2];

			for (int i = 0; i < n; i++)
			{
				if (s[i] == '1')
					arr[i] = 1;
			}

			buildTree(1, 0, n - 1);

			while (q-- > 0)
			{
				tok = in.readLine().split(" ");

				int l = Integer.parseInt(tok[0]) - 1;
				int r = Integer.parseInt(tok[1]) - 1;
				long size = (r - l);
				long ones = queryTree(1, 0, n - 1, l, r);
				long zeroes = size + 1 - ones;
				long a = CMath.modPower(2, ones, MOD) - 1;

				if (a < 0)
					a += MOD;

				long x = CMath.modPower(2, zeroes, MOD) - 1;

				if (x < 0)
					x += MOD;

				long b = CMath.mod(a * x, MOD);

				out.println(CMath.mod(a + b, MOD));
			}
		}

		void buildTree(int node, int treeStart, int treeEnd)
		{
			if (treeStart > treeEnd)
				return;

			// i.e., leaf node
			if (treeStart == treeEnd)
			{
				tree[node] = arr[treeStart];

				return;
			}

			int mid = (treeStart + treeEnd) / 2;

			// left child
			buildTree(2 * node, treeStart, mid);
			// right child
			buildTree(2 * node + 1, mid + 1, treeEnd);

//			tree[node] = Math.max(tree[2 * node], tree[2 * node + 1]);
			tree[node] = tree[2 * node] + tree[2 * node + 1];
		}

		long queryTree(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			// if the query range is completely out of the range that this node stores information of
			if (treeStart > treeEnd || treeStart > rangeEnd || treeEnd < rangeStart)
				return 0;

			// if the range that this node holds is completely inside of the qeury range
			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return tree[node];

			int mid;
			long leftChildMax, rightChildMax;

			mid = (treeStart + treeEnd) / 2;
			leftChildMax = queryTree(2 * node, treeStart, mid, rangeStart, rangeEnd);
			rightChildMax = queryTree(2 * node + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			return leftChildMax + rightChildMax;
		}

		public Solver(BufferedReader in, PrintWriter out)
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

	static class CMath
	{
		static long power(long number, long power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			if (power == 1)
				return number;

			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}

		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);
			else
				return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

		static long min(long... arr)
		{
			long min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static long max(long... arr)
		{
			long max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static int max(int... arr)
		{
			int max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

	}

	static class Utils
	{
		static boolean nextPermutation(int[] arr)
		{
			for (int a = arr.length - 2; a >= 0; --a)
			{
				if (arr[a] < arr[a + 1])
				{
					for (int b = arr.length - 1; ; --b)
					{
						if (arr[b] > arr[a])
						{
							int t = arr[a];

							arr[a] = arr[b];
							arr[b] = t;

							for (++a, b = arr.length - 1; a < b; ++a, --b)
							{
								t = arr[a];
								arr[a] = arr[b];
								arr[b] = t;
							}

							return true;
						}
					}
				}
			}

			return false;
		}

	}

	public TaskC(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskC", 1 << 29);

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
			try
			{
				in.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			out.flush();
			out.close();
		}
	}

}

/*

5 3
00000
1 3
1 5
2 4

5 3
10000
1 5
1 3
2 5

*/
