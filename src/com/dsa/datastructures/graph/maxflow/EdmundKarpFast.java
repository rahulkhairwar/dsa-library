package com.dsa.datastructures.graph.maxflow;

import java.util.*;

/**
 * An implementation of Edmund Karp's algorithm which uses BFS to find augmenting paths for Ford Fulkerson's
 * algorithm to find the max flow in a DIRECTED Network.
 * <br /> Running time : O(VE^2), where V : number of vertices, E : number of edges.
 */
public class EdmundKarpFast
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
		static final int INFINITY = (int) 1e9 << 1;
		int v, e, s, t, flow, maxFlow;
		Edge[] edges;
		List<Integer>[] adj, reverse;
		int[] par, parEdge;
		Scanner in;

		/**
		 *
		 *
		 *
		 *
		 *
		 * WRONG IMPLEMENTATION!!!!!!!!!!!!!!
		 *
		 *
		 *
		 *
		 *
		 */
		void solve()
		{
			System.out.println("Enter the number of nodes :");
			v = in.nextInt();
			System.out.println("Enter the number of edges :");
			e = in.nextInt();
			System.out.println("Enter the source index :");
			s = in.nextInt();
			System.out.println("Enter the sink index :");
			t = in.nextInt();
			maxFlow = 0;
			edges = new Edge[e << 1];
			adj = new ArrayList[v];
			reverse = new ArrayList[v];

			for (int i = 0; i < v; i++)
			{
				adj[i] = new ArrayList<>();
				reverse[i] = new ArrayList<>();
			}

			System.out.println("Enter the edges :");

			int counter = 0;

			for (int i = 0; i < e; i++)
			{
				int from, to, weight;

				from = in.nextInt();
				to = in.nextInt();
				weight = in.nextInt();
				edges[counter] = new Edge(to, weight);
				adj[from].add(counter++);
				edges[counter] = new Edge(from, weight);
				reverse[to].add(counter++);
			}

			while (true)
			{
				boolean[] visited = new boolean[v];
				Queue<Integer> queue = new LinkedList<>();

				flow = 0;
				par = new int[v];
				parEdge = new int[v];
				Arrays.fill(par, -1);
				Arrays.fill(parEdge, -1);
				visited[s] = true;
				queue.add(s);

				while (queue.size() > 0)
				{
					int curr = queue.poll();

					if (curr == t)
						break;	// breaking immediately if we reach the sink.

					Iterator<Integer> iterator = adj[curr].iterator();

					while (iterator.hasNext())
					{
						int ed = iterator.next();
						int to = edges[ed].to;
						int weight = edges[ed].weight;

						if (weight > 0 && !visited[to])
						{
							visited[to] = true;
							queue.add(to);
							par[to] = curr;
							parEdge[to] = ed;
						}
					}
				}

				augment(t, INFINITY);

				if (flow == 0)
					break;	// no more augmenting paths can increase the flow.

				maxFlow += flow;
			}

			System.out.println("The Max Flow possible in the given graph is : " + maxFlow);
		}

		void augment(int node, int minEdge)
		{
			if (node == s)
				flow = minEdge;
			else if (par[node] != -1)
			{
				int ed = parEdge[node];
				int weight = edges[ed].weight;

				augment(par[node], Math.min(minEdge, weight));

				if (ed % 2 == 0)
				{
					edges[ed].weight -= flow;
					edges[ed + 1].weight += flow;    // decreasing flow from the reverse edge.
				}
				else
				{
					edges[ed].weight -= flow;
					edges[ed - 1].weight += flow;
				}
			}
		}

		class Edge
		{
			int to, weight;

			public Edge(int to, int weight)
			{
				this.to = to;
				this.weight = weight;
			}

		}

		public Solver(Scanner in)
		{
			this.in = in;
		}

	}

}

/*

4 5 0 1
0 0 70 30
0 0 25 70
70 25 0 5
30 70 5 0

n = 4, e = 5, s = 0, t = 1
4 5 0 1
0 2 70
0 3 30
2 3 5
2 1 25
3 1 70

*/
