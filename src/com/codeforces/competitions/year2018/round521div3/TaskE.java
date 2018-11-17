package com.codeforces.competitions.year2018.round521div3;

import java.io.*;
import java.util.*;

public final class TaskE
{
	public static void main(String[] args)
	{
		new TaskE(System.in, System.out);
	}

	static class Solver implements Runnable
	{
        int n;
        Map<Integer, Integer> map;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			map = new HashMap<>();

			for (int i = 0; i < n; i++)
			{
				int topic = in.nextInt();
				Integer cnt = map.get(topic);

				if (cnt == null)
					map.put(topic, 1);
				else
					map.put(topic, cnt + 1);
			}

			List<Integer> list = new ArrayList<>();

			for (Map.Entry<Integer, Integer> entry : map.entrySet())
				list.add(entry.getValue());

			Collections.sort(list);

			long max = 0;
			int previousStart = 0;

			for (int i = 0; i < list.size(); i++)
			{
				int cnt = list.get(i);

				for (int startWith = previousStart + 1; startWith <= cnt; startWith++)
				{
					long req = startWith * 2;
					long tot = startWith;
					int low = i + 1;

					while (true)
					{
						int ind = binarySearch(list, low, (int) req);

						if (ind < 0)
							break;

						tot += req;
						req += req;
						low = ind + 1;
					}

					max = Math.max(max, tot);
				}

				previousStart = cnt;
			}

			out.println(max);
		}

		int binarySearch(List<Integer> list, int low, int x)
		{
			int high = list.size() - 1;

			while (low <= high)
			{
				int mid = low + high >> 1;

				if (list.get(mid) >= x)
				{
					if (mid == low || list.get(mid - 1) < x)
						return mid;

					high = mid - 1;
				}
				else
					low = mid + 1;
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
