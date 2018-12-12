package com.codeforces.competitions.educational.year2018.round55;

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
		int n, edges, visCnt;
		int[] deg;
		Set<Integer>[] adj;
		boolean[] vis;
		int[][] dist;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			deg = new int[n];
			adj = new Set[n];

			PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> {
				if (deg[o1] == deg[o2])
					return 0;

				return Integer.compare(deg[o2], deg[o1]);
			});

			for (int i = 0; i < n; i++)
			{
				deg[i] = in.nextInt();
				queue.add(i);
				adj[i] = new HashSet<>();
			}

			boolean[] added = new boolean[n];
			int minInd = 0;
			List<Integer> snake = new ArrayList<>();

			for (int i = 0; i < n; i++)
			{
				if (deg[i] < deg[minInd])
					minInd = i;
			}

			snake.add(minInd);
			added[minInd] = true;

			while (true)
			{
//				System.out.println(">> snake : " + snake);
				int maxInd = -1;

				for (int i = 0; i < n; i++)
				{
					if (!added[i])
					{
						if (deg[i] > 0)
						{
							if (maxInd == -1)
								maxInd = i;
							else if (deg[i] > deg[maxInd])
								maxInd = i;
						}
					}
				}

				if (maxInd == -1)
					break;

				int last = snake.get(snake.size() - 1);
//				System.out.println("maxI : " + maxInd + ", deg[mi] : " + deg[maxInd]);
//				System.out.println("\tlast : " + last + ", deg[last] : " + deg[last]);

				if (deg[last] == 0)
					break;

				snake.add(maxInd);
				added[maxInd] = true;
				deg[last]--;
				deg[maxInd]--;
				adj[last].add(maxInd);
				adj[maxInd].add(last);
			}

//			System.out.println("snake : " + snake);
//			System.out.println("deg : " + Arrays.toString(deg));

			queue = new PriorityQueue<>((o1, o2) -> {
				if (deg[o1] == deg[o2])
					return 0;

				return Integer.compare(deg[o2], deg[o1]);
			});

			for (int i = 0; i < n; i++)
			{
				if (deg[i] > 0)
					queue.add(i);
			}

			while (queue.size() > 0)
			{
				vis = new boolean[n];
				visCnt = 0;
				dfs(0);

				if (visCnt == n)
					break;

				int first = queue.poll();
				boolean b = false;

				for (int i = 0; i < n; i++)
				{
					if (i == first || deg[i] == 0 || adj[first].contains(i))
						continue;

//					System.out.println("first : " + first + ", i : " + i + ", deg[f] : " + deg[first] + ", deg[i] : "
//							+ deg[i]);
					adj[first].add(i);
					adj[i].add(first);
					deg[first]--;
					b = true;

					break;
				}

				if (deg[first] > 0 && b)
					queue.add(first);
			}

			visCnt = 0;
			edges = 0;
			vis = new boolean[n];
			dfs(0);

			if (visCnt < n)
			{
				out.println("NO");

				return;
			}

			int maxDist = 0;

			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < n; j++)
					maxDist = Math.max(maxDist, dist[i][j]);
			}

/*			for (int i = 0; i < n; i++)
			{
				dist = new int[n][n];
				dijkstra(i);

				for (int j = 0; j < n; j++)
				{
					if (i == j)
						continue;

					maxDist = Math.max(maxDist, dist[i][j]);
				}
			}*/

			out.println("YES " + maxDist);
			out.println(edges / 2);

			for (int i = 0; i < n; i++)
			{
				for (int x : adj[i])
				{
					out.println(i + 1 + " " + (x + 1));
					adj[x].remove(i);
				}
			}
		}

		void floydWarshall()
		{
			for (int i = 0; i < n; i++)
				Arrays.fill(dist[i], (int) 1e6);

			for (int i = 0; i < n; i++)
			{
				dist[i][i] = 0;

				for (int x : adj[i])
					dist[i][x] = 1;
			}

			for (int k = 0; k < n; k++)
			{
				for (int i = 0; i < n; i++)
				{
					for (int j = 0; j < n; j++)
					{
						if (dist[i][k] + dist[k][j] < dist[i][j])
							dist[i][j] = dist[i][k] + dist[k][j];
					}
				}
			}
		}

		void dfs(int node)
		{
			visCnt++;
			vis[node] = true;
			edges += adj[node].size();

			for (int x : adj[node])
			{
				if (!vis[x])
					dfs(x);
			}
		}

		void debug(Object... o)
		{
			System.err.println(Arrays.deepToString(o));
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

