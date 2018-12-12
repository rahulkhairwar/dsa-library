package com.dsa.algorithms.graph.traversal;

import java.awt.Point;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Algorithm to find out all bridges
 * (def. : <a href="https://en.wikipedia.org/wiki/Bridge_(graph_theory)">wikipedia</a>,
 * <a href="https://www.youtube.com/watch?v=SFFEc8DbO0Y">youtube</a>)
 * in a graph.
 * <br /><br />
 * Practice Question
 * <a href="https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=9&page=show_problem&problem=737">Link</a>
 */
public class Bridges
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		OutputWriter out = new OutputWriter(System.out);
		Solver solver = new Solver();

		solver.solve(in, out);
		out.flush();
		in.close();
		out.close();
	}

	static class Solver
	{
		static int n, currTime;
		static Node[] nodes;
		static List<Point> bridges;    // bridges.
		Scanner in;
		OutputWriter out;

		/**
		 * Prints all bridges in a graph.
		 *
		 * @param in  a Scanner object to read input
		 * @param out an OutputWriter object to write output
		 */
		void solve(Scanner in, OutputWriter out)
		{
			this.in = in;
			this.out = out;

			n = in.nextInt();
			in.nextLine();

			createGraph();

			bridges = new ArrayList<>();
			currTime = 1;

			for (int i = 0; i < n; i++)
				if (!nodes[i].visited)
					dfs(i);

			int size = bridges.size();

			out.println(size + " critical links");

			bridges.sort(new Comparator<Point>()
			{
				@Override
				public int compare(Point one, Point two)
				{
					if (one.x == two.x)
						return Integer.compare(one.y, two.y);

					return Integer.compare(one.x, two.x);
				}
			});

			for (int i = 0; i < size; i++)
				out.println(bridges.get(i).x + " - " + bridges.get(i).y);

			out.println();
		}

		void createGraph()
		{
			nodes = new Node[n];

			for (int i = 0; i < n; i++)
			{
				String input = in.nextLine();
				String[] line = input.split(" ");

				line[1] = line[1].split("()")[0];

				int curr = Integer.parseInt(line[0]);

				Node temp = nodes[curr] = new Node(-1, -1, -1, new ArrayList<>(), false);

				for (int j = 2; j < line.length; j++)
					temp.adj.add(Integer.parseInt(line[j]));
			}
		}

		void dfs(int node)
		{
			Node temp = nodes[node];
			Iterator<Integer> iterator = temp.adj.iterator();

			temp.visited = true;
			temp.visitingTime = temp.low = currTime++;

			while (iterator.hasNext())
			{
				int curr = iterator.next();

				if (!nodes[curr].visited)
				{
					nodes[curr].parent = node;

					dfs(curr);

					temp.low = Math.min(temp.low, nodes[curr].low);

					if (temp.visitingTime < nodes[curr].low)
					{
						if (node < curr)
							bridges.add(new Point(node, curr));
						else
							bridges.add(new Point(curr, node));
					}
				}
				else if (temp.parent != curr)
					temp.low = Math.min(temp.low, nodes[curr].visitingTime);
			}
		}

		class Node
		{
			int parent, visitingTime, low;
			List<Integer> adj;
			boolean visited;

			public Node(int parent, int visitingTime, int low, List<Integer> adj, boolean visited)
			{
				this.parent = parent;
				this.visitingTime = visitingTime;
				this.low = low;
				this.adj = adj;
				this.visited = visited;
			}

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

		public void flush()
		{
			writer.flush();
		}

		public void close()
		{
			writer.close();
		}

	}

	static class CMath
	{
		static long power(long number, long power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			if (power == 1)
				return number;

			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}

		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);
			else
				return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

}

/*

9
0 (1) 1
1 (3) 0 2 6
2 (3) 1 7 3
3 (3) 2 8 4
4 (2) 3 5
5 (2) 4 6
6 (2) 5 1
7 (1) 2
8 (1) 3

 */
