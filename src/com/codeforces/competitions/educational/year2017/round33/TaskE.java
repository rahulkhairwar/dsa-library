package com.codeforces.competitions.educational.year2017.round33;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public class TaskE
{
	public static void main(String[] args)
	{
		new TaskE(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final int MOD = (int) 1e9 + 7;
		int q;
		int[] smallestPrimeFactor;
		long[] fact, invFact;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			pre();
			q = in.nextInt();

			while (q-- > 0)
			{
				int x = in.nextInt();
				int y = in.nextInt();
				long ans = CMath.modPower(2, y - 1, MOD);

				while (x > 1)
				{
					int sPF = smallestPrimeFactor[x];
					int cnt = 0;

					while (x % sPF == 0)
					{
						x /= sPF;
						cnt++;
					}

					ans = CMath.mod(ans * nCr(cnt + y - 1, y - 1), MOD);
				}

				out.println(ans);
			}
		}

		void pre()
		{
			int lim = (int) 1e6 + 50;

			smallestPrimeFactor = new int[lim];
			fact = new long[lim];
			invFact = new long[lim];
			smallestPrimeFactor[2] = 2;
			fact[0] = fact[1] = 1;

			int curr = 2;

			while ((curr << 1) < lim)
			{
				smallestPrimeFactor[curr << 1] = 2;
				curr++;
			}

			for (int i = 3; i < lim; i += 2)
			{
				if (smallestPrimeFactor[i] > 0)
					continue;

				smallestPrimeFactor[i] = i;
				curr = i + i;

				while (curr < lim)
				{
					if (smallestPrimeFactor[curr] == 0)
						smallestPrimeFactor[curr] = i;

					curr += i;
				}
			}

			for (int i = 2; i < lim; i++)
				fact[i] = CMath.mod(fact[i - 1] * i, MOD);

			invFact[lim - 1] = CMath.moduloInverse(fact[lim - 1], MOD);

			for (int i = lim - 1; i > 0; i--)
				invFact[i - 1] = CMath.mod(invFact[i] * i, MOD);
		}

		int nCr(int n, int r)
		{
			return (int) CMath.mod(fact[n] * CMath.mod(invFact[r] * invFact[n - r], MOD), MOD);
		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
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

	public TaskE(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskE", 1 << 29);

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
