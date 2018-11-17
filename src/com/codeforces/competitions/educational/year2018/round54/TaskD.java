package com.codeforces.competitions.educational.year2018.round54;

import java.io.*;
import java.util.*;

public class TaskD
{
	public static void main(String[] args)
	{
		new TaskD(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final long INFINITY = Long.MAX_VALUE;
		int n, m, maxRemaining;
		int[] par, passUp;
		long[] dist;
		Edge[] edges;
		List<Edge>[] adj;
		Map<Integer, Integer>[] map;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			m = in.nextInt();
			maxRemaining = in.nextInt();
			par = new int[n];
			passUp = new int[n];
			edges = new Edge[m];
			adj = new List[n];
			map = new Map[n];
			Arrays.fill(par, -1);

			for (int i = 0; i < n; i++)
			{
				adj[i] = new ArrayList<>();
				map[i] = new HashMap<>();
			}

			for (int i = 0; i < m; i++)
			{
				int from = in.nextInt() - 1;
				int to = in.nextInt() - 1;
				long wt = in.nextLong();

				edges[i] = new Edge(i, from, to, wt);
				adj[from].add(edges[i]);
				adj[to].add(new Edge(i, to, from, wt));
				map[from].put(to, i);
				map[to].put(from, i);
			}

			findShortestPaths(0);

			PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> {
				if (dist[o1] == dist[o2])
					return Integer.compare(o1, o2);

				return Long.compare(dist[o2], dist[o1]);
			});

			for (int i = 0; i < n; i++)
				queue.add(i);

			while (queue.size() > 0)
			{
				int x = queue.poll();

				findCnts(x);
			}

			queue = new PriorityQueue<>((o1, o2) -> {
				if (edges[o1].usedCnt == edges[o2].usedCnt)
					return Integer.compare(o1, o2);

				return Integer.compare(edges[o1].usedCnt, edges[o2].usedCnt);
			});

			for (int i = 0; i < m; i++)
				queue.add(i);

			int remove = m - maxRemaining;

			out.println(maxRemaining);

			Set<Integer> rem = new HashSet<>();

			while (remove-- > 0)
			{
				int ed = queue.poll();

				rem.add(ed);
			}

			for (int i = 0; i < m; i++)
			{
				if (!rem.contains(i))
					out.print(i + 1 + " ");
			}
		}

		void findCnts(int x)
		{
			if (x == 0)
				return;

			int p = par[x];
			int ed = map[x].get(p);
			Edge edge = edges[ed];

			edge.usedCnt = 1 + passUp[x];
			passUp[p] = edge.usedCnt;
		}

		void findShortestPaths(int start)
		{
			dist = new long[n];

			for (int i = 0; i < n; i++)
				dist[i] = INFINITY;

			TreeSet<Node> set = new TreeSet<>();

			dist[start] = 0;
			set.add(new Node(start, 0, -1));

			while (set.size() > 0)
			{
				Node curr = set.pollFirst();

				for (Edge edge : adj[curr.node])
				{
					if (dist[curr.node] + edge.weight < dist[edge.to])
					{
						set.remove(new Node(edge.to, dist[edge.to], -1));
						dist[edge.to] = dist[curr.node] + edge.weight;
						set.add(new Node(edge.to, dist[edge.to], curr.node));
						par[edge.to] = curr.node;
					}
				}
			}
		}

		static class Edge
		{
			int ind, from, to, usedCnt;
			long weight;

			public Edge(int ind, int from, int to, long weight)
			{
				this.ind = ind;
				this.from = from;
				this.to = to;
				this.weight = weight;
			}
		}

		static class Node implements Comparable<Node>
		{
			int node, parent;
			long dist;

			public Node(int node, long dist, int parent)
			{
				this.node = node;
				this.dist = dist;
				this.parent = parent;
			}

			@Override public int compareTo(Node o)
			{
				if (dist == o.dist)
					return Integer.compare(node, o.node);

				return Long.compare(dist, o.dist);
			}
		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override public void run()
		{
			try
			{
				solve();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

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

		public boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
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

		public InputReader(InputStream stream)
		{
			this.stream = stream;
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

	static class Utils
	{
		static boolean nextPermutation(int[] arr)
		{
			for (int a = arr.length - 2; a >= 0; --a)
			{
				if (arr[a] < arr[a + 1])
				{
					for (int b = arr.length - 1; ; --b)
					{
						if (arr[b] > arr[a])
						{
							int t = arr[a];

							arr[a] = arr[b];
							arr[b] = t;

							for (++a, b = arr.length - 1; a < b; ++a, --b)
							{
								t = arr[a];
								arr[a] = arr[b];
								arr[b] = t;
							}

							return true;
						}
					}
				}
			}

			return false;
		}

	}

	public TaskD(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskD", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			in.close();
			out.flush();
			out.close();
		}
	}

}

