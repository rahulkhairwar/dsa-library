package com.codechef.practice.medium.year2015.julytoseptember;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class FlippingCoins
{
	private static int numberOfCoins, numberOfQueries, option, start, end;
	private static Node tree[];
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		FlippingCoins coins = new FlippingCoins();
		
		reader = coins.new InputReader(System.in);
		writer = coins.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		numberOfCoins = reader.nextInt();
		numberOfQueries = reader.nextInt();
		
		int log, sizeOfTree;
		
		log = (int) Math.ceil(Math.log(numberOfCoins) / Math.log(2));
		sizeOfTree = 2 * (int) Math.pow(2, log);
		
		tree = new Node[sizeOfTree];
		
		buildTree(1, 0, numberOfCoins - 1);
		
		for (int i = 0; i < numberOfQueries; i++)
		{
			option = reader.nextInt();
			start = reader.nextInt();
			end = reader.nextInt();
			
			if (option == 0)
				updateTree(1, 0, numberOfCoins - 1, start, end);
			else
				writer.println(queryTree(1, 0, numberOfCoins - 1, start, end));
		}
	}

	/**
	 * Builds the Segment Tree.
	 * 
	 * @param currentNode
	 *            the 1-based index of the current node in the tree.
	 * @param treeRangeStart
	 *            the 0-based starting index of the range of the array, which
	 *            this tree holds information of.
	 * @param treeRangeEnd
	 *            the 0-based ending index of the range of the array, which this
	 *            tree holds information of.
	 */
	public static void buildTree(int currNode, int tRS,
			int tRE)
	{
		if (tRS > tRE)
			return;
		
		if (tRS == tRE)
		{
			tree[currNode] = new Node(0, false);
			
			return;
		}
		
		int mid = (tRS + tRE) / 2;
		
		buildTree(2 * currNode, tRS, mid);
		buildTree(2 * currNode + 1, mid + 1, tRE);
		
		tree[currNode] = new Node(0, false);
	}

	public static void updateTree(int currNode, int tSI, int tEI, int uSI,
			int uEI)
	{
		Node temp = tree[currNode];
		
		if (temp.flip)
		{
			temp.numberOfHeads = (tEI - tSI + 1) - temp.numberOfHeads;
			
			if (tSI != tEI)
			{
				tree[2 * currNode].flip = !tree[2 * currNode].flip;
				tree[2 * currNode + 1].flip = !tree[2 * currNode + 1].flip;
			}
			
			temp.flip = false;
		}
		
		if (tSI > tEI || tSI > uEI || tEI < uSI)
			return;
		
		if (tSI >= uSI && tEI <= uEI)
		{
			if (!temp.flip)
			{
				temp.numberOfHeads = (tEI - tSI + 1) - temp.numberOfHeads;

				if (tSI != tEI)
				{
					tree[2 * currNode].flip = !tree[2 * currNode].flip;
					tree[2 * currNode + 1].flip = !tree[2 * currNode + 1].flip;
				}
			}
			else
				temp.flip = false;

			return;
		}

		int mid = (tSI + tEI) / 2;

		updateTree(2 * currNode, tSI, mid, uSI, uEI);
		updateTree(2 * currNode + 1, mid + 1, tEI, uSI, uEI);
		
		temp.numberOfHeads = tree[2 * currNode].numberOfHeads
				+ tree[2 * currNode + 1].numberOfHeads; 
	}

	public static int queryTree(int currNode, int tSI, int tEI, int qSI, int qEI)
	{
		Node temp = tree[currNode];
		
		if (temp.flip)
		{
			temp.numberOfHeads = (tEI - tSI + 1) - temp.numberOfHeads;

			if (tSI != tEI)
			{
				tree[2 * currNode].flip = !tree[2 * currNode].flip;
				tree[2 * currNode + 1].flip = !tree[2 * currNode + 1].flip;
			}

			temp.flip = false;
		}
		
		if (tSI > tEI || tSI > qEI || tEI < qSI)
			return -1;
		
		if (tSI >= qSI && tEI <= qEI)
			return temp.numberOfHeads;
		
		int mid, lCH, rCH;
		
		mid = (tSI + tEI) / 2;
		
		lCH = queryTree(2 * currNode, tSI, mid, qSI, qEI);
		rCH = queryTree(2 * currNode + 1, mid + 1, tEI, qSI, qEI);
		
		if (lCH == -1)
			lCH = 0;
		
		if (rCH == -1)
			rCH = 0;
		
		return (lCH + rCH);
	}

	static class Node
	{
		int numberOfHeads;
		boolean flip;

		public Node(int numberOfHeads, boolean flip)
		{
			this.numberOfHeads = numberOfHeads;
			this.flip = flip;
		}
		
		@Override
		public String toString()
		{
			return "" + numberOfHeads;
		}

	}
	
	class InputReader
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
			}
			while (!isSpaceChar(c));

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
			}
			while (!isSpaceChar(c));

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
			}
			while (!isSpaceChar(c));

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

	class OutputWriter
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

/*

4 7
1 0 3
0 1 2
1 0 1
1 0 0
0 0 3
1 0 3 
1 3 3

4 10
1 0 3
0 1 2
1 0 1
1 0 0
0 0 3
1 0 3 
1 0 0
1 1 1
1 2 2
1 3 3

8 8
0 2 4
1 0 7
0 1 3
1 0 7
0 0 7
1 0 7
0 4 7
1 0 7

*/
