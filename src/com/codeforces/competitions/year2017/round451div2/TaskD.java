package com.codeforces.competitions.year2017.round451div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.TreeSet;

public class TaskD
{
	public static void main(String[] args)
	{
		new TaskD(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, m, k;
		int[] bit;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			m = in.nextInt();
			k = in.nextInt();
			bit = new int[(int) 1e6 + 5];

			TreeSet<Integer> set = new TreeSet<>(Integer::compareTo);

			for (int i = 0; i < n; i++)
			{
				int x = in.nextInt();

				add(x, 1);
				set.add(x);
			}

			int cnt = get(m);
			int ans = 0;

			for (int i = 1; i + m - 1 <= 1e6; i++)
			{
				int extra = cnt - (k - 1);

				if (extra > 0)
				{
					ans += extra;

					TreeSet<Integer> headSet = (TreeSet<Integer>) set.headSet(i + m);

					while (extra > 0)
					{
						int x = headSet.pollLast();

						add(x, -1);
						extra--;
					}
				}

				cnt = get(i + m) - get(i);
			}

			out.println(ans);
		}

		void add(int ind, int val)
		{
			while (ind <= 1e6)
			{
				bit[ind] += val;
				ind += ind & -ind;
			}
		}

		int get(int ind)
		{
			int sum = 0;

			while (ind > 0)
			{
				sum += bit[ind];
				ind -= ind & -ind;
			}

			return sum;
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

