package com.hackerearth.competitions.encodethebeginning;

import java.io.*;
import java.util.*;

class Q3
{
	static int t, n, m;
	static List<Pair>[] list;
	static boolean[] visited;
	static Scanner in;
	static OutputWriter out;

	public static void main(String[] args) throws FileNotFoundException
	{
		out = new OutputWriter(System.out);

		in = new Scanner(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
				+ "Programming/src/com/hackerearth/competitions/shortcompetitions/encodethebeginning/input5.txt"));

		solve();

		out.flush();

		in.close();
		out.close();
	}

	static void solve()
	{
		t = in.nextInt();

		while (t-- > 0)
		{
			n = in.nextInt();
			m = in.nextInt();

			createGraph(m);

			int town = in.nextInt() - 1;

			Group[] groups = new Group[n];
			int groupCount = 0;

			for (int i = 0; i < n; i++)
				groups[groupCount++] = countBananas(i);

			Arrays.sort(groups, 0, groupCount, new Comparator<Group>()
			{
				@Override
				public int compare(Group o1, Group o2)
				{
					return Long.compare(o1.weight, o2.weight);
				}
			});

			if (groupCount == 0)
			{
				out.println("YES");

				return;
			}

			Group largest = groups[groupCount - 1];

			if (largest == null)
			{
				out.println("YES");
				continue;
			}

			boolean ans;

			if (largest.list != null)
				ans = largest.list.contains(town);
			else
				ans = false;

			if (ans)
				out.println("NO");
			else
				out.println("YES");
		}
	}

	static void createGraph(int edges)
	{
		list = new ArrayList[(int) 1e5 + 5];
		visited = new boolean[(int) 1e5 + 5];

		for (int i = 0; i < edges; i++)
		{
			int from, to, weight;

			from = in.nextInt() - 1;
			to = in.nextInt() - 1;
			weight = in.nextInt();

			if (list[from] == null)
				list[from] = new ArrayList<>();

			list[from].add(new Pair(to, weight));

			if (list[to] == null)
				list[to] = new ArrayList<>();

			list[to].add(new Pair(from, weight));
		}
	}

	static Group countBananas(int node)
	{
		if (visited[node])
			return new Group(null, -1);

		if (list[node] == null)
			return new Group(null, 0);

		Stack<Pair> stack = new Stack<>();
		Group group = new Group(new ArrayList<>(), 0);

		visited[node] = true;
		stack.push(new Pair(node, 0));
		group.list.add(node);

		long total = 0;

		Iterator<Pair> iterator = list[node].iterator();

		while (iterator.hasNext())
		{
			Pair curr = iterator.next();

			if (!visited[curr.node])
			{
				stack.push(curr);
				group.list.add(curr.node);
			}
		}

		while (stack.size() > 1)
		{
			Pair top = stack.pop();

			if (visited[top.node])
				continue;

			visited[top.node] = true;
			total += top.weight;

			if (list[top.node] == null)
				continue;

			iterator = list[top.node].iterator();

			while (iterator.hasNext())
			{
				Pair curr = iterator.next();

				if (!visited[curr.node])
				{
					stack.push(curr);
					group.list.add(curr.node);
				}
			}
		}

		stack.pop();
		group.weight = total;

		return group;
	}

	static class Pair
	{
		int node, weight;

		public Pair(int node, int weight)
		{
			this.node = node;
			this.weight = weight;
		}

	}

	static class Group
	{
		List<Integer> list;
		long weight;

		public Group(List<Integer> list, long weight)
		{
			this.list = list;
			this.weight = weight;
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

}
