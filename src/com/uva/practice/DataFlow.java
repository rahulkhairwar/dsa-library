package com.uva.practice;

import java.io.*;
import java.util.*;

class DataFlow
{
	public static void main(String[] args)
	{
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader in = new BufferedReader(new FileReader(new File("/Users/rahulkhairwar/Documents/IntelliJ "
//					+ "IDEA Workspace/Competitive Programming/src/com/uva/practice/dataFlow.in")));
			OutputWriter out = new OutputWriter(System.out);
			Solver solver = new Solver(in, out);

			solver.solve();
			in.close();
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	static class Solver
	{
		static final int INFINITY = (int) 1e9;
		int v, e, s, t, d, k;
		long flow, cost;
		int[] par, parEdge;
		Edge[] edges;
		Set<Integer>[] adj;
		BufferedReader in;
		OutputWriter out;

		void solve() throws IOException
		{
			String line;

			while ((line = in.readLine()) != null)
			{
				String[] tokens = line.split(" ");

				v = Integer.parseInt(tokens[0]);
				e = Integer.parseInt(tokens[1]);
				s = 0;
				t = v - 1;
				edges = new Edge[e << 1];
				adj = new HashSet[v];

				for (int i = 0; i < v; i++)
					adj[i] = new HashSet<>();

				int counter = 0;

				for (int i = 0; i < e; i++)
				{
					int from, to, weight;

					tokens = in.readLine().split(" ");
					from = Integer.parseInt(tokens[0]) - 1;
					to = Integer.parseInt(tokens[1]) - 1;
					weight = Integer.parseInt(tokens[2]);
					edges[counter] = new Edge(from, to, weight);
					adj[from].add(counter++);
					edges[counter] = new Edge(to, from, weight);
					adj[to].add(counter++);
				}

				tokens = in.readLine().split(" ");
				d = Integer.parseInt(tokens[0]);
				k = Integer.parseInt(tokens[1]);
				cost = 0;

				for (int i = 0; i < (e << 1); i++)
//					edges[i].weight = k;
					edges[i].flow = k;

				while (true)
				{
					// while there exists a path from s to t, find the shortest one
					long[] dist = new long[v];
					boolean[] visited = new boolean[v];
					PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>()
					{
						@Override public int compare(Integer o1, Integer o2)
						{
							return Long.compare(dist[o1], dist[o2]);
						}
					});

					flow = 0;
					par = new int[v];
					parEdge = new int[v];
					Arrays.fill(par, -1);
					Arrays.fill(parEdge, -1);
					Arrays.fill(dist, INFINITY);
					dist[s] = 0;
					visited[s] = true;
					queue.add(s);

					while (queue.size() > 0)
					{
						int curr = queue.poll();

						if (curr == t)
							break;

						Iterator<Integer> iterator = adj[curr].iterator();

						visited[curr] = false;

						while (iterator.hasNext())
						{
							int ed = iterator.next();
							int to = edges[ed].to;
							long weight = edges[ed].weight;
							long flow = edges[ed].flow;

							// relaxation.
							if (flow > 0 && dist[curr] + weight < dist[to])
							{
								dist[to] = dist[curr] + weight;
								par[to] = curr;
								parEdge[to] = ed;
								queue.add(to);
							}
						}
					}

					if (dist[t] == INFINITY)
						break;

					removeEdges(t, INFINITY);

					if (flow > d)
						flow = d;

					d -= flow;
					cost += flow * dist[t];

					if (cost > 1e15)
					{
						d = 1;

						break;
					}

					if (d == 0)
						break;
				}

				if (d == 0 && cost <= 1e15)
					out.println(cost);
				else
					out.println("Impossible.");
			}
		}

		void removeEdges(int node, long minEdge)
		{
			if (node == s)
				flow = minEdge;
			else if (par[node] != -1)
			{
				int ed = parEdge[node];

				removeEdges(par[node], Math.min(minEdge, edges[ed].flow));
				edges[ed].flow -= flow;
				edges[ed ^ 1].flow += flow;
//				removeEdges(par[node], Math.min(minEdge, edges[ed].weight));
//				edges[ed].weight -= flow;
//				edges[ed ^ 1].weight += flow;
			}
		}

		class Edge
		{
			int from, to;
			long weight, flow;

			public Edge(int from, int to, long weight)
			{
				this.from = from;
				this.to = to;
				this.weight = weight;
			}

		}

		public Solver(BufferedReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	static class OutputWriter
	{
		private PrintWriter writer;

		public OutputWriter(OutputStream stream)
		{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					stream)));
		}

		public OutputWriter(Writer writer)
		{
			this.writer = new PrintWriter(writer);
		}

		public void println(int x)
		{
			writer.println(x);
		}

		public void print(int x)
		{
			writer.print(x);
		}

		public void println(char x)
		{
			writer.println(x);
		}

		public void print(char x)
		{
			writer.print(x);
		}

		public void println(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i] + " ");
		}

		public void println(long x)
		{
			writer.println(x);
		}

		public void print(long x)
		{
			writer.print(x);
		}

		public void println(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i]);
		}

		public void println(float num)
		{
			writer.println(num);
		}

		public void print(float num)
		{
			writer.print(num);
		}

		public void println(double num)
		{
			writer.println(num);
		}

		public void print(double num)
		{
			writer.print(num);
		}

		public void println(String s)
		{
			writer.println(s);
		}

		public void print(String s)
		{
			writer.print(s);
		}

		public void println()
		{
			writer.println();
		}

		public void printSpace()
		{
			writer.print(" ");
		}

		public void printf(String format, Object args)
		{
			writer.printf(format, args);
		}

		public void flush()
		{
			writer.flush();
		}

		public void close()
		{
			writer.close();
		}

	}

}
