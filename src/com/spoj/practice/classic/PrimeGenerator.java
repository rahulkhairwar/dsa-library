package com.spoj.practice.classic;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

class PrimeGenerator
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
		int t, l, r, lim;
		List<Integer> primes;
		boolean[] isComp;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			pre();
			t = in.nextInt();

			while (t-- > 0)
			{
				l = in.nextInt();
				r = in.nextInt();

				for (int i = l; i <= r; i++)
					if (isPrime(i))
						out.println(i);

				out.println();
			}
		}

		boolean isPrime(int x)
		{
			if (x <= lim)
				return !isComp[x];

			int sqrt = (int) Math.sqrt(x) + 1;

			for (int p : primes)
			{
				if (p > sqrt)
					break;

				if (x % p == 0)
					return false;
			}

			return true;
		}

		void pre()
		{
			lim = (int) Math.sqrt(1e9);
			isComp = new boolean[lim + 5];
			isComp[1] = true;
			primes = new ArrayList<>();
			primes.add(2);

			int curr = 2;

			while ((curr << 1) <= lim)
			{
				isComp[curr << 1] = true;
				curr++;
			}

			for (int i = 3; i <= lim; i += 2)
			{
				if (isComp[i])
					continue;

				primes.add(i);
				curr = i + i;

				while (curr <= lim)
				{
					isComp[curr] = true;
					curr += i;
				}
			}
		}

		Solver(InputReader in, PrintWriter out)
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

		public boolean hasNext()
		{
			try
			{
				return stream.available() > 0;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			return false;
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
