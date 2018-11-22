package com.google.kickstart18.roundH;

import java.io.*;
import java.util.*;

public final class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final long MOD = (long) 1e9 + 7;
		int t, n, m;
		long[] fact, invFact;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			pre();
			t = in.nextInt();

			for (int test = 1; test <= t; test++)
			{
				n = in.nextInt();
				m = in.nextInt();
				out.printf("Case #%d: %d\n", test, find(n << 1, m));
			}
		}

		long find(int seats, int newlyWeds)
		{
			long totalWays = fact[seats];

			for (int i = 1; i <= newlyWeds; i++)
			{
				long x = CMath.mod(nCR(newlyWeds, i), MOD);

				x = CMath.mod(x * CMath.modPower(2, i, MOD), MOD);
				x = CMath.mod(x * fact[seats - i], MOD);

				if (i % 2 == 1)
					x *= -1;

				totalWays = CMath.mod(totalWays + x, MOD);
			}

			if (totalWays < 0)
				totalWays += MOD;

			return CMath.mod(totalWays, MOD);
		}

		long nCR(int n, int r)
		{
			return CMath.mod(fact[n] * CMath.mod(invFact[r] * invFact[n - r], MOD), MOD);
		}

		void pre()
		{
			int lim = (int) 2e5 + 5;

			fact = new long[lim];
			invFact = new long[lim];
			fact[0] = fact[1] = 1;

			for (int i = 2; i < lim; i++)
				fact[i] = CMath.mod(i * fact[i - 1], MOD);

			invFact[lim - 1] = CMath.moduloInverse(fact[lim - 1], MOD);

			for (int i = lim - 1; i > 0; i--)
				invFact[i - 1] = CMath.mod(i * invFact[i], MOD);
		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override public void run()
		{
			try
			{
				solve();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
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

	static class CMath
	{
		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);
			else
				return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

	public TaskC(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskC", 1 << 29);

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
