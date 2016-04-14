package com.dsa.datastructures.graph.traversal;

import java.util.*;

/**
 * Program to find all the Strongly Connected Components (SCC) in a directed graph.
 */
public class SCC
{
	static int numberOfNodes, numberOfEdges, currentTime, sCCCount;
	static Node[] nodes, reverse;
	static List<Set<Integer>> sCC;
	static Scanner in;

	public static void main(String[] args)
	{
		in = new Scanner(System.in);

		System.out.println("Enter the number of nodes : ");
		numberOfNodes = in.nextInt();
		System.out.println("Enter the number of edges : ");
		numberOfEdges = in.nextInt();

		createGraph();

		for (int i = 0; i < numberOfNodes; i++)
			if (!nodes[i].visited)
				dfs(i);

		Arrays.sort(nodes, new Comparator<Node>()
		{
			@Override public int compare(Node o1, Node o2)
			{
				return Integer.compare(o2.leavingTime, o1.leavingTime);
			}
		});

		sCC = new ArrayList<>();

		for (int i = 0; i < numberOfNodes; i++)
		{
			if (!reverse[i].visited)
			{
				sCC.add(new HashSet<>());
				sCC.get(sCCCount).add(i);
				findSCCs(i);
				sCCCount++;
			}
		}

		System.out.println("The number of SCCs in the graph are : " + sCCCount + ", and they are : ");

		for (int i = 0; i < sCCCount; i++)
			System.out.println(sCC.get(i).toString());
	}

	static void createGraph()
	{
		nodes = new Node[numberOfNodes];
		reverse = new Node[numberOfNodes];
		System.out.println("Enter the edges : ");

		for (int i = 0; i < numberOfEdges; i++)
		{
			int from, to;

			from = in.nextInt();
			to = in.nextInt();

			if (nodes[from] == null)
				nodes[from] = new Node();

			nodes[from].adj.add(to);

			if (nodes[to] == null)
				nodes[to] = new Node();

			if (reverse[to] == null)
				reverse[to] = new Node();

			reverse[to].adj.add(from);

			if (reverse[from] == null)
				reverse[from] = new Node();
		}
	}

	static void dfs(int node)
	{
		Node temp = nodes[node];

		temp.visited = true;
		temp.visitingTime = currentTime++;

		Iterator<Integer> iterator = temp.adj.iterator();

		while (iterator.hasNext())
		{
			int curr = iterator.next();

			if (!nodes[curr].visited)
			{
				nodes[curr].parent = node;

				dfs(curr);
			}
		}

		temp.leavingTime = currentTime++;
	}

	static void findSCCs(int node)
	{
		Node temp = reverse[node];
		Iterator<Integer> iterator = temp.adj.iterator();

		temp.visited = true;

		while (iterator.hasNext())
		{
			int curr = iterator.next();

			if (!reverse[curr].visited)
			{
				sCC.get(sCCCount).add(curr);
				findSCCs(curr);
			}
		}
	}

	static class Node
	{
		int parent, visitingTime, leavingTime;
		boolean visited;
		List<Integer> adj;

		public Node()
		{
			parent = -1;
			adj = new ArrayList<>();
		}

	}

}

/*

8 14
0 1
1 2
1 4
1 5
5 1
4 5
5 6
2 6
6 2
4 3
5 3
3 7
7 3
6 7

 */
