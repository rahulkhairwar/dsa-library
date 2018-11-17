package com.codeforces.practice.medium;

import java.io.*;
import java.util.*;

public final class PythonIndentation
{
	public static void main(String[] args)
	{
		new PythonIndentation(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final int MOD = (int) 1e9 + 7;
		int n;
		char[] arr;
		long[][] dp;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = Integer.parseInt(in.readLine());
			arr = new char[n];
			dp = new long[n + 1][n + 1];

			for (int i = 0; i < n; i++)
				arr[i] = in.readLine().charAt(0);

			for (int i = 0; i <= n; i++)
				Arrays.fill(dp[i], -1);

			dp[0][0] = 1;

			if (arr[0] == 's')
				out.println(find(1, 0));
			else
				out.println(find(1, 1));
		}

		long find(int curr, int backIndents)
		{
			if (backIndents < 0)
				return 0;

			if (curr == n)
				return 1;

			if (dp[curr][backIndents] != -1)
				return dp[curr][backIndents];

			long ans;

			if (arr[curr] == 's')
				ans = find(curr + 1, backIndents);
			else
				ans = find(curr + 1, backIndents + 1);

			if (arr[curr - 1] != 'f')
				ans = CMath.mod(ans + find(curr, backIndents - 1), MOD);

			return dp[curr][backIndents] = ans;
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
		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

	private PythonIndentation(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "PythonIndentation", 1 << 29);

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
