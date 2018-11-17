package com.codeforces.competitions.year2018.round496div3;

import java.io.*;
import java.util.*;
import java.util.List;

public final class TaskF
{
	public static void main(String[] args)
	{
		new TaskF(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, m, k, currTime;
		Edge[] edges;
		Node[] nodes;
		Set<Edge> bridges;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			m = in.nextInt();
			k = in.nextInt();
			createGraph();
			bridges = new HashSet<>();
			currTime = 1;
			dfs(0);

			boolean[] isBridge = new boolean[m];
			int cnt = m;

			for (int i = 0; i < m; i++)
			{
				if (bridges.contains(edges[i]))
				{
					isBridge[i] = true;
					cnt--;
				}
			}

			if (bridges.size() > 0)
			{
				out.println(1);

				for (int i = 0; i < m; i++)
					out.print(1);
			}
			else
			{

			}
		}

		void createGraph()
		{
			nodes = new Node[n];
			edges = new Edge[m];

			for (int i = 0; i < n; i++)
				nodes[i] = new Node(-1, -1, -1, new ArrayList<>(), false);

			for (int i = 0; i < m; i++)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				edges[i] = new Edge(u, v);
				nodes[u].adj.add(i);
				nodes[v].adj.add(i);
			}

/*			for (int i = 0; i < n; i++)
			{
				String input = in.nextLine();
				String[] line = input.split(" ");

				line[1] = line[1].split("()")[0];

				int curr = Integer.parseInt(line[0]);

				Node temp = nodes[curr] = new Node(-1, -1, -1, new ArrayList<>(), false);

				for (int j = 2; j < line.length; j++)
					temp.adj.add(Integer.parseInt(line[j]));
			}*/
		}

		void dfs(int node)
		{
			Node temp = nodes[node];
			Iterator<Integer> iterator = temp.adj.iterator();

			temp.visited = true;
			temp.visitingTime = temp.low = currTime++;

			while (iterator.hasNext())
			{
				int ed = iterator.next();
				int to = edges[ed].getTo(node);

				if (!nodes[to].visited)
				{
					nodes[to].parent = node;

					dfs(to);

					temp.low = Math.min(temp.low, nodes[to].low);

					if (temp.visitingTime < nodes[to].low)
					{
						if (node < to)
							bridges.add(new Edge(node, to));
						else
							bridges.add(new Edge(to, node));
					}
				}
				else if (temp.parent != to)
					temp.low = Math.min(temp.low, nodes[to].visitingTime);
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

		class Edge
		{
			int from, to;

			public Edge(int from, int to)
			{
				this.from = from;
				this.to = to;
			}

			public int getTo(int x)
			{
				return from + to - x;
			}

			@Override public boolean equals(Object obj)
			{
				Edge ed = (Edge) obj;

				return from == ed.from && to == ed.to;
			}

			@Override public int hashCode()
			{
				return Objects.hash(from, to);
			}

		}

		void debug(Object... o)
		{
			System.err.println(Arrays.deepToString(o));
		}

//		uncomment below line to change to BufferedReader
//		public Solver(BufferedReader in, PrintWriter out)
		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
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

	public TaskF(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskF", 1 << 29);

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
