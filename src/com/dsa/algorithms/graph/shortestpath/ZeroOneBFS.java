package com.dsa.algorithms.graph.shortestpath;

import java.util.*;

/**
 * This class takes the number of nodes, number of edges and edge details (from, to and edge weight which is either 0 or 1)
 * as input and finds the shortest distance to all nodes from node 0(can be changed).
 * <p>
 * Created by rahulkhairwar on 29/01/16.
 */
public class ZeroOneBFS
{
	static final int infinity = Integer.MAX_VALUE;
	static int nodes, edges;
	static int[] dist;
	static ArrayList<Edge>[] list;
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args)
	{
		System.out.print("Enter the number of nodes you want in the graph : ");
		nodes = in.nextInt();

		System.out.print("\nEnter the number of edges : ");
		edges = in.nextInt();

		createGraph();
		zeroOneBFS(0);

		System.out.println("The shortest path to all nodes from node 0 is : ");

		for (int i = 0; i < nodes; i++)
			System.out.println(i + " : " + dist[i]);
	}

	static void createGraph()
	{
		list = new ArrayList[nodes];

		for (int i = 0; i < nodes; i++)
			list[i] = new ArrayList<>();

		for (int i = 0; i < edges; i++)
		{
			int from, to, weight;

			from = in.nextInt();
			to = in.nextInt();
			weight = in.nextInt();
			list[from].add(new Edge(to, weight));
			list[to].add(new Edge(from, weight));
		}
	}

	static void zeroOneBFS(int source)
	{
		dist = new int[nodes];
		for (int i = 0; i < nodes; i++)
			dist[i] = infinity;
		dist[source] = 0;
		Deque<Integer> deque = new ArrayDeque<>();
		deque.add(source);

		while (deque.size() > 0)
		{
			int front = deque.poll();
			for (Edge curr : list[front])
			{
				if (dist[front] + curr.weight < dist[curr.to])
				{
					dist[curr.to] = dist[front] + curr.weight;
					if (curr.weight == 0)
						deque.addFirst(curr.to);
					else
						deque.addLast(curr.to);
				}
			}
		}
	}

	static class Edge
	{
		int to, weight;

		public Edge(int to, int weight)
		{
			this.to = to;
			this.weight = weight;
		}

	}

}

/*

5
6
0 1 1
0 3 0
0 2 1
1 4 0
3 4 0
2 3 0
ans : 0, 0, 0, 0, 0

 */
