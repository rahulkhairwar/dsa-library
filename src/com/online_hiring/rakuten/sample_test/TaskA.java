package com.online_hiring.rakuten.sample_test;

public final class TaskA
{
	public static void main(String[] args)
	{
		Solver solver = new Solver();

		solver.solve();
	}

	static class Solver
	{
		public int solution(int N)
		{
			int cnt = 0;

			for (int i = 0; i <= 99999; i++)
			{
				if (isValid(N, i))
					cnt++;
			}

			return cnt;
		}

		boolean isValid(int n, int x)
		{
			int[] a = new int[10];
			int[] b = new int[10];
			String nn = "" + n;
			String xx = "" + x;

			for (char c : nn.toCharArray())
				a[c - '0']++;

			for (char c : xx.toCharArray())
				b[c - '0']++;

			for (int i = 0; i < 10; i++)
				if (a[i] != b[i])
					return false;

			return true;
		}

		int factSolution(int n)
		{
			if (n == 0)
				return 1;

			int[] map = new int[10];
			String x = "" + n;

			for (char c : x.toCharArray())
				map[c - '0']++;

			int ans = fact(x.length());

			for (int i = 0; i < 10; i++)
				ans /= fact(map[i]);

			if (map[0] > 0)
			{
				int sub = fact(x.length() - 1);

				map[0]--;

				for (int i = 0; i < 10; i++)
					sub /= fact(map[i]);

				ans -= sub;
			}

			return ans;
		}

		public int fact(int x)
		{
			if (x == 0 || x == 1)
				return 1;

			return x * fact(x - 1);
		}

		void solve()
		{
		}

	}

}

/*

12002

*/
