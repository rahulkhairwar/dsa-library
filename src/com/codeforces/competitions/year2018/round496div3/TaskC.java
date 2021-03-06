package com.codeforces.competitions.year2018.round496div3;

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
        long[] arr;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			arr = new long[n];

			Map<Long, Integer> map = new HashMap<>();
			Set<Long> set = new HashSet<>();

			for (int i = 0; i < n; i++)
			{
				arr[i] = in.nextInt();

				Integer cnt = map.get(arr[i]);

				if (cnt == null)
					map.put(arr[i], 1);
				else
					map.put(arr[i], cnt + 1);
			}

			for (int i = 0; i < n; i++)
			{
				long num = 2;

				while (num < arr[i])
					num *= 2;

				while (num < 1e10)
				{
					long get = num - arr[i];

					if (map.containsKey(get))
					{
						if (get == arr[i] && map.get(get) == 1)
						{
							num *= 2;

							continue;
						}

						set.add(arr[i]);
						set.add(get);

						break;
					}
					else
						num *= 2;
				}
			}

			int cnt = 0;

			for (long x : arr)
			{
				if (!set.contains(x))
					cnt++;
			}

			out.println(cnt);
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
