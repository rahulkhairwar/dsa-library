package com.a2oj.groupcontests.feb14_2017;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class PFASTInc
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
		int n, m;
		int[] pair;
		int[][] dp;
		boolean done;
		boolean[][] notConn;
		BufferedReader in;
		PrintWriter out;

		/**
		 * Solution using bitmask dp. Slower than brute force subset check.
		 * <br />Complexity : O(n^3 + n * 2^n) ~ O(n * 2^n)
		 * @throws IOException if an I/O error occurs
		 */
		void solve() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			n = Integer.parseInt(tok[0]);
			m = Integer.parseInt(tok[1]);
			notConn = new boolean[n][n];
			pair = new int[n];
			dp = new int[n][1 << n];

			String[] names = new String[n];
			Map<String, Integer> map = new HashMap<>();
			int ctr = 0;

			for (int i = 0; i < n; i++)
			{
				names[i] = in.readLine();
				map.put(names[i], ctr++);
			}

			for (int i = 0; i < m; i++)
			{
				tok = in.readLine().split(" ");

				int x = map.get(tok[0]);
				int y = map.get(tok[1]);

				pair[x] |= (1 << y);
				pair[y] |= (1 << x);
				notConn[x][y] = notConn[y][x] = true;
			}

			for (int i = 0; i < n; i++)
				Arrays.fill(dp[i], -1);

			int total = find(0, 0);
			String[] ans = new String[total];
			int mask = 0;

			out.println(total);
			ctr = 0;
			total--;
			done = true;

			for (int i = 0; i < n; i++)
			{
				if (find(i + 1, mask | (1 << i)) == total && isValid(mask | (1 << i)))
				{
					ans[ctr++] = names[i];
					mask |= (1 << i);
					total--;
				}
			}

			Arrays.sort(ans);

			for (String s : ans)
				out.println(s);
		}

		int find(int curr, int mask)
		{
			if (curr == n - 1)
			{
				if ((mask & pair[curr]) == 0)
					return 1;

				return 0;
			}

			if (curr == n)
				return 0;

			if (mask == (1 << n) - 1)
				return 0;

			if (dp[curr][mask] != -1)
				return dp[curr][mask];

			int max = 0;

			if ((mask & pair[curr]) == 0)
				max = Math.max(max, 1 + find(curr + 1, mask | (1 << curr)));

			max = Math.max(max, find(curr + 1, mask));

			return dp[curr][mask] = max;
		}

		boolean isValid(int mask)
		{
			for (int i = 0; i < n; i++)
			{
				if ((mask & (1 << i)) == 0)
					continue;

				for (int j = i + 1; j < n; j++)
					if ((mask & (1 << j)) > 0 && notConn[i][j])
						return false;
			}

			return true;
		}

		/**
		 * Brute for solution, checking all subsets.
		 * <br />Complexity : O(n^2 * 2^n)
		 * @throws IOException if an I/O error occurs
		 */
		void solve2() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			n = Integer.parseInt(tok[0]);
			m = Integer.parseInt(tok[1]);
			notConn = new boolean[n][n];

			String[] names = new String[n];
			Map<String, Integer> map = new HashMap<>();

			for (int i = 0; i < n; i++)
			{
				names[i] = in.readLine();
				map.put(names[i], i);
			}

			for (int i = 0; i < m; i++)
			{
				tok = in.readLine().split(" ");

				int x = map.get(tok[0]);
				int y = map.get(tok[1]);

				notConn[x][y] = true;
				notConn[y][x] = true;
			}

			int max = 0;

			for (int i = 1; i < (1 << n); i++)
				if (Integer.bitCount(i) > Integer.bitCount(max) && isValid(i))
					max = i;

			int tot = Integer.bitCount(max);
			String[] ans = new String[tot];
			int ctr = 0;

			out.println(tot);

			for (int i = 0; i < n; i++)
			{
				if ((max & (1 << i)) > 0)
					ans[ctr++] = names[i];
			}

			Arrays.sort(ans);

			for (String s : ans)
				out.println(s);
		}

		public Solver(BufferedReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

}
