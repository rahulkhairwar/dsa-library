package com.codechef.competitions.longcompetitions.year2018.july;

import java.io.*;
import java.util.*;

class NMNMX
{
	public static void main(String[] args)
	{
		new NMNMX(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final long MOD = (long) 1e9 + 6;
		static final long MOD2 = (long) 1e9 + 7;
		int t, n, k;
		int[] arr;
		long[][] nCR;
		long[] pow;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			pre();
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				k = in.nextInt();
				arr = in.nextIntArray(n);
				pow = new long[n];

				long p = nCR(n - 1, k - 1);
				long ans = 1;
				long[] sub = new long[n];

				Arrays.sort(arr);
				Arrays.fill(pow, p);

				for (int i = 0; i < n; i++)
				{
					int remaining = n - 1 - i;

					sub[i] = nCR(remaining, k - 1);
				}

				for (int i = 0; i < n; i++)
				{
					pow[i] -= sub[i];

					if (pow[i] < 0)
						pow[i] += MOD;
				}

				for (int i = 0, j = n - 1; i < n; i++, j--)
				{
					pow[i] -= sub[j];

					if (pow[i] < 0)
						pow[i] += MOD;
				}

				for (int i = 0; i < n; i++)
					ans = CMath.mod(ans * CMath.modPower(arr[i], pow[i], MOD2), MOD2);

				out.println(ans);
			}
		}

		void pre()
		{
			int lim = (int) 5e3 + 5;

			nCR = new long[lim][lim];
			nCR[0][0] = nCR[1][1] = 1;

			for (int i = 1; i < lim; i++)
			{
				nCR[i][0] = 1;

				for (int j = 1; j < lim; j++)
					nCR[i][j] = CMath.mod(nCR[i - 1][j] + nCR[i - 1][j - 1], MOD);
			}
		}

		long nCR(int n, int r)
		{
			if (r > n)
				return 0;

			return nCR[n][r];
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

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

	public NMNMX(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "NMNMX", 1 << 29);

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
