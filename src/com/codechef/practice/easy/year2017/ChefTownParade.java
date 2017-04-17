package com.codechef.practice.easy.year2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

class ChefTownParade
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
		int n, w, arr[];
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			w = in.nextInt();
			arr = in.nextIntArray(n);

			ArrayDeque<Integer> min = new ArrayDeque<>();
			ArrayDeque<Integer> max = new ArrayDeque<>();

			for (int i = 0; i < w; i++)
			{
				while (min.size() > 0 && min.peekLast() > arr[i])
					min.pollLast();

				min.addLast(arr[i]);

				while (max.size() > 0 && max.peekLast() < arr[i])
					max.pollLast();

				max.addLast(arr[i]);
			}

			int cnt = 0;

			for (int i = w; i <= n; i++)
			{
				if (max.peekFirst() - min.peekFirst() == w - 1)
					cnt++;

				if (i == n)
					break;

				if (min.peekFirst() == arr[i - w])
					min.pollFirst();

				if (max.peekFirst() == arr[i - w])
					max.pollFirst();

				while (min.size() > 0 && min.peekLast() > arr[i])
					min.pollLast();

				min.addLast(arr[i]);

				while (max.size() > 0 && max.peekLast() < arr[i])
					max.pollLast();

				max.addLast(arr[i]);
			}

			out.println(cnt);
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

}
