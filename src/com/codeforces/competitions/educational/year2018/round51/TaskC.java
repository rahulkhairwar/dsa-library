package com.codeforces.competitions.educational.year2018.round51;

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
		int n;
		int[] arr;
		char[] ans;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			arr = in.nextIntArray(n);
			ans = new char[n];

			int[] cnt = new int[105];

			for (int x : arr)
				cnt[x]++;

			int singleCnt = 0;

			for (int x : cnt)
			{
				if (x == 1)
					singleCnt++;
			}

			if (singleCnt % 2 == 0)
			{
				boolean isA = true;

				for (int i = 0; i < n; i++)
				{
					if (cnt[arr[i]] == 1)
					{
						if (isA)
							ans[i] = 'A';
						else
							ans[i] = 'B';

						isA = !isA;
					}
					else
						ans[i] = 'A';
				}
			}
			else
			{
				int greaterThanTwo = 0;
				int greaterFor = -1;

				for (int i = 1; i < 105; i++)
				{
					if (cnt[i] > 2)
					{
						greaterThanTwo++;
						greaterFor = i;

						break;
					}
				}

				if (greaterThanTwo == 0)
				{
					out.println("NO");

					return;
				}

				boolean isA = true;

				for (int i = 0; i < n; i++)
				{
					if (cnt[arr[i]] == 1)
					{
						if (isA)
							ans[i] = 'A';
						else
							ans[i] = 'B';

						isA = !isA;
					}
					else
						ans[i] = 'A';
				}

				boolean greaterB = false;

				for (int i = 0; i < n; i++)
				{
					if (arr[i] == greaterFor && !greaterB)
					{
						ans[i] = 'B';
						greaterB = true;
					}
					else if (arr[i] == greaterFor)
						ans[i] = 'A';
				}
			}

			out.println("YES");
			out.println(new String(ans));
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
