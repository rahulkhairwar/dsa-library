package com.a2oj.ladder.CodeforcesDiv2D;

import java.io.*;
import java.util.*;

public class Problem16
{
	public static void main(String[] args)
	{
		new Problem16(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, m;
		int[][] arr;
		long[][] alexTopToBottom, alexBottomToTop, eveTopToBottom, eveBottomToTop;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			arr = new int[n][m];
			alexTopToBottom = new long[n][m];
			alexBottomToTop = new long[n][m];
			eveTopToBottom = new long[n][m];
			eveBottomToTop = new long[n][m];

			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++)
					arr[i][j] = in.nextInt();

			alexTopToBottom[0][0] = arr[0][0];
			alexBottomToTop[n - 1][m - 1] = arr[n - 1][m - 1];
			eveTopToBottom[0][m - 1] = arr[0][m - 1];
			eveBottomToTop[n - 1][0] = arr[n - 1][0];

//			Alex.
			for (int i = 1; i < n; i++)
				alexTopToBottom[i][0] = alexTopToBottom[i - 1][0] + arr[i][0];
			for (int i = 1; i < m; i++)
				alexTopToBottom[0][i] = alexTopToBottom[0][i - 1] + arr[0][i];
			for (int i = m - 2; i >= 0; i--)
				alexBottomToTop[n - 1][i] = alexBottomToTop[n - 1][i + 1] + arr[n - 1][i];
			for (int i = n - 2; i >= 0; i--)
				alexBottomToTop[i][m - 1] = alexBottomToTop[i + 1][m - 1] + arr[i][m - 1];

			for (int i = 1; i < n; i++)
				for (int j = 1; j < m; j++)
					alexTopToBottom[i][j] = arr[i][j] + Math.max(alexTopToBottom[i - 1][j], alexTopToBottom[i][j - 1]);

			for (int i = n - 2; i >= 0; i--)
				for (int j = m - 2; j >= 0; j--)
					alexBottomToTop[i][j] = arr[i][j] + Math.max(alexBottomToTop[i + 1][j], alexBottomToTop[i][j + 1]);

//			Eve.
			for (int i = n - 2; i >= 0; i--)
				eveBottomToTop[i][0] = eveBottomToTop[i + 1][0] + arr[i][0];
			for (int i = 1; i < m; i++)
				eveBottomToTop[n - 1][i] = eveBottomToTop[n - 1][i - 1] + arr[n - 1][i];
			for (int i = m - 2; i >= 0; i--)
				eveTopToBottom[0][i] = eveTopToBottom[0][i + 1] + arr[0][i];
			for (int i = 1; i < n; i++)
				eveTopToBottom[i][m - 1] = eveTopToBottom[i - 1][m - 1] + arr[i][m - 1];

			for (int i = n - 2; i >= 0; i--)
				for (int j = 1; j < m; j++)
					eveBottomToTop[i][j] = arr[i][j] + Math.max(eveBottomToTop[i + 1][j], eveBottomToTop[i][j - 1]);

			for (int i = 1; i < n; i++)
				for (int j = m - 2; j >= 0; j--)
					eveTopToBottom[i][j] = arr[i][j] + Math.max(eveTopToBottom[i - 1][j], eveTopToBottom[i][j + 1]);

			long max = 0;

			for (int i = 1; i < n - 1; i++)
			{
				for (int j = 1; j < m - 1; j++)
				{
					long tot = alexTopToBottom[i - 1][j] + alexBottomToTop[i + 1][j] + eveBottomToTop[i][j - 1]
							+ eveTopToBottom[i][j + 1];

					tot = Math.max(tot,
							alexTopToBottom[i][j - 1] + alexBottomToTop[i][j + 1] + eveBottomToTop[i + 1][j]
							+ eveTopToBottom[i - 1][j]);
					max = Math.max(max, tot);
				}
			}

			out.println(max);
		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override public void run()
		{
			solve();
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

	public Problem16(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "Problem16", 1 << 29);

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
