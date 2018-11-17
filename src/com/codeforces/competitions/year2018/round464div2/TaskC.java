package com.codeforces.competitions.year2018.round464div2;

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
		int n, start, end;
		int[] arr, cum;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			arr = new int[n + 1];
			cum = new int[n + 1];

			for (int i = 1; i <= n; i++)
				arr[i] = in.nextInt();

			start = in.nextInt();
			end = in.nextInt();
			cum[1] = arr[1];

			for (int i = 2; i <= n; i++)
				cum[i] = arr[i] + cum[i - 1];

			int maxParticipants = 0;
			int maxTime = 0;
			int par = cum[end - 1] - cum[start - 1];
			int startPtr = start - 1;
			int endPtr = end - 1;

			for (int i = 1; i <= n; i++)
			{
				if (par > maxParticipants)
				{
					maxParticipants = par;
					maxTime = i;
				}

				endPtr--;
				startPtr--;

				if (startPtr == -1)
					startPtr = n - 1;

				if (endPtr == -1)
					endPtr = n - 1;

				if (startPtr > endPtr)
				{
					par = cum[n] - cum[startPtr];
					par += cum[endPtr];
				}
				else
					par = cum[endPtr] - cum[startPtr];
			}

			out.println(maxTime);
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

