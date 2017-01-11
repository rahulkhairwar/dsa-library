package com.a2onlinejudge.groupcontests.jan02_2017;

import java.io.*;
import java.util.*;

public final class QueriesForNumberOfPalindromes
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
		char[] s;
		int n, q;
		int[][] cnt;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			s = in.readLine().toCharArray();
			n = s.length;
			q = Integer.parseInt(in.readLine());
			pre();

			while (q-- > 0)
			{
				String[] tok = in.readLine().split(" ");
				int left, right;

				left = Integer.parseInt(tok[0]) - 1;
				right = Integer.parseInt(tok[1]) - 1;
				out.println(cnt[left][right]);
			}
		}

		void pre()
		{
			cnt = new int[n][n];

			for (int i = 0; i < n; i++)
				markOdd(i);

			for (int i = 1; i < n; i++)
				markEven(i - 1, i);

			for (int i = 0; i < n; i++)
				for (int j = 1; j < n; j++)
					cnt[i][j] += cnt[i][j - 1];

			for (int i = n - 1; i >= 0; i--)
				pushUp(n - 2, i, cnt[n - 1][i]);
		}

		void markOdd(int center)
		{
			cnt[center][center] = 1;

			for (int left = center - 1, right = center + 1; left >= 0 && right < n; left--, right++)
			{
				if (s[left] != s[right])
					return;

				cnt[left][right] = 1;
			}
		}

		void markEven(int left, int right)
		{
			for (; left >= 0 && right < n; left--, right++)
			{
				if (s[left] != s[right])
					return;

				cnt[left][right] = 1;
			}
		}

		void pushUp(int row, int col, int add)
		{
			if (row == -1)
				return;

			cnt[row][col] += add;
			pushUp(row - 1, col, cnt[row][col]);
		}

		public Solver(BufferedReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

}

/*

caaaba
5
1 1
1 4
2 3
4 6
4 5

*/
