package com.a2oj.groupcontests.feb08_2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public final class VasilyTheBearAndSequence
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
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			arr = in.nextIntArray(n);

			int max = 0;
			List<Integer> ans = new ArrayList<>();

			for (int i = 0; i < 30; i++)
			{
				List<Integer> list = new ArrayList<>();

				for (int x : arr)
				{
					if ((x & (1 << i)) > 0)
						list.add(x);
				}

				if (list.size() == 0)
					continue;

				int and = list.get(0);
				int size = list.size();

				for (int j = 1; j < size; j++)
					and &= list.get(j);

				int maxPower = getMaxPower(and);

				if (maxPower > max)
				{
					max = maxPower;
					ans = list;
				}
				else if (maxPower == max && list.size() > ans.size())
					ans = list;
			}

			out.println(ans.size());

			for (int x : ans)
				out.print(x + " ");
		}

		int getMaxPower(int x)
		{
			int cnt = 0;

			while ((x & 1) == 0)
			{
				x >>= 1;
				cnt++;
			}

			return cnt;
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
