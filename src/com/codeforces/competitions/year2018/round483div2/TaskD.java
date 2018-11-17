package com.codeforces.competitions.year2018.round483div2;

import java.io.*;
import java.util.*;

public class TaskD
{
	public static void main(String[] args)
	{
		new TaskD(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, q;
		long[] arr, oddXor, evenXor, xor, cum;
		long[][] trees;
//		BufferedReader in;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			arr = in.nextLongArray(n);
			oddXor = new long[n];
			evenXor = new long[n];
			xor = new long[n];
			cum = new long[n];

			long[][] ans = new long[n][n];

			oddXor[0] = arr[0];
			evenXor[1] = arr[1];
			xor[0] = arr[0];
			xor[1] = arr[1];
			cum[0] = arr[0];
			cum[1] = arr[0] ^ arr[1];

/*			for (int i = 2; i < n; i += 2)
			{
				oddXor[i - 1] = oddXor[i - 2];
				oddXor[i] = oddXor[i - 1] ^ arr[i];
			}

			for (int i = 3; i < n; i += 2)
			{
				evenXor[i - 1] = evenXor[i - 2];
				evenXor[i] = evenXor[i - 1] ^ arr[i];
			}*/

			for (int i = 2; i < n; i++)
			{
				xor[i] = xor[i - 2] ^ arr[i];
//				cum[i] = cum[i - 1] ^ arr[i];
			}

			cum[0] = arr[0];

			for (int i = 1; i < n; i++)
				cum[i] = cum[i - 1] ^ arr[i];

			System.out.println("cum : " + Arrays.toString(cum));

			for (int i = 0; i < n; i++)
			{
				for (int j = i; j < n; j++)
					ans[i][j] = find(i, j);
			}

			System.out.println("ans :");

			for (int i = 0; i < n; i++, System.out.println())
			{
				for (int j = 0; j < n; j++)
					System.out.print(ans[i][j] + " ");
			}

			for (int i = n - 1; i >= 0; i--)
			{
				for (int j = i - 1; j >= 0; j--)
					ans[j][i] = Math.max(ans[j + 1][i], ans[j][i]);
			}
			System.out.println("ans :");

			for (int i = 0; i < n; i++, System.out.println())
			{
				for (int j = 0; j < n; j++)
					System.out.print(ans[i][j] + " ");
			}

			trees = new long[n][];

			for (int i = 0; i < n; i++)
			{
//				int size = (n - i) << 2;
				int size = n << 2;

				trees[i] = new long[size];
				buildTree(trees[i], ans[i], 1, 0, n - 1);
			}

			q = in.nextInt();

			while (q-- > 0)
			{
				int l = in.nextInt() - 1;
				int r = in.nextInt() - 1;

//				System.out.println();

				out.println(queryTree(trees[l], 1, 0, n - 1, l, r));
			}
		}

		long find(int l, int r)
		{
			int diff = r - l + 1;

			if (l == r)
				return arr[l];

//			System.out.println("l : " + l + ", r : " + r);
//			System.out.println("x[r] : " + xor[r] + ", x[l - 4] : " + (l > 3 ? xor[l - 4] : 0));

			if (diff % 2 == 0)
				return cum[r] ^ (l > 0 ? cum[l - 1] : 0);
			else
				return xor[r] ^ (l > 1 ? xor[l - 2] : 0);
		}

		void buildTree(long[] tree, long[] array, int node, int treeStart, int treeEnd)
		{
			if (treeStart > treeEnd)
				return;

			// i.e., leaf node
			if (treeStart == treeEnd)
			{
				tree[node] = array[treeStart];

				return;
			}

			int mid = (treeStart + treeEnd) / 2;

			// left child
			buildTree(tree, array, 2 * node, treeStart, mid);
			// right child
			buildTree(tree, array, 2 * node + 1, mid + 1, treeEnd);
			tree[node] = Math.max(tree[2 * node], tree[2 * node + 1]);
		}

		/**
		 * This method finds the maximum value in
		 * {@code [queryRangeStart, queryRangeEnd]} range.
		 *
		 * @param node       the 1-based index of the current node of the tree
		 * @param treeStart  the 0-based leftmost index of the range, which the current
		 *                   node stores value of
		 * @param treeEnd    the 0-based rightmost index of the range, which the current
		 *                   node stores value of
		 * @param rangeStart this method returns the maximum value in the range which
		 *                   starts at this index
		 * @param rangeEnd   this method returns the maximum value in the range which ends
		 *                   at this index
		 * @return the maximum value in the range
		 * [queryRangeStart, queryRangeEnd].
		 */
		static long queryTree(long[] tree, int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			// if the query range is completely out of the range that this node stores information of
			if (treeStart > treeEnd || treeStart > rangeEnd || treeEnd < rangeStart)
				return Integer.MIN_VALUE;

			// if the range that this node holds is completely inside of the qeury range
			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return tree[node];

			int mid;
			long leftChildMax, rightChildMax;

			mid = (treeStart + treeEnd) / 2;
			leftChildMax = queryTree(tree, 2 * node, treeStart, mid, rangeStart, rangeEnd);
			rightChildMax = queryTree(tree, 2 * node + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			return Math.max(leftChildMax, rightChildMax);
		}

		void debug(Object... o)
		{
			System.err.println(Arrays.deepToString(o));
		}

//		uncomment below line to change to BufferedReader
//		public Solver(BufferedReader in, PrintWriter out)
		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
		{
			try
			{
				solve();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

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

		public InputReader(InputStream stream)
		{
			this.stream = stream;
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

		static long min(long... arr)
		{
			long min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static long max(long... arr)
		{
			long max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static int max(int... arr)
		{
			int max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

	}

	static class Utils
	{
		static boolean nextPermutation(int[] arr)
		{
			for (int a = arr.length - 2; a >= 0; --a)
			{
				if (arr[a] < arr[a + 1])
				{
					for (int b = arr.length - 1; ; --b)
					{
						if (arr[b] > arr[a])
						{
							int t = arr[a];

							arr[a] = arr[b];
							arr[b] = t;

							for (++a, b = arr.length - 1; a < b; ++a, --b)
							{
								t = arr[a];
								arr[a] = arr[b];
								arr[b] = t;
							}

							return true;
						}
					}
				}
			}

			return false;
		}

	}

	public TaskD(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskD", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			in.close();
			out.flush();
			out.close();
		}
	}

}

/*

5
1 2 3 4 5
0

*/
