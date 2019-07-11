package com.a2oj.ladder.CodeforcesDiv2C;

import java.io.*;
import java.util.Arrays;

public class Problem33
{
	public static void main(String[] args)
	{
		new Problem33(System.in, System.out);
	}

	private static class Solver implements Runnable
	{
		int n, m, x, y;
		char[][] map;
		int[] blackCnt, cum;
		long[][][] dp;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			n = Integer.parseInt(tok[0]);
			m = Integer.parseInt(tok[1]);
			x = Integer.parseInt(tok[2]);
			y = Integer.parseInt(tok[3]);
			map = new char[n][];

			for (int i = 0; i < n; i++)
				map[i] = in.readLine().toCharArray();

			blackCnt = new int[m];
			cum = new int[m];
			blackCnt[0] = cum[0] = getBlackCnt(0);

			for (int i = 1; i < m; i++)
			{
				blackCnt[i] = getBlackCnt(i);
				cum[i] = cum[i - 1] + blackCnt[i];
			}

			dp = new long[m + 1][m + 1][2];

			for (int i = 0; i <= m; i++)
				for (int j = 0; j <= m; j++)
					Arrays.fill(dp[i][j], -1);

			out.println(Math.min(find(0, 0, 0), find(0, 0, 1)));
		}

		long find(int ind, int continuousColourCnt, int prevColour)
		{
			if (continuousColourCnt > y)
				return dp[ind][continuousColourCnt][prevColour] = Integer.MAX_VALUE;

			if (ind == m - 1)
			{
				if (continuousColourCnt < y)
				{
					if (continuousColourCnt >= x - 1)
						return prevColour == 0 ? blackCnt[ind] : n - blackCnt[ind];

					return Integer.MAX_VALUE;
				}
				else if (continuousColourCnt == y && x == 1)
					return prevColour == 0 ? n - blackCnt[ind] : blackCnt[ind];

				return Integer.MAX_VALUE;
			}

			if (dp[ind][continuousColourCnt][prevColour] != -1)
				return dp[ind][continuousColourCnt][prevColour];

			long min = Integer.MAX_VALUE;
			int add = (prevColour == 0 ? blackCnt[ind] : n - blackCnt[ind]);
			int addOther = n - add;

			if (continuousColourCnt < y)
			{
				if (continuousColourCnt >= x)
					min = Math.min(min, addOther + find(ind + 1, 1, prevColour ^ 1));

				min = Math.min(min, add + find(ind + 1, continuousColourCnt + 1, prevColour));
			}

			if (continuousColourCnt == y)
				min = Math.min(min, addOther + find(ind + 1, 1, prevColour ^ 1));

			return dp[ind][continuousColourCnt][prevColour] = min;
		}

		int getBlackCnt(int col)
		{
			int blacks = 0;

			for (int i = 0; i < n; i++)
				if (map[i][col] == '#')
					blacks++;

			return blacks;
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

	private Problem33(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "Solver", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

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
