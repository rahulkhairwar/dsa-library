package com.codechef.competitions.longcompetitions.year2016.september;

import java.io.*;
import java.util.*;

class DividingMachine
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
		int t, n, m, arr[], minPrimeDiv[];
		Node[] tree;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			pre();
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				m = in.nextInt();
				arr = new int[n];
				tree = new Node[4 * n];

				for (int i = 0; i < n; i++)
					arr[i] = in.nextInt();

				for (int i = 0; i < 4 * n; i++)
					tree[i] = new Node();

				build(1, 0, n - 1);

				for (int i = 0; i < m; i++)
				{
					int type, left, right;

					type = in.nextInt();
					left = in.nextInt() - 1;
					right = in.nextInt() - 1;

					if (type == 0)
						update(1, 0, n - 1, left, right);
					else
						out.print(query(1, 0, n - 1, left, right) + " ");
				}

				out.println();
			}
		}

		void build(int node, int treeStart, int treeEnd)
		{
			Node temp = tree[node];

			if (treeStart == treeEnd)
			{
				temp.val = arr[treeStart];
				temp.max = minPrimeDiv[temp.val];

				if (temp.val == 1)
					temp.done = true;

				return;
			}

			int mid = treeStart + ((treeEnd - treeStart) >> 1);

			build(node << 1, treeStart, mid);
			build((node << 1) + 1, mid + 1, treeEnd);
			temp.val = temp.max = Math.max(tree[node << 1].max, tree[(node << 1) + 1].max);

			if (tree[node << 1].done && tree[(node << 1) + 1].done)
				temp.done = true;
		}

		void update(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			Node temp = tree[node];

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return;

			if (temp.done)
				return;

			if (treeStart == treeEnd)
			{
				temp.val /= minPrimeDiv[temp.val];
				temp.max = minPrimeDiv[temp.val];

				if (temp.val == 1)
					temp.done = true;

				return;
			}

			int mid = treeStart + ((treeEnd - treeStart) >> 1);

			update(node << 1, treeStart, mid, rangeStart, rangeEnd);
			update((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd);
			temp.max = Math.max(tree[node << 1].max, tree[(node << 1) + 1].max);

			if (tree[node << 1].done && tree[(node << 1) + 1].done)
				temp.done = true;
		}

		int query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			Node temp = tree[node];

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return 1;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return temp.max;

			int mid, left, right;

			mid = treeStart + ((treeEnd - treeStart) >> 1);
			left = query(node << 1, treeStart, mid, rangeStart, rangeEnd);
			right = query((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			if (tree[node << 1].done && tree[(node << 1) + 1].done)
				temp.done = true;

			return Math.max(left, right);
		}

		class Node
		{
			int val, max;
			boolean done;

		}

		void pre()
		{
			minPrimeDiv = new int[(int) (1e6 + 5)];
			minPrimeDiv[1] = 1;
			minPrimeDiv[2] = 2;

			boolean[] isComp = new boolean[(int) (1e6 + 5)];
			int counter = 2;

			while ((counter << 1) <= 1e6)
			{
				isComp[counter << 1] = true;
				minPrimeDiv[counter << 1] = 2;
				counter++;
			}

			for (int i = 3; i <= 1e6; i += 2)
			{
				if (isComp[i])
					continue;

				minPrimeDiv[i] = i;
				counter = 2;

				while (i * counter <= 1e6)
				{
					if (!isComp[i * counter])
					{
						isComp[i * counter] = true;
						minPrimeDiv[i * counter] = i;
					}

					counter++;
				}
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
				} catch (IOException e)
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
			} catch (IOException e)
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