package com.coursera.algorithms.graphSearch_shortestPaths_dS;

import java.io.*;
import java.util.*;

public class SCC implements Runnable
{
	public static void main(String[] args)
	{
		try
		{
			Thread thread = new Thread(null, new SCC(), "SCC", 1 << 29);

			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	@Override public void run()
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);

		try
		{
			in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/coursera/algorithms/graphSearch_shortestPaths_dS/SCCInput.txt"))));

/*			in = new BufferedReader(new InputStreamReader(new FileInputStream(new File
					("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/checker/input.txt"))));
			out = new PrintWriter(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
					+ "Programming/src/com/checker/output.txt"));*/
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
		int v, time;
		int[] order, size;
		boolean[] vis;
		List<Integer>[] adj, rev;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			v = 875714;
//			v = 8;
//			v = Integer.parseInt(in.readLine().split(" ")[0]);
			order = new int[v];
			size = new int[v];
			vis = new boolean[v];
			adj = new List[v];
			rev = new List[v];

			for (int i = 0; i < v; i++)
			{
				adj[i] = new ArrayList<>();
				rev[i] = new ArrayList<>();
			}

			String line;

			while ((line = in.readLine()) != null)
			{
				String[] tok = line.split(" ");
				int u = Integer.parseInt(tok[0]) - 1;
				int v = Integer.parseInt(tok[1]) - 1;

				adj[u].add(v);
				rev[v].add(u);
			}

			for (int i = 0; i < v; i++)
			{
				if (!vis[i])
					findOrder(i);
			}

			Arrays.fill(vis, false);

			for (int i = v - 1; i >= 0; i--)
				if (!vis[order[i]])
					findSCCs(order[i], order[i]);

			PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
			StringBuilder ans = new StringBuilder("");

			for (int i = 0; i < v; i++)
				queue.add(size[i]);

			for (int i = 0; i < 5; i++)
			{
				if (queue.size() == 0)
					ans.append("0,");
				else
					ans.append(queue.poll()).append(",");
			}

			out.println(ans.substring(0, ans.length() - 1));
		}

		void findOrder(int node)
		{
			vis[node] = true;

			for (int x : rev[node])
				if (!vis[x])
					findOrder(x);

			order[time++] = node;
		}

		void findSCCs(int node, int rep)
		{
			vis[node] = true;
			size[rep]++;

			for (int x : adj[node])
				if (!vis[x])
					findSCCs(x, rep);
		}

		Solver(BufferedReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

}

/*

v = 8, e = 14

1 2
2 3
2 5
2 6
6 2
5 6
6 7
3 7
7 3
5 4
6 4
4 8
8 4
7 8

-----------

for the input file, answer :
434821,968,459,313,211
I'm getting :
15525,704,293,228,188

*/
