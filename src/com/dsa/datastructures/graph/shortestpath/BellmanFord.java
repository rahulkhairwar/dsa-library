package com.dsa.datastructures.graph.shortestpath;

import java.util.*;

/**
 * An implementation of Bellman Ford Algorithm.
 * <br />
 * Hackerrank Question <a href="https://www.hackerrank.com/challenges/dijkstrashortreach">link</a>
 * <br />
 * P.S. : Storing the Graph as a Graph object with edges having an in-a relationship
 * with the graph is much faster than storing the graph as Adjacency List of edges.
 */
public class BellmanFord
{
	static final int INFINITY = Integer.MAX_VALUE;
	static int nodes, edges;
	static int[] distance, parent;
	static Graph graph;
	static List<Edge>[] list;
	static Scanner in;

	public static void main(String[] args)
	{
		in = new Scanner(System.in);

//		solve();
		int t = in.nextInt();

		while (t-- > 0)
		{
//			list();
			graph();

			System.out.println();
		}
	}

	static void list()
	{
		nodes = in.nextInt();
		edges = in.nextInt();

		createList();

		int start = in.nextInt() - 1;

		calculateShortestPathsUsingList(start);

		for (int i = 0; i < nodes; i++)
		{
			if (i == start)
				;
			else
			{
				if (distance[i] == INFINITY)
					System.out.print(-1 + " ");
				else
					System.out.print(distance[i] + " ");
			}
		}
	}

	static void graph()
	{
		nodes = in.nextInt();
		edges = in.nextInt();

		createGraph();

		int start = in.nextInt() - 1;

		calculateShortestPathsUsingGraph(start);

		for (int i = 0; i < nodes; i++)
		{
			if (i == start)
				continue;
			else
			{
				if (distance[i] == INFINITY)
					System.out.print(-1 + " ");
				else
					System.out.print(distance[i] + " ");
			}
		}
	}

	static void solve()
	{
		System.out.println("Enter the number of nodes in the graph : ");
		nodes = in.nextInt();
		System.out.println("Enter the number of edges in the graph : ");
		edges = in.nextInt();

		createList();
		createGraph();

		long start, afterList, end;

		start = System.currentTimeMillis();

		calculateShortestPathsUsingList(0);
		afterList = System.currentTimeMillis();

		calculateShortestPathsUsingGraph(0);
		end = System.currentTimeMillis();

		System.out.println("Time taken for List : " + (afterList - start) + "\nand for Graph : " + (end - afterList));
	}

	static void createList()
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

	static void createGraph()
	{
		graph = new Graph(nodes, edges);

		for (int i = 0; i < 2 * edges; i += 2)
		{
			int from, to, weight;

			from = in.nextInt() - 1;
			to = in.nextInt() - 1;
			weight = in.nextInt();

			graph.edges[i].from = from;
			graph.edges[i].to = to;
			graph.edges[i].weight = weight;

			graph.edges[i + 1].from = to;
			graph.edges[i + 1].to = from;
			graph.edges[i + 1].weight = weight;
		}
	}

	static void calculateShortestPathsUsingList(int start)
	{
		distance = new int[nodes];
		parent = new int[nodes];

		Arrays.fill(distance, INFINITY);
		Arrays.fill(parent, -1);
		distance[start] = 0;

		for (int i = nodes - 1; i >= 0; i--)
		{
			for (int j = 0; j < nodes; j++)
			{
				if (list[j] == null)
					continue;

				Iterator<Edge> iterator = list[j].iterator();

				while (iterator.hasNext())
				{
					Edge curr = iterator.next();

					if (distance[j] < INFINITY && distance[j] + curr.weight < distance[curr.toNode])
					{
						distance[curr.toNode] = distance[j] + curr.weight;
						parent[curr.toNode] = j;
					}
				}
			}
		}
	}

	static void calculateShortestPathsUsingGraph(int start)
	{
		distance = new int[nodes];

		Arrays.fill(distance, INFINITY);
		distance[start] = 0;

		for (int i = 1; i < nodes; i++)
		{
			for (int j = 0; j < 2 * edges; j++)
			{
				if (distance[graph.edges[j].from] < INFINITY && distance[graph.edges[j].from] + graph.edges[j].weight <
						distance[graph.edges[j].to])
				{
					distance[graph.edges[j].to] = distance[graph.edges[j].from] + graph.edges[j].weight;
					graph.parent[graph.edges[j].to] = graph.edges[j].from;
				}
			}
		}
	}

	static class Graph
	{
		int v, e;
		int[] parent;
		Edge[] edges;

		public Graph(int v, int e)
		{
			this.v = v;
			this.e = e;

			parent = new int[v];
			edges = new Edge[2 * e];

			for (int i = 0; i < v; i++)
				parent[i] = -1;

			for (int i = 0; i < 2 * e; i++)
				edges[i] = new Edge();
		}

		class Edge
		{
			int from, to, weight;

			public Edge()
			{
				from = to = weight = -1;
			}

		}

	}

	static class Edge
	{
		int toNode, weight;

		public Edge(int toNode, int weight)
		{
			this.toNode = toNode;
			this.weight = weight;
		}

	}

}
