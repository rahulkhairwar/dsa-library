package com.codeforces.competitions.year2018.manthancodefest2018div1_2;

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
		int n;
		Set<Integer>[] adj;
		int[] order, indOf, par;
		boolean[] vis;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			adj = new Set[n];
			order = new int[n];
			indOf = new int[n];
			par = new int[n];
			vis = new boolean[n];
			par[0] = -1;

			for (int i = 0; i < n; i++)
				adj[i] = new HashSet<>();

			for (int i = 1; i < n; i++)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				adj[u].add(v);
				adj[v].add(u);
			}

			for (int i = 0; i < n; i++)
			{
				order[i] = in.nextInt() - 1;
				indOf[order[i]] = i;
			}

			if (order[0] != 0)
			{
				out.println("No");

				return;
			}

			vis[0] = true;

			Queue<Integer> q = new LinkedList<>();

			bfs(0, q);

			Set<Integer> removed = new HashSet<>();
			Queue<Integer> queue = new LinkedList<>();
			int ptr = 1;

			for (int i = 0; i < n; i++)
				queue.add(order[i]);

			while (queue.size() > 0)
			{
				int front = queue.poll();

				removed.add(front);

				while (ptr < n && adj[front].contains(order[ptr]))
					ptr++;

				if (ptr < n && removed.contains(par[order[ptr]]))
				{
					out.println("No");

					return;
				}

				if (ptr == n)
					break;
			}

			out.println("Yes");

/*			int ptr = 1;
			int curr = 0;
			int currSeenCnt = 0;

			while (ptr < n)
			{
//				System.out.println("curr : " + curr + ", ptr : " + ptr + ", ord[ptr] : " + order[ptr] + ", csc : "
//					+ currSeenCnt);
				if (adj[curr].contains(order[ptr]))
				{
					currSeenCnt++;
					ptr++;

					if (currSeenCnt == adj[curr].size() - (curr > 0 ? 1 : 0))
					{
						curr = order[indOf[curr] + 1];
						currSeenCnt = 0;
					}
				}
				else
				{
					out.println("No");

					return;
				}
			}

			out.println("Yes");*/
		}

		void bfs(int node, Queue<Integer> queue)
		{
			for (int x : adj[node])
			{
				if (!vis[x])
				{
					vis[x] = true;
					queue.add(x);
					par[x] = node;
				}
			}

			if (queue.size() == 0)
				return;

			int front = queue.poll();

			bfs(front, queue);
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

/*

10
1 2
2 5
2 7
1 3
1 4
5 6
3 10
4 8
4 9
1 2 3 4 5 7 10 8 9 6

2
1 2
2 1

3
1 2
2 3
1 2 3

4
1 2
2 3
2 4
1 2 3 4

*/
