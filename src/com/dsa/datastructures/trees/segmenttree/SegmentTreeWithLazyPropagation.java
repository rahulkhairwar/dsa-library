package com.dsa.datastructures.trees.segmenttree;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Implementation of Segment Tree With Lazy Propagation, which does Range Minimum Query.
 */
public class SegmentTreeWithLazyPropagation
{
	private static int data[];
	private static Node tree[];
	private static InputReader reader;
	private static OutputWriter writer;

	public static void main(String[] args)
	{
		reader = new InputReader(System.in);
		writer = new OutputWriter(System.out);

		solve();

		reader.close();
		writer.flush();
		writer.close();
	}

	static void solve()
	{
		System.out.println("Enter the size of the array : ");

		int sizeOfDataArray, sizeOfTree, log;

		sizeOfDataArray = reader.nextInt();
		log = (int) (Math.ceil(Math.log(sizeOfDataArray) / Math.log(2)));
		sizeOfTree = (int) (2 * Math.pow(2, log));
		data = new int[sizeOfDataArray];
		tree = new Node[sizeOfTree];

		for (int i = 0; i < sizeOfDataArray; i++)
			data[i] = reader.nextInt();

		buildTree(1, 0, sizeOfDataArray - 1);

		int ch;

		do
		{
			System.out.println("******\n1. Query.");
			System.out.println("2. Update.");
			System.out.println("3. Exit.");

			ch = reader.nextInt();

			switch (ch)
			{
				case 1:
				{
					int rangeStart, rangeEnd;

					System.out.println("start of range : ");
					rangeStart = reader.nextInt();
					System.out.println("end of range : ");
					rangeEnd = reader.nextInt();

					System.out.println("The minimum of the range is " + query(1, 0, sizeOfDataArray - 1,
							rangeStart - 1, rangeEnd - 1));
					break;
				}
				case 2:
				{
					int rangeStart, rangeEnd, updateValue;

					System.out.println("start of range : ");
					rangeStart = reader.nextInt();
					System.out.println("end of range : ");
					rangeEnd = reader.nextInt();
					System.out.println("update value : ");
					updateValue = reader.nextInt();

					update(1, 0, sizeOfDataArray - 1, rangeStart - 1, rangeEnd - 1, updateValue);
					System.out.println("The tree in the range has been updated");

					break;
				}
				case 3:
					break;// return;
			}
		} while (ch != 3);
	}

	static void buildTree(int node, int treeStart, int treeEnd)
	{
		// out of range
		if (treeStart > treeEnd)
			return;

		// leaf node
		if (treeStart == treeEnd)
		{
			// initializing with appropriate data and marking the node as not lazy
			tree[node] = new Node(data[treeStart]);

			return;
		}

		int mid = treeStart + treeEnd >> 1;
		int left = node << 1;
		int right = left + 1;

		buildTree(left, treeStart, mid);
		buildTree(right, mid + 1, treeEnd);
		tree[node] = new Node(Math.min(tree[left].min, tree[right].min));
	}

	static int query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
	{
		push(node, treeStart, treeEnd);

		// if the asked for range is completely out of the range that this node stores information of
		if (treeStart > treeEnd || treeStart > rangeEnd || treeEnd < rangeStart)
			return Integer.MAX_VALUE;

		Node tempNode = tree[node];

		// if the range that the tree holds is completely inside of the query range
		if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			return tempNode.min;

		int mid, left, right;

		mid = treeStart + treeEnd >> 1;
		left = query(node << 1, treeStart, mid, rangeStart, rangeEnd);
		right = query((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

		return Math.min(left, right);
	}

	static void push(int node, int treeStart, int treeEnd)
	{
		Node temp = tree[node];

		if (temp.lazy != 0)    // lazy
		{
			// not a leaf node, so will push laziness to children
			if (treeStart != treeEnd)
			{
				int update = temp.lazy;

				tree[node << 1].lazy = update;
				tree[(node << 1) + 1].lazy = update;
			}

			temp.min += temp.lazy;
			temp.lazy = 0;
		}
	}

	static void update(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, int val)
	{
		push(node, treeStart, treeEnd);

		// if the update range is completely out of the range that this node stores information of
		if (treeStart > treeEnd || treeStart > rangeEnd || treeEnd < rangeStart)
			return;

		Node temp = tree[node];

		// if the range this node holds is completely inside of the update range
		if (treeStart >= rangeStart && treeEnd <= rangeEnd)
		{
			// not a leaf node, so will push laziness to children
			if (treeStart != treeEnd)
			{
				tree[node << 1].lazy += val;
				tree[(node << 1) + 1].lazy += val;
			}

			temp.min += val;

			return;
		}

		int mid = treeStart + treeEnd >> 1;
		int left = node << 1;
		int right = left + 1;

		update(left, treeStart, mid, rangeStart, rangeEnd, val);
		update(right, mid + 1, treeEnd, rangeStart, rangeEnd, val);
		temp.min = Math.min(tree[left].min, tree[right].min);
	}

	/**
	 * A class to store the minimum value in a particular node of the Segment Tree, and if it is lazy or not.
	 */
	static class Node
	{
		int min, lazy; // field lazy != 0 denotes that the node is lazy.

		public Node(int min)
		{
			this.min = min;
			this.lazy = 0;
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

		public void println(long x)
		{
			writer.println(x);
		}

		public void print(int x)
		{
			writer.print(x);
		}

		public void print(long x)
		{
			writer.println(x);
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
