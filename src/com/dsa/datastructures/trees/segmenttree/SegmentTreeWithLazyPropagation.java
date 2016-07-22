package com.dsa.datastructures.trees.segmenttree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

/**
 * Implementation of Segment Tree With Lazy Propagation, which does Range Minimum Query.
 */
public class SegmentTreeWithLazyPropagation
{
	private static int data[];
	private static Node segmentTree[];
	private static InputReader reader;
	private static OutputWriter writer;

	public static void main(String[] args)
	{
		reader = new InputReader(System.in);
		writer = new OutputWriter(System.out);

		solve();

		writer.flush();

		reader.close();
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
		segmentTree = new Node[sizeOfTree];

		for (int i = 0; i < sizeOfDataArray; i++)
			data[i] = reader.nextInt();

		buildTree(1, 0, sizeOfDataArray - 1);

		int ch = 0;

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

					System.out.println("The minimum of the range is "
							+ queryTree(1, 0, sizeOfDataArray - 1,
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

					updateTree(1, 0, sizeOfDataArray - 1, rangeStart - 1,
							rangeEnd - 1, updateValue);
					System.out
							.println("The tree in the range has been updated");

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
			// initializing with appropriate data and not lazy
			segmentTree[node] = new Node(data[treeStart]);

			return;
		}

		int mid = (treeStart + treeEnd) / 2;

		buildTree(2 * node, treeStart, mid);
		buildTree(2 * node + 1, mid + 1, treeEnd);

		segmentTree[node] = new Node(Math.min(
				segmentTree[2 * node].minimum,
				segmentTree[2 * node + 1].minimum));
	}

	static int queryTree(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
	{
		Node tempNode = segmentTree[node];

		// i.e., lazy
		if (tempNode.update > 0)
		{
			tempNode.minimum += tempNode.update;

			// not a leaf node
			if (treeStart != treeEnd)
			{
				int update = tempNode.update;

				segmentTree[2 * node].update = update;
				segmentTree[2 * node + 1].update = update;
			}

			tempNode.update = 0;
		}
		
		// if the asked for range is completely out of the range that this node
		// stores information of
		if (treeStart > treeEnd || treeStart > rangeEnd
				|| treeEnd < rangeStart)
			return Integer.MAX_VALUE;

		// if the range that the tree holds is completely inside of the qeury
		// range
		if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			return tempNode.minimum;

		int mid, leftChildMin, rightChildMin;

		mid = (treeStart + treeEnd) / 2;
		leftChildMin = queryTree(2 * node, treeStart, mid,
				rangeStart, rangeEnd);
		rightChildMin = queryTree(2 * node + 1, mid + 1, treeEnd,
				rangeStart, rangeEnd);

		return Math.min(leftChildMin, rightChildMin);
	}

	static void updateTree(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, int addValue)
	{
		Node tempNode = segmentTree[node];

		// i.e., lazy
		if (tempNode.update > 0)
		{
			tempNode.minimum += tempNode.update;

			// not a leaf node
			if (treeStart != treeEnd)
			{
				segmentTree[2 * node].update += tempNode.update;
				segmentTree[2 * node + 1].update += tempNode.update;
			}

			tempNode.update = 0;
		}

		// if the update range is completely out of the range that this node
		// stores information of
		if (treeStart > treeEnd || treeStart > rangeEnd
				|| treeEnd < rangeStart)
			return;

		// if the range this node holds is completely inside of the update range
		if (treeStart >= rangeStart && treeEnd <= rangeEnd)
		{
			tempNode.minimum += addValue;

			// not a leaf node
			if (treeStart != treeEnd)
			{
				segmentTree[2 * node].update += addValue;
				segmentTree[2 * node + 1].update += addValue;
			}

			return;
		}

		int mid = (treeStart + treeEnd) / 2;

		updateTree(2 * node, treeStart, mid, rangeStart,
				rangeEnd, addValue);
		updateTree(2 * node + 1, mid + 1, treeEnd, rangeStart,
				rangeEnd, addValue);

		tempNode.minimum = Math.min(segmentTree[2 * node].minimum,
				segmentTree[2 * node + 1].minimum);
	}

	/**
	 * A class to store the minimum value in a particular node of the Segment
	 * Tree, and if it is lazy or not.
	 * 
	 * <p>
	 * <b>Attributes</b><br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 * <b>minimum</b> the minimum value in the range of this node<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 * <b>update</b> the update value of all nodes in this node the node is lazy
	 * if this value is != 0, and not otherwise
	 * </p>
	 */
	static class Node
	{
		int minimum;
		int update; // if lazy, this will be != 0, else, 0

		public Node(int minimum)
		{
			this.minimum = minimum;
			this.update = 0;
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
