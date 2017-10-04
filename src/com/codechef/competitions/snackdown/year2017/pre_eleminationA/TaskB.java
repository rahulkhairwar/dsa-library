package com.codechef.competitions.snackdown.year2017.pre_eleminationA;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

class TaskB
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
		int t, n;
		char[][] map;
		boolean[][] vis;
		int[][] maxDist;
		int[] dx, dy;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				map = new char[2][];
				vis = new boolean[2][n];
				maxDist = new int[2][n];
				dx = new int[]{1, 0, -1, 0};
				dy = new int[]{0, -1, 0, 1};
				map[0] = in.next().toCharArray();
				map[1] = in.next().toCharArray();

				int black = 0;

				for (char c : map[0])
					if (c == '#')
						black++;

				for (char c : map[1])
					if (c == '#')
						black++;

				int start = -1;

				for (int i = 0; i < n; i++)
				{
					if (map[0][i] == '#' || map[1][i] == '#')
					{
						start = i;

						break;
					}
				}

				init();

				int a = dfs(0, start);

				init();

				int b = dfs(1, start);

				if (a == black || b == black)
					out.println("yes");
				else
					out.println("no");
			}
		}

		private void init()
		{
			Arrays.fill(maxDist[0], -1);
			Arrays.fill(maxDist[1], -1);
			Arrays.fill(vis[0], false);
			Arrays.fill(vis[1], false);
		}

		int dfs(int r, int c)
		{
			if (r < 0 || r > 1 || c < 0 || c >= n || map[r][c] == '.' || vis[r][c])
				return 0;

			if (maxDist[r][c] != -1)
				return maxDist[r][c];

			vis[r][c] = true;

			int max = 0;

			for (int i = 0; i < 4; i++)
				max = Math.max(max, dfs(r + dx[i], c + dy[i]));

			return maxDist[r][c] = max + 1;
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

		public String next()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			StringBuilder res = new StringBuilder();

			do
			{
				res.appendCodePoint(c);

				c = read();
			} while (!isSpaceChar(c));

			return res.toString();
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

}

/*

6
2
##
..
2
##
.#
2
#.
.#
7
#.###..
#######
6
##.#..
.###..
5
##.##
.#.#.

1
2
##
..

2
2
##
#.
2
#.
#.

1
2
##
#.

*/
