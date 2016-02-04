package com.dsa.datastructures.graph.traversal;

import java.util.*;

/**
 * Created by rahulkhairwar on 28/01/16.
 */
public class BFS
{
	static int nodes, edges;
	static List<Integer>[] list;
	static boolean[] visited;
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args)
	{
		System.out.println("Enter the number of nodes in the graph : ");
		nodes = in.nextInt();

		System.out.println("Enter the number of edges : ");
		edges = in.nextInt();

		createGraph();

		bfs(0);
	}

	static void createGraph()
	{
		list = new ArrayList[nodes];

		for (int i = 0; i < edges; i++)
		{
			//System.out.println("Enter the details for edge " + (i + 1));

			int from, to;

			from = in.nextInt() - 1;
			to = in.nextInt() - 1;

			if (list[from] == null)
				list[from] = new ArrayList<>();

			list[from].add(to);

			if (list[to] == null)
				list[to] = new ArrayList<>();

			list[to].add(from);
		}
	}

	static void bfs(int node)
	{
		if (list[node] == null)
		{
			System.out.print(node + " ");

			return;
		}

		visited = new boolean[nodes];
		Queue<Integer> queue = new LinkedList<>();

		queue.add(node);

		while (queue.size() > 0)
		{
			int front = queue.poll();

			if (visited[front])
				continue;

			System.out.print(front + " ");

			visited[front] = true;
			Iterator<Integer> iterator = list[front].iterator();

			while (iterator.hasNext())
			{
				int curr = iterator.next();

				if (!visited[curr])
					queue.add(curr);
			}
		}
	}

}

/*

15
15
1 2
1 3
2 9
2 14
3 4
3 14
3 15
9 8
9 5
9 13
8 7
5 6
5 10
5 11
5 12

 */
