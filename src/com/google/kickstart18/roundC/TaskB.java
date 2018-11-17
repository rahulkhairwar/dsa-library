package com.google.kickstart18.roundC;

import java.io.*;
import java.util.*;

public final class TaskB
{
	public static void main(String[] args)
	{
		new TaskB(System.in, System.out);
	}

	static class Solver implements Runnable
	{
        int t, n;
        int[][] adj;
        Edge[] edges;
		Set<String> set;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();

			for (int test = 1; test <= t; test++)
			{
				n = in.nextInt();
				adj = new int[n][n];

				int m = 0;

				for (int i = 0; i < n; i++)
				{
					for (int j = 0; j < n; j++)
					{
						adj[i][j] = in.nextInt();

						if (adj[i][j] != 0)
							m++;
					}
				}

				m >>= 1;
				edges = new Edge[m];
				set = new HashSet<>();

				if (m < 3)
				{
					out.println("Case #" + test + ": " + 0);

					continue;
				}

				int ctr = 0;

				for (int i = 0; i < n; i++)
				{
					for (int j = i + 1; j < n; j++)
					{
						if (adj[i][j] != 0)
							edges[ctr++] = new Edge(i, j, adj[i][j]);
					}
				}

				int ans = 0;

				for (int i = 1; i <= (1 << ctr); i++)
				{
					if (Integer.bitCount(i) > 2 && poss(Integer.toBinaryString(i)))
						ans++;
				}

				out.println("Case #" + test + ": " + ans);
			}
		}

		boolean poss(String bin)
		{
			boolean[] used = new boolean[n];
			char[] charArray = bin.toCharArray();
			List<Integer> sides = new ArrayList<>();
			char[] s = new char[bin.length()];

			for (int i = 0; i < charArray.length; i++)
			{
				char ch = charArray[i];

				if (ch == '1')
				{
					int fr = edges[i].from;
					int to = edges[i].to;

					if (used[fr] || used[to])
						s[i] = '0';
					else
					{
						s[i] = '1';
						sides.add(edges[i].len);
						used[fr] = used[to] = true;
					}
				}
				else
					s[i] = '0';
			}

			Collections.sort(sides);

			long sum = 0;
			String str = new String(s);

			for (int i = 0; i < sides.size() - 1; i++)
				sum += sides.get(i);

			if (sides.size() > 2 && sides.get(sides.size() - 1) < sum)
			{
//				System.out.println("***sides : " + sides + ", bin : " + bin + ", str : " + str);

				if (!set.contains(str))
				{
					set.add(str);

					return true;
				}

				return false;
			}

			return false;
		}

		class Edge
		{
			int from, to, len;

			public Edge(int from, int to, int len)
			{
				this.from = from;
				this.to = to;
				this.len = len;
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

	public TaskB(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskB", 1 << 29);

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

/*

5
6
0 1 0 0 0 0
1 0 1 0 0 0
0 1 0 1 0 0
0 0 1 0 1 0
0 0 0 1 0 1
0 0 0 0 1 0
6
0 2 0 0 0 0
2 0 0 0 0 0
0 0 0 3 0 0
0 0 3 0 0 0
0 0 0 0 0 4
0 0 0 0 4 0
6
0 1 0 0 0 0
1 0 0 0 0 0
0 0 0 2 0 0
0 0 2 0 0 0
0 0 0 0 0 4
0 0 0 0 4 0
6
0 1 1 1 1 1
1 0 0 0 0 0
1 0 0 0 0 0
1 0 0 0 0 0
1 0 0 0 0 0
1 0 0 0 0 0
8
0 5 0 0 0 0 0 0
5 0 0 0 0 0 0 0
0 0 0 5 0 0 0 0
0 0 5 0 0 0 0 0
0 0 0 0 0 5 0 0
0 0 0 0 5 0 0 0
0 0 0 0 0 0 0 5
0 0 0 0 0 0 5 0

1
6
0 1 0 0 0 0
1 0 1 0 0 0
0 1 0 1 0 0
0 0 1 0 1 0
0 0 0 1 0 1
0 0 0 0 1 0

1
6
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0

*/
