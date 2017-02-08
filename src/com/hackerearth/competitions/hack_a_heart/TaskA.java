package com.hackerearth.competitions.hack_a_heart;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public final class TaskA
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
		int n, arr[];
		double x, y;
		double ans;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			arr = in.nextIntArray(n);
			ans = Double.MAX_VALUE;
			fun(0);
			out.printf("%.6f", ans);
		}

		void fun(int pos)
		{
			if (pos == n)
			{
				if (x == 0 || y == 0)
					return;

				ans = Math.min(ans, Math.min(Math.abs(1.0 - x / y), Math.abs(1.0 - y / x)));

				return;
			}

			fun(pos + 1);
			x += arr[pos];
			fun(pos + 1);
			x -= arr[pos];
			y += arr[pos];
			fun(pos + 1);
			y -= arr[pos];
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

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

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

	}

}
