package com.codechef.practice.medium.year2016;

import java.io.*;
import java.util.*;

class ChefAndProblems
{
	public static void main(String[] args)
	{
        InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		int n, m, k;
		int[] arr;
		int[][] memo;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			k = in.nextInt();
			arr = new int[n];

			List<Integer>[] pos = new List[m + 1];

			for (int i = 0; i <= m; i++)
				pos[i] = new ArrayList<>();

			for (int i = 0; i < n; i++)
			{
				arr[i] = in.nextInt();
				pos[arr[i]].add(i);
			}

			// keeping block size as sqrt(n) is slower than keeping it as 100. Probably because of the edge blocks
			// that we have to iterate through completely. So, by this logic, block size of 10 would be even better,
			// but then the pre computation cost and storage cost will be greater.
//			int blockSize = (int) Math.sqrt(n);
			int blockSize = 100;
			int blocks = (n + blockSize - 1) / blockSize;
			int[] found = new int[m + 1];

			memo = new int[blocks + 1][blocks + 1];

			for (int i = 0; i < n; i += blockSize)
			{
				Arrays.fill(found, -1);

				int max = 0;
				int x = i / blockSize;

				for (int j = i; j < n; j++)
				{
					if (j % blockSize == 0)
						memo[x][j / blockSize] = max;

					if (found[arr[j]] == -1)
						found[arr[j]] = j;
					else
						max = Math.max(max, j - found[arr[j]]);
				}

				memo[x][blocks] = max;
			}

			while (k-- > 0)
			{
				int left, right, s, t;

				left = in.nextInt() - 1;
				right = in.nextInt();
				s = (left + blockSize - 1) / blockSize;
				t = right / blockSize;

				int answer = memo[s][t];

				s *= blockSize;
				t *= blockSize;

				if (s > t)
					s = t;

				for (int i = left; i < s; i++)
				{
					int x = searchRight(pos[arr[i]], right - 1);

					if (x == -1)
						continue;

					answer = Math.max(answer, pos[arr[i]].get(x) - i);
				}

				for (int i = right - 1; i >= t; i--)
				{
					int x = searchLeft(pos[arr[i]], left);

					if (x == -1)
						continue;

					answer = Math.max(answer, i - pos[arr[i]].get(x));
				}

				out.println(answer);
			}
		}

		int searchRight(List<Integer> pos, int val)
		{
			int low, high, mid;

			low = 0;
			high = pos.size() - 1;

			while (low <= high)
			{
				mid = low + high >> 1;

				if (pos.get(mid) == val)
					return mid;

				if (pos.get(mid) < val)
				{
					if (mid == pos.size() - 1)
						return mid;

					if (pos.get(mid + 1) > val)
						return mid;

					low = mid + 1;
				}
				else
					high = mid - 1;
			}

			return -1;
		}

		int searchLeft(List<Integer> pos, int val)
		{
			int low, high, mid;

			low = 0;
			high = pos.size() - 1;

			while (low <= high)
			{
				mid = low + high >> 1;

				if (pos.get(mid) == val)
					return mid;

				if (pos.get(mid) > val)
				{
					if (mid == 0)
						return mid;

					if (pos.get(mid - 1) < val)
						return mid;

					high = mid - 1;
				}
				else
					low = mid + 1;
			}

			return -1;
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

}

/*

7 7 5
4 5 6 6 5 7 4
6 6
5 6
3 5
3 7
1 7

7 7 1
4 5 6 6 5 7 4
3 5

*/
