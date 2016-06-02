package com.hackerearth.practice.easy.year2016;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;

/**
 * Created by rahulkhairwar on 23/05/16.
 */
public class FightInNinjaWorld
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
		int t, n;
		HashMap<Integer, Node> disjointSets;
		InputReader in;
		OutputWriter out;

		public Solver(InputReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
		}

		void solve(int testNumber)
		{
			t = in.nextInt();

			while (t-- > 0)
			{

			}
		}

		void getFights()
		{
			for (int i = 1; i <= n; i++)
			{
				int from, to;

				from = in.nextInt();
				to = in.nextInt();


			}
		}

		void makeSet(int key)
		{
			disjointSets.put(key, new Node(key));
		}

		Node findParent(Node node)
		{
			if (node.key == node.parent.key)
				return node;
			else
			{
				Node parent = findParent(node.parent);
				node.parent = parent;

				return parent;
			}
		}

		void union(Node first, Node second)
		{
			if (first.parent.key == second.parent.key)
				return;

			Node firstParent, secondParent;

			firstParent = findParent(first);
			secondParent = findParent(second);

			if (firstParent.height > secondParent.height)
			{
				secondParent.parent = firstParent;
				firstParent.size += secondParent.size;
			}
			else if (firstParent.height < secondParent.height)
			{
				firstParent.parent = secondParent;
				secondParent.size += firstParent.size;
			}
			else
			{
				secondParent.parent = firstParent;
				firstParent.size += secondParent.size;
				firstParent.height++;
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

		class Pair
		{
			Node left, right;
			int connectIndex;
			boolean isConnectedToLeft;

			public Pair()
			{
				connectIndex = -1;
			}

		}

	}

/*	static class Solver
	{
		int t, n, count;
		//		int[] used;
		Used[] used;
		Pair[] pairs;
		HashMap<Integer, Node>[] disjointSets;
		InputReader in;
		OutputWriter out;

		public Solver(InputReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
		}

		void solve(int testNumber)
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				count = 0;

				used = new Used[(int) (2 * 1e4 + 5)];
				pairs = new Pair[n + 1];

				Arrays.fill(used, -1);
				getFights();
			}
		}

		void getFights()
		{
			for (int i = 0; i < n; i++)
			{
				int a, b;

				a = in.nextInt();
				b = in.nextInt();

				if (used[a] == null && used[b] == null)
				{
					makePair(new Node(a), new Node(b));
					used[a] = new Used(count, true, true);
					used[b] = new Used(count, true, false);
					count++;
				}
				else if (used[a] == null)
				{
					if (used[b].isInFirst)
					{
						used[a] = new Used(used[b].index, true, false);
						union(new Node(a), pairs[used[b].index].second);
					}
					else
					{
						used[a] = new Used(used[b].index, true, true);
						union(new Node(a), pairs[used[b].index].first);
					}
				}
				else if (used[b] == null)
				{
					if (used[a].isInFirst)
					{
						used[b] = new Used(used[a].index, true, false);
						union(new Node(b), pairs[used[a].index].second);
					}
					else
					{
						used[b] = new Used(used[a].index, true, true);
						union(new Node(b), pairs[used[a].index].first);
					}
				}
				else
				{
					if (used[a].isInFirst)
					{
						if (used[b].isInFirst)
						{
							union(pairs[used[a].index].first, pairs[used[b].index].first);
							pairs[used[a].index].firstConnectedTo = used[b].index;
							pairs[used[b].index].firstConnectedTo = used[a].index;
						}
						else
						{
							union(pairs[used[a].index].first, pairs[used[b].index].second);
							pairs[used[a].index].firstConnectedTo = used[b].index;
							pairs[used[b].index].secondConnectedTo = used[a].index;
						}
					}
					else
					{

					}
				}
			}
		}

		void makePair(Node first, Node second)
		{
			pairs[count] = new Pair(first, second);
		}

		Node findParent(Node node)
		{
			if (node.key == node.parent.key)
				return node;
			else
			{
				Node parent = findParent(node.parent);
				node.parent = parent;

				return parent;
			}
		}

		void union(Node first, Node second)
		{
			if (first.parent.key == second.parent.key)
				return;

			Node firstParent, secondParent;

			firstParent = findParent(first);
			secondParent = findParent(second);

			if (firstParent.height > secondParent.height)
			{
				secondParent.parent = firstParent;
				firstParent.size += secondParent.size;
			}
			else if (firstParent.height < secondParent.height)
			{
				firstParent.parent = secondParent;
				secondParent.size += firstParent.size;
			}
			else
			{
				secondParent.parent = firstParent;
				firstParent.size += secondParent.size;
				firstParent.height++;
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

		class Used
		{
			int index;
			boolean used, isInFirst;

			public Used(int index, boolean used, boolean isInFirst)
			{
				this.used = used;
				this.isInFirst = isInFirst;
			}

		}

		class Pair
		{
			Node first, second;
			int firstParent, secondParent, firstConnectedTo, secondConnectedTo;

			public Pair(Node first, Node second)
			{
				this.first = first;
				this.second = second;
				firstParent = first.key;
				secondParent = second.key;
				firstConnectedTo = secondConnectedTo = -1;
			}

		}

	}*/

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
