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
		int n, m;
		List<Integer>[] adj;
		boolean[] visited;
		int connectedComponentCount, cycleCount;
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

				createGraph();
				visited = new boolean[n];

				for (int i = 0; i < n; i++)
				{
					if (!visited[i])
					{
						connectedComponentCount++;

						if (dfs(i, -1, false))
							cycleCount++;
					}
				}

				out.println(connectedComponentCount - cycleCount);
			}
		}

		void createGraph()
		{
			adj = new ArrayList[n];

			for (int i = 0; i < m; i++)
			{
				int from, to;

				from = in.nextInt() - 1;
				to = in.nextInt() - 1;

				if (adj[from] == null)
					adj[from] = new ArrayList<>();

				adj[from].add(to);

				if (adj[to] == null)
					adj[to] = new ArrayList<>();

				adj[to].add(from);
			}
		}

		boolean dfs(int node, int parent, boolean hasCycle)
		{
			visited[node] = true;

			if (adj[node] == null)
				return hasCycle;

			Iterator<Integer> iterator = adj[node].iterator();

			while (iterator.hasNext())
			{
				int curr = iterator.next();

				if (visited[curr] && curr != parent)
					hasCycle = true;
				else if (!visited[curr])
					hasCycle |= dfs(curr, node, hasCycle);
			}

			return hasCycle;
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
