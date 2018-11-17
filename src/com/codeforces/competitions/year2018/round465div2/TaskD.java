package com.codeforces.competitions.year2018.round465div2;

import java.io.*;
import java.util.*;

import static com.codeforces.competitions.year2018.round465div2.TaskD.Solver.MOD;

public class TaskD
{
	public static void main(String[] args)
	{
		System.out.println("8^-1 : " + CMath.moduloInverse(8, MOD)*10);
		new TaskD(System.in, System.out);
/*
		long gcd = CMath.gcd(288652, 456976);
		long p = 288652 / gcd;
		long q = 456976 / gcd;

		System.out.println("p * q^-1 : " + CMath.mod(p * CMath.moduloInverse(q, MOD), MOD));*/
	}

	static class Solver implements Runnable
	{
		static final long MOD = (long) 1e9 + 7;
		int n, m;
		int[] a, b;
		long[] unknown;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			m = in.nextInt();
			a = in.nextIntArray(n);
			b = in.nextIntArray(n);
			unknown = new long[n];

			for (int i = n - 1; i >= 0; i--)
			{
				if (i < n - 1)
					unknown[i] = unknown[i + 1];

				if (a[i] == 0)
					unknown[i]++;

				if (b[i] == 0)
					unknown[i]++;
			}

			long p = 0;
//			long q = CMath.modPower(m, unknown[0], MOD);
			long q = CMath.modPower(CMath.moduloInverse(m, MOD), unknown[0], MOD);
			int bothZero = 0;

			for (int i = 0; i < n; i++)
			{
				System.out.println("i : " + i + ", a[i] : " + a[i] + ", b[i] : " + b[i]);

				if (a[i] == 1)
				{
					if (b[i] > a[i] || i == n - 1)
						break;
				}
				else if (a[i] != 0 && b[i] != 0 && a[i] < b[i])
					break;
				else if (a[i] == 0 && b[i] == 0)
				{
					long x = CMath.mod((m - 1) * m / 2, MOD);
					long add = CMath.mod(x * (i < n - 1 ? CMath.modPower(m, unknown[i + 1], MOD) : 1), MOD);

					add = CMath.mod(add * CMath.modPower(m, bothZero, MOD), MOD);
					p = CMath.mod(p + add, MOD);
					bothZero++;
				}
				else if (a[i] == 0)
				{
					long add = CMath.mod((m - b[i]) * (i < n - 1 ? CMath.modPower(m, unknown[i + 1], MOD) : 1), MOD);

					add = CMath.mod(add * CMath.modPower(m, bothZero, MOD), MOD);
					p = CMath.mod(p + add, MOD);
				}
				else if (b[i] == 0)
				{
					long add = CMath.mod((a[i] - 1) * (i < n - 1 ? CMath.modPower(m, unknown[i + 1], MOD) : 1), MOD);

					add = CMath.mod(add * CMath.modPower(m, bothZero, MOD), MOD);
					p = CMath.mod(p + add, MOD);
				}
				else if (a[i] > b[i])
				{
					long add = (i < n - 1 ? CMath.modPower(m, unknown[i + 1], MOD) : 1);

					System.out.println("add : " + add + ", p : " + p);
					add = CMath.mod(add * CMath.modPower(m, bothZero, MOD), MOD);
					System.out.println("changed add to : " + add);
					p = CMath.mod(p + add, MOD);

					break;
				}
				System.out.println("\ti : " + i + ", p : " + p);
			}

			System.out.println("p : " + p + ", q : " + q);

			if (p == 0)
				out.println(0);
			else
			{
				long gcd = CMath.gcd(p, q);

//				p /= gcd;
//				q /= gcd;
				out.println(CMath.mod(p /** q*/, MOD));
			}
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

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

		static long gcd(long a, long b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

	}

	public TaskD(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskD", 1 << 29);

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

