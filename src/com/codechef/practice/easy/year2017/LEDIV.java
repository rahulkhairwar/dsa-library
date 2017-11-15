package com.codechef.practice.easy.year2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.TreeSet;

public final class LEDIV
{
	public static void main(String[] args)
	{
		new LEDIV(System.in, System.out);
	}

	static class Solver
	{
		int t, n;
		int[] arr;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				arr = new int[n];

				int sqrt = (int) Math.sqrt(arr[0] = in.nextInt());
				TreeSet<Integer> set = new TreeSet<>(Integer::compareTo);

				for (int i = 2; i <= sqrt; i += (i == 2 ? 1 : 2))
				{
					if (arr[0] % i == 0)
					{
						set.add(i);

						while (arr[0] % i == 0)
							arr[0] /= i;
					}
				}

				if (arr[0] > 1)
					set.add(arr[0]);

				for (int i = 1; i < n; i++)
				{
					arr[i] = in.nextInt();
					intersect(arr[i], set);
				}

				if (set.size() == 0)
					out.println(-1);
				else
					out.println(set.first());
			}
		}

		void intersect(int x, TreeSet<Integer> set)
		{
			List<Integer> remove = new ArrayList<>();

			for (int a : set)
			{
				if (x % a != 0)
					remove.add(a);
			}

			for (int a : remove)
				set.remove(a);
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

	private LEDIV(InputStream inputStream, OutputStream outputStream)
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
