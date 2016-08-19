package com.campusplacements.directi.round1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Third
{
	static long infinity = (long) 1e16;
	static int t, n, m, x, y, d, height[][], num[][];
	static Node[] nodes;
	static long val[][];
	static boolean[][] visited;
	static InputReader in = new InputReader();

	public static void main(String[] args) throws NumberFormatException, IOException
	{
		solve();	// Dijkstra
//		solve2();	// DFS
	}

	/**
	 * Solution using Dijkstra's Algorithm.
	 *
	 * @throws IOException
	 */
	static void solve() throws IOException
	{
		t = in.nextInt();

		while (t-- > 0)
		{
			String[] tokens = in.in.readLine().split(" ");

			n = in.nextInt(tokens[0]);
			m = in.nextInt(tokens[1]);
			x = in.nextInt(tokens[2]) - 1;
			y = in.nextInt(tokens[3]) - 1;
			d = in.nextInt(tokens[4]);

			height = new int[n][m];
			num = new int[n][m];
			nodes = new Node[n * m];

			int counter = 0;

			for (int i = 0; i < n; i++)
			{
				tokens = in.in.readLine().split(" ");

				for (int j = 0; j < m; j++)
				{
					height[i][j] = in.nextInt(tokens[j]);
					nodes[counter] = new Node(counter);
					num[i][j] = counter++;
				}
			}

			createGraph();

			long dist = dijkstra();

			if (x == 0 && y == 0)
				System.out.println(0);
			else if (dist == infinity)
				System.out.println(-1);
			else
				System.out.println(dist - 1);
		}
	}

	private static long dijkstra()
	{
		PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>()
		{
			@Override public int compare(Node o1, Node o2)
			{
				return Long.compare(o1.dist, o2.dist);
			}
		});

		for (int i = 1; i < n * m; i++)
			nodes[i].dist = infinity;

		nodes[0].dist = 0;

		Iterator<Integer> iterator = nodes[0].adj.iterator();

		while (iterator.hasNext())
		{
			int curr = iterator.next();

			nodes[curr].dist = 1;
			queue.add(nodes[curr]);
		}

		while (queue.size() > 0)
		{
			Node curr = queue.poll();

			iterator = nodes[curr.index].adj.iterator();

			while (iterator.hasNext())
			{
				int next = iterator.next();

				if (curr.dist + 1 < nodes[next].dist)
				{
					nodes[next].dist = curr.dist + 1;
					queue.add(nodes[next]);
				}
			}
		}

		return nodes[num[x][y]].dist;
	}

	private static void createGraph()
	{
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < m; j++)
			{
				int from, to;

				from = num[i][j];

				if (j != m - 1)
				{
					if (Math.abs(height[i][j] - height[i][j + 1]) <= d)
					{
						to = num[i][j + 1];

						nodes[from].adj.add(to);
						nodes[to].adj.add(from);
					}
				}

				if (i != n - 1)
				{
					if (Math.abs(height[i][j] - height[i + 1][j]) <= d)
					{
						to = num[i + 1][j];

						nodes[from].adj.add(to);
						nodes[to].adj.add(from);
					}
				}
			}
		}
	}

	static class Node
	{
		int index;
		long dist;
		List<Integer> adj;

		public Node(int index)
		{
			this.index = index;
			adj = new ArrayList<>();
		}

	}

	/**
	 * Solution using DFS.
	 *
	 * @throws IOException
	 */
	private static void solve2() throws IOException
	{
		t = in.nextInt();

		while (t-- > 0)
		{
			String[] tok = in.in.readLine().split(" ");

			n = in.nextInt(tok[0]);
			m = in.nextInt(tok[1]);
			x = in.nextInt(tok[2]) - 1;
			y = in.nextInt(tok[3]) - 1;
			d = in.nextInt(tok[4]);

			height = new int[n][m];
			val = new long[n][m];
			visited = new boolean[n][m];

			for (int i = 0; i < n; i++)
				Arrays.fill(val[i], infinity);

			for (int i = 0; i < n; i++)
			{
				String[] tokens = in.in.readLine().split(" ");

				for (int j = 0; j < m; j++)
					height[i][j] = in.nextInt(tokens[j]);
			}

			visited[0][0] = true;

			long min = Math.min(Math.abs(height[0][1] - height[0][0]) <= d ? dfs(0, 1) : infinity,
					Math.abs(height[1][0] - height[0][0]) <= d ? dfs(1, 0) : infinity);

			if (x == 0 && y == 0)
				System.out.println(0);
			else if (min == infinity)
				System.out.println(-1);
			else
				System.out.println(min - 1);
		}
	}

	static long dfs(int r, int c)
	{
		if (visited[r][c])
			return val[r][c];

		visited[r][c] = true;

		if (r == x && c == y)
		{
			val[r][c] = 1;

			return 1;
		}

		long left, right, up, down;

		if (c == 0)
			left = infinity;
		else
		{
			if (Math.abs(height[r][c] - height[r][c - 1]) <= d)
				left = dfs(r, c - 1);
			else
				left = infinity;
		}

		if (c == m - 1)
			right = infinity;
		else
		{
			if (Math.abs(height[r][c] - height[r][c + 1]) <= d)
				right = dfs(r, c + 1);
			else
				right = infinity;
		}

		if (r == 0)
			up = infinity;
		else
		{
			if (Math.abs(height[r][c] - height[r - 1][c]) <= d)
				up = dfs(r - 1, c);
			else
				up = infinity;
		}

		if (r == n - 1)
			down = infinity;
		else
		{
			if (Math.abs(height[r][c] - height[r + 1][c]) <= d)
				down = dfs(r + 1, c);
			else
				down = infinity;
		}

		long min = Math.min(left, Math.min(right, Math.min(up, down)));

		if (min < infinity)
			val[r][c] = min + 1;
		else
			val[r][c] = infinity;

		return val[r][c];
	}

	static class InputReader
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int nextInt() throws NumberFormatException, IOException
		{
			return Integer.parseInt(in.readLine());
		}

		int nextInt(String s)
		{
			return Integer.parseInt(s);
		}

		long nextLong(String s)
		{
			return Long.parseLong(s);
		}

	}

}

/*

1
7 7 6 3 5
2 4 5 6 2 3 2
10 10 10 2 2 2 2
10 10 10 2 10 10 10
10 10 10 2 10 10 10
10 10 1 2 10 10 10
10 10 3 10 10 10 10
10 10 10 10 10 10 10
: 8

1
7 7 6 3 5
2 4 5 6 2 3 2
10 10 10 10 10 10 2
10 10 10 2 2 2 2
10 10 10 2 10 10 10
10 10 1 2 10 10 10
10 10 3 10 10 10 10
10 10 10 10 10 10 10
: 14

1
7 7 1 1 5
2 4 5 6 2 3 2
10 10 10 2 2 2 2
10 10 10 2 10 10 10
10 10 10 2 10 10 10
10 10 1 2 10 10 10
10 10 3 10 10 10 10
10 10 10 10 10 10 10
: 0

1
7 7 1 2 5
2 4 5 6 2 3 2
10 10 10 2 2 2 2
10 10 10 2 10 10 10
10 10 10 2 10 10 10
10 10 1 2 10 10 10
10 10 3 10 10 10 10
10 10 10 10 10 10 10
: 0

1
7 7 2 1 5
2 4 5 6 2 3 2
10 10 10 2 2 2 2
10 10 10 2 10 10 10
10 10 10 2 10 10 10
10 10 1 2 10 10 10
10 10 3 10 10 10 10
10 10 10 10 10 10 10
: 4

1
6 7 6 7 1
3 4 4 4 4 4 4
2 10 4 4 4 4 4
2 10 4 4 4 4 4
2 10 4 4 4 4 4
2 10 4 4 4 4 4
2 2 2 2 2 2 3
: 10

*/
