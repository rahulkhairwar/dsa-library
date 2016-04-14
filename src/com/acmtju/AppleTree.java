package com.acmtju;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Question <a href="http://acm.tju.edu.cn/toj/showp.php?pid=2840">link</a>
 */
public class AppleTree
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver(in, out);

		int i = 0;

//		while (in.hasNext())
		solver.solve(i++);

		out.flush();

		in.close();
		out.close();
	}

	static class Solver
	{
		int n, m, count;
		Node[] nodes;
		List fallingValues;
		InputReader in;
		OutputWriter out;

		void solve(int testNumber)
		{
			while (true)
			{
				n = in.nextInt();
				m = in.nextInt();
				count = 0;
				fallingValues = new ArrayList();

				if (n == 0 && m == 0)
					return;

				createGraph();
				dfs(0);

				int max, size;

				max = Integer.MIN_VALUE;
				size = fallingValues.size();

				for (int i = 0; i < size; i++)
				{
					Point point = ((Point) fallingValues.get(i));
					int temp = nodes[point.x].fallingValue;

					if (temp > max)
						max = temp;
				}

				if (size == 0)
					out.println("No apple");
				else
					out.println(max);
			}
		}

		void createGraph()
		{
			nodes = new Node[n];

			for (int i = 0; i < m; i++)
			{
				int from, to;

				from = in.nextInt() - 1;
				to = in.nextInt() - 1;

				if (nodes[from] == null)
					nodes[from] = new Node(-1, -1, -1, new ArrayList(), false);

				nodes[from].adj.add(to);

				if (nodes[to] == null)
					nodes[to] = new Node(-1, -1, -1, new ArrayList(), false);

				nodes[to].adj.add(from);
			}

			for (int i = 0; i < n; i++)
				nodes[i].deliciousValue = in.nextInt();
		}

		boolean dfs(int node)
		{
			Node temp = nodes[node];

			temp.visited = true;
			temp.visitingTime = temp.low = count++;

			Iterator iterator = temp.adj.iterator();

			while (iterator.hasNext())
			{
				int curr = (Integer) iterator.next();

				if (!nodes[curr].visited)
				{
					nodes[curr].parent = node;

					dfs(curr);

					temp.low = Math.min(temp.low, nodes[curr].low);

					if (temp.visitingTime < nodes[curr].low)
					{
						temp.fallingValue += nodes[curr].deliciousValue;
						temp.fallingValue += nodes[curr].fallingValue;
						fallingValues.add(new Point(node, curr));
					}
				}
				else if (temp.parent != curr)
					temp.low = Math.min(temp.low, nodes[curr].visitingTime);
			}

			return false;
		}

		static class Node
		{
			int parent, visitingTime, low, deliciousValue, fallingValue;
			List<Integer> adj;
			boolean visited;

			public Node(int parent, int visitingTime, int low, List<Integer> adj, boolean visited)
			{
				this.parent = parent;
				this.visitingTime = visitingTime;
				this.low = low;
				this.adj = adj;
				this.visited = visited;
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
			int c =  read();

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
			if (number == 1 || number == 0 || power == 0) return 1;

			if (power == 1) return number;

			if (power % 2 == 0) return power(number * number, power / 2);
			else return power(number * number, power / 2) * number;
		}

		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0) return 1;

			number = mod(number, mod);

			if (power == 1) return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0) return modPower(square, power / 2, mod);
			else return mod(modPower(square, power / 2, mod) * number, mod);
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
