package com.codeforces.competitions.year2018.round495div2;

import java.io.*;
import java.util.*;

public class TaskE
{
	public static void main(String[] args)
	{
		new TaskE(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, k, ans;
		Set<Integer>[] adj;
		long[] sub, par, totDist;
		boolean[] deleted, isIceCream;
		//		BufferedReader in;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			k = in.nextInt();
			adj = new Set[n];
			sub = new long[n];
			par = new long[n];
			totDist = new long[n];
			deleted = new boolean[n];
			isIceCream = new boolean[n];

			for (int i = 0; i < n; i++)
				adj[i] = new HashSet<>();

			for (int i = 1; i < n; i++)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				adj[u].add(v);
				adj[v].add(u);
			}

//			decompose(0,-1);
			nn = 0;
			dfs1(0, -1);

			int centroid = dfs2(0, -1);

			Arrays.fill(sub, 0);
			dfs5(centroid, -1);
			findTotDist(centroid, -1);
//			System.out.println("centroid : " + centroid);

			PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>()
			{
				@Override public int compare(Integer o1, Integer o2)
				{
					return Long.compare(totDist[o1], totDist[o2]);
				}
			});

			System.out.println("cen : " + centroid);
			setPar(centroid, -1);
			queue.add(centroid);
//			isIceCream[centroid] = true;
//			k--;

			while (k > 0)
			{
				int x = queue.poll();

				System.out.println("setting x : " + x);
				isIceCream[x] = true;
				k--;

				for (int ad : adj[x])
				{
					if (ad != par[x])
					{
						queue.add(ad);
					}
				}
			}

			findMin(centroid, -1, 0);

			out.println(ans);

/*			System.out.println("totD ");

			for (int i = 0; i < n; i++)
				System.out.println("i : " + i + ", toD : " + totDist[i]);*/
		}

		void findMin(int node, int par, int dist)
		{
			if (!isIceCream[node])
				dist++;

			ans = Math.max(ans, dist);

			for (int x : adj[node])
			{
				if (x != par)
				{
					findMin(x, node, dist);
				}
			}
		}

		void findTotDist(int node, int par)
		{
//			System.out.println("nd : " + node);
			for (int x : adj[node])
			{
				if (x != par)
				{
					findTotDist(x, node);
					totDist[node] += totDist[x];
					totDist[node] += sub[x];
				}
			}

			totDist[node]++;
//			System.out.println("nd : " + node + ", tD : " + totDist[node] + ", sub : " + sub[node]);
		}


		/*-----------------Decomposition Part--------------------------*/
		int nn;

		void dfs1(int u, int p)
		{
			sub[u] = 1;
			nn++;
			System.out.println("u : " + u);

			for (int it : adj[u])
			{
				if (it != p)
				{
					dfs1(it, u);
					sub[u] += sub[it];
				}
			}
		}

		void dfs5(int u, int p)
		{
			sub[u] = 1;
//			nn++;
			System.out.println("***u : " + u);

			for (int it : adj[u])
			{
				if (it != p)
				{
					dfs5(it, u);
					sub[u] += sub[it];
				}
			}
		}

		void setPar(int u, int p)
		{
			for (int it : adj[u])
			{
				if (it != p)
				{
					par[it] = u;
					setPar(it, u);
				}
			}
		}

		int dfs2(int u, int p)
		{
			for (int it : adj[u])
			{
				if (it != p && sub[it] > nn / 2)
					return dfs2(it, u);
			}

			return u;
		}

		void decompose(int root, int p)
		{
			nn = 0;
			dfs1(root, -1);

			int centroid = dfs2(root, -1);

//			System.out.println("cen : " + centroid);

			if (p == -1)
				p = centroid;

			par[centroid] = p;

			for (int it : adj[centroid])
			{
				adj[it].remove(centroid);
				decompose(it, centroid);
			}

			adj[centroid].clear();
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

	public TaskE(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskE", 1 << 29);

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

16
1 4
2 4
3 4
4 5
5 6
6 7
8 7
7 9
6 10
10 11
11 12
11 13
12 14
13 15
13 16

*/
