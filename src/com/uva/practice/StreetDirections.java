package com.uva.practice;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Something weird happened. Have an array of all edges (u, v) and in the algorithm I searched if an edge (a, b)
 * exists in the list of edges, and if that didn't exist, then (b, a) definitely must exist. So, in one place, I didn't
 * check for the positivity of the index of the edge (b, a) if edge (a, b) wasn't present in the list of edges, but
 * got Runtime Error because of that, and got Accepted if I checked for that condition. WEIRD.
 * <br />
 * Question <a href="https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=8&page=show_problem&problem=551">link</a>
 */
public class StreetDirections
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
		int n, m, count;
		Node[] nodes;
		Point[] allRoads;
		List<Point> unidirectionalRoads, bridges;
		int[] roadsDirection;
		boolean[] setOnce;
		Comparator<Point> comparator;
		InputReader in;
		OutputWriter out;

		void solve(int testNumber)
		{
			while (true)
			{
				n = in.nextInt();
				m = in.nextInt();

				if (n == 0 && m == 0)
					return;

				count = 0;
				allRoads = new Point[m];
				unidirectionalRoads = new ArrayList<>();
				bridges = new ArrayList<>();
				roadsDirection = new int[m];
				setOnce = new boolean[m];

				Arrays.fill(roadsDirection, 2);

				comparator = new Comparator<Point>()
				{
					@Override public int compare(Point o1, Point o2)
					{
						if (o1.x == o2.x)
							return Integer.compare(o1.y, o2.y);

						return Integer.compare(o1.x, o2.x);
					}
				};

				createGraph();

				Arrays.sort(allRoads, comparator);

				dfs(0);

				out.println(testNumber++ + "\n");

				for (int i = 0; i < m; i++)
				{
					if (roadsDirection[i] == 0)
						out.println((allRoads[i].x + 1 ) + " " + (allRoads[i].y + 1) + "\n" + (allRoads[i].y + 1) + " "
								+ (allRoads[i].x + 1));
					else if (roadsDirection[i] == 1)
						out.println((allRoads[i].x + 1) + " " + (allRoads[i].y + 1));
					else
						out.println((allRoads[i].y + 1) + " " + (allRoads[i].x + 1));
				}

				out.println("#");
			}
		}

		public void createGraph()
		{
			nodes = new Node[n];

			for (int i = 0; i < m; i++)
			{
				int from, to;

				from = in.nextInt() - 1;
				to = in.nextInt() - 1;

				allRoads[i] = new Point(from, to);

				if (nodes[from] == null)
					nodes[from] = new Node();

				nodes[from].adj.add(to);

				if (nodes[to] == null)
					nodes[to] = new Node();

				nodes[to].adj.add(from);
			}
		}

		public void dfs(int node)
		{
			Node temp = nodes[node];

			temp.visited = true;
			temp.visitingTime = temp.low = count++;

			Iterator<Integer> iterator = temp.adj.iterator();

			while (iterator.hasNext())
			{
				int curr = iterator.next();
				Point leftToRight, rightToLeft;
				int indexLeftToRight, indexRightToLeft;

				leftToRight = new Point(node, curr);
				rightToLeft = new Point(curr, node);

				indexLeftToRight = Arrays.binarySearch(allRoads, leftToRight, comparator);

				if (indexLeftToRight >= 0)
					indexRightToLeft = -1;
				else
					indexRightToLeft = Arrays.binarySearch(allRoads, rightToLeft, comparator);

				if (nodes[curr].visited && temp.parent != curr)
				{
					if (indexLeftToRight >= 0 && !setOnce[indexLeftToRight])
					{
						roadsDirection[indexLeftToRight] = 1;
						setOnce[indexLeftToRight] = true;
					}
					else if (indexRightToLeft >= 0 && !setOnce[indexRightToLeft])
					{
						roadsDirection[indexRightToLeft] = -1;
						setOnce[indexRightToLeft] = true;
					}
				}

				if (!nodes[curr].visited)
				{
					nodes[curr].parent = node;
					temp.outgoingCount++;
					nodes[curr].incomingCount++;

					dfs(curr);

					temp.low = Math.min(temp.low, nodes[curr].low);

					if (temp.visitingTime < nodes[curr].low)
					{
						int index = indexLeftToRight;

						if (index < 0)
							index = indexRightToLeft;

						roadsDirection[index] = 0;
					}
					else
					{
						if (indexLeftToRight >= 0)
							roadsDirection[indexLeftToRight] = 1;
						else if (indexRightToLeft >= 0)
							roadsDirection[indexRightToLeft] = -1;
					}
				}
				else if (temp.parent != curr)
					temp.low = Math.min(temp.low, nodes[curr].visitingTime);
			}
		}

		static class Node
		{
			int parent, visitingTime, low, incomingCount, outgoingCount;
			List<Integer> adj;
			boolean visited;

			public Node()
			{
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

	}

}

/*

7 10
1 2
1 3
2 4
3 4
4 5
4 6
5 7
6 7
2 5
3 6
7 9
1 2
1 3
1 4
2 4
3 4
4 5
5 6
5 7
7 6
0 0

8 8
1 2
1 5
2 3
2 4
2 6
4 7
6 7
6 8
0 0

 */
