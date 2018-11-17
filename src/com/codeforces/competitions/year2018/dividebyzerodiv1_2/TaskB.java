package com.codeforces.competitions.year2018.dividebyzerodiv1_2;

import java.io.*;
import java.util.*;

public class TaskB
{
	public static void main(String[] args)
	{
		new TaskB(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, k1, k2;
		int[] a, b;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			k1 = in.nextInt();
			k2 = in.nextInt();
			a = in.nextIntArray(n);
			b = in.nextIntArray(n);

			while (k1 > 0)
			{
				int pos = 0;
				int maxDiff = 0;

				for (int i = 0; i < n; i++)
				{
					if (Math.abs(a[i] - b[i]) > maxDiff)
					{
						maxDiff = Math.abs(a[i] - b[i]);
						pos = i;
					}
				}

				k1--;

				if (a[pos] < b[pos])
					a[pos]++;
				else
					a[pos]--;
			}

			while (k2 > 0)
			{
				int pos = 0;
				int maxDiff = 0;

				for (int i = 0; i < n; i++)
				{
					if (Math.abs(a[i] - b[i]) > maxDiff)
					{
						maxDiff = Math.abs(a[i] - b[i]);
						pos = i;
					}
				}

				k2--;

				if (b[pos] < a[pos])
					b[pos]++;
				else
					b[pos]--;
			}

			long ans = 0;

			for (int i = 0; i < n; i++)
				ans += Math.pow(a[i] - b[i], 2);

			out.println(ans);
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

