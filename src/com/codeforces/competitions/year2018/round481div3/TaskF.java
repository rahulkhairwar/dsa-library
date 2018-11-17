package com.codeforces.competitions.year2018.round481div3;

import java.io.*;
import java.util.*;

public class TaskF
{
	public static void main(String[] args)
	{
		new TaskF(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, k, ctr;
		int[] skills;
		List<Integer>[] adj;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			k = in.nextInt();
			skills = in.nextIntArray(n);
			adj = new List[n];

			for (int i = 0; i < n; i++)
				adj[i] = new ArrayList<>();

			compress();

			long[] cnt = new long[ctr];

			for (int x : skills)
				cnt[x]++;

			for (int i = 1; i < ctr; i++)
				cnt[i] += cnt[i - 1];

			for (int i = 0; i < k; i++)
			{
				int x = in.nextInt() - 1;
				int y = in.nextInt() - 1;

				adj[x].add(y);
				adj[y].add(x);
			}

			for (int i = 0; i < n; i++)
			{
				long ans = (skills[i] > 0 ? cnt[skills[i] - 1] : 0);

				for (int x : adj[i])
				{
					if (skills[x] < skills[i])
						ans--;
				}

				out.print(ans + " ");
			}
		}

		void compress()
		{
			List<Integer> list = new ArrayList<>();

			for (int x : skills)
				list.add(x);

			Collections.sort(list);
			ctr = 1;

			Map<Integer, Integer> map = new HashMap<>();

			for (int x : list)
			{
				if (!map.containsKey(x))
					map.put(x, ctr++);
			}

			for (int i = 0; i < n; i++)
				skills[i] = map.get(skills[i]);
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

		public int[] nextIntArray(int arraySize)
		{
			int array[] = new int[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextInt();

			return array;
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

	public TaskF(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskF", 1 << 29);

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

