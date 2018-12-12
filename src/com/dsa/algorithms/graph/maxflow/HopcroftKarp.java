package com.dsa.algorithms.graph.maxflow;

import java.util.*;

/**
 * Java Program to Implement Hopcroft Algorithm
 **/
public class HopcroftKarp
{
	private final int NIL = 0;
	private final int INF = Integer.MAX_VALUE;
	private List<Integer>[] adj;
	private int[] pair;
	private int[] dist;
	private int cx, cy;

	public static void main(String[] args)
	{
		System.out.println("Hopcroft Algorithm Test\n");
		Scanner scan = new Scanner(System.in);
		HopcroftKarp hc = new HopcroftKarp();

		/* Accept number of edges */
		System.out.println("Enter number of edges\n");

		int E = scan.nextInt();
		int[] x = new int[E];
		int[] y = new int[E];

		hc.cx = 0;
		hc.cy = 0;
		System.out.println("Enter " + E + " x, y coordinates ");

		for (int i = 0; i < E; i++)
		{
			// input 0-based, but used as 1-based.
			x[i] = scan.nextInt();
			y[i] = scan.nextInt();
			// getting the max. node no. in either of the bipartite graph sets.
			hc.cx = Math.max(hc.cx, x[i]);
			hc.cy = Math.max(hc.cy, y[i]);
		}

		hc.cx += 1;
		hc.cy += 1;

		/* make graph with vertices */
		hc.makeGraph(x, y, E);
		System.out.println("\nMatches : " + hc.hopcroftKarp());
	}

	/**
	 * Function to get maximum matching
	 **/
	int hopcroftKarp()
	{
		pair = new int[cx + cy + 1];
		dist = new int[cx + cy + 1];
		int matching = 0;

		while (bfs())
			for (int v = 1; v <= cx; ++v)
				if (pair[v] == NIL && dfs(v))
						matching = matching + 1;

		return matching;
	}

	boolean bfs()
	{
		Queue<Integer> queue = new LinkedList<>();
		System.out.println("Entered bfs()");

		// adding all unmatched nodes of the 1st set in the queue.
		for (int v = 1; v <= cx; ++v)
		{
			// this is an unmatched node.
			if (pair[v] == NIL)
			{
				dist[v] = 0;
				queue.add(v);
			}
			else
				dist[v] = INF;
		}

		// dist[x] = inf denotes a matched node.
		dist[NIL] = INF;

		while (!queue.isEmpty())
		{
			int u = queue.poll();

			// if u is unmatched.
			if (dist[u] < dist[NIL])
			{
				for (int v : adj[u])
				{
					// if v is matched.
					if (dist[pair[v]] == INF)
					{
						dist[pair[v]] = dist[u] + 1;
						queue.add(pair[v]);
					}
				}
			}
			else
				System.out.println("condition false for u : " + u);
		}

		return dist[NIL] != INF;
	}

	boolean dfs(int u)
	{
		if (u != NIL)
		{
			for (int v : adj[u])
			{
				if (dist[pair[v]] == dist[u] + 1 && dfs(pair[v]))
				{
					pair[v] = u;
					pair[u] = v;

					return true;
				}
			}

			dist[u] = INF;

			return false;
		}

		return true;
	}

	/**
	 * Function to make graph with vertices x , y
	 **/
	void makeGraph(int[] x, int[] y, int E)
	{
		adj = new ArrayList[cx + cy + 1];

		for (int i = 0; i < adj.length; ++i)
			adj[i] = new ArrayList<>();

		// + 1 to change from 0-based to 1-based.
		for (int i = 0; i < E; ++i)
			addEdge(x[i] + 1, y[i] + 1);
	}

	void addEdge(int u, int v)
	{
		adj[u].add(cx + v);
		adj[cx + v].add(u);
	}

}

/*

10
0 1
1 0
0 0
3 0
1 4
2 2
2 3
3 4
4 1
4 3

1
1 2

*/
