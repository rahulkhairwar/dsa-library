package com.codechef.competitions.longcompetitions.year2016.september;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;

class JosephLand
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver(in, out);
		solver.solve();

		out.flush();

		in.close();
		out.close();
	}

	static class Solver
	{
		static final long INFINITY = Long.MAX_VALUE;
		int n, m;
		long[] answers;
		boolean[] visited;
		Node[] tree;
		List<Integer>[] adj;
		List<Point>[] tickets;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			answers = new long[n];
			visited = new boolean[n];
			tree = new Node[4 * n];
			adj = new ArrayList[n];
			tickets = new ArrayList[n];

			for (int i = 0; i < n; i++)
			{
				adj[i] = new ArrayList<>();
				tickets[i] = new ArrayList<>();
			}

			for (int i = 1; i < n; i++)
			{
				int from, to;

				from = in.nextInt() - 1;
				to = in.nextInt() - 1;
                adj[to].add(from);
			}

			for (int i = 0; i < m; i++)
			{
				int city, times, price;

				city = in.nextInt() - 1;
				times = in.nextInt();
				price = in.nextInt();
				tickets[city].add(new Point(price, times));
			}

			for (int i = 0; i < 4 * n; i++)
				tree[i] = new Node(INFINITY);

			update(1, 0, n - 1, 0, 0, 0);
			update(1, 0, n - 1, 1, n - 1, INFINITY);

			Iterator<Integer> iterator = adj[0].iterator();

			while (iterator.hasNext())
				dfs(iterator.next(), 1);

			int queries = in.nextInt();

			while (queries-- > 0)
				out.println(answers[in.nextInt() - 1]);
		}

		void dfs(int city, int index)
		{
			visited[city] = true;

			long min = INFINITY;
			Iterator<Point> iterator = tickets[city].iterator();

			while (iterator.hasNext())
			{
				Point curr = iterator.next();
				long cost = curr.x;

				cost += query(1, 0, n - 1, Math.max(0, index - curr.y), index - 1);
				min = Math.min(min, cost);
			}

			answers[city] = min;
			update(1, 0, n - 1, index, index, min);

			Iterator<Integer> it = adj[city].iterator();

			while (it.hasNext())
			{
				int curr = it.next();

				if (!visited[curr])
					dfs(curr, index + 1);
			}
		}

		void update(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, long value)
		{
			Node temp = tree[node];

			if (temp.update != 0)
			{
				temp.min = temp.update;

				if (treeStart != treeEnd)
				{
					tree[node << 1].update = temp.update;
					tree[(node << 1) + 1].update = temp.update;
				}

				temp.update = 0;
			}

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			{
				temp.min = value;

				if (treeStart != treeEnd)
				{
					tree[node << 1].update = value;
					tree[(node << 1) + 1].update = value;
				}

				return;
			}

			int mid = treeStart + ((treeEnd - treeStart) >> 1);

			update(node << 1, treeStart, mid, rangeStart, rangeEnd, value);
			update((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd, value);

			temp.min = Math.min(tree[node << 1].min, tree[(node << 1) + 1].min);
		}

		long query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			Node temp = tree[node];

			if (temp.update != 0)
			{
				temp.min = temp.update;

				if (treeStart != treeEnd)
				{
					tree[node << 1].update = temp.update;
					tree[(node << 1) + 1].update = temp.update;
				}

				temp.update = 0;
			}

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return INFINITY;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return temp.min;

			int mid = treeStart + ((treeEnd - treeStart) >> 1);
			long left, right;

			left = query(node << 1, treeStart, mid, rangeStart, rangeEnd);
			right = query((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			return Math.min(left, right);
		}

		class Node
		{
			long min, update;

			public Node(long min)
			{
				this.min = min;
			}

		}

		public Solver(InputReader in, OutputWriter out)
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

	static class OutputWriter
	{
		private PrintWriter writer;

		public OutputWriter(OutputStream stream)
		{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
		}

		public OutputWriter(Writer writer)
		{
			this.writer = new PrintWriter(writer);
		}

		public void println(int x)
		{
			writer.println(x);
		}

		public void print(int x)
		{
			writer.print(x);
		}

		public void println(char x)
		{
			writer.println(x);
		}

		public void print(char x)
		{
			writer.print(x);
		}

		public void println(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i] + " ");
		}

		public void println(long x)
		{
			writer.println(x);
		}

		public void print(long x)
		{
			writer.print(x);
		}

		public void println(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i]);
		}

		public void println(float num)
		{
			writer.println(num);
		}

		public void print(float num)
		{
			writer.print(num);
		}

		public void println(double num)
		{
			writer.println(num);
		}

		public void print(double num)
		{
			writer.print(num);
		}

		public void println(String s)
		{
			writer.println(s);
		}

		public void print(String s)
		{
			writer.print(s);
		}

		public void println()
		{
			writer.println();
		}

		public void printSpace()
		{
			writer.print(" ");
		}

		public void printf(String format, Object args)
		{
			writer.printf(format, args);
		}

		public void flush()
		{
			writer.flush();
		}

		public void close()
		{
			writer.close();
		}

	}

}
