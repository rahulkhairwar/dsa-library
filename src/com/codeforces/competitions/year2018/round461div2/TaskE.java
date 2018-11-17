package com.codeforces.competitions.year2018.round461div2;

import java.io.*;
import java.util.*;

public class TaskE
{
	public static void main(String[] args)
	{
		new TaskE(System.in, System.out);
	}

	private static class Solver implements Runnable
	{
		int n;
		long capacity, capacityIncrease, manaGain;
		int[] count;
		long[] costs;
		long[][] dp;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			capacity = in.nextInt();
			capacityIncrease = in.nextInt();
			manaGain = in.nextInt();
			count = new int[n + 1];
			costs = new long[n + 1];

			int lim = 0;

			for (int i = 0; i < n; i++)
			{
				count[i] = in.nextInt();
				lim += count[i];
			}

			for (int i = 0; i < n; i++)
				costs[i] = in.nextInt();

			dp = new long[n + 1][lim + 1];

			for (int i = 0; i <= n; i++)
				Arrays.fill(dp[i], -1);

			dp[0][0] = capacity;

			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j <= lim; j++)
				{
					if (dp[i][j] == -1)
						continue;

					long maxPossMana = capacity + j * capacityIncrease;

					for (int k = 0; k <= count[i]; k++)
					{
						long newMana = dp[i][j] - k * costs[i];

						if (newMana < 0)
							break;

						long nextMax = maxPossMana + k * capacityIncrease;
						long nextNewMana = newMana + manaGain;

						dp[i + 1][j + k] = Math.max(dp[i + 1][j + k], Math.min(nextMax, nextNewMana));
					}
				}
			}

			for (int i = lim; i >= 0; i--)
			{
				if (dp[n][i] >= 0)
				{
					out.println(i);

					return;
				}
			}
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

	private TaskE(InputStream inputStream, OutputStream outputStream)
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
