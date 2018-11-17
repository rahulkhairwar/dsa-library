package com.codeforces.competitions.year2018.round521div3;

import java.io.*;
import java.util.*;

public final class TaskF1
{
	public static void main(String[] args)
	{
		new TaskF1(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final long INF = (long) 1e15;
        int n, k, x;
        int[] arr;
        long[][][] dp;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			k = in.nextInt();
			x = in.nextInt();
			arr = in.nextIntArray(n);
			dp = new long[n + 1][n + 1][x + 1];

			for (int i = 0; i <= n; i++)
			{
				for (int j = 0; j <= n; j++)
					Arrays.fill(dp[i][j], -1);
			}

			long max = 0;

			for (int i = 0; i < k; i++)
			{
				// starting at arr[i]
				max = Math.max(max, arr[i] + find(i + 1, i, x - 1));
			}

			if (max == 0)
				out.println(-1);
			else
				out.println(max);
		}

		long find(int curr, int prev, int rem)
		{
			if (curr == n)
				return 0;

			if (rem == 0)
				return (n - 1 - prev < k ? 0 : -INF);

			if (curr - prev > k)
				return -INF;

			if (dp[curr][prev][rem] != -1)
				return dp[curr][prev][rem];

			long max = Math.max(arr[curr] + find(curr + 1, curr, rem - 1), find(curr + 1, prev, rem));

			return dp[curr][prev][rem] = max;
		}

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

	public TaskF1(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskF1", 1 << 29);

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

5 2 2
1 2 3 4 5

*/
