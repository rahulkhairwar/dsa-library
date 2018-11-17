package com.codeforces.competitions.year2018.round481div3;

import java.io.*;
import java.util.*;

public class TaskD
{
	public static void main(String[] args)
	{
		new TaskD(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n;
		int[] arr;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			arr = in.nextIntArray(n);

			if (n == 1 || n == 2)
			{
				out.println(0);

				return;
			}

			int min = Integer.MAX_VALUE;
			int[] diff = new int[]{arr[1] - arr[0], arr[1] - arr[0] - 1, arr[1] - arr[0] + 1, arr[1] - 1 - arr[0],
					arr[1] - 1 - arr[0] - 1, arr[1] - 1 - arr[0] + 1, arr[1] + 1 - arr[0], arr[1] + 1 - arr[0] - 1,
					arr[1] + 1 - arr[0] + 1};

			for (int d : diff)
				min = Math.min(min, check(arr[0], d));

			for (int d : diff)
				min = Math.min(min, check(arr[0] - 1, d));

			for (int d : diff)
				min = Math.min(min, check(arr[0] + 1, d));

			if (min == Integer.MAX_VALUE)
				out.println(-1);
			else
				out.println(min);
		}

		int check(int min, int diff)
		{
			int[] temp = new int[n];

			temp[0] = min;

			for (int i = 1; i < n; i++)
				temp[i] = temp[i - 1] + diff;

			int steps = 0;

			for (int i = 0; i < n; i++)
			{
				int abs = Math.abs(temp[i] - arr[i]);

				if (abs == 1)
					steps++;
				else if (abs > 1)
					return Integer.MAX_VALUE;
			}

			return steps;
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

	public TaskD(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskD", 1 << 29);

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

