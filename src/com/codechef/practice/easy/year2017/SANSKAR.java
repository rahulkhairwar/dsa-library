package com.codechef.practice.easy.year2017;

import java.io.*;
import java.util.*;

public final class SANSKAR
{
	public static void main(String[] args)
	{
		new SANSKAR(System.in, System.out);
	}

	static class Solver
	{
		int t, n, k;
		long req;
		int[] arr;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				k = in.nextInt();
				arr = new int[n];

				long sum = 0;
				List<Integer> sanskars = new ArrayList<>();

				for (int i = 0; i < n; i++)
				{
					arr[i] = in.nextInt();
					sum += arr[i];
					sanskars.add(arr[i]);
				}

				req = sum / k;

				if (sum % k != 0)
				{
					out.println("no");

					continue;
				}

				if (poss(sanskars, 0))
					out.println("yes");
				else
					out.println("no");
			}
		}

		boolean poss(List<Integer> sanskars, int curr)
		{
			if (curr == k)
				return true;

			if (sanskars.size() == 0)
				return false;

			long toMake = req;
			int sz = sanskars.size();

			for (int i = 1; i <= (1 << sz); i++)
			{
				if (findSum(sanskars, i) == toMake)
				{
					List<Integer> remaining = new ArrayList<>();

					for (int j = 0; j < sanskars.size(); j++)
					{
						if ((i & (1 << j)) == 0)
							remaining.add(sanskars.get(j));
					}

					return poss(remaining, curr + 1);
				}
			}

			return false;
		}

		long findSum(List<Integer> sanskars, int mask)
		{
			long sum = 0;

			for (int i = 0; i < sanskars.size(); i++)
			{
				if ((mask & (1 << i)) > 0)
					sum += sanskars.get(i);
			}

			return sum;
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

	private SANSKAR(InputStream inputStream, OutputStream outputStream)
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
