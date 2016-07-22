package com.codechef.practice.easy.year2016.maytoaugust;

import java.io.*;
import java.util.*;

/**
 * Created by rahulkhairwar on 12/07/16.
 */
class Matrix implements Runnable
{
	public static void main(String[] args) throws InterruptedException
	{
		Thread thread = new Thread(null, new Matrix(), "Matrix", 1 << 27);

		thread.start();
		thread.join();
	}

	public Matrix()
	{
	}

	@Override public void run()
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);

		Solver solver2 = new Solver(in, out);
		solver2.solve(1);

		out.flush();

		in.close();
		out.close();
	}

	static class Solver
	{
		int t, n, m, q, maxSize, visitedCount;
		long answer;
		Node[] disjointSets;
		Cell[][] cells;
		InputReader in;
		OutputWriter out;

		void solve(int testNumber)
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				m = in.nextInt();
				q = in.nextInt();
				maxSize = 1;
				visitedCount = 0;
				answer = 0;
				cells = new Cell[n + 1][m + 1];
				disjointSets = new Node[n * m + 1];

				int counter = 1;

				for (int i = 0; i < n; i++)
					for (int j = 0; j < m; j++)
					{
						disjointSets[counter] = new Node(counter);
						cells[i][j] = new Cell(counter++);
					}

				Query[] queries = new Query[q];
				counter = 0;

				for (int i = 0; i < q; i++)
				{
					int type = in.nextInt();
					Query temp = new Query(type);

					if (type == 4)
					{
						queries[counter++] = temp;

						continue;
					}

					int x, y;

					x = in.nextInt();
					y = in.nextInt();
					x--;
					y--;
					temp.x1 = x;
					temp.y1 = y;

					if (type == 3)
					{
						x = in.nextInt();
						y = in.nextInt();
						x--;
						y--;
						temp.x2 = x;
						temp.y2 = y;
						queries[counter++] = temp;
					}
					else
					{
						if (type == 1)
						{
							if (!cells[x][y].right)
							{
								queries[counter++] = temp;
								cells[x][y].right = true;
							}
						}
						else
						{
							if (!cells[x][y].down)
							{
								queries[counter++] = temp;
								cells[x][y].down = true;
							}
						}
					}
				}

				dfs();

				for (int i = counter - 1; i >= 0; i--)
				{
					Query curr = queries[i];

					if (curr.type == 1)
						union(cells[curr.x1][curr.y1].index, cells[curr.x1][curr.y1 + 1].index);
					else if (curr.type == 2)
						union(cells[curr.x1][curr.y1].index, cells[curr.x1 + 1][curr.y1].index);
					else if (curr.type == 3)
					{
						int one, two;

						one = cells[curr.x1][curr.y1].index;
						two = cells[curr.x2][curr.y2].index;

						Node parentOne, parentTwo;

						parentOne = findParent(one);
						parentTwo = findParent(two);

						if (parentOne.parent.key == parentTwo.parent.key)
							answer++;
					}
					else
						answer += maxSize;
				}

				out.println(answer);
			}
		}

		void dfs()
		{
			int total = n * m;

			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < m; j++)
				{
					if (cells[i][j].visited)
						continue;

					cells[i][j].visited = true;
					visitedCount++;

					if (!cells[i][j].right && j < m - 1/* && !cells[i][j].visited*/)
						union(cells[i][j].index, cells[i][j + 1].index);

					if (!cells[i][j].down && i < n - 1/* && !cells[i + 1][j].visited*/)
						union(cells[i][j].index, cells[i + 1][j].index);

					if (i > 0 && !cells[i - 1][j].down /*&& !cells[i - 1][j].visited*/)
						union(cells[i][j].index, cells[i - 1][j].index);

					if (j > 0 && !cells[i][j - 1].right /*&& !cells[i][j - 1].visited*/)
						union(cells[i][j].index, cells[i][j - 1].index);

					if (visitedCount == total)
						return;
				}
			}
		}

		void union(int one, int two)
		{
			Node parentOne, parentTwo;

			parentOne = findParent(one);
			parentTwo = findParent(two);

			if (parentOne.key == parentTwo.key)
				return;

			maxSize = Math.max(maxSize, parentOne.size + parentTwo.size);

			if (parentOne.height > parentTwo.height)
			{
				parentTwo.parent = parentOne;
				parentOne.size += parentTwo.size;
			}
			else if (parentOne.height < parentTwo.height)
			{
				parentOne.parent = parentTwo;
				parentTwo.size += parentOne.size;
			}
			else
			{
				parentTwo.parent = parentOne;
				parentOne.size += parentTwo.size;
				parentOne.height++;
			}
		}

		Node findParent(int index)
		{
			Node temp = disjointSets[index];

			if (temp.key == temp.parent.key)
				return temp;
			else
			{
				Node parent = findParent(temp.parent.key);
				temp.parent = parent;

				return parent;
			}
		}

		class Node
		{
			int key, height, size;
			Node parent;

			public Node(int key)
			{
				this.key = key;
				height = 0;
				size = 1;
				parent = this;
			}

		}

		class Cell
		{
			int index;
			boolean right, down, visited;

			public Cell(int index)
			{
				this.index = index;
			}

		}

		class Query
		{
			int type, x1, x2, y1, y2;

			public Query(int type)
			{
				this.type = type;
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

	static class CMath
	{
		static long power(long number, long power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			if (power == 1)
				return number;

			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}

		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);
			else
				return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

	}

}
