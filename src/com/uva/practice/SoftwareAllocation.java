package com.uva.practice;

import java.io.*;
import java.util.*;

public final class SoftwareAllocation
{
	public static void main(String[] args) throws FileNotFoundException
	{
		Scanner in = new Scanner(new File(
				"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
						+ "Programming/src/com/uva/practice/software.in"));
		OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver(in, out);
		solver.solve();

		out.flush();

		in.close();
		out.close();
	}

	static class Solver
	{
		static final int INFINITY = (int) 1e7;
		int v, s, t, target, flow, maxFlow;
		Edge[] edges;
		List<Integer>[] adj;
		int[] par, parEdge;
		Scanner in;
		OutputWriter out;

		void solve()
		{
			// source as node 0
			// applications A...Z as nodes 1...26
			// computers 0...9 as nodes 27...36
			// sink as node 37
			v = 38;
			s = 0;
			t = 37;

			outer: do
			{
				target = maxFlow = 0;
				edges = new Edge[300];
				adj = new ArrayList[v];

				for (int i = 0; i < v; i++)
					adj[i] = new ArrayList<>();

				int counter = 0;

				while (in.hasNext())
				{
					String[] tokens = in.nextLine().split(" ");

					if (tokens.length < 2)
						break;

					int curr = tokens[0].charAt(0) - 'A' + 1;
					int capacity = tokens[0].charAt(1) - '0';

					target += capacity;

					// adding a new edge from the source to the current application
					edges[counter] = new Edge(curr, capacity);
					adj[0].add(counter++);
					// adding a back edge from the current application to the source
					edges[counter] = new Edge(0, 0);
					adj[curr].add(counter++);

					int len = tokens[1].length();

					// adding new edges from the current application to all computers it can run on and back edges
					// from those computers to the application
					for (int i = 0; i < len - 1; i++)
					{
						int x = tokens[1].charAt(i) - '0' + 27;

						edges[counter] = new Edge(x, 1);
						adj[curr].add(counter++);
						edges[counter] = new Edge(curr, 0);
						adj[x].add(counter++);
					}
				}

				for (int i = 27; i < 37; i++)
				{
					edges[counter] = new Edge(37, 1);
					adj[i].add(counter++);
					edges[counter] = new Edge(i, 0);
					adj[37].add(counter++);
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
						Iterator<Integer> iterator = adj[curr].iterator();

						if (curr == t)
							break;

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
						break;

					maxFlow += flow;
				}

				if (maxFlow != target)
					out.println("!");
				else
				{
					StringBuilder ans = new StringBuilder("__________");

					for (int i = 1; i <= 26; i++)
					{
						Iterator<Integer> iterator = adj[i].iterator();

						while (iterator.hasNext())
						{
							int ed = iterator.next();
							int to = edges[ed].to;
							int weight = edges[ed].weight;

							if (weight == 0 && to > i)
								ans.setCharAt(to - 27, (char) (i + 'A' - 1));
						}
					}

					out.println(ans.toString());
				}
			} while (in.hasNext());
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
					// back edge will be in (ed + 1)
					edges[ed].weight -= flow;
					edges[ed + 1].weight += flow;
				}
				else
				{
					// back edge will be in (ed - 1)
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

		public Solver(Scanner in, OutputWriter out)
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
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
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

/*

A4 01234;
Q1 5;
P4 56789;

A4 01234;
Q1 5;
P5 56789;

A1 01;
B2 023;
C2 35;
D1 4;

answers :
AAAA_QPPPP
!
BABCDC____


 */
