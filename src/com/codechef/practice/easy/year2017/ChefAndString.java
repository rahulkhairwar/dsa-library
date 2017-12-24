package com.codechef.practice.easy.year2017;

import java.io.*;
import java.util.*;

class ChefAndString
{
	public static void main(String[] args)
	{
		new ChefAndString(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final int MOD = (int) 1e9 + 7;
        int t, n, q, lim;
        long[][] nCr;
        String s;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			pre();
			t = in.nextInt();

			long[] ans = new long[lim];

			while (t-- > 0)
			{
				n = in.nextInt();
				q = in.nextInt();
				s = in.nextLine();

				Map<String, Integer> map = new HashMap<>();

				for (int i = 0; i < n; i++)
				{
					for (int j = i; j < n; j++)
					{
						Integer cnt = map.get(s.substring(i, j + 1));

						if (cnt == null)
							map.put(s.substring(i, j + 1), 1);
						else
							map.put(s.substring(i, j + 1), cnt + 1);
					}
				}

				Arrays.fill(ans, 0);

				for (Map.Entry<String, Integer> entry : map.entrySet())
				{
					int x = entry.getValue();

					for (int i = 1; i <= x; i++)
						ans[i] = CMath.mod(ans[i] + nCr[x][i], MOD);
				}

				while (q-- > 0)
				{
					int k = in.nextInt();

					if (k >= ans.length)
						out.println(0);
					else
						out.println(ans[k]);
				}
			}
		}

		void pre()
		{
			lim = (int) 5e3 + 5;
			nCr = new long[lim][lim];
			nCr[0][0] = 1;

			for (int i = 0; i < lim; i++)
			{
				nCr[i][0] = 1;

				for (int j = 1; j <= i; j++)
					nCr[i][j] = CMath.mod(nCr[i - 1][j - 1] + nCr[i - 1][j], MOD);
			}
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

		public String nextLine()
		{
			int c = read();

			StringBuilder result = new StringBuilder();

			do
			{
				result.appendCodePoint(c);

				c = read();
			} while (!isNewLine(c));

			return result.toString();
		}

		public boolean isNewLine(int c)
		{
			return c == '\n';
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

	private ChefAndString(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "ChefAndString", 1 << 29);

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

/*

1
5 1
aaaaa
3

*/
