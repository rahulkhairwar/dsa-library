package com.google.codejam16.round1a;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by rahulkhairwar on 16/04/16.
 */
public class TaskC
{
	public static void main(String[] args)
	{
		BufferedReader in;

		try
		{
			in = new BufferedReader(new FileReader(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/google/codejam16/round1a/inputC.txt"));

			OutputWriter out = new OutputWriter(System.out);
			Thread thread = new Thread(null, new Solver(in, out), "Solver", 1 << 28);

			thread.start();

			try
			{
				thread.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			out.flush();

			in.close();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	static class Solver implements Runnable
	{
		int t, n;
		List<Integer>[] straight, reverse;
		boolean[] visited;
		BufferedReader in;
		OutputWriter out;

		public Solver(BufferedReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
		{
			try
			{
				t = Integer.parseInt(in.readLine());

				for (int i = 0; i < t; i++)
				{
					n = Integer.parseInt(in.readLine());

					createGraph();

					int max = 0;

					for (int j = 0; j < n; j++)
					{
						if (!visited[j])
						{
							max = Math.max(max, dfs(j));
						}
					}

//					out.println("Case #" + (i + 1) + ": " + max);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		void createGraph() throws IOException
		{
			straight = new ArrayList[n];
			reverse = new ArrayList[n];
			visited = new boolean[n];

			String[] tokens = in.readLine().split(" ");
			int[] bff = new int[n];

			for (int i = 0; i < n; i++)
			{
				int from, to;

				bff[i] = Integer.parseInt(tokens[i]);
				from = i;
				to = bff[i] - 1;

				System.out.println("$$$$ " + from + "->" + to);

				if (straight[from] == null)
					straight[from] = new ArrayList<>();

				straight[from].add(to);

				if (reverse[to] == null)
					reverse[to] = new ArrayList<>();

				reverse[to].add(from);
			}
		}

		int dfs(int node)
		{
			visited[node] = true;

			System.out.println("@@@@ entered " + node);
			if (reverse[node] == null)
			{
				System.out.println("^^ no path so leaving " + node);
				return 1;
			}

			Iterator<Integer> iterator = reverse[node].iterator();
			int max = 0;

			while (iterator.hasNext())
			{
				int curr = iterator.next();

				if (!visited[curr])
					max = Math.max(max, dfs(curr));
			}

			System.out.println("**** leaving node : " + node + ", size : " + (max + 1));
			return max + 1;
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
