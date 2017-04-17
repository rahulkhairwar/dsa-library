package com.codeforces.competitions.educational.year2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

public final class TaskB
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
			arr = new int[n];

			List<Integer> odd = new ArrayList<>();
			long sum = 0;

			for (int i = 0; i < n; i++)
			{
				arr[i] = in.nextInt();

				if (Math.abs(arr[i]) % 2 == 0 && arr[i] > 0)
					sum += arr[i];
				else if (Math.abs(arr[i]) % 2 == 1)
					odd.add(arr[i]);
			}

			Collections.sort(odd);

			int sz = odd.size();
			boolean[] used = new boolean[sz];

			for (int i = 0; i < sz; i++)
			{
				if (odd.get(i) > 0)
				{
					sum += odd.get(i);
					used[i] = true;
				}
			}

			if (Math.abs(sum) % 2 == 1)
				out.println(sum);
			else
			{
				long max = odd.get(0);

				if (max > 0)
				{
					out.println(sum - max);

					return;
				}

				long ans = Long.MIN_VALUE;

				for (int i = 0; i < sz; i++)
				{
					if (!used[i])
						ans = Math.max(ans, sum + odd.get(i));
					else
						ans = Math.max(ans, sum - odd.get(i));
				}

				out.println(ans);
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

}
