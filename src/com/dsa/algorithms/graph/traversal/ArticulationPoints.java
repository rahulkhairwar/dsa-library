package com.dsa.algorithms.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * An algorithm to find articulation points in an undirected graph.
 * <br />
 * An articulation point is a node in a <i><b>connected</b></i> graph, removing which will result in the graph becoming
 * disconnected.
 * For additional documentation, refer
 * <a href="http://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of <i>Algorithms, 4th Edition</i>
 */
public class ArticulationPoints
{
	static int t, n, m, k, articulationPoints, currTime;
	static int[] parent, visitingTime, low;
	static List<Integer>[] adj;
	static boolean[] visited;
	static InputReader in;
	static OutputWriter out;

	public static void main(String[] args)
	{
		in = new InputReader(System.in);
		out = new OutputWriter(System.out);

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
			k = in.nextInt();

			articulationPoints = 0;
			currTime = 1;
			parent = new int[n];
			visitingTime = new int[n];
			low = new int[n];
			visited = new boolean[n];

			parent[0] = -1;
			createGraph();
			dfs(0);

			int count = 0;
			for (int i = 0; i < n; i++)
				if (parent[i] == 0)
					count++;

			if (count < 2)
				articulationPoints--;

			out.println(articulationPoints * k);
		}
	}

	static void createGraph()
	{
		adj = new List[n];

		for (int i = 0; i < n; i++)	adj[i] = new ArrayList<>();

		for (int i = 0; i < m; i++)
		{
			int from, to;

			from = in.nextInt();
			to = in.nextInt();
			adj[from].add(to);
			adj[to].add(from);
		}
	}

	static void dfs(int node)
	{
		visited[node] = true;
		visitingTime[node] = low[node] = currTime++;

		Iterator<Integer> iterator = adj[node].iterator();
		boolean isArticulationPoint = false;

		while (iterator.hasNext())
		{
			int curr = iterator.next();

			if (!visited[curr])
			{
				parent[curr] = node;
				dfs(curr);
				low[node] = Math.min(low[node], low[curr]);

				if (visitingTime[node] <= low[curr])
					isArticulationPoint = true;
			}
			else if (curr != parent[node])
				low[node] = Math.min(low[node], visitingTime[curr]);
		}

		if (isArticulationPoint)
			articulationPoints++;
	}

	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

		public int read()
		{
			if (numChars == -1)
				throw new InputMismatchException();

			if (curChar >= numChars)
			{
				curChar = 0;
				try
				{
					numChars = stream.read(buf);
				} catch (IOException e)
				{
					throw new InputMismatchException();
				}
				if (numChars <= 0)
					return -1;
			}

			return buf[curChar++];
		}

		public int nextInt()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sgn = 1;

			if (c == '-')
			{
				sgn = -1;
				c = read();
			}

			int res = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				res *= 10;
				res += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return res * sgn;
		}

		public int[] nextIntArray(int arraySize)
		{
			int array[] = new int[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextInt();

			return array;
		}

		public long nextLong()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sign = 1;

			if (c == '-')
			{
				sign = -1;

				c = read();
			}

			long result = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				result *= 10;
				result += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return result * sign;
		}

		public long[] nextLongArray(int arraySize)
		{
			long array[] = new long[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextLong();

			return array;
		}

		public float nextFloat() // problematic
		{
			float result, div;
			byte c;

			result = 0;
			div = 1;
			c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean isNegative = (c == '-');

			if (isNegative)
				c = (byte) read();

			do
			{
				result = result * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					result += (c - '0') / (div *= 10);

			if (isNegative)
				return -result;

			return result;
		}

		public double nextDouble() // not completely accurate
		{
			double ret = 0, div = 1;
			byte c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean neg = (c == '-');

			if (neg)
				c = (byte) read();

			do
			{
				ret = ret * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);

			if (neg)
				return -ret;

			return ret;
		}

		public String next()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			StringBuilder res = new StringBuilder();

			do
			{
				res.appendCodePoint(c);

				c = read();
			} while (!isSpaceChar(c));

			return res.toString();
		}

		public boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public String nextLine()
		{
			int c = read();

			StringBuilder result = new StringBuilder();

			do
			{
				result.appendCodePoint(c);

				c = read();
			} while (!isNewLine(c));

			return result.toString();
		}

		public boolean isNewLine(int c)
		{
			return c == '\n';
		}

		public void close()
		{
			try
			{
				stream.close();
			} catch (IOException e)
			{
				e.printStackTrace();
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

}

/*

1
7 6 5
0 1
1 2
3 4
2 4
2 6
5 2
: 15

1
6 5 5
0 1
1 2
2 3
2 4
1 5
: 10

2
7 6 5
0 1
1 2
3 4
2 4
2 6
5 2
6 5 5
0 1
1 2
2 3
2 4
1 5
: 15, 10

1
16 19 1
0 1
1 2
1 3
2 4
2 5
4 5
0 6
6 7
6 8
6 13
8 9
8 12
9 12
9 10
9 11
7 13
0 14
0 15
14 15
: 6

1
2 1 1
0 1
: 0

1
3 2 1
0 1
0 2
: 1

1
3 3 1
0 1
0 2
1 2
: 0

 */
