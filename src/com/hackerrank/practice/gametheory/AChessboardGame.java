package com.hackerrank.practice.gametheory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class AChessboardGame
{
	public static void main(String[] args)
	{
		new AChessboardGame(System.in, System.out);
	}

	private static class Solver implements Runnable
	{
		int t;
		int[] dx, dy;
		int[][] grundy, isWinningPos;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			pre();
			t = in.nextInt();

			while (t-- > 0)
			{
				int x = in.nextInt() - 1;
				int y = in.nextInt() - 1;

				if (isWinningPos[x][y] > 0)
					out.println("First");
				else
					out.println("Second");
			}
		}

		void pre()
		{
			dx = new int[]{-2, -2, 1, -1};
			dy = new int[]{1, -1, -2, -2};
			grundy = new int[15][15];
			isWinningPos = new int[15][15];

			for (int i = 0; i < 15; i++)
				Arrays.fill(isWinningPos[i], -1);

			isWinningPos[0][0] = isWinningPos[0][1] = isWinningPos[1][0] = isWinningPos[1][1] = 0;

			for (int i = 0; i < 15; i++)
				for (int j = 0; j < 15; j++)
					isWinning(i, j);
		}

		boolean isWinning(int r, int c)
		{
			if (r < 0 || c < 0 || r >= 15 || c >= 15)
				return true;

			if (isWinningPos[r][c] != -1)
				return isWinningPos[r][c] > 0;

			boolean losingStateFound = false;

			for (int i = 0; i < 4; i++)
				if (!isWinning(r + dx[i], c + dy[i]))
					losingStateFound = true;

			if (losingStateFound)
				isWinningPos[r][c] = 1;
			else
				isWinningPos[r][c] = 0;

			return losingStateFound;
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

	private AChessboardGame(InputStream inputStream, OutputStream outputStream)
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
