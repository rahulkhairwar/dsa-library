package com.codechef.competitions.longcompetitions.year2016.october;

import java.io.*;
import java.util.*;

class BigQueries
{
	public static void main(String[] args)
	{
/*		IR in = new IR(System.in);
		OutputWriter out = new OutputWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();*/

		try
		{
			InputReader in = new InputReader(new FileInputStream(new File(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA "
							+ "Workspace/Competitive Programming/src/com/codechef/competitions/longcompetitions"
							+ "/year2016/october" + "/input.txt")));
			OutputWriter out = new OutputWriter(new FileOutputStream(new File(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/codechef/competitions/longcompetitions"
							+ "/year2016/october/output.txt")));
			Solver solver = new Solver(in, out);

			solver.solve();
			in.close();
			out.flush();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	static class Solver
	{
		int t, n, q;
		int[] arr, two, five;
		Node[] tree;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				q = in.nextInt();
				arr = new int[n];
				two = new int[(int) (1e5 + 5)];
				five = new int[(int) (1e5 + 5)];
				tree = new Node[n << 2];

				pre();

				for (int i = 0; i < n; i++)
					arr[i] = in.nextInt();

				build(1, 0, n - 1);

				long ans = 0;

				for (int i = 0; i < q; i++)
				{
					int type, left, right;

					type = in.nextInt();
					left = in.nextInt() - 1;
					right = in.nextInt() - 1;

					if (type == 1)
					{
						int x, twos, fives;

						x = in.nextInt();
						twos = fives = 0;

						while (x % 2 == 0)
						{
							x /= 2;
							twos++;
						}

						while (x % 5 == 0)
						{
							x /= 5;
							fives++;
						}

						multiply(1, 0, n - 1, left, right, twos, fives);
					}
					else if (type == 2)
					{
						int y, twos, fives;

						y = in.nextInt();
						twos = fives = 0;

						while (y % 2 == 0)
						{
							y /= 2;
							twos++;
						}

						while (y % 5 == 0)
						{
							y /= 5;
							fives++;
						}

						replace(1, 0, n - 1, left, right, twos, fives, 1);
					}
					else
					{
						int twos, fives;

						twos = queryTwos(1, 0, n - 1, left, right);
						fives = queryFives(1, 0, n - 1, left, right);

						ans += Math.min(twos, fives);
						out.println(Math.min(twos, fives));
					}
					System.out.println("@@@@done query : " + i);

					if (type == 2)
					{
						System.out.println("nodes : ");

						for (int j = 1; j < n << 2; j++)
						{
							if (tree[j] == null)
								continue;

							System.out.println("j : " + j + ", node[j] => " + tree[j]);
						}
					}
				}

//				out.println(ans);

			}
		}

		void pre()
		{
			for (int i = 2; i <= 1e5; i += 2)
			{
				int curr = i;

				while (curr % 2 == 0)
				{
					two[i]++;
					curr /= 2;
				}
			}

			for (int i = 5; i <= 1e5; i += 5)
			{
				int curr = i;

				while (curr % 5 == 0)
				{
					five[i]++;
					curr /= 5;
				}
			}

			for (int i = 1; i <= 1e5; i++)
			{
				two[i] = two[i] + two[i - 1];
				five[i] = five[i] + five[i - 1];
			}
		}

		void build(int node, int treeStart, int treeEnd)
		{
			if (treeStart == treeEnd)
			{
				tree[node] = new Node(arr[treeStart]);
//				System.out.println("\tbuilt nd " + node + " : " + tree[node]);

				return;
			}

			int mid = treeStart + treeEnd >> 1;

			build(node << 1, treeStart, mid);
			build((node << 1) + 1, mid + 1, treeEnd);

			Node left, right;

			left = tree[node << 1];
			right = tree[(node << 1) + 1];

			tree[node] = new Node(left.twos + right.twos, left.fives + right.fives);
//			System.out.println("\tbuild nd " + node + " : " + tree[node]);
		}

		void multiply(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, int twos, int fives)
		{
			Node temp = tree[node];

			replacePush(node, treeStart, treeEnd);
			multiplyPush(node, treeStart, treeEnd);

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			{
				if (treeStart != treeEnd)
				{
					Node left, right;

					left = tree[node << 1];
					right = tree[(node << 1) + 1];
					left.updateTwos += twos;
					right.updateTwos += twos;
					left.updateFives += fives;
					right.updateFives += fives;
					left.update = right.update = true;
				}

				int range = treeEnd - treeStart + 1;

				temp.twos += range * twos;
				temp.fives += range * fives;

				return;
			}

			int mid = treeStart + treeEnd >> 1;

			multiply(node << 1, treeStart, mid, rangeStart, rangeEnd, twos, fives);
			multiply((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd, twos, fives);

			Node left, right;

			left = tree[node << 1];
			right = tree[(node << 1) + 1];
			temp.twos = left.twos + right.twos;
			temp.fives = left.fives + right.fives;
		}

		void replace(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, int twos, int fives,
					 int counter)
		{
			Node temp = tree[node];

			replacePush(node, treeStart, treeEnd);

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			{
				temp.leftCounter = counter;
				temp.rightCounter = counter + (treeEnd - treeStart);

				if (treeStart != treeEnd)
				{
					Node left, right;

					left = tree[node << 1];
					right = tree[(node << 1) + 1];
					left.replaceTwos = right.replaceTwos = twos;
					left.replaceFives = right.replaceFives = fives;
					left.updateTwos = right.updateTwos = left.updateFives = right.updateFives = 0;
					left.update = right.update = false;
					left.replace = right.replace = true;
				}

				int range = treeEnd - treeStart + 1;

				temp.twos =
						range * twos + two[temp.rightCounter] - two[temp.leftCounter > 0 ? temp.leftCounter - 1 : 0];
				temp.fives =
						range * fives + five[temp.rightCounter] - five[temp.leftCounter > 0 ? temp.leftCounter - 1 :
																	   0];
				temp.updateTwos = temp.updateFives = temp.replaceTwos = temp.replaceFives = 0;
				temp.update = temp.replace = false;

				return;
			}

			int mid = treeStart + treeEnd >> 1;

			replace(node << 1, treeStart, mid, rangeStart, rangeEnd, twos, fives, counter);
			replace((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd, twos, fives, counter + mid);

			Node left, right;

			left = tree[node << 1];
			right = tree[(node << 1) + 1];
			temp.twos = left.twos + right.twos;
			temp.fives = left.fives + right.fives;
		}

		void replacePush(int node, int treeStart, int treeEnd)
		{
			Node temp = tree[node];

			if (temp.replace)
			{
				if (treeStart != treeEnd)
				{
					Node left, right;

					left = tree[node << 1];
					right = tree[(node << 1) + 1];
					left.replace = right.replace = true;
					left.replaceTwos = right.replaceTwos = temp.replaceTwos;
					left.replaceFives = right.replaceFives = temp.replaceFives;
					left.twos = right.twos = left.fives = right.fives = /*left.ans = right.ans =*/ 0;
				}

				int range = treeEnd - treeStart + 1;

				temp.twos =
						range * temp.replaceTwos + two[temp.rightCounter] - two[temp.leftCounter > 0 ? temp.leftCounter
								- 1 : 0];
				temp.fives = range * temp.replaceFives + five[temp.rightCounter] - five[temp.leftCounter > 0 ?
																						temp.leftCounter - 1 : 0];
				temp.updateTwos = temp.updateFives = temp.replaceTwos = temp.replaceFives = 0;
				temp.update = temp.replace = false;
			}
		}

		void multiplyPush(int node, int treeStart, int treeEnd)
		{
			Node temp = tree[node];

			if (temp.update)
			{
				if (treeStart != treeEnd)
				{
					Node left, right;

					left = tree[node << 1];
					right = tree[(node << 1) + 1];
					left.update = right.update = true;
					left.updateTwos += temp.updateTwos;
					right.updateTwos += temp.updateTwos;
					left.updateFives += temp.updateFives;
					right.updateFives += temp.updateFives;
				}

				int range = treeEnd - treeStart + 1;

				temp.twos += range * temp.updateTwos;
				temp.fives += range * temp.updateFives;
				temp.update = false;
			}
		}

		int queryTwos(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			Node temp = tree[node];

			replacePush(node, treeStart, treeEnd);
			multiplyPush(node, treeStart, treeEnd);

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return 0;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			{
				System.out.println("\tqry nd " + node + ", 2s : " + temp.twos);
				return temp.twos;
			}

			int mid, left, right;

			mid = treeStart + treeEnd >> 1;
			left = queryTwos(node << 1, treeStart, mid, rangeStart, rangeEnd);
			right = queryTwos((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			return left + right;
		}

		int queryFives(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			Node temp = tree[node];

			replacePush(node, treeStart, treeEnd);
			multiplyPush(node, treeStart, treeEnd);

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return 0;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return temp.fives;

			int mid, left, right;

			mid = treeStart + treeEnd >> 1;
			left = queryFives(node << 1, treeStart, mid, rangeStart, rangeEnd);
			right = queryFives((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd);


			return left + right;
		}

		class Node
		{
			int twos, fives;    // counter for answer
			int updateTwos, updateFives;    // counter for multiplication updates
			int replaceTwos, replaceFives;    // counter for replace updates
			int leftCounter, rightCounter;
			boolean update, replace;    // flag for replace

			public Node(int num)
			{
				while (num % 2 == 0)
				{
					num /= 2;
					twos++;
				}

				while (num % 5 == 0)
				{
					num /= 5;
					fives++;
				}

				update = replace = false;
			}

			public Node(int twos, int fives)
			{
				this.twos = twos;
				this.fives = fives;
				update = replace = false;
			}

			@Override public String toString()
			{
				return "2s : " + twos + ", 5s : " + fives + ", u2 : " + updateTwos + ", u5 : " + updateFives + ", r2 : "
						+ replaceTwos + ", r5 : " + replaceFives + ", lc : " + leftCounter + ", rc : " + rightCounter;
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

		public float nextFloat()
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

		public double nextDouble()
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

		public void printf(String format, Object args)
		{
			writer.printf(format, args);
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

1
5 5
2 4 3 5 5
3 2 4
3 2 5
2 2 4 1
1 3 3 10
3 1 5

1
5 2
2 4 3 5 5
3 2 4
3 2 5

1
5 4
29 99 93 85 7
2 3 3 5
2 3 5 50
3 5 5
3 4 5

*/
