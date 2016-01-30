package com.hackerrank.practice.easy;

import java.util.*;

/**
 * Created by rahulkhairwar on 30/01/16.
 */
public class DijkstraShortestReach2
{
	static final int infinity = Integer.MAX_VALUE;
	static int t, nodes, edges;
	static int[] distance;
	static List<Edge>[] list;
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args)
	{
		t = in.nextInt();

		while (t-- > 0)
		{
			nodes = in.nextInt();
			edges = in.nextInt();

			createGraph();

			int start = in.nextInt() - 1;

			findShortestPaths(start);

			for (int i = 0; i < nodes; i++)
			{
				if (i == start)
					continue;
				else
				{
					if (distance[i] != infinity)
						System.out.print(distance[i] + " ");
					else
						System.out.print(-1 + " ");
				}
			}

			System.out.println();
		}
	}

	static void createGraph()
	{
		list = new ArrayList[nodes];

		for (int i = 0; i < edges; i++)
		{
			int from, to, weight;

			from = in.nextInt() - 1;
			to = in.nextInt() - 1;
			weight = in.nextInt();

			if (list[from] == null)
				list[from] = new ArrayList<>();

			list[from].add(new Edge(to, weight));

			if (list[to] == null)
				list[to] = new ArrayList<>();

			list[to].add(new Edge(from, weight));
		}
	}

	static void findShortestPaths(int node)
	{
		distance = new int[nodes];

		for (int i = 0; i < nodes; i++)
			distance[i] = infinity;

		distance[node] = 0;

		PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(new Node(node, 0));

		while (priorityQueue.size() > 0)
		{
			Node min = priorityQueue.poll();
			Iterator<Edge> iterator = list[min.node].iterator();

			while (iterator.hasNext())
			{
				Edge curr = iterator.next();

				if (distance[min.node] + curr.weight < distance[curr.to])
				{
					distance[curr.to] = distance[min.node] + curr.weight;
					priorityQueue.add(new Node(curr.to, distance[curr.to]));
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

	static class Node implements Comparable<Node>
	{
		int node, shortestPathWeight;

		public Node(int node, int shortestPathWeight)
		{
			this.node = node;
			this.shortestPathWeight = shortestPathWeight;
		}

		@Override
		public int compareTo(Node o)
		{
			return Integer.compare(shortestPathWeight, o.shortestPathWeight);
		}

	}

}
