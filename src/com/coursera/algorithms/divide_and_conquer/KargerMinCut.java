package com.coursera.algorithms_divide_and_conquer;

import java.io.*;
import java.util.*;

public final class KargerMinCut
{
	public static void main(String[] args)
	{
		try
		{
			InputReader in = new InputReader(new FileInputStream(new File("/Users/rahulkhairwar/Documents/IntelliJ "
					+ "IDEA Workspace/Competitive Programming/src/com/coursera/algorithms_divide_and_conquer"
					+ "/minCut.txt")));
			in = new InputReader(System.in);
			PrintWriter out = new PrintWriter(System.out);
			Solver solver = new Solver(in, out);

			solver.solve();
			in.close();
			out.flush();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	static class Solver
	{
		int v, e, vertices, size, minCut;
		InputReader in;
		PrintWriter out;

		void solve()
		{
//			v = 200;
//			e = 5034;
			v = 6;
			e = 16;
			Set<Integer>[] adj = new Set[v];
			Edge[] edges = new Edge[e];

			for (int i = 0; i < v; i++)
				adj[i] = new HashSet<>();

			int ctr = 0;

			for (int i = 0; i < v; i++)
			{
				String[] tok = in.nextLine().split("\\s");
				int len = tok.length;
				Set<Integer> set = new HashSet<>();

				for (int j = 1; j < len; j++)
				{
					int to = Integer.parseInt(tok[j]) - 1;

					if (set.contains(to))
					{
						System.out.println("multiple edge from " + i + " to " + to);
					}

					set.add(to);
					adj[i].add(ctr);
					edges[ctr++] = new Edge(i, to);
				}
			}

			minCut = e;

			int iter = v * v;

			iter *= iter;

			for (int i = 0; i < iter; i++)
			{
				minCut = Math.min(minCut, findMinCut(edges, adj));

//				if (i % 1000 == 0)
//					System.out.println(i + " iterations done");
			}

			System.out.println("minCut : " + minCut);
		}

		int findMinCut(Edge[] edges, Set<Integer>[] adj)
		{
			vertices = v;
			size = edges.length;

			Edge[] edgesCopy = new Edge[size];
			Set<Integer>[] adjCopy = new Set[v];

			for (int i = 0; i < size; i++)
				edgesCopy[i] = new Edge(edges[i].from, edges[i].to);

			for (int i = 0; i < v; i++)
				adjCopy[i] = new HashSet<>();

			for (int i = 0; i < v; i++)
				for (int x : adj[i])
					adjCopy[i].add(x);

/*			System.out.println("edges :");

			for (int i = 0; i < e; i++)
				System.out.println("i : " + i + ", ed[i] : " + edges[i]);*/

			while (vertices > 2)
			{
				int pos = getRandomEdge();
				Edge edge = edgesCopy[pos];

				removeEdge(edgesCopy, adjCopy, pos, edge.from);
				combineVertices(edge.from, edge.to, edgesCopy, adjCopy);
			}

			return size;
		}

		void combineVertices(int u, int v, Edge[] edges, Set<Integer>[] adj)
		{
			Set<Edge> remove = new HashSet<>();
			Set<Integer> add = new HashSet<>();

			for (int x : adj[v])
			{
				if (edges[x].to == u)
				{
					removeEdge(edges, adj, x, v);
//					adj[v].remove(x);
					remove.add(new Edge(v, x));
				}
				else
				{
					remove.add(new Edge(v, x));
					removeEdge(edges, adj, x, v);
					add.add(size);
					addEdge(edges, adj, u, edges[x].to);
				}
			}

			for (Edge x : remove)
				adj[x.from].remove(x.to);

			for (int x : add)
				adj[u].add(x);

			vertices--;
		}

		int getRandomEdge()
		{
			return (int) (Math.random() * size);
		}

		void removeEdge(Edge[] edges, Set<Integer>[] adj, int pos, int x)
		{
			if (size == 0)
				return;

//			System.out.println("removing edge " + pos);

//			adj[edges[pos].from].remove(pos);

			if (edges[size - 1].from != x)
			{
				// removing previous location of the edge from the node's adjacency set.
				adj[edges[size - 1].from].remove(size - 1);
				// adding new location of the edge to the node's adjacency set.
				adj[edges[size - 1].from].add(pos);
			}
			edges[pos] = edges[size - 1];
			size--;
		}

		void addEdge(Edge[] edges, Set<Integer>[] adj, int from, int to)
		{
/*			System.out.println("want to add edge " + from + " -> " + to);
			System.out.println("old edges : " + Arrays.toString(edges));*/
			edges[size] = new Edge(from, to);
			size++;
		}

		class Edge
		{
			int from, to;

			public Edge(int from, int to)
			{
				this.from = from;
				this.to = to;
			}

			@Override public String toString()
			{
				return String.format("%d -> %d", from, to);
			}

		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
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

		public float nextFloat()
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

		public double nextDouble()
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

		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

		static long min(long... arr)
		{
			long min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static long max(long... arr)
		{
			long max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static int max(int... arr)
		{
			int max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

	}

}

/*

n = 6, e = 16
1 2 4
2 1 3 4 6
3 2 6
4 1 2 5
5 4 6
6 2 3 5

*/
