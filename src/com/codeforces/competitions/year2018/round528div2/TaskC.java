package com.codeforces.competitions.year2018.round528div2;

import java.awt.*;
import java.io.*;
import java.util.*;

public class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int lim;
		Point a, b, c;
		boolean[][] vis, vis2, vis3;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			a = new Point(in.nextInt(), in.nextInt());
			b = new Point(in.nextInt(), in.nextInt());
			c = new Point(in.nextInt(), in.nextInt());

			Point[] pts = new Point[]{new Point(a.x, a.y), new Point(b.x, b.y), new Point(c.x, c.y)};

			Arrays.sort(pts, (a, b) -> {
				if (a.x == b.x)
					return Integer.compare(a.y, b.y);

				return Integer.compare(a.x, b.x);
			});

			a = pts[0];
			b = pts[1];
			c = pts[2];
			lim = (int) 1e3 + 5;
			vis = new boolean[lim][lim];
			vis2 = new boolean[lim][lim];
			vis3 = new boolean[lim][lim];
			vis[a.x][a.y] = vis2[a.x][a.y] = vis3[a.x][a.y] = true;
			vis[b.x][b.y] = vis2[b.x][b.y] = vis3[b.x][b.y] = true;
			vis[c.x][c.y] = vis2[c.x][c.y] = vis3[c.x][c.y] = true;

			// 3 ways to connect all A, B, C => AB + BC or AC + BC or AB + AC.
			// AB + BC.
			dfs(a.x, a.y, b.x, b.y, vis);

			if (!isReachable(a.x, a.y, c.x, c.y, vis))
				dfs(b.x, b.y, c.x, c.y, vis);

			int aCnt = findCnt(vis);

			// AC + BC.
			dfs(a.x, a.y, c.x, c.y, vis2);

			if (!isReachable(b.x, b.y, c.x, c.y, vis2))
				dfs(b.x, b.y, c.x, c.y, vis2);

			int bCnt = findCnt(vis2);

			// AB + AC.
			dfs(a.x, a.y, b.x, b.y, vis3);

			if (!isReachable(a.x, a.y, c.x, c.y, vis3))
				dfs(a.x, a.y, c.x, c.y, vis3);

			int cCnt = findCnt(vis3);
			int min = CMath.min(aCnt, bCnt, cCnt);

			out.println(min);

			if (aCnt == min)
				print(vis);
			else if (bCnt == min)
				print(vis2);
			else
				print(vis3);
		}

		void print(boolean[][] visited)
		{
			for (int i = 0; i < lim; i++)
			{
				for (int j = 0; j < lim; j++)
				{
					if (visited[i][j])
						out.println(i + " " + j);
				}
			}
		}

		int findCnt(boolean[][] visited)
		{
			int cnt = 0;

			for (int i = 0; i < lim; i++)
			{
				for (int j = 0; j < lim; j++)
				{
					if (visited[i][j])
						cnt++;
				}
			}

			return cnt;
		}

		void dfs(int startX, int startY, int endX, int endY, boolean[][] visited)
		{
			visited[startX][startY] = true;

			if (startX == endX && startY == endY)
				return;

			if (startX < endX)
				dfs(startX + 1, startY, endX, endY, visited);
			else if (startX > endX)
				dfs(startX - 1, startY, endX, endY, visited);
			else if (startY > endY)
				dfs(startX, startY - 1, endX, endY, visited);
			else if (startY < endY)
				dfs(startX, startY + 1, endX, endY, visited);
		}

		boolean isReachable(int startX, int startY, int endX, int endY, boolean[][] visited)
		{
			if (!visited[startX][startY])
				return false;

			if (startX == endX && startY == endY)
				return true;

			boolean poss = false;

			if (startX < endX)
				poss = isReachable(startX + 1, startY, endX, endY, visited);
			else if (startX > endX)
				poss = isReachable(startX - 1, startY, endX, endY, visited);

			if (poss)
				return true;

			if (startY < endY)
				poss = isReachable(startX, startY + 1, endX, endY, visited);
			else if (startY > endY)
				poss = isReachable(startX, startY - 1, endX, endY, visited);

			return poss;
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
		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

	}

	public TaskC(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskC", 1 << 29);

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

1 1
2 2
1 3

1 1
3 2
4 4

*/
