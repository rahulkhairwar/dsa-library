package com.codeforces.competitions.year2018.round464div2;

import java.io.*;
import java.util.*;

public class TaskE
{
	public static void main(String[] args)
	{
		new TaskE(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int q, ptr;
		int[] arr;
		long[] cum;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			q = in.nextInt();
			arr = new int[q];
			cum = new long[q];

			while (q-- > 0)
			{
				int type = in.nextInt();

				if (type == 1)
					add(in.nextInt());
				else
				{
					if (ptr == 1)
					{
						out.println("0.000000000000");

						continue;
					}

					double max = arr[ptr - 1];
					int min = findMin();
					double mean = cum[min] + max;

					mean /= (min + 2);
					out.printf("%.12f\n", (max - mean));
				}
			}
		}

		void add(int x)
		{
			if (ptr == 0)
				cum[0] = arr[0] = x;
			else
			{
				arr[ptr] = x;
				cum[ptr] = cum[ptr - 1] + arr[ptr];
			}

			ptr++;
		}

		int findMin()
		{
			int low = 0;
			int high = ptr - 2;

			while (low <= high)
			{
				int mid = low + high >> 1;
				long sum = cum[mid] + arr[ptr - 1];
				long nextSum = cum[mid + 1] + arr[ptr - 1];
				double cnt = mid + 2;
				double nextCnt = cnt + 1;

				if (mid == ptr - 2)
					return mid;

				if (sum / cnt >= nextSum / nextCnt)
					low = mid + 1;
				else
				{
					if (mid == 0)
						return mid;

					if ((cum[mid - 1] + arr[ptr - 1]) / (double) (mid + 1) >= sum / cnt)
						return mid;

					high = mid - 1;
				}
			}

			return -1;
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

	public TaskE(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskE", 1 << 29);

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
