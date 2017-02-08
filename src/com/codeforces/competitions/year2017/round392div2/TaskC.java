package com.codeforces.competitions.year2017.round392div2;

import java.io.*;
import java.util.*;

public final class TaskC
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		int n, m, x, y;
		long k;
		long[][] cnt;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			k = in.nextLong();
			x = in.nextInt();
			y = in.nextInt();
			cnt = new long[n][m];

			if (n == 1)
			{
				long[] ans = new long[m];
				long times = k / m;

				Arrays.fill(ans, times);
				long mod = k % m;

				for (int i = 0; i < m && mod > 0; i++)
				{
					ans[i]++;
					mod--;
				}

				out.println(ans[0] + " " + ans[m - 1] + " " + ans[y - 1]);

				return;
			}

			// first time, fill all cells.
			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < m && k > 0; j++)
				{
					cnt[i][j]++;
					k--;
				}
			}

			if (k == 0)
			{
				long max, min;

				max = 0;
				min = Long.MAX_VALUE;

				for (int i = 0; i < n; i++)
				{
					for (int j = 0; j < m; j++)
					{
						max = Math.max(max, cnt[i][j]);
						min = Math.min(min, cnt[i][j]);
					}
				}

				out.println(max + " " + min + " " + cnt[x - 1][y - 1]);

				return;
			}

			long size = (n - 2) * m * 2 + 2 * m;
			long times = k / size;

			// each cycle starts from the last row, goes to the 1st row, and then ends after the 2nd-last row.
			for (int i = 0; i < m; i++)
				cnt[n - 1][i] += times;

			for (int i = 1; i < n - 1; i++)
				for (int j = 0; j < m; j++)
					cnt[i][j] += times * 2;

			for (int i = 0; i < m; i++)
				cnt[0][i] += times;

			k -= times * size;

			if (k > 0)
			{
				int tot = (n - 1) * m;
				boolean top = false;

				while (k >= tot)
				{
					if (top)
					{
						for (int i = 1; i < n; i++)
							for (int j = 0; j < m; j++)
								cnt[i][j]++;
					}
					else
					{
						for (int i = n - 2; i >= 0; i--)
							for (int j = 0; j < m; j++)
								cnt[i][j]++;
					}

					k -= tot;
					top = !top;
				}

				if (top)
				{
					for (int i = 1; i < n; i++)
					{
						for (int j = 0; j < m && k > 0; j++)
						{
							cnt[i][j]++;
							k--;
						}
					}
				}
				else
				{
					for (int i = n - 2; i >= 0; i--)
					{
						for (int j = 0; j < m && k > 0; j++)
						{
							cnt[i][j]++;
							k--;
						}
					}
				}
			}

			long max, min;

			max = 0;
			min = Long.MAX_VALUE;

			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < m; j++)
				{
					max = Math.max(max, cnt[i][j]);
					min = Math.min(min, cnt[i][j]);
				}
			}

			out.println(max + " " + min + " " + cnt[x - 1][y - 1]);
		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

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

		public long nextLong()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sign = 1;

			if (c == '-')
			{
				sign = -1;

				c = read();
			}

			long result = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				result *= 10;
				result += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return result * sign;
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

	}

}
