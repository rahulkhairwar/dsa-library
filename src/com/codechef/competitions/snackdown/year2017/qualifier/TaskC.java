package com.codechef.competitions.snackdown.year2017.qualifier;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class TaskC
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve2();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		int t, n, q;
		int[] arr;
		long[] pre;
		InputReader in;
		PrintWriter out;

		void solve2()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				q = in.nextInt();
				arr = in.nextIntArray(n);
				pre = new long[n];
				Arrays.sort(arr);

				pre[0] = arr[0];

				for (int i = 1; i < n; i++)
					pre[i] = pre[i - 1] + arr[i];

				while (q-- > 0)
				{
					int k, low, high, mid;

					k = in.nextInt();
					low = 0;
					high = n;

					while (low <= high)
					{
						mid = low + ((high - low) >> 1);

						boolean poss = possible(mid, k);

						if (poss)
						{
							if (mid == n || !possible(mid + 1, k))
							{
								out.println(mid);

								break;
							}

							low = mid + 1;
						}
						else
							high = mid - 1;
					}
				}
			}
		}

		boolean possible(int size, int k)
		{
			if (size == 0)
				return true;

			// range = [n - size, n - 1].
			// finding shortest valid snake in subarray.
			int pos = binarySearch(n - size, n - 1, k);

			if (pos == -1)
				pos = n;

			if (n - pos >= size)
				return true;

			long req = (long) k * (pos - n + size) - (pre[pos - 1] - (n - size - 1 < 0 ? 0 : pre[n - size - 1]));

			return req <= n - size;
		}

		int binarySearch(int left, int right, int val)
		{
			int low, high, mid;

			low = left;
			high = right;

			while (low <= high)
			{
				mid = low + ((high - low) >> 1);

				if (arr[mid] >= val)
				{
					if (mid == left || arr[mid - 1] < val)
						return mid;

					high = mid - 1;
				}
				else
				{
					if (mid == right)
						return -1;

					if (arr[mid + 1] >= val)
						return mid + 1;

					low = mid + 1;
				}
			}

			return -1;
		}

		public Solver(InputReader in, PrintWriter out)
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

}

/*

1
10 15
2 4 5 5 6 7 8 8 12 20
2 4 5 6 7 8 9 10 11 12 14 15 20 25 105
: 10 9 9 8 7 6 5 4 4 3 2 2 2 1 0

1
10 1
2 4 5 5 6 7 8 8 12 20
14

2
5 2
21 9 5 8 10
10
15
5 1
1 2 3 4 5
100
: 3 1 0

1
9 1
427863920 550983107 207364077 417604115 871923166 721923368 710588228 493538507 594661713
898065849
: 0

*/
