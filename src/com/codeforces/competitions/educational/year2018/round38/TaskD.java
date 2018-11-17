package com.codeforces.competitions.educational.year2018.round38;

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
		int n, m;
		long[] minCosts;
		List<Integer>[] adj;
		Edge[] edges;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			m = in.nextInt();
			minCosts = new long[n];
			adj = new List[n];
			edges = new Edge[m];

			for (int i = 0; i < n; i++)
				adj[i] = new ArrayList<>();

			for (int i = 0; i < m; i++)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;
				long weight = in.nextLong();

				edges[i] = new Edge(u, v, weight);
				adj[u].add(i);
				adj[v].add(i);
			}

			TreeSet<Integer> treeSet = new TreeSet<>((o1, o2) ->
			{
				if (minCosts[o1] == minCosts[o2])
					return Integer.compare(o1, o2);

				return Long.compare(minCosts[o1], minCosts[o2]);
			});

			for (int i = 0; i < n; i++)
			{
				minCosts[i] = in.nextLong();
				treeSet.add(i);
			}

			while (treeSet.size() > 0)
			{
				int min = treeSet.pollFirst();

				for (int ed : adj[min])
				{
					int to = edges[ed].getTo(min);
					long cost = minCosts[min] + (edges[ed].weight << 1);

					if (cost < minCosts[to])
					{
						treeSet.remove(to);
						minCosts[to] = cost;
						treeSet.add(to);
					}
				}
			}

			for (long x : minCosts)
				out.print(x + " ");
		}

		class Edge
		{
			int from, to;
			long weight;

			Edge(int from, int to, long weight)
			{
				this.from = from;
				this.to = to;
				this.weight = weight;
			}

			int getTo(int x)
			{
				return from + to - x;
			}

		}

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

	public TaskD(InputStream inputStream, OutputStream outputStream)
	{
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

