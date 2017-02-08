package com.hackerearth.competitions.inter_nit_contest;

import java.io.*;
import java.util.*;

public final class TaskD
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

	public static class Solver
	{
		static final long mod = (long) 1e9 + 7;
		int tests;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			tests = in.nextInt();

			while (tests-- > 0)
			{
				long a, b;
				int n;

				a = in.nextInt();
				b = in.nextInt();
				n = in.nextInt();

				if (n == 0)
					out.println(a);
				else if (n == 1)
					out.println(b);
				else if (n == 2)
					out.println(a + b + a * b);
				else
				{
					long aPower, bPower;

					aPower = getNthFibonacci(n - 1);
					bPower = getNthFibonacci(n);
					a = CMath.modPower(a + 1, aPower, mod);
					b = CMath.modPower(b + 1, bPower, mod);

					out.println(CMath.mod(a * b - 1, mod));
				}
			}
		}

		long getNthFibonacci(int n)
		{
			long[][] t = new long[][]{{0, 1}, {1, 1}};
			long[] f0 = new long[]{0, 1};
			long x;

			t = power(t, n - 1);
			x = CMath.mod(t[1][0] * f0[0], mod - 1) + CMath.mod(t[1][1] * f0[1], mod - 1);

			return CMath.mod(x, mod - 1);
		}

		long[][] multiply(long[][] a, long[][] b)
		{
			long[][] prod = new long[2][2];

			for (int i = 0; i < 2; i++)
			{
				for (int j = 0; j < 2; j++)
				{
					long sum = 0;

					for (int k = 0; k < 2; k++)
					{
						sum += CMath.mod(a[i][k] * b[k][j], mod - 1);
						sum = CMath.mod(sum, mod - 1);
					}

					prod[i][j] = sum;
				}
			}

			return prod;
		}

		long[][] power(long[][] a, int pow)
		{
			if (pow == 1)
				return a;

			long[][] square = multiply(a, a);

			if (pow % 2 == 0)
				return power(square, pow / 2);

			return multiply(power(square, pow / 2), a);
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

		public InputReader(InputStream stream)
		{
			this.stream = stream;
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

		public long nextLong()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sign = 1;

			if (c == '-')
			{
				sign = -1;

				c = read();
			}

			long result = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				result *= 10;
				result += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return result * sign;
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

	static class CMath
	{
		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);

			return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

}

/*

2
2 7 3
2 7 4

1
13 29 50

1
10 16 45

1
5 7 45

*/
