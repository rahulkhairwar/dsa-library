package com.codeforces.practice.medium;

import java.io.*;
import java.util.*;

public final class TavasAndMalekas
{
	public static void main(String[] args)
	{
		new TavasAndMalekas(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final int MOD = (int) 1e9 + 7;
        int n, m;
        char[] p, s;
        int[] positions;
        Node[] tree;
		BufferedReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			n = Integer.parseInt(tok[0]);
			m = Integer.parseInt(tok[1]);
			p = in.readLine().toCharArray();
			s = new char[n];
			positions = new int[m];
			tree = new Node[n << 2];
			tok = in.readLine().split(" ");
			build(1, 0, n - 1);

			for (int i = 0; i < m; i++)
				positions[i] = Integer.parseInt(tok[i]) - 1;

			for (int i = 0; i < m; i++)
				update(1, 0, n - 1, positions[i], positions[i] + p.length - 1, 1);

			int blank = 0;

			for (int i = 0; i < n; i++)
				if (query(1, 0, n - 1, i, i) == 0)
					blank++;

			out.println(CMath.modPower(26, blank, MOD));
		}

		void build(int node, int treeStart, int treeEnd)
		{
			if (treeStart == treeEnd)
			{
//				System.out.println("building nd : " + node);
				tree[node] = new Node();

				return;
			}

			int mid = treeStart + treeEnd >> 1;

			build(node << 1, treeStart, mid);
			build((node << 1) + 1, mid + 1, treeEnd);
			tree[node] = new Node();
		}

		void update(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, int val)
		{
			push(node, treeStart, treeEnd);

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			{
				tree[node].cnt += val;

				if (treeStart != treeEnd)
				{
					int left = node << 1;
					int right = left + 1;

					tree[left].lazy += val;
					tree[right].lazy += val;
				}

				return;
			}

			int mid = treeStart + treeEnd >> 1;
			int left = node << 1;
			int right = left + 1;

			update(left, treeStart, mid, rangeStart, rangeEnd, val);
			update(right, mid + 1, treeEnd, rangeStart, rangeEnd, val);
		}

		int query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			push(node, treeStart, treeEnd);

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return 0;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return tree[node].cnt;

			int mid = treeStart + treeEnd >> 1;
			int lC = query(node << 1, treeStart, mid, rangeStart, rangeEnd);
			int rC = query((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			return lC + rC;
		}

		void push(int node, int treeStart, int treeEnd)
		{
//			System.out.println("pushing nd : " + node);
			Node temp = tree[node];

			if (temp.lazy > 0)
			{
				if (treeStart != treeEnd)
				{
					int left = node << 1;
					int right = left + 1;

					tree[left].lazy += temp.lazy;
					tree[right].lazy += temp.lazy;
				}

				temp.cnt += temp.lazy;
				temp.lazy = 0;
			}
		}

		static class Node
		{
			int cnt, lazy;

		}

		void debug(Object... o)
		{
			System.err.println(Arrays.deepToString(o));
		}

		public Solver(BufferedReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
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

	public TavasAndMalekas(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TavasAndMalekas", 1 << 29);

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
