package com.codechef.competitions.longcompetitions.year2016.may;

import java.io.*;
import java.util.*;

/**
 * Created by rahulkhairwar on 15/05/16.
 */
class EasyQueries
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver();

		solver.solve(1, in, out);

		out.flush();

		in.close();
		out.close();
	}

	static class Solver
	{
		int n, q, a, b, c, d, k, arr[];
		Query[] queries;
		static int sqrtN;
//		TreeSet<Integer> treeSet;
		InputReader in;
		OutputWriter out;


		void solve2(int testNumber, InputReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;

			n = in.nextInt();
			q = in.nextInt();
//			arr = in.nextIntArray(n);

			arr = new int[n];
			queries = new Query[q];

/*			treeSet = new TreeSet<>(new Comparator<Integer>()
			{
				@Override public int compare(Integer o1, Integer o2)
				{
					if (o1.equals(o2))
						return 1;

					return Integer.compare(o1, o2);
				}
			});*/

			for (int i = 0; i < n; i++)
			{
				arr[i] = in.nextInt();
//				treeSet.add(arr[i]);
			}

			sqrtN = (int) Math.sqrt(n);

			for (int i = 0; i < q; i++)
			{
				a = in.nextInt();
				b = in.nextInt();
				c = in.nextInt();
				d = in.nextInt();
				k = in.nextInt();

				if (b <= d)
					queries[i] = new Query(b, d, k, i);
				else
					queries[i] = new Query(d, b, k, i);
			}

			Arrays.sort(queries, new Comparator<Query>()
			{
				@Override public int compare(Query o1, Query o2)
				{
					if (o1.blockNumber == o2.blockNumber)
						return Integer.compare(o1.right, o2.right);

					return Integer.compare(o1.blockNumber, o2.blockNumber);
				}
			});

			int left, right;
			int[] answer;

			left = right = 0;
			answer = new int[q];

			for (int i = 0; i < q; i++)
			{
				while (left < queries[i].left)
				{
					delete(arr[left]);
					left++;
				}

				while (left > queries[i].left)
				{
					add(arr[left]);
					left--;
				}

				while (right < queries[i].right)
				{
					add(arr[right]);
					right++;
				}

				while (right > queries[i].right)
				{
					delete(arr[right]);
					right--;
				}

/*				if (k > treeSet.size())
				{
					answer[queries[i].queryNumber] = -1;

					continue;
				}

				Iterator<Integer> iterator = treeSet.iterator();

				while (k-- > 1)
					iterator.next();

				answer[queries[i].queryNumber] = iterator.next();*/
			}

			for (int i = 0; i < q; i++)
				out.println(answer[i]);
		}

		void solve(int testNumber, InputReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;

			n = in.nextInt();
			q = in.nextInt();
//			arr = in.nextIntArray(n);

//			arr = new int[n];
			arr = in.nextIntArray(n);
			tree = new int[4 * n];

			buildTree(1, 0, n);

//			createTree(1, 0, n);
		}

		int[] tree;

		int createTree(int node, int tSI, int tEI)
		{
			if (tSI > tEI)
				return Integer.MAX_VALUE;

			if (tSI == tEI)
			{
				tree[node] = arr[tSI];

				return tree[node];
			}

			int left, right, mid;

			mid = tSI + (tEI - tSI) / 2;

			left = createTree(2 * node, tSI, mid);
			right = createTree(2 * node + 1, mid + 1, tEI);

			tree[node] = Math.min(left, right);

			return tree[node];
		}

		int query(int node, int tSI, int tEI, int rSI, int rEI)
		{
			if (tSI > tEI)
				return Integer.MAX_VALUE;

			if (tSI == tEI)
				return tree[node];

			if (tSI >= rSI && tEI <= rEI)
				return tree[node];

			return 0;
		}

		void buildTree(int currentNode, int treeStart, int treeEnd)
		{
			if (treeStart > treeEnd)
				return;

			if (treeStart == treeEnd)
			{
				tree[currentNode] = arr[treeStart];

				return;
			}

			int mid = (treeStart + treeEnd) / 2;

			buildTree(2 * currentNode, treeStart, mid);
			buildTree(2 * currentNode + 1, mid + 1, treeEnd);

			tree[currentNode] = Math.min(tree[2 * currentNode],
					tree[2 * currentNode + 1]);
		}

		int queryTree(int currentNode, int treeStart, int treeEnd,
							 int queryRangeStart, int queryRangeEnd)
		{
			if (treeStart > treeEnd || treeStart > queryRangeEnd
					|| treeEnd < queryRangeStart)
				return Integer.MAX_VALUE;

			if (treeStart >= queryRangeStart && treeEnd <= queryRangeEnd)
				return tree[currentNode];

			int mid, leftChildMax, rightChildMax;

			mid = (treeStart + treeEnd) / 2;
			leftChildMax = queryTree(2 * currentNode, treeStart, mid,
					queryRangeStart, queryRangeEnd);
			rightChildMax = queryTree(2 * currentNode + 1, mid + 1, treeEnd,
					queryRangeStart, queryRangeEnd);

			return Math.min(leftChildMax, rightChildMax);
		}


		void add(int num)
		{
//			treeSet.add(num);
		}

		void delete(int num)
		{
//			treeSet.remove(num);
		}

		static class Query
		{
			int left, right, k, blockNumber, queryNumber;

			public Query(int left, int right, int k, int queryNumber)
			{
				this.left = left;
				this.right = right;
				this.k = k;
				this.queryNumber = queryNumber;
				blockNumber = left / sqrtN;
			}
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
