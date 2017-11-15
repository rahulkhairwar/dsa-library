package com.codechef.practice.easy.year2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public final class SPOTWO
{
	public static void main(String[] args)
	{
		new SPOTWO(System.in, System.out);
	}

	static class Solver
	{
		static final int mod = (int) 1e9 + 7;
		int t, n;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();

			long[] powers = new long[25];

			// powers[i] = 4 ^ (10 ^ i).
			powers[0] = 4;

			for (int i = 1; i < 20; i++)
			{
				powers[i] = powers[i - 1];

				for (int j = 0; j < 9; j++)
					powers[i] = CMath.mod(powers[i] * powers[i - 1], mod);
			}

			while (t-- > 0)
			{
				n = in.nextInt();

				char[] bin = Integer.toBinaryString(n).toCharArray();
				long pow = 1;

				for (int i = bin.length - 1, j = 0; i >= 0; i--, j++)
					if (bin[i] == '1')
						pow = CMath.mod(pow * powers[j], mod);

				out.println(CMath.mod(pow, mod));
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

	static class CMath
	{
		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

	public SPOTWO(InputStream inputStream, OutputStream outputStream)
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
