package com.codeforces.competitions.year2018.round476div2;

import java.io.*;

public class TaskB
{
	public static void main(String[] args)
	{
		new TaskB(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, k;
		char[][] map;
		int[][] cnt;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			n = Integer.parseInt(tok[0]);
			k = Integer.parseInt(tok[1]);
			map = new char[n][];
			cnt = new int[n][n];

			for (int i = 0; i < n; i++)
				map[i] = in.readLine().toCharArray();

			for (int row = 0; row < n; row++)
			{
				for (int i = 0; i + k <= n; i++)
				{
					if (checkHorizontal(row, i))
						add(row, i, i + k - 1);
				}
			}

			for (int col = 0; col < n; col++)
			{
				for (int i = 0; i + k <= n; i++)
				{
					if (checkVertical(col, i))
						addCol(col, i, i + k - 1);
				}
			}

			int max = 0;
			int row = 0;
			int col = 0;

			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < n; j++)
				{
					if (cnt[i][j] > max)
					{
						max = cnt[i][j];
						row = i;
						col = j;
					}
				}
			}

			out.println(row + 1 + " " + (col + 1));
		}

		boolean checkHorizontal(int row, int l)
		{
			for (int i = l, j = 0; j < k; i++, j++)
				if (map[row][i] != '.')
					return false;

			return true;
		}

		boolean checkVertical(int col, int up)
		{
			for (int i = up, j = 0; j < k; i++, j++)
				if (map[i][col] != '.')
					return false;

			return true;
		}

		void addCol(int col, int up, int down)
		{
			for (int i = up; i <= down; i++)
				cnt[i][col]++;
		}

		void add(int row, int l, int r)
		{
			for (int i = l; i <= r; i++)
				cnt[row][i]++;
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

	public TaskB(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskB", 1 << 29);

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

