package com.codechef.competitions.longcompetitions.year2017.june;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

public class UNIONSET
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
		int t, n, k;
		Set<Integer>[] sets;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			int lim = 2500;

			t = in.nextInt();
			sets = new Set[lim];

			while (t-- > 0)
			{
				n = in.nextInt();
				k = in.nextInt();

				for (int i = 0; i < n; i++)
					sets[i] = new HashSet<>();

				for (int i = 0; i < n; i++)
				{
					int sz = in.nextInt();

					while (sz-- > 0)
						sets[i].add(in.nextInt());
				}

				int cnt = 0;

				for (int i = 0; i < n; i++)
				{
					for (int j = i + 1; j < n; j++)
					{
						if (sets[i].size() + sets[j].size() < k)
							continue;

						if (sets[i].size() < sets[j].size())
							cnt += check(sets[i], sets[j]);
						else
							cnt += check(sets[j], sets[i]);
					}
				}

				out.println(cnt);
			}
		}

		byte check(Set<Integer> one, Set<Integer> two)
		{
			int common = 0;

			for (int x : one)
				if (two.contains(x))
					common++;

			if (one.size() + two.size() - common == k)
				return 1;
			else
				return 0;
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
