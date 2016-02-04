package com.dsa.datastructures.graph.traversal;

import java.util.*;

/**
 * Created by rahulkhairwar on 29/01/16.
 */
public class BFSLevels
{
	static int[] level;
	static int nodes, edges;
	static List<Integer>[] list;
	static boolean[] visited;
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args)
	{
		System.out.print("Enter the number of nodes you want in the graph : ");
		nodes = in.nextInt();

		System.out.print("\nEnter the number of edges : ");
		edges = in.nextInt();

		createGraph();
		findLevels(0);

		System.out.println("The level of each node in the graph : ");

		for (int i = 0; i < nodes; i++)
			System.out.println(i + " : " + level[i]);
	}

	static void createGraph()
	{
		list = new ArrayList[nodes];

		for (int i = 0; i < edges; i++)
		{
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

	static void findLevels(int node)
	{
		level = new int[nodes];
		visited = new boolean[nodes];

		level[node] = 0;
		visited[node] = true;

		if (list[node] == null)
			return;

		Queue<Integer> queue = new LinkedList<>();
		queue.add(node);

		while (queue.size() > 0)
		{
			int front = queue.poll();
			Iterator<Integer> iterator = list[front].iterator();

			while (iterator.hasNext())
			{
				int curr = iterator.next();

				if (!visited[curr])
				{
					level[curr] = level[front] + 1;
					visited[curr] = true;
					queue.add(curr);
				}
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
