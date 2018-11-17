package com.codeforces.competitions.year2018.round521div3;

import java.io.*;
import java.util.*;

public final class TaskD
{
	public static void main(String[] args)
	{
		new TaskD(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, k, lim;
		int[] arr, map;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			lim = (int) 2e5 + 5;
			n = in.nextInt();
			k = in.nextInt();
			arr = new int[n];
			map = new int[lim];

			for (int i = 0; i < n; i++)
			{
				arr[i] = in.nextInt();
				map[arr[i]]++;
			}

			int low = 1;
			int high = n / k;

			while (low <= high)
			{
				int mid = low + high >> 1;

				if (poss(mid))
				{
					if (!poss(mid + 1))
					{
						print(mid);

						return;
					}

					low = mid + 1;
				}
				else
					high = mid - 1;
			}
		}

		boolean poss(int cnt)
		{
			PriorityQueue<Element> queue = new PriorityQueue<>((o1, o2) -> {
				if (o1.cnt == o2.cnt)
					return Integer.compare(o1.x, o2.x);

				return Integer.compare(o2.cnt, o1.cnt);
			});

			boolean[] vis = new boolean[lim];

			for (int i = 0; i < n; i++)
			{
				if (!vis[arr[i]])
				{
					queue.add(new Element(arr[i], map[arr[i]]));
					vis[arr[i]] = true;
				}
			}

			int taken = 0;

			while (taken < k)
			{
				if (queue.size() == 0)
					return false;

				Element first = queue.poll();

				first.cnt -= cnt;
				taken++;

				if (first.cnt < 0)
					return false;
				else if (first.cnt > 0)
					queue.add(first);
			}

			return true;
		}

		void print(int cnt)
		{
			PriorityQueue<Element> queue = new PriorityQueue<>((o1, o2) -> {
				if (o1.cnt == o2.cnt)
					return Integer.compare(o1.x, o2.x);

				return Integer.compare(o2.cnt, o1.cnt);
			});

			boolean[] vis = new boolean[lim];

			for (int i = 0; i < n; i++)
			{
				if (!vis[arr[i]])
				{
					queue.add(new Element(arr[i], map[arr[i]]));
					vis[arr[i]] = true;
				}
			}

			int taken = 0;

			while (taken < k)
			{
				Element first = queue.poll();

				first.cnt -= cnt;
				taken++;
				out.print(first.x + " ");

				if (first.cnt > 0)
					queue.add(first);
			}
		}

		class Element
		{
			int x, cnt;

			Element(int x, int cnt)
			{
				this.x = x;
				this.cnt = cnt;
			}

		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override public void run()
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
