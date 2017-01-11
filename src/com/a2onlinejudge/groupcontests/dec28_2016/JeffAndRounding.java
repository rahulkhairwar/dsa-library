package com.a2onlinejudge.groupcontests.dec28_2016;

import java.io.*;
import java.util.*;

public final class JeffAndRounding
{
	public static void main(String[] args)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(in, out);

		try
		{
			solver.solve();
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		out.flush();
		out.close();
	}

	static class Solver
	{
		int n;
		int[] arr, cum, suff;
		int[][] dp;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = Integer.parseInt(in.readLine());
			arr = new int[n << 1];
			cum = new int[n << 1];
			suff = new int[n << 1];

			String[] tok = in.readLine().split(" ");

			for (int i = 0; i < n << 1; i++)
			{
				arr[i] = Integer.parseInt(tok[i].substring(tok[i].indexOf('.') + 1, tok[i].length()));
				cum[i] = arr[i];
				suff[i] = (1000 - arr[i]) % 1000;
			}

			for (int i = (n << 1) - 2; i >= 0; i--)
			{
				cum[i] += cum[i + 1];
				suff[i] += suff[i + 1];
			}

			dp = new int[n << 1][n + 1];

			for (int i = 0; i < n << 1; i++)
				Arrays.fill(dp[i], -1);

			double ans = find(0, n);

			out.printf("%.3f", (ans < 0 ? -ans : ans) / 1000);
		}

		int find(int ind, int floorsRemaining)
		{
			if (ind == n << 1)
			{
				if (floorsRemaining > 0)
					return (int) 1e7;

				return 0;
			}

			if (dp[ind][floorsRemaining] != -1)
				return dp[ind][floorsRemaining];

			if (floorsRemaining == 0)
				return dp[ind][floorsRemaining] = -suff[ind];

			// we can round this number to it's floor, or it's ceiling.
			int roundToFloor = find(ind + 1, floorsRemaining - 1) + arr[ind];
			int roundToCeil = find(ind + 1, floorsRemaining) - (1000 - arr[ind]) % 1000;

			if (Math.abs(roundToFloor) < Math.abs(roundToCeil))
				return dp[ind][floorsRemaining] = roundToFloor;
			else
				return dp[ind][floorsRemaining] = roundToCeil;
		}

		public Solver(BufferedReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

}

/*

1
1.250 3.500

3
0.674 0.142 0.000 0.000 0.000 0.000

*/
