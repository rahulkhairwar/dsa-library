package com.codeforces.competitions.year2019.globalround2;

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
		long[] s;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			s = in.nextLongArray(n);
			q = in.nextInt();
			Arrays.sort(s);

			TreeMap<Long, Integer> map = new TreeMap<>(Long::compareTo);

			for (int i = 1; i < n; i++)
			{
				long diff = s[i] - s[i - 1];

				map.merge(diff, 1, (a, b) -> a + b);
			}

			int size = map.size();
			Window[] windows = new Window[size];
			int iterator = 0;

			for (Map.Entry<Long, Integer> entry : map.entrySet())
			{
				long diff = entry.getKey();
				int cnt = entry.getValue();

				windows[iterator] = new Window(diff, cnt, cnt * diff, cnt);

				if (iterator > 0)
				{
					windows[iterator].cumCnt += windows[iterator - 1].cumCnt;
					windows[iterator].cumSum += windows[iterator - 1].cumSum;
				}

				iterator++;
			}

			while (q-- > 0)
			{
				long l = in.nextLong();
				long r = in.nextLong();
				long diff = r - l + 1;
				int ind = binarySearch(diff, windows);

				if (ind == -1)
				{
					long ans = diff * n;

					out.print(ans + " ");
				}
				else
				{
					long ans = diff + windows[ind].cumSum + (windows[size - 1].cumCnt - windows[ind].cumCnt) * diff;

					out.print(ans + " ");
				}
			}
		}

		int binarySearch(long diff, Window[] windows)
		{
			int low = 0;
			int high = windows.length - 1;

			while (low <= high)
			{
				int mid = low + high >> 1;

				if (windows[mid].jumpSize < diff)
				{
					if (mid == windows.length - 1 || windows[mid + 1].jumpSize >= diff)
						return mid;

					low = mid + 1;
				}
				else
					high = mid - 1;
			}

			return -1;
		}

		class Window
		{
			long jumpSize, cumCnt, cumSum;
			int cnt;

			Window(long jumpSize, long cumCnt, long cumSum, int cnt)
			{
				this.jumpSize = jumpSize;
				this.cumCnt = cumCnt;
				this.cumSum = cumSum;
				this.cnt = cnt;
			}

		}

		Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override public void run()
		{
			solve();
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
			long[] array = new long[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextLong();

			return array;
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

		InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	public TaskD(InputStream inputStream, OutputStream outputStream)
	{
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
