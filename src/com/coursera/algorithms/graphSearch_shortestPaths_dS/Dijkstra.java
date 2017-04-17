package com.coursera.algorithms.graphSearch_shortestPaths_dS;

import java.io.*;
import java.util.*;

public final class Dijkstra
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);

		try
		{
			in = new InputReader(new FileInputStream(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA "
					+ "Workspace/Competitive Programming/src/com/coursera/algorithms/graphSearch_shortestPaths_dS"
					+ "/DijkstraInput.txt")));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		static final int INF = (int) 1e6;
		int v;
		int[] dist;
		Edge[] edges;
		List<Integer>[] adj;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			v = 200;
			dist = new int[v];
			edges = new Edge[(int) 1e5];
			adj = new List[v];

			for (int i = 0; i < v; i++)
				adj[i] = new ArrayList<>();

			int ctr = 0;

			for (int i = 0; i < v; i++)
			{
				String line = in.nextLine();
				String[] tok = line.split("\\s");
				int len = tok.length;

				for (int j = 1; j < len; j++)
				{
					String[] tok2 = tok[j].split(",");
					int to = Integer.parseInt(tok2[0]) - 1;
					int wt = Integer.parseInt(tok2[1]);

					edges[ctr] = new Edge(i, to, wt);
					adj[i].add(ctr);
					ctr++;
				}
			}

			dijkstra(0);

			int[] find = new int[]{7, 37, 59, 82, 99, 115, 133, 165, 188, 197};
			StringBuilder ans = new StringBuilder("");

			for (int x : find)
				ans.append(dist[x - 1]).append(",");

			out.println(ans.substring(0, ans.length() - 1));
		}

		void dijkstra(int src)
		{
			TreeSet<Integer> set = new TreeSet<>(new Comparator<Integer>()
			{
				@Override public int compare(Integer o1, Integer o2)
				{
					return Integer.compare(dist[o1], dist[o2]);
				}
			});

			Arrays.fill(dist, INF);
			dist[src] = 0;
			set.add(src);

			while (set.size() > 0)
			{
				int curr = set.pollFirst();

				for (int x : adj[curr])
				{
					Edge ed = edges[x];
					int to = (ed.to == curr ? ed.from : ed.to);
					int wt = ed.wt;

					if (dist[curr] + wt < dist[to])
					{
						dist[to] = dist[curr] + wt;
						set.add(to);
					}
				}
			}
		}

		void debug(Object... o)
		{
			System.err.println(Arrays.deepToString(o));
		}

		class Edge
		{
			int from, to, wt;

			Edge(int from, int to, int wt)
			{
				this.from = from;
				this.to = to;
				this.wt = wt;
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

}

/*

4 4
1 2,24 4,20 3,3
2 1,24
3 1,3 4,12
4 1,20 3,12

1 2 24
1 4 20
3 1 3
4 3 12
1

*/
