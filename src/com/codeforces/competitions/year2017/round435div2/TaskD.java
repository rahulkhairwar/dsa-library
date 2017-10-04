package com.codeforces.competitions.year2017.round435div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class TaskD
{
	public static void main(String[] args)
	{
		new TaskD(System.in, System.out);
	}

	static class Solver
	{
		int n, ones, zeroes;
		char[] s;
		Map<Interval, Integer> map;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			s = new char[n];
			Arrays.fill(s, '1');
			ones = getMatchesCount();
			zeroes = n - ones;
			map = new HashMap<>();

			int low = 0;
			int high = n - 1;
			int zeroPos = -1;
			int onePos = -1;

			while (low <= high)
			{
				int mid = low + high >> 1;
				int zeroesMinusOnesCount = getZeroesMinusOnesCount(low, mid);
//				System.out.println("(0s - 1s) in [" + low + ", " + mid + "] : " + zeroesMinusOnesCount);

				if (zeroesMinusOnesCount == mid - low + 1)
				{
					zeroPos = low;

					break;
				}
				else if (zeroesMinusOnesCount == -(mid - low + 1))
				{
					onePos = low;
					low = mid + 1;
				}
				else
					high = mid;
			}

//			System.out.println("zP : " + zeroPos + ", oP : " + onePos);

			if (onePos == -1)
			{
				low = 0;
				high = n - 1;

				while (low <= high)
				{
					int mid = low + high >> 1;
					int onesMinusZeroesCount = getOnesMinusZeroesCount(low, mid);
//					System.out.println("(1s - 0s) in [" + low + ", " + mid + "] : " + onesMinusZeroesCount);

					if (onesMinusZeroesCount == mid - low + 1)
					{
						onePos = low;

						break;
					}
					else if (onesMinusZeroesCount == -(mid - low + 1))
						low = mid + 1;
					else
						high = mid;
				}
			}

			out.println("! " + (zeroPos + 1) + " " + (onePos + 1));
		}

		int getZeroesMinusOnesCount(int l, int r)
		{
			Interval interval = new Interval(l, r);

			if (map.containsKey(interval))
				return map.get(interval);

			for (int i = 0; i < l; i++)
				s[i] = '1';

			for (int i = l; i <= r; i++)
				s[i] = '0';

			for (int i = r + 1; i < n; i++)
				s[i] = '1';

			int x = getMatchesCount() - ones;

			map.put(interval, x);

			return x;
		}

		int getOnesMinusZeroesCount(int l, int r)
		{
//			System.out.println("****l : " + l + ", r : " + r + ", (0s - 1s) : " + getZeroesMinusOnesCount(l, r));
			return -getZeroesMinusOnesCount(l, r);
		}

		int getMatchesCount()
		{
			out.print("? ");

			for (int i = 0; i < n; i++)
				out.print(s[i]);

			out.println();
			out.flush();

			return n - in.nextInt();
		}

		static class Interval
		{
			int l, r;

			Interval(int l, int r)
			{
				this.l = l;
				this.r = r;
			}

			@Override public int hashCode()
			{
				return Objects.hash(l, r);
			}

			@Override public boolean equals(Object obj)
			{
				Interval interval = (Interval) obj;

				return l == interval.l && r == interval.r;
			}
			
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

	public TaskD(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Solver solver = new Solver(in, out);

		try
		{
			solver.solve();
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		out.flush();
		out.close();
	}

}

