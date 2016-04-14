package com.uva.practice;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by rahulkhairwar on 02/04/16.
 */
public class RevolCFaeLoN
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver();

		solver.solve(1, in, out);

		out.flush();

		in.close();
		out.close();
	}

	static class Solver
	{
		int n, m, currentTime, bridges;
		Node[] nodes;
		int[] bridgeCount;
		int connectedComponents;
		Scanner in;
		OutputWriter out;

		void solve(int testNumber, Scanner in, OutputWriter out)
		{
			this.in = in;
			this.out = out;

			while (in.hasNextInt())
			{
				n = in.nextInt();
				m = in.nextInt();

				currentTime = 0;
				bridges = 0;
				bridgeCount = new int[n];
				connectedComponents = 0;

				createGraph();

				for (int i = 0; i < n; i++)
				{
					if (!nodes[i].visited)
					{
						dfs(i);
						connectedComponents++;
					}
				}

				for (int i = 0; i < connectedComponents; i++)
					bridgeCount[i] -= 2;

				int answer = connectedComponents;
				int remaining = 0;

				for (int i = 0; i < connectedComponents; i++)
				{
					if (bridgeCount[i] > 0)
						remaining += bridgeCount[i];
				}

				answer += Math.ceil(remaining / 2d);
				out.println(answer);
			}
		}

		void createGraph()
		{
			nodes = new Node[n];

			for (int i = 0; i < n; i++)
				nodes[i] = new Node();

			for (int i = 0; i < m; i++)
			{
				int from, to;

				from = in.nextInt() - 1;
				to = in.nextInt() - 1;

				nodes[from].adj.add(to);
				nodes[to].adj.add(from);
			}
		}

		public void dfs(int node)
		{
			Node temp = nodes[node];

//			System.out.println("node : " + node);
			temp.visited = true;
			temp.visitingTime = temp.low = currentTime++;

			Iterator<Integer> iterator = temp.adj.iterator();
			boolean isArticulationPoint = false;

			while (iterator.hasNext())
			{
				int curr = iterator.next();

				if (!nodes[curr].visited)
				{
					temp.childCount++;
					nodes[curr].parent = node;

					dfs(curr);

					temp.low = Math.min(temp.low, nodes[curr].low);

					if (temp.visitingTime < nodes[curr].low)
					{
						if (nodes[curr].bridges == 0)
						{
							temp.bridges++;
							bridges++;
							isArticulationPoint = true;
							bridgeCount[connectedComponents]++;
						}
					}
				}
				else if (temp.parent != curr)
					temp.low = Math.min(temp.low, nodes[curr].visitingTime);
			}

//			System.out.println("** leaving node : " + node);
		}

		static class Node
		{
			int parent, visitingTime, low, childCount, bridges;
			boolean visited;
			List<Integer> adj;

			public Node()
			{
				parent = -1;
				adj = new ArrayList<>();
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

9 8
1 2
1 3
2 3
2 9
3 4
4 5
6 7
6 8



0 1
0 2
1 2
1 8
2 3
3 4
5 6
5 7

 */
