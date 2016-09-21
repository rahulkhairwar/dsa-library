package com.codeforces.competitions.year2015.round335div2;

import java.awt.*;
import java.io.*;
import java.util.*;

public final class Q4
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
		int n, m;
		Edge[] edges, copy;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			edges = new Edge[m];
			copy = new Edge[m];

			for (int i = 0; i < m; i++)
			{
				int weight, used;

				weight = in.nextInt();
				used = in.nextInt();
				edges[i] = new Edge(i, weight, used);
				copy[i] = new Edge(i, weight, used);
			}

			Arrays.sort(edges, new Comparator<Edge>()
			{
				@Override public int compare(Edge o1, Edge o2)
				{
					if (o1.weight == o2.weight)
						return Integer.compare(o2.used, o1.used);

					return Integer.compare(o1.weight, o2.weight);
				}
			});

			int[] weight = new int[n + 1];
			int counter = 2;

			for (int i = 0; i < m; i++)
				if (edges[i].used == 1)
					weight[counter++] = edges[i].weight;

			Deque<Point> pointDeque = new ArrayDeque<>();
			counter = 1;

			for (int i = 0; i < m; i++)
			{
				if (edges[i].used == 0)
				{
					if (pointDeque.size() == 0)
					{
						out.println(-1);

						return;
					}

					Point first = pointDeque.pollFirst();

					if (edges[i].weight < weight[first.y])
					{
						out.println(-1);

						return;
					}

					edges[i].from = first.x;
					edges[i].to = first.y;
					first.x++;

					if (first.x < first.y - 1)
						pointDeque.addFirst(first);
				}
				else
				{
					edges[i].from = counter;
					edges[i].to = ++counter;

					if (i > 0)
						pointDeque.addLast(new Point(1, counter));
				}
			}

			Arrays.sort(edges, new Comparator<Edge>()
			{
				@Override public int compare(Edge o1, Edge o2)
				{
					return Integer.compare(o1.index, o2.index);
				}
			});

			for (int i = 0; i < m; i++)
				out.println(edges[i].from + " " + edges[i].to);
		}

		class Edge
		{
			int index, weight, from, to;
			int used;

			public Edge(int index, int weight, int used)
			{
				this.index = index;
				this.weight = weight;
				this.used = used;
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

4 4
4 1
5 1
7 0
8 1

4 4
4 1
5 0
7 1
8 1

10 15
900000012 1
900000010 1
900000007 0
900000005 0
900000014 1
900000000 1
900000004 0
900000006 1
900000009 0
900000002 0
900000008 0
900000001 1
900000011 1
900000003 1
900000013 1

*/
