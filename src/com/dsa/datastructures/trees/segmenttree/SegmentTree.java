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
 * A basic implementation of Segment tree, which does Range Maximum Query and
 * point updation.
 */
public class SegmentTree
{
	private static int numberOfElements, array[], tree[];
	private static InputReader reader;
	private static OutputWriter writer;

	public static void main(String args[])
	{
		reader = new InputReader(System.in);
		writer = new OutputWriter(System.out);

		solve();

		writer.flush();

		reader.close();
		writer.close();
	}

	/**
	 * This method takes the array using which the tree has to be built, then
	 * finds the space needed for the tree array, which is
	 * {@code 2 * 2^(ceil(log2(n))) - 1}, then builds the tree, and then asks
	 * for queries.
	 */
	static void solve()
	{
		System.out.println("Enter the size of the array : ");

		numberOfElements = reader.nextInt();

		int log = (int) Math.ceil((Math.log(numberOfElements) / Math.log(2)));

		array = new int[numberOfElements];
		tree = new int[2 * (int) Math.pow(2, log)];

		System.out.println("Enter the array : ");

		for (int i = 0; i < numberOfElements; i++)
			array[i] = reader.nextInt();

		buildTree(1, 0, numberOfElements - 1);

		System.out.println("The built tree looks like : ");

		for (int i = 1; i < tree.length; i++)
			System.out.print(tree[i] + " ");

		System.out.print("\nHow many operations do you want to perform? : ");

		int q = reader.nextInt();

		for (int i = 0; i < q; i++)
		{
			System.out
					.print("\n*******\n1. Query.\n2. Point Update.\n\nEnter your choice : ");

			if (reader.nextInt() == 1)
			{
				System.out.println("start of range(1-based) : ");
				int start = reader.nextInt();
				System.out.println("end of range(1-based) : ");
				int end = reader.nextInt();

				System.out.println("The maximum number in the range is : "
						+ queryTree(1, 0, numberOfElements - 1, start - 1,
								end - 1));
			}
			else
			{
				System.out
						.println("Enter the array index(1-based) you want to update : ");

				int arrayIndex, updateValue;

				arrayIndex = reader.nextInt();
				System.out
						.println("Enter the value you want to the update the index with : ");
				updateValue = reader.nextInt();

				pointUpdate(1, 0, numberOfElements - 1, arrayIndex - 1,
						updateValue);

				System.out.println("The index has been updated.");
				System.out.println("The built tree looks like : ");

				for (int j = 1; j < tree.length; j++)
					System.out.print(tree[j] + " ");
			}
		}
	}

	/**
	 * This method builds the tree recursively.
	 * 
	 * @param currentNode
	 *            the 1-based index of the current node of the tree
	 * @param treeStart
	 *            the 0-based leftmost index of the range, which the current
	 *            node stores value of
	 * @param treeEnd
	 *            the 0-based rightmost index of the range, which the current
	 *            node stores value of
	 */
	static void buildTree(int currentNode, int treeStart, int treeEnd)
	{
		if (treeStart > treeEnd)
			return;

		// i.e., leaf node
		if (treeStart == treeEnd)
		{
			tree[currentNode] = array[treeStart];

			return;
		}

		int mid = (treeStart + treeEnd) / 2;

		// left child
		buildTree(2 * currentNode, treeStart, mid);
		// right child
		buildTree(2 * currentNode + 1, mid + 1, treeEnd);

		tree[currentNode] = Math.max(tree[2 * currentNode],
				tree[2 * currentNode + 1]);
	}

	/**
	 * This method finds the maximum value in
	 * {@code [queryRangeStart, queryRangeEnd]} range.
	 * 
	 * @param currentNode
	 *            the 1-based index of the current node of the tree
	 * @param treeStart
	 *            the 0-based leftmost index of the range, which the current
	 *            node stores value of
	 * @param treeEnd
	 *            the 0-based rightmost index of the range, which the current
	 *            node stores value of
	 * @param queryRangeStart
	 *            this method returns the maximum value in the range which
	 *            starts at this index
	 * @param queryRangeEnd
	 *            this method returns the maximum value in the range which ends
	 *            at this index
	 * @return the maximum value in the range 
	 *         [<code>queryRangeStart, queryRangeEnd</code>].
	 */
	static int queryTree(int currentNode, int treeStart, int treeEnd,
			int queryRangeStart, int queryRangeEnd)
	{
		// if the query range is completely out of the range that this node
		// stores information of
		if (treeStart > treeEnd || treeStart > queryRangeEnd
				|| treeEnd < queryRangeStart)
			return Integer.MIN_VALUE;

		// if the range that this node holds is completely inside of the qeury
		// range
		if (treeStart >= queryRangeStart && treeEnd <= queryRangeEnd)
			return tree[currentNode];

		int mid, leftChildMax, rightChildMax;

		mid = (treeStart + treeEnd) / 2;
		leftChildMax = queryTree(2 * currentNode, treeStart, mid,
				queryRangeStart, queryRangeEnd);
		rightChildMax = queryTree(2 * currentNode + 1, mid + 1, treeEnd,
				queryRangeStart, queryRangeEnd);

		return Math.max(leftChildMax, rightChildMax);
	}

	/**
	 * Update a particular index of the array with another value.
	 * 
	 * @param currentNode
	 *            the current node of the tree.
	 * @param treeStart
	 *            the starting index of the range that the current node(tree)
	 *            holds.
	 * @param treeEnd
	 *            the ending index of the range that the current node(tree)
	 *            holds.
	 * @param updateNode
	 *            the 0-based index of the array element which needs to be
	 *            updated.
	 * @param updateValue
	 *            the value with which the array element needs to be updated.
	 */
	static void pointUpdate(int currentNode, int treeStart, int treeEnd,
			int updateNode, int updateValue)
	{
		if (treeStart > treeEnd || treeStart > updateNode
				|| treeEnd < updateNode)
			return;

		// i.e., treeStart = treeEnd = updateNode
		if (treeStart == treeEnd)
		{
			tree[currentNode] = updateValue;

			return;
		}

		int mid = (treeStart + treeEnd) / 2;

		pointUpdate(2 * currentNode, treeStart, mid, updateNode, updateValue);
		pointUpdate(2 * currentNode + 1, mid + 1, treeEnd, updateNode,
				updateValue);

		tree[currentNode] = Math.max(tree[2 * currentNode],
				tree[2 * currentNode + 1]);
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
