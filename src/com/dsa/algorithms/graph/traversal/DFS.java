package com.dsa.algorithms.graph.traversal;

import java.util.*;

/**
 * Program to perform a Depth First Search on a graph.
 * <br />Running time : O(V + E) where V : number of vertices, E : number of edges.
 */
public final class DFS
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
        int v, e;
		Node[] nodes;
        Scanner in;

		void solve()
		{
			System.out.println("Enter the number of vertices in the graph :");
			v = in.nextInt();
			System.out.println("Enter the number of edges in the graph :");
			e = in.nextInt();

			createGraph();
			dfs(0);
		}

		void createGraph()
		{
			nodes = new Node[v];

			for (int i = 0; i < v; i++)
				nodes[i] = new Node();

			for (int i = 0; i < e; i++)
			{
				int from, to;

				from = in.nextInt() - 1;
				to = in.nextInt() - 1;
				nodes[from].adj.add(to);
				nodes[to].adj.add(from);
			}
		}

		void dfs(int node)
		{
			System.out.print((node + 1) + " ");

			Node temp = nodes[node];
			temp.visited = true;

			Iterator<Integer> iterator = temp.adj.iterator();

			while (iterator.hasNext())
			{
				int curr = iterator.next();

				if (!nodes[curr].visited)
					dfs(curr);
			}
		}

		class Node
		{
			List<Integer> adj;
			boolean visited;

			public Node()
			{
				adj = new ArrayList<>();
			}

		}

        public Solver(Scanner in)
        {
        	this.in = in;
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
: 1 2 9 8 7 5 6 10 11 12 13 14 3 4 15

*/