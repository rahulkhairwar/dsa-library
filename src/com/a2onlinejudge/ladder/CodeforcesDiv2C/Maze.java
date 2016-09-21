package com.a2onlinejudge.ladder.CodeforcesDiv2C;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;

public class Maze
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
		int n, m, k;
		Cell[][] maze;
		Node[] nodes;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			k = in.nextInt();
			maze = new Cell[n][m];
			nodes = new Node[n * m];

			int counter = 0;
			int nodesCount = 0;

			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < m; j++)
				{
					char ch;

					while (in.isSpaceChar(ch = (char) in.read()))
						;

					if (ch == '.')
					{
						nodes[nodesCount] = new Node(nodesCount, i, j);
						maze[i][j] = new Cell(counter++, nodesCount++, ch);
						connect(i, j);
					}
					else
						maze[i][j] = new Cell(counter++, -1, ch);
				}
			}

			dfs(0);

			for (int i = 0; i < nodesCount; i++)
			{
				if (nodes[i].delete)
				{
					int row, column;

					row = nodes[i].row;
					column = nodes[i].column;
					maze[row][column].ch = 'X';
				}
			}

			for (int i = 0; i < n; i++, out.println())
				for (int j = 0; j < m; j++)
					out.print(maze[i][j].ch);
		}

		void connect(int row, int column)
		{
			if (row > 0)
			{
				if (maze[row - 1][column].isEmpty)
				{
					int nodeIndex, upperNodeIndex;

					nodeIndex = maze[row][column].nodeIndex;
					upperNodeIndex = maze[row - 1][column].nodeIndex;

					nodes[nodeIndex].adj.add(upperNodeIndex);
					nodes[upperNodeIndex].adj.add(nodeIndex);
				}
			}

			if (column > 0)
			{
				if (maze[row][column - 1].isEmpty)
				{
					int nodeIndex, leftNodeIndex;

					nodeIndex = maze[row][column].nodeIndex;
					leftNodeIndex = maze[row][column - 1].nodeIndex;

					nodes[nodeIndex].adj.add(leftNodeIndex);
					nodes[leftNodeIndex].adj.add(nodeIndex);
				}
			}
		}

		void dfs(int node)
		{
			Node temp = nodes[node];
			temp.visited = true;

			Iterator<Integer> iterator = temp.adj.iterator();
			boolean entered, allChildrenDeleted;

			entered = false;
			allChildrenDeleted = true;

			while (iterator.hasNext())
			{
				int curr = iterator.next();

				if (!nodes[curr].visited)
				{
					entered = true;
					dfs(curr);

					if (!nodes[curr].delete)
						allChildrenDeleted = false;
				}
			}

			if ((!entered && k > 0) || (allChildrenDeleted && k > 0))
			{
				temp.delete = true;
				k--;
			}
		}

		public class Cell
		{
			int index, nodeIndex;
			char ch;
			boolean isEmpty;

			public Cell(int index, int nodeIndex, char ch)
			{
				this.index = index;
				this.nodeIndex = nodeIndex;
				this.ch = ch;
				isEmpty = (ch == '.');
			}

		}

		class Node
		{
			int index, row, column;
			List<Integer> adj;
			boolean visited, delete;

			public Node(int index, int row, int column)
			{
				this.index = index;
				this.row = row;
				this.column = column;
				adj = new ArrayList<>();
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
