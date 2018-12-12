package com.dsa.algorithms.graph.maxflow;

import java.io.*;
import java.util.*;

/**
 * An implementation of Edmund Karp's algorithm which uses BFS to find augmenting paths for Fold Fulkerson's
 * algorithm to find the max flow in an UNDIRECTED network.
 * <br />Running time : O(VE^2), where V : number of vertices, E : number of edges.
 */
public class EdmundKarpUndirected
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		static final int INFINITY = (int) 2e9;
		int v, e, s, t, flow, maxFlow;
		Edge[] edges;
		int[] par, parEdge;
		List<Integer>[] adj;
		Scanner in;
		PrintWriter out;

		void solve()
		{
			System.out.println("Enter the number of vertices :");
			v = in.nextInt();
			System.out.println("Enter the source index :");
			s = in.nextInt() - 1;
			System.out.println("Enter the sink index :");
			t = in.nextInt() - 1;
			System.out.println("Enter the number of edges :");
			e = in.nextInt();
			maxFlow = 0;
			edges = new Edge[e << 2];
			adj = new ArrayList[v];

			for (int i = 0; i < v; i++)
				adj[i] = new ArrayList<>();

			System.out.println("Enter the edges :");
			int counter = 0;

			for (int i = 0; i < e; i++)
			{
				int from, to, weight;

				from = in.nextInt() - 1;
				to = in.nextInt() - 1;
				weight = in.nextInt();
				edges[counter] = new Edge(to, weight, counter + 2);
				adj[from].add(counter++);
				edges[counter] = new Edge(from, 0, -1);
				adj[to].add(counter++);
				edges[counter] = new Edge(from, weight, counter - 2);
				adj[to].add(counter++);
				edges[counter] = new Edge(to, 0, -1);
				adj[from].add(counter++);
			}

			while (true)
			{
				int[] dist = new int[v];
				Queue<Integer> queue = new LinkedList<>();

				flow = 0;
				par = new int[v];
				parEdge = new int[v];
				Arrays.fill(dist, INFINITY);
				Arrays.fill(par, -1);
				Arrays.fill(parEdge, -1);
				dist[s] = 0;
				queue.add(s);

				while (queue.size() > 0)
				{
					int curr = queue.poll();

					if (curr == t)
						break;    // breaking immediately if reached the sink.

					for (int ed : adj[curr])
					{
						int to = edges[ed].to;
						int weight = edges[ed].weight;

						if (weight > 0 && dist[to] == INFINITY)
						{
							dist[to] = dist[curr] + 1;
							queue.add(to);
							par[to] = curr;
							parEdge[to] = ed;
						}
					}
				}

				augment(t, INFINITY);

				if (flow == 0)
					break;

				maxFlow += flow;
			}
		}

		void augment(int node, int minEdge)
		{
			if (node == s)
				flow = minEdge;
			else if (par[node] != -1)
			{
				int ed = parEdge[node];
				int weight = edges[ed].weight;
				int oppEdge = edges[ed].oppEdge;

				augment(par[node], Math.min(minEdge, weight));

				edges[ed].weight -= flow;
				edges[ed ^ 1].weight += flow;

				if (oppEdge >= 0)
					edges[oppEdge].weight -= flow;
			}
		}

		class Edge
		{
			int to, weight, oppEdge;

			public Edge(int to, int weight, int oppEdge)
			{
				this.to = to;
				this.weight = weight;
				this.oppEdge = oppEdge;
			}

		}

		public Solver(Scanner in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

}
