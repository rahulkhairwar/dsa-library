package com.spoj.practice.classic;

import java.io.*;
import java.util.*;

public class ZQUERY
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

	private static class Solver
	{
		int n, q, arr[], pre[];
		int[][] memo;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			q = in.nextInt();
			arr = in.nextIntArray(n);
			pre = new int[n];

			pre[0] = arr[0];

			for (int i = 1; i < n; i++)
				pre[i] = pre[i - 1] + arr[i];

			int blockSize = 100;
			int blocks = (n + blockSize - 1) / blockSize;

			memo = new int[blocks + 1][blocks + 1];

			for (int i = 0; i < n; i += blockSize)
			{
				int max = 0;
				int x = i / blockSize;
				HashMap<Integer, Integer> found = new HashMap<>(n << 1);

				for (int j = i; j < n; j++)
				{
					if (j % blockSize == 0)
						memo[x][j / blockSize] = max;

					if (found.containsKey(pre[j]))
						max = Math.max(max, j - found.get(pre[j]));
					else
						found.put(pre[j], j);
				}

				memo[x][blocks] = max;
			}

			Map<Integer, List<Integer>> pos = new HashMap<>(n << 1);

			for (int i = 0; i < n; i++)
				pos.computeIfAbsent(pre[i], k -> new ArrayList<>()).add(i);

			while (q-- > 0)
			{
				int left, right, s, t;

				left = in.nextInt() - 1;
				right = in.nextInt() - 1;
				s = (left + blockSize - 1) / blockSize;
				t = right / blockSize;

				// query is divided as : left....[s....t]....right
				int ans = memo[s][t];

				s *= blockSize;
				t *= blockSize;

				if (s > t)
					s = t;

				for (int i = left; i <= s; i++)
				{
					int x;

					if (i == 0)
					{
						x = searchRight(pos.get(0), right);

						if (x != -1)
							ans = Math.max(ans, pos.get(0).get(x) + 1);
					}
					else
					{
						x = searchRight(pos.get(pre[i - 1]), right);

						if (x != -1)
							ans = Math.max(ans, pos.get(pre[i - 1]).get(x) - i + 1);
					}
				}

				for (int i = right; i >= t; i--)
				{
					int x = searchLeft(pos.get(pre[i]), left - 1);

					if (x != -1)
						ans = Math.max(ans, i - pos.get(pre[i]).get(x));
				}

				out.println(ans);
			}
		}

		int searchRight(List<Integer> pos, int right)
		{
			if (pos == null)
				return -1;

			int len = pos.size();
			int low, high, mid;

			low = 0;
			high = len - 1;

			while (low <= high)
			{
				mid = low + high >> 1;

				if (pos.get(mid) == right)
					return mid;
				else if (pos.get(mid) < right)
				{
					if (mid == len - 1)
						return mid;

					if (pos.get(mid + 1) > right)
						return mid;

					low = mid + 1;
				}
				else
				{
					if (mid == 0)
						return -1;

					if (pos.get(mid - 1) <= right)
						return mid - 1;

					high = mid - 1;
				}
			}

			return -1;
		}

		int searchLeft(List<Integer> pos, int left)
		{
			if (pos == null)
				return -1;

			int len = pos.size();
			int low, high, mid;

			low = 0;
			high = len - 1;

			while (low <= high)
			{
				mid = low + high >> 1;

				if (pos.get(mid) == left)
					return mid;
				else if (pos.get(mid) < left)
				{
					if (mid == len - 1)
						return -1;

					if (pos.get(mid + 1) == left)
						return mid + 1;

					low = mid + 1;
				}
				else
				{
					if (mid == 0)
						return mid;

					if (pos.get(mid - 1) < left)
						return mid;

					high = mid - 1;
				}
			}

			return -1;
		}

		Solver(InputReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	private static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		InputReader(InputStream stream)
		{
			this.stream = stream;
		}

		int read()
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

		int nextInt()
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

		int[] nextIntArray(int arraySize)
		{
			int array[] = new int[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextInt();

			return array;
		}

		boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		void close()
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

	private static class OutputWriter
	{
		private PrintWriter writer;

		OutputWriter(OutputStream stream)
		{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
		}

		void println(int x)
		{
			writer.println(x);
		}

		void flush()
		{
			writer.flush();
		}

		void close()
		{
			writer.close();
		}

	}

}

/*

6 4
1 1 1 -1 -1 -1
1 3
1 4
1 5
1 6

6 1
1 1 1 -1 -1 -1
1 6

*/
