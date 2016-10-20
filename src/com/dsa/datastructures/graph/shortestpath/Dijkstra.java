package com.dsa.datastructures.graph.shortestpath;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * An implementation of Dijkstra's Algorithm.
 * <br />
 * <p>
 * Hackerrank question <a href="https://www.hackerrank.com/challenges/dijkstrashortreach">link</a>
 */
public class Dijkstra
{
	static final int INFINITY = Integer.MAX_VALUE;
	static int t, nodes, edges;
	static int[] distance;
	static List<Edge>[] adj;
	static InputReader in;

	public static void main(String[] args)
	{
/*		System.out.print("Enter the number of nodes you want in the graph : ");
		nodes = in.nextInt();

		System.out.print("\nEnter the number of edges : ");
		edges = in.nextInt();

		createGraph();
		findShortestPaths(0);

		System.out.println("The shortest path to all nodes from node 0 is : ");

		for (int i = 0; i < nodes; i++)
			System.out.println(i + " : " + distance[i]);*/

		in = new InputReader(System.in);
		t = in.nextInt();

		while (t-- > 0)
		{
			nodes = in.nextInt();
			edges = in.nextInt();

			createGraph();

			int start = in.nextInt() - 1;

			findShortestPaths(start);

			for (int i = 0; i < nodes; i++)
			{
				if (i == start)
					continue;

				if (distance[i] != INFINITY)
					System.out.print(distance[i] + " ");
				else
					System.out.print(-1 + " ");
			}

			System.out.println();
		}
	}

	static void createGraph()
	{
		adj = new ArrayList[nodes];

		for (int i = 0; i < nodes; i++)
			adj[i] = new ArrayList<>();

		for (int i = 0; i < edges; i++)
		{
			int from, to, weight;

			from = in.nextInt() - 1;
			to = in.nextInt() - 1;
			weight = in.nextInt();
			adj[from].add(new Edge(to, weight));
			adj[to].add(new Edge(from, weight));
		}
	}

	static void findShortestPaths(int start)
	{
		distance = new int[nodes];

		for (int i = 0; i < nodes; i++)
			distance[i] = INFINITY;

		TreeSet<Node> set = new TreeSet<>();

		distance[start] = 0;
		set.add(new Node(start, 0, -1));

		while (set.size() > 0)
		{
			Node curr = set.pollFirst();

			for (Edge edge : adj[curr.node])
			{
				if (distance[curr.node] + edge.weight < distance[edge.to])
				{
					set.remove(new Node(edge.to, distance[edge.to], -1));
					distance[edge.to] = distance[curr.node] + edge.weight;
					set.add(new Node(edge.to, distance[edge.to], curr.node));
				}
			}
		}
	}

	static class Edge
	{
		int to, weight;

		public Edge(int to, int weight)
		{
			this.to = to;
			this.weight = weight;
		}

	}

	static class Node implements Comparable<Node>
	{
		int node, dist, parent;

		public Node(int node, int dist, int parent)
		{
			this.node = node;
			this.dist = dist;
			this.parent = parent;
		}

		@Override public int compareTo(Node o)
		{
			if (dist == o.dist)
				return Integer.compare(node, o.node);

			return Integer.compare(dist, o.dist);
		}

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
				}
				catch (IOException e)
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
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

}
