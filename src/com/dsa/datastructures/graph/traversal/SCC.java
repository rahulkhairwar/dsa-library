package com.dsa.datastructures.graph.traversal;

import java.util.*;

/**
 * Program to find all the Strongly Connected Components (SCC's) in a directed graph.
 * <br />Running Time : O(V log V + E) where V : number of vertices, E : number of edges.
 */
public class SCC
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		Solver solver = new Solver(in);

		solver.solve();
		in.close();
	}

	static class Solver
	{
		int v, e, currTime;
		Node[] nodes, reverse;
		List<List<Integer>> sCC;
		Scanner in;

		void solve()
		{
			System.out.println("Enter the number of vertices :");
			v = in.nextInt();
			System.out.println("Enter the number of edges :");
			e = in.nextInt();
			currTime = 1;
			nodes = new Node[v];
			reverse = new Node[v];
			sCC = new ArrayList<>();

			for (int i = 0; i < v; i++)
			{
				nodes[i] = new Node(i);
				reverse[i] = new Node(i);
			}

			System.out.println("Enter the e edges(0-based) :");

			for (int i = 0; i < e; i++)
			{
				int from, to;

				from = in.nextInt();
				to = in.nextInt();
				nodes[from].adj.add(to);
				reverse[to].adj.add(from);
			}

			for (int i = 0; i < v; i++)
				if (!nodes[i].visited)
					dfs(i);

			Arrays.sort(nodes, new Comparator<Node>()
			{
				@Override
				public int compare(Node one, Node two)
				{
					return Integer.compare(two.leavingTime, one.leavingTime);
				}
			});

			for (int i = 0; i < v; i++)
			{
				int curr = nodes[i].index;

				if (!reverse[curr].visited)
				{
					List<Integer> list = new ArrayList<>();

					findSCC(curr, list);
					sCC.add(list);
				}
			}

			System.out.println("All the Strongly Connected Components are :");

			int size = sCC.size();

			for (int i = 0; i < size; i++)
				System.out.println(sCC.get(i));
		}

		void dfs(int node)
		{
			Node temp = nodes[node];
			Iterator<Integer> iterator = temp.adj.iterator();

			temp.visited = true;
			temp.visitingTime = currTime++;

			while (iterator.hasNext())
			{
				int curr = iterator.next();

				if (!nodes[curr].visited)
					dfs(curr);
			}

			temp.leavingTime = currTime++;
		}

		void findSCC(int node, List<Integer> list)
		{
			Node temp = reverse[node];
			Iterator<Integer> iterator = temp.adj.iterator();

			temp.visited = true;
			list.add(node);

			while (iterator.hasNext())
			{
				int curr = iterator.next();

				if (!reverse[curr].visited)
					findSCC(curr, list);
			}
		}

		class Node
		{
			int index, visitingTime, leavingTime;
			boolean visited;
			List<Integer> adj;

			public Node(int index)
			{
				this.index = index;
				this.adj = new ArrayList<>();
			}

		}

		public Solver(Scanner in)
		{
			this.in = in;
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
: {0}, {1, 4, 5}, {2, 6}, {7, 3}

 */
