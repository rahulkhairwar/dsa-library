package com.codeforces.competitions.year2017.goodbye2017;

import java.io.*;

public class TaskB
{
	public static void main(String[] args)
	{
		new TaskB(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, m, len;
		char[][] board;
		char[] steps;
		int[] dx, dy;
		int[] dir;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			n = Integer.parseInt(tok[0]);
			m = Integer.parseInt(tok[1]);
			board = new char[n][m];
			dx = new int[]{1, 0, -1, 0};
			dy = new int[]{0, 1, 0, -1};
			dir = new int[]{0, 1, 2, 3};

			for (int i = 0; i < n; i++)
				board[i] = in.readLine().toCharArray();

			steps = in.readLine().toCharArray();
			len = steps.length;

			int startR = -1;
			int startC = -1;

			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++)
				{
					if (board[i][j] == 'S')
					{
						startR = i;
						startC = j;

						break;
					}
				}

			int cnt = 0;

			do
			{
				if (dfs(startR, startC, 0))
					cnt++;
			} while (nextPermutation(dir));

			out.println(cnt);
		}

		boolean dfs(int r, int c, int ind)
		{
			if (r >= n || r < 0 || c >= m || c < 0)
				return false;

			if (board[r][c] == 'E')
				return true;

			if (board[r][c] == '#' || ind == len)
				return false;

			int x = dir[steps[ind] - '0'];

			return dfs(r + dx[x], c + dy[x], ind + 1);
		}

		boolean nextPermutation(int[] arr)
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

