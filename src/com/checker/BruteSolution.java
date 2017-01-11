package com.checker;

import java.io.*;
import java.util.*;

public final class BruteSolution
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);

		try
		{
			in = new InputReader(new FileInputStream(new File(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/checker/input.txt")));
			out = new PrintWriter(new FileOutputStream(new File(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/checker/bruteOutput.txt")));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		int n, f;
		int[] arr;
		int[][] pos, neg;
		boolean[][] sign;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			arr = new int[41];
			pos = new int[41][(int) 16e3 + 5];
			neg = new int[41][(int) 16e3 + 5];
			sign = new boolean[2][41];

			while (true)
			{
				n = in.nextInt();
				f = in.nextInt();

				if (n == 0 && f == 0)
					break;

				for (int i = 0; i < n; i++)
					arr[i] = in.nextInt();

				String zeroes = "0000000000000000000000000000";
				boolean possible = false;

				for (int i = 0; i < (1 << n); i++)
				{
					String bin = Integer.toBinaryString(i);

					bin = zeroes.substring(0, n - bin.length()) + bin;

//					System.out.println("bin : " + bin);

					boolean check = check(bin);

					if (check)
					{
						possible = true;

						for (int j = 0; j < n; j++)
						{
							if (bin.charAt(j) == '0')
								sign[0][j] = true;
							else
								sign[1][j] = true;
						}
					}
				}

				if (!possible)
					out.println("*");
				else
				{
					StringBuilder ans = new StringBuilder("");

					for (int i = 0; i < n; i++)
					{
						if (sign[0][i] && sign[1][i])
							ans.append("?");
						else if (sign[0][i])
							ans.append("-");
						else
							ans.append("+");
					}

					out.println(ans);
				}
			}
		}

		boolean check(String bin)
		{
			int sum = 0;

			for (int i = 0; i < n; i++)
			{
				if (bin.charAt(i) == '0')
					sum -= arr[i];
				else
					sum += arr[i];
			}

			return sum == f;
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

		public int[] nextIntArray(int arraySize)
		{
			int array[] = new int[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextInt();

			return array;
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

		public long[] nextLongArray(int arraySize)
		{
			long array[] = new long[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextLong();

			return array;
		}

		public float nextFloat()
		{
			float result, div;
			byte c;

			result = 0;
			div = 1;
			c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean isNegative = (c == '-');

			if (isNegative)
				c = (byte) read();

			do
			{
				result = result * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					result += (c - '0') / (div *= 10);

			if (isNegative)
				return -result;

			return result;
		}

		public double nextDouble()
		{
			double ret = 0, div = 1;
			byte c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean neg = (c == '-');

			if (neg)
				c = (byte) read();

			do
			{
				ret = ret * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);

			if (neg)
				return -ret;

			return ret;
		}

		public String next()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			StringBuilder res = new StringBuilder();

			do
			{
				res.appendCodePoint(c);

				c = read();
			} while (!isSpaceChar(c));

			return res.toString();
		}

		public boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
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
