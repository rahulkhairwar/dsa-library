package com.codeforces.competitions.educational.year2018.round42;

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
		int n;
		long[] arr;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			arr = new long[n];

			TreeMap<Long, TreeSet<Integer>> map = new TreeMap<>(new Comparator<Long>()
			{
				@Override public int compare(Long o1, Long o2)
				{
					return Long.compare(o1, o2);
				}
			});

			for (int i = 0; i < n; i++)
			{
				arr[i] = in.nextLong();

				TreeSet<Integer> set = map.get(arr[i]);

				if (set == null)
				{
					set = new TreeSet<>();
					map.put(arr[i], set);
				}

				set.add(i);
			}

			while (map.size() > 0)
			{
				Map.Entry<Long, TreeSet<Integer>> entry = map.pollFirstEntry();
				TreeSet<Integer> set = entry.getValue();

				if (set.size() == 1)
					continue;

				int pos = set.pollFirst();

				arr[pos] = -1;
				pos = set.pollFirst();

				if (set.size() > 0)
					map.put(entry.getKey(), set);

				arr[pos] += arr[pos];

				TreeSet<Integer> ts = map.get(arr[pos]);

				if (ts == null)
				{
					ts = new TreeSet<>();
					map.put(arr[pos], ts);
				}

				ts.add(pos);
			}

			int minus = 0;

			for (long x : arr)
				if (x == -1)
					minus++;

			out.println(n - minus);

			for (long x : arr)
			{
				if (x == -1)
					continue;

				out.print(x + " ");
			}
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
