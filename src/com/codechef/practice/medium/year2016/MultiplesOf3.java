package com.codechef.practice.medium.year2016;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by rahulkhairwar on 09/07/16.
 */
class MultiplesOf3
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver(in, out);

		solver.solve(1);

		out.flush();

		in.close();
		out.close();
	}

	static class Solver
	{
		int n, q;
		Node[] tree;
		InputReader in;
		OutputWriter out;

		void solve(int testNumber)
		{
			n = in.nextInt();
			q = in.nextInt();

			tree = new Node[4 * n];

			buildTree(1, 0, n - 1);

			for (int i = 0; i < q; i++)
			{
				int type, from, to;

				type = in.nextInt();
				from = in.nextInt();
				to = in.nextInt();

				if (type == 0)
					update(1, 0, n - 1, from, to);
				else
					out.println(query(1, 0, n - 1, from, to));
			}
		}

		void buildTree(int node, int treeStart, int treeEnd)
		{
			tree[node] = new Node();
			Node temp = tree[node];

			if (treeStart == treeEnd)
			{
				temp.count[0] = 1;

				return;
			}

			int mid = treeStart + ((treeEnd - treeStart) >> 1);

			buildTree(2 * node, treeStart, mid);
			buildTree(2 * node + 1, mid + 1, treeEnd);

			temp.count[0] = tree[2 * node].count[0] + tree[2 * node + 1].count[0];
		}

		void update(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			Node temp = tree[node];
			temp.lazy %= 3;

			if (temp.lazy > 0)
			{
				if (treeStart != treeEnd)
				{
					tree[2 * node].lazy += temp.lazy;
					tree[2 * node + 1].lazy += temp.lazy;
				}

				while (temp.lazy > 0)
				{
					int temp2 = temp.count[2];

					temp.count[2] = temp.count[1];
					temp.count[1] = temp.count[0];
					temp.count[0] = temp2;
					temp.lazy--;
				}
			}

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			{
				int temp2 = temp.count[2];

				temp.count[2] = temp.count[1];
				temp.count[1] = temp.count[0];
				temp.count[0] = temp2;

				if (treeStart != treeEnd)
				{
					tree[2 * node].lazy++;
					tree[2 * node + 1].lazy++;
				}

				return;
			}

			int mid = treeStart + ((treeEnd - treeStart) >> 1);

			update(2 * node, treeStart, mid, rangeStart, rangeEnd);
			update(2 * node + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			temp.count[0] = tree[2 * node].count[0] + tree[2 * node + 1].count[0];
			temp.count[1] = tree[2 * node].count[1] + tree[2 * node + 1].count[1];
			temp.count[2] = tree[2 * node].count[2] + tree[2 * node + 1].count[2];
		}

		int query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			Node temp = tree[node];
			temp.lazy %= 3;

			if (temp.lazy > 0)
			{
				if (treeStart != treeEnd)
				{
					tree[2 * node].lazy += temp.lazy;
					tree[2 * node + 1].lazy += temp.lazy;
				}

				while (temp.lazy > 0)
				{
					int temp2 = temp.count[2];

					temp.count[2] = temp.count[1];
					temp.count[1] = temp.count[0];
					temp.count[0] = temp2;
					temp.lazy--;
				}
			}

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return 0;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return temp.count[0];

			int mid, left, right;

			mid = treeStart + ((treeEnd - treeStart) >> 1);
			left = query(2 * node, treeStart, mid, rangeStart, rangeEnd);
			right = query(2 * node + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			return left + right;
		}

		class Node
		{
			int[] count;
			int lazy;

			public Node()
			{
				count = new int[3];
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
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					stream)));
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
