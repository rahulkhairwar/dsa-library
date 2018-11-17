package com.codeforces.competitions.year2018.hello2018;

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
		int n, t;
		List<Integer> list;
		Question[] questions;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			t = in.nextInt();
			questions = new Question[n];

			for (int i = 0; i < n; i++)
				questions[i] = new Question(in.nextInt(), in.nextInt(), i);

			Arrays.sort(questions, (one, two) ->
			{
				if (one.time == two.time)
					return Integer.compare(two.a, one.a);

				return Integer.compare(one.time, two.time);
			});

			int low = 0;
			int high = n;
			int ans = 0;

			while (low <= high)
			{
				int mid = low + high >> 1;

				if (isPossible(mid, false))
				{
					if (mid == n || !isPossible(mid + 1, false))
					{
						ans = mid;

						break;
					}

					low = mid + 1;
				}
				else
					high = mid - 1;
			}

			list = new ArrayList<>();
			isPossible(ans, true);
			out.println(list.size());
			out.println(list.size());

			for (int x : list)
				out.print(x + 1 + " ");
		}

		boolean isPossible(int cnt, boolean addToList)
		{
			long time = 0;
			int taken = 0;

			for (int i = 0; i < n; i++)
			{
				if (taken == cnt)
					break;

				if (questions[i].a >= cnt)
				{
					time += questions[i].time;
					taken++;

					if (addToList)
						list.add(questions[i].ind);
				}
			}

			return time <= t && taken == cnt;
		}


		static class Question
		{
			int a, time, ind;

			public Question(int a, int time, int ind)
			{
				this.a = a;
				this.time = time;
				this.ind = ind;
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
