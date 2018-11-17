package com.codeforces.competitions.year2018.round521div3;

import java.io.*;
import java.util.*;

public final class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver implements Runnable
	{
        int n;
        int[] arr, leftMax, rightMax;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			arr = in.nextIntArray(n);
			leftMax = new int[n];
			rightMax = new int[n];
			leftMax[0] = arr[0];
			rightMax[n - 1] = arr[n - 1];

			for (int i = 1; i < n; i++)
				leftMax[i] = Math.max(leftMax[i - 1], arr[i]);

			for (int i = n - 2; i >= 0; i--)
				rightMax[i] = Math.max(rightMax[i + 1], arr[i]);

			long sum = 0;

			for (int x : arr)
				sum += x;

			int cnt = 0;
			StringBuilder ans = new StringBuilder("");

			for (int i = 0; i < n; i++)
			{
				if ((sum - arr[i]) == 2 * (Math.max(i < n - 1 ? rightMax[i + 1] : 0, i > 0 ? leftMax[i - 1] : 0)))
				{
					cnt++;
					ans.append(i + 1).append(" ");
				}
			}

			out.println(cnt);

			if (cnt > 0)
				out.println(ans.toString());
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

	public TaskC(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskC", 1 << 29);

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
