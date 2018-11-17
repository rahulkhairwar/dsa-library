package com.codeforces.competitions.year2018.round459div2;

import java.io.*;
import java.util.*;

public class TaskD
{
	public static void main(String[] args)
	{
		new TaskD(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, m;
		List<Integer>[] adj;
		Edge[] edges;
		int[][][] dp;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			n = Integer.parseInt(tok[0]);
			m = Integer.parseInt(tok[1]);
			adj = new List[n];
			edges = new Edge[m];
			dp = new int[n][n][26];

			for (int i = 0; i < n; i++)
				adj[i] = new ArrayList<>();

			for (int i = 0; i < m; i++)
			{
				tok = in.readLine().split(" ");

				int from = Integer.parseInt(tok[0]) - 1;
				int to = Integer.parseInt(tok[1]) - 1;
				int ch = tok[2].charAt(0) - 'a';

				edges[i] = new Edge(from, to, ch);
				adj[from].add(i);
			}

			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					Arrays.fill(dp[i][j], -1);

			for (int i = 0; i < n; i++, out.println())
			{
				for (int j = 0; j < n; j++)
				{
					if (find(i, j, 0) == 1)
						out.print('A');
					else
						out.print('B');
				}
			}
		}

		int find(int a, int b, int prevChar)
		{
			if (dp[a][b][prevChar] != -1)
				return dp[a][b][prevChar];

			int ans = 0;

			for (int ed : adj[a])
			{
				if (edges[ed].ch >= prevChar && find(b, edges[ed].to, edges[ed].ch) == 0)
				{
					ans = 1;

					break;
				}
			}

			return dp[a][b][prevChar] = ans;
		}

		class Edge
		{
			int from, to, ch;

			Edge(int from, int to, int ch)
			{
				this.from = from;
				this.to = to;
				this.ch = ch;
			}

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

	public TaskD(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskD", 1 << 29);

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

