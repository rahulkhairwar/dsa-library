package com.codechef.competitions.snackdown.year2018.qualifier;

import java.io.*;
import java.util.*;

class TaskE
{
	public static void main(String[] args)
	{
		new TaskE(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final long MOD = (long) 1e9 + 7;
		int t, n;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();

				TreeMap<Integer, Integer> map = new TreeMap<>((o1, o2) -> Integer.compare(o2, o1));

				for (int i = 0; i < n; i++)
				{
					int s = in.nextInt();
					Integer cnt = map.get(s);

					if (cnt == null)
						map.put(s, 1);
					else
						map.put(s, cnt + 1);
				}

				long ans = 1;
				Deque<Integer> queue = new LinkedList<>();

				for (Map.Entry<Integer, Integer> entry : map.entrySet())
				{
					long cnt = entry.getValue();

					queue.add((int) cnt);
				}

				while (queue.size() > 0)
				{
					int first = queue.pollFirst();

					if (first % 2 == 0)
						ans = CMath.mod(ans * getMul(first - 1), MOD);
					else
					{
						if (first == 1)
						{
							int second = queue.pollFirst();

							ans = CMath.mod(ans * second, MOD);
							second--;
							queue.addFirst(second);

							continue;
						}

						ans = CMath.mod(ans * CMath.mod(first * getMul(first - 2), MOD), MOD);
						queue.addFirst(1);
					}
				}

				out.println(ans);
			}
		}

		long getMul(long cnt)
		{
			long mul = 1;

			while (cnt >= 1)
			{
				mul = CMath.mod(mul * cnt, MOD);
				cnt -= 2;
			}

			return mul;
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

	static class CMath
	{
		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
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
