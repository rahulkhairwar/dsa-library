package com.codeforces.practice.medium;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public final class D388Div2
{
	public static void main(String[] args)
	{
		new D388Div2(System.in, System.out);
	}

	static class Solver implements Runnable
	{
        int n, q;
        Point[] pts;
        int[] maxBids;
        List<Integer>[] allBids;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			pts = new Point[n];
			maxBids = new int[n];
			allBids = new List[n];

			for (int i = 0; i < n; i++)
				allBids[i] = new ArrayList<>();

			for (int i = 0; i < n; i++)
			{
				pts[i] = new Point(in.nextInt() - 1, in.nextInt());
				maxBids[pts[i].x] = pts[i].y;
				allBids[pts[i].x].add(pts[i].y);
			}

			TreeSet<Point> treeSet = new TreeSet<>(Comparator.comparingInt(one -> one.y));

			for (int i = 0; i < n; i++)
			{
				if (maxBids[i] > 0)
					treeSet.add(new Point(i, maxBids[i]));
			}

			q = in.nextInt();

			while (q-- > 0)
			{
				int k = in.nextInt();
				List<Integer> remove = new ArrayList<>();

				for (int i = 0; i < k; i++)
				{
					int a = in.nextInt() - 1;

					remove.add(a);
					treeSet.remove(new Point(a, maxBids[a]));
				}

				if (treeSet.size() == 0)
					out.println("0 0");
				else
				{
					out.print(treeSet.last().x + 1 + " ");

					if (treeSet.size() == 1)
					{
						Point pt = treeSet.last();

						out.println(allBids[pt.x].get(0));
					}
					else
					{
						Point last = treeSet.pollLast();
						Point secondLast = treeSet.last();

						out.println(binarySearch(allBids[last.x], maxBids[secondLast.x]));
						treeSet.add(last);
					}
				}

				for (int r : remove)
					if (maxBids[r] > 0)
						treeSet.add(new Point(r, maxBids[r]));
			}
		}

		int binarySearch(List<Integer> list, int val)
		{
			int low = 0;
			int high = list.size() - 1;

			while (low <= high)
			{
				int mid = low + high >> 1;

				if (list.get(mid) > val)
				{
					if (mid == 0 || list.get(mid - 1) < val)
						return list.get(mid);

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

	private D388Div2(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "D388Div2", 1 << 29);

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
