package com.google.kickstart18.roundC;

import java.io.*;
import java.util.*;

public final class TaskA
{
	public static void main(String[] args)
	{
		new TaskA(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final int INFINITY = (int) 1e6 + 5;
		int t, n, startFrom;
		boolean[] vis;
		int[] dist;
		List<Integer>[] adj;
		Set<Integer> cycleSet;
//		Edge[] edges;
//		Graph graph;
		//		BufferedReader in;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();

			for (int test = 1; test <= t; test++)
			{
				n = in.nextInt();
				vis = new boolean[n];
				adj = new List[n];
				cycleSet = new HashSet<>();
//				nodes = new Node[n];
//				edges = new Edge[n];
//				graph = new Graph(n, n);

				for (int i = 0; i < n; i++)
					adj[i] = new ArrayList<>();

//				createGraph();

				for (int i = 0; i < n; i++)
				{
					int u = in.nextInt() - 1;
					int v = in.nextInt() - 1;

					adj[u].add(v);
					adj[v].add(u);
				}

				dfs(0, -1);
//				System.out.println("startFrom : " + startFrom);
				Arrays.fill(vis, false);
				dfs2(startFrom, -1);
				dist = new int[n];
				Arrays.fill(dist, INFINITY);

//				System.out.println("cycleSet : " + cycleSet);

				for (int x : cycleSet)
					dist[x] = 0;

//				for (int i = 0; i < n; i++)
				for (int i : cycleSet)
				{
					Arrays.fill(vis, false);
					giveDist(i, -1, 0);
				}

				out.print("Case #" + test + ": ");

				for (int i = 0; i < n; i++)
					out.print(dist[i] + " ");

				out.println();
			}
		}

		void giveDist(int node, int par, int d)
		{
//			System.out.println("node : " + node + ", par : " + par + ", d : " + d);
			dist[node] = Math.min(dist[node], d);
			vis[node] = true;

			for (int x : adj[node])
			{
				if (x != par)
				{
					if (!vis[x] /*&& !cycleSet.contains(x)*/)
					{
//						System.out.println("will give x " + x + " d=" + (d + 1));
						giveDist(x, node, d + 1);
					}
				}
			}
		}

		boolean dfs(int node, int par)
		{
			vis[node] = true;

			boolean cycle = false;
//			System.out.println("**node :" + node);

			for (int x : adj[node])
			{
				if (x != par)
				{
					if (vis[x])
					{
//						System.out.println("");
						cycle = true;
//						cycleSet.add(node);
						startFrom = x;
					}
					else
					{
						cycle |= dfs(x, node);

//						if (cycle)
//							cycleSet.add(node);
					}
				}
			}

			return cycle;
		}

		boolean dfs2(int node, int par)
		{
			vis[node] = true;

			boolean cycle = false;

//			System.out.println("***node : " + node + ", adj[node] : " + adj[node] + ", vis : " + Arrays.toString(vis));

			for (int x : adj[node])
			{
				if (x != par)
				{
					if (vis[x])
					{
						cycle = true;
						cycleSet.add(node);
//						startFrom = x;
					}
					else
					{
						cycle |= dfs2(x, node);

						if (cycle)
							cycleSet.add(node);
					}
				}
			}

			return cycle;
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

	public TaskA(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskA", 1 << 29);

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

/*

2
5
1 2
2 3
3 4
2 4
5 3
3
1 2
3 2
1 3

1
5
1 2
2 3
3 4
2 4
5 3

*/
