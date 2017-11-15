package com.a2onlinejudge.ladder.CodeforcesDiv2D;

import java.io.*;
import java.util.*;

public class Problem9
{
	public static void main(String[] args)
	{
		new Problem9(System.in, System.out);
	}

	private static class Solver implements Runnable
	{
		int n, m;
		int[] col;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			m = in.nextInt();
			col = new int[n];

			for (int i = 0; i < n; i++)
				col[i] = in.nextInt();

			Map<Integer, Set<Integer>> map = new HashMap<>();

			for (int i = 0; i < m; i++)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				if (col[u] == col[v])
					continue;

				Set<Integer> set = map.get(col[u]);

				if (set == null)
				{
					set = new HashSet<>();
					map.put(col[u], set);
				}

				set.add(col[v]);
				set = map.get(col[v]);

				if (set == null)
				{
					set = new HashSet<>();
					map.put(col[v], set);
				}

				set.add(col[u]);
			}

			int max = 0;
			int maxCol = -1;

			for (Map.Entry<Integer, Set<Integer>> entry : map.entrySet())
			{
				if (entry.getValue().size() > max)
				{
					max = entry.getValue().size();
					maxCol = entry.getKey();
				}
				else if (entry.getValue().size() == max)
				{
					max = entry.getValue().size();
					maxCol = Math.min(maxCol, entry.getKey());
				}
			}

			if (max == 0)
			{
				int min = Integer.MAX_VALUE;

				for (int x : col)
					min = Math.min(min, x);

				out.println(min);
			}
			else
				out.println(maxCol);
		}

		Solver(InputReader in, PrintWriter out)
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

	private static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		int read()
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

		int nextInt()
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

		boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		void close()
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

	private Problem9(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "Solver", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		in.close();
		out.flush();
		out.close();
	}

}
