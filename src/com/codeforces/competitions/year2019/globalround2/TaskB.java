package com.codeforces.competitions.year2019.globalround2;

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
		int n, height;
		int[] bottles;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			height = in.nextInt();
			bottles = in.nextIntArray(n);

			PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
			int ans = 1;

			for (int i = 0; i < n; i++)
			{
				queue.add(bottles[i]);

				if (check(queue, height))
					ans = i;
			}

			out.println(ans + 1);
		}

		boolean check(PriorityQueue<Integer> queue, int remHeight)
		{
			if (queue.size() == 0)
				return true;

			int max = queue.poll();

			if (queue.size() > 0)
			{
				int secondMax = queue.poll();
				boolean result;

				if (max <= remHeight)
					result = check(queue, remHeight - max);
				else
					result = false;

				queue.add(secondMax);
				queue.add(max);

				return result;
			}
			else
			{
				queue.add(max);

				return max <= remHeight;
			}
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

		public int[] nextIntArray(int arraySize)
		{
			int[] array = new int[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextInt();

			return array;
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

		InputReader(InputStream stream)
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

