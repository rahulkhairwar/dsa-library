package com.spoj.practice.classic;

import java.io.*;
import java.util.*;

public final class Matrix
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
		int n, m, matrix[][];
		int a, b, max;
		int[] bit;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			System.out.println("Program to count the number of subarrays with sum of all elements in a range.");
			System.out.println("Enter the number of elements : ");
			n = in.nextInt();
			System.out.println("Enter the array : ");

			int[] arr = new int[n];

			for (int i = 0; i < n; i++)
				arr[i] = in.nextInt();

			System.out.println("Enter the range [arr, b] : ");
			a = in.nextInt();
			b = in.nextInt();

			System.out.println("Answer with brute force : " + bruteForce(arr));

			compress(arr);

			System.out.println("arr after compressing : " + Arrays.toString(arr) + ", and a : " + a + ", b : " + b);
		}

		void compress(int[] arr)
		{
/*			int[] x = new int[n];
			int ans = 0;

			x[0] = arr[0];

			for (int i = 1; i < n; i++)
				x[i] = x[i - 1] + arr[i];

			for (int i = 0; i < n; i++)
			{
				for (int j = i; j < n; j++)
				{
					int y = x[j] - (i > 0 ? x[i - 1] : 0);

					if (arr <= y && y <= b)
						ans++;
				}
			}

			System.out.println("Answer with original array : " + ans);*/

			Map<Integer, Integer> map = new HashMap<>();
			int counter = 1;
			int len = arr.length;
			int[] copy = Arrays.copyOf(arr, len + 2);

			copy[n] = a;
			copy[n + 1] = b;

			for (int i = 1; i < n; i++)
				copy[i] = copy[i - 1] + copy[i];

			System.out.println("copy : " + Arrays.toString(copy));
			int[] compressed = Arrays.copyOf(copy, n);

			Arrays.sort(copy);

			for (int i = 0; i < len + 2; i++)
			{
				if (!map.containsKey(copy[i]))
					map.put(copy[i], counter++);
			}

			for (int i = 0; i < n; i++)
				compressed[i] = map.get(compressed[i]);

/*			for (int i = 0; i < n; i++)
				arr[i] = map.get(arr[i]);*/

			System.out.println("compressed cum arr : " + Arrays.toString(compressed));

			a = map.get(a);
			b = map.get(b);
			max = counter - 1;

			System.out.println("all subarray sums : ");

			int count = 0;
			int all = 0;

			for (int i = 0; i < n; i++)
			{
				System.out.println("***** i : " + i);
				for (int j = i; j < n; j++)
				{
					int cum = compressed[j] - (i > 0 ? compressed[i - 1] : 0);
					all++;
					System.out.println("\tcum : " + cum);

					if (cum >= a && cum <= b)
					{
//						System.out.print(cum + " ");
						count++;
					}
				}
			}

			System.out.println("\ntotal : " + count + ", all : " + all);
		}

		long bruteForce(int[] arr)
		{
			int[] cum = new int[n];

			cum[0] = arr[0];

			for (int i = 1; i < n; i++)
				cum[i] = cum[i - 1] + arr[i];

			int count = 0;

			for (int i = 0; i < n; i++)
			{
				for (int j = i; j < n; j++)
				{
					int subarraySum = cum[j] - (i > 0 ? cum[i - 1] : 0);

					if (subarraySum >= a && subarraySum <= b)
						count++;
				}
			}

			return count;
		}

		long optimised(int[] arr)
		{
			int cum = 0;

			for (int i = 0; i < n; i++)
			{
				cum += arr[i];

				int left, right;

				left = cum - a;
				right = cum - b;
			}

			return 0;
		}

		void add(int index)
		{
			while (index <= max)
			{
				bit[index]++;
				index += index & -index;
			}
		}

		int query(int index)
		{
			int answer = 0;

			while (index > 0)
			{
				answer += bit[index];
				index -= index & -index;
			}

			return answer;
		}

		void solve2(int testNumber)
		{
			n = in.nextInt();
			m = in.nextInt();
			matrix = new int[n + 1][m + 1];

			for (int i = 1; i <= n; i++)
				for (int j = 1; j <= m; j++)
					matrix[i][j] = in.nextInt();

			long[][] sum = new long[n + 1][m + 1];
			long a, b, count;

			a = in.nextInt();
			b = in.nextInt();
			count = 0;

			for (int i = 1; i <= n; i++)
				for (int j = 1; j <= m; j++)
					sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + matrix[i][j];


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

10
3 2 -3 -1 4 5 -2 3 4 -1
3 10

 */
