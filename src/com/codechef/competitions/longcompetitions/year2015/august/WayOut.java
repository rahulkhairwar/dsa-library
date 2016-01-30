package com.codechef.competitions.longcompetitions.year2015.august;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class WayOut
{
	static int t, n, height, left, right, path[];
	static long sum[];
	static Node tree[];
	static InputReader reader;
	static OutputWriter writer;

	public static void main(String[] args)
	{
		WayOut wayOut = new WayOut();

		reader = wayOut.new InputReader(System.in);
		writer = wayOut.new OutputWriter(System.out);

		getAttributes();

		writer.flush();

		reader.close();
		writer.close();
	}

	static void getAttributes()
	{
		int log, treeSize;

		t = reader.nextInt();

		for (int i = 0; i < t; i++)
		{
			n = reader.nextInt();
			height = reader.nextInt();

			log = (int) Math.ceil((Math.log(n) / Math.log(2)));
			treeSize = (int) (2 * Math.pow(2, log));

			tree = new Node[treeSize];

			for (int j = 1; j < treeSize; j++)
				tree[j] = new Node(0, 0);

			for (int j = 0; j < n; j++)
			{
				left = reader.nextInt();
				right = reader.nextInt();

				updateTree(1, 0, n - 1, left, right);
			}
			
			sum = new long[n];

			sum[0] = queryTree(1, 0, n - 1, 0, 0);

			for (int j = 1; j < n; j++)
				sum[j] = sum[j - 1] + queryTree(1, 0, n - 1, j, j);

			long maxSum, currSum;

			maxSum = sum[height - 1];

			for (int j = height; j < n; j++)
			{
				currSum = sum[j] - sum[j - height];

				if (currSum > maxSum)
					maxSum = currSum;
			}
			
			writer.println((long) height * n - maxSum);
		}
	}

	static void updateTree(int currNode, int tSI, int tEI, int rSI, int rEI)
	{
		Node temp = tree[currNode];

		if (temp.update > 0)
		{
			temp.count += ((tEI - tSI + 1) * temp.update);
			
			if (tSI != tEI)
			{
				tree[2 * currNode].update += temp.update;
				tree[2 * currNode + 1].update += temp.update;
			}

			temp.update = 0;
		}

		if (tSI > tEI || tSI > rEI || tEI < rSI)
			return;

		if (tSI >= rSI && tEI <= rEI)
		{
			temp.count += (tEI - tSI + 1);

			if (tSI != tEI)
			{
				tree[2 * currNode].update += 1;
				tree[2 * currNode + 1].update += 1;
			}

			return;
		}

		int mid = (tSI + tEI) / 2;

		updateTree(2 * currNode, tSI, mid, rSI, rEI);
		updateTree(2 * currNode + 1, mid + 1, tEI, rSI, rEI);

		temp.count = tree[2 * currNode].count + tree[2 * currNode + 1].count;
	}

	static int queryTree(int currNode, int tSI, int tEI, int rSI, int rEI)
	{
		Node temp = tree[currNode];

		if (temp.update > 0)
		{
			temp.count += ((tEI - tSI + 1) * temp.update);

			if (tSI != tEI)
			{
				tree[2 * currNode].update += temp.update;
				tree[2 * currNode + 1].update += temp.update;
			}

			temp.update = 0;
		}

		if (tSI > tEI || tSI > rEI || tEI < rSI)
			return -1;

		if (tSI >= rSI && tEI <= rEI)
			return temp.count;

		int mid, lCC, rCC;

		mid = (tSI + tEI) / 2;

		lCC = queryTree(2 * currNode, tSI, mid, rSI, rEI);
		rCC = queryTree(2 * currNode + 1, mid + 1, tEI, rSI, rEI);
		
		if (lCC == -1)
			lCC = 0;
		
		if (rCC == -1)
			rCC = 0;

		return lCC + rCC;
	}

	static class Node
	{
		int count;
		int update;

		public Node(int count, int update)
		{
			this.count = count;
			this.update = update;
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

/*

2
4 3
1 2
1 2
1 2
1 2
5 2
2 3
1 2
2 3
1 2
2 3
answer : 4, 2

1
6 3
1 3
2 4
0 3
1 5
2 3
3 4
answer : 4

1
5 4
0 0
1 1
2 2
3 3
4 4
answer : 16

1
5 4
0 1
1 1
2 3
1 4
4 4
answer : 11

1
5 4
0 4
0 4
0 4
0 4
0 4
answer : 0

 */
