package com.hackerearth.practice.easy.year2016;

import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * Created by rahulkhairwar on 24/07/16.
 */
class PrateekAndTheories
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
		int t, n, max;
		int[] times, bit;
		Node[] tree;
		Point[] periods;
		HashMap<Integer, Integer> comp;
		InputReader in;
		OutputWriter out;

		public Solver(InputReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
		}

		/**
		 * Solution using BIT.
		 * @param testNumber
		 */
		void solve(int testNumber)
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				periods = new Point[n];
				times = new int[2 * n];

				int counter = 0;

				for (int i = 0; i < n; i++)
				{
					int from, to;

					from = in.nextInt();
					to = in.nextInt() - 1;

					periods[i] = new Point(from, to);
					times[counter++] = from;
					times[counter++] = to;
				}

				max = 0;
				compress();

				bit = new int[max + 1];

				for (int i = 0; i < n; i++)
				{
					add(periods[i].x, 1);
					add(periods[i].y + 1, -1);
				}

				int[] count = new int[max + 1];
				int answer = 0;

				for (int i = 1; i <= max; i++)
				{
					count[i] = query(i);
					answer = Math.max(answer, count[i]);
				}

				out.println(answer);
			}
		}

		void add(int index, int value)
		{
			while (index <= max)
			{
				bit[index] += value;
				index += index & -index;
			}
		}

		int query(int index)
		{
			int answer = 0;

			while (index > 0)
			{
				answer += bit[index];
				index -= index & -index;
			}

			return answer;
		}

		/**
		 * Solution using Segment tree with lazy propogation.
		 * @param testNumber
		 */
		void solve2(int testNumber)
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				periods = new Point[n];
				times = new int[2 * n];

				int counter = 0;

				for (int i = 0; i < n; i++)
				{
					int from, to;

					from = in.nextInt();
					to = in.nextInt() - 1;

					periods[i] = new Point(from, to);
					times[counter++] = from;
					times[counter++] = to;
				}

				max = 0;
				compress();

				max++;
				tree = new Node[4 * max];

				build(1, 0, max - 1);

				for (int i = 0; i < n; i++)
					update(1, 0, max - 1, periods[i].x, periods[i].y);

				int[] count = new int[max + 1];
				int answer = 0;

				for (int i = 1; i <= max; i++)
				{
					count[i] = query(1, 0, max - 1, i, i);
					answer = Math.max(answer, count[i]);
				}

				out.println(answer);
			}
		}

		void compress()
		{
			comp = new HashMap<>();
			Arrays.sort(times);
			int counter = 1;

			for (int i = 0; i < (n << 1); i++)
				comp.put(times[i], counter++);

			for (int i = 0; i < n; i++)
			{
				periods[i].x = comp.get(periods[i].x);
				periods[i].y = comp.get(periods[i].y);
				max = Math.max(max, periods[i].x);
				max = Math.max(max, periods[i].y);
			}
		}

		void build(int node, int treeStart, int treeEnd)
		{
			if (treeStart == treeEnd)
			{
				tree[node] = new Node(0);

				return;
			}

			int mid = treeStart + ((treeEnd - treeStart) >> 1);

			build(2 * node, treeStart, mid);
			build(2 * node + 1, mid + 1, treeEnd);

			tree[node] = new Node(0);
		}

		void update(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			Node temp = tree[node];

			if (temp.lazy > 0)
			{
				if (treeStart != treeEnd)
				{
					tree[2 * node].lazy += temp.lazy;
					tree[2 * node + 1].lazy += temp.lazy;
				}

				temp.value += (treeEnd - treeStart + 1) * temp.lazy;
				temp.lazy = 0;
			}

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			{
				temp.value += (treeEnd - treeStart + 1);

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

			temp.value = tree[2 * node].value + tree[2 * node + 1].value;
		}

		int query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			Node temp = tree[node];

			if (temp.lazy > 0)
			{
				if (treeStart != treeEnd)
				{
					tree[2 * node].lazy += temp.lazy;
					tree[2 * node + 1].lazy += temp.lazy;
				}

				temp.value += (treeEnd - treeStart + 1) * temp.lazy;
				temp.lazy = 0;
			}

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return 0;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return temp.value;

			int mid, left, right;

			mid = treeStart + ((treeEnd - treeStart) >> 1);
			left = query(2 * node, treeStart, mid, rangeStart, rangeEnd);
			right = query(2 * node + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			temp.value = tree[2 * node].value + tree[2 * node + 1].value;

			return left + right;
		}

		class Node
		{
			int value, lazy;

			public Node(int value)
			{
				this.value = value;
			}

			@Override public String toString()
			{
				return "" + value;
			}
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
