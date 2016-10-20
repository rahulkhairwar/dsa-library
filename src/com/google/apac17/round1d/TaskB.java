package com.google.apac17.round1d;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

public class TaskB
{
	public static void main(String[] args)
	{
		try
		{
			InputReader in = new InputReader(new FileInputStream(new File("/Users/rahulkhairwar/Documents/IntelliJ "
					+ "IDEA Workspace/Competitive Programming/src/com/google/apac17/round1d/inputB.txt")));
			OutputWriter out = new OutputWriter(System.out);
			Thread thread = new Thread(null, new Solver(in, out), "Solver", 1 << 28);

			thread.start();

			try
			{
				thread.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			out.flush();

			in.close();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	static class Solver implements Runnable
	{
		int t, r, c;
		int[][] num;
		boolean[][] grid;
		InputReader in;
		OutputWriter out;

		@Override public void run()
		{
			t = in.nextInt();

			for (int test = 1; test <= t; test++)
			{
				r = in.nextInt();
				c = in.nextInt();
				num = new int[r][c];

				int counter = 1;

				for (int i = 0; i < r; i++)
					for (int j = 0; j < c; j++)
						num[i][j] = counter++;

				counter--;

				String zeroes = "00000000000000000000000000000000";
				int max = 0;

				for (int i = (1 << counter) - 1; i > 0; i--)
				{
					String bin = Integer.toBinaryString(i);
					String x = zeroes.substring(0, counter - bin.length()) + bin;

					if (poss(x))
						max = Math.max(max, countOnes(bin));
				}

				System.out.println("Case #" + test + ": " + max);
			}
		}

		boolean poss(String bin)
		{
			int len = bin.length();

			grid = new boolean[r][c];

			for (int i = 0; i < len; i++)
			{
				if (bin.charAt(i) == '1')
				{
					int curr = i + 1;

					outer:
					for (int j = 0; j < r; j++)
					{
						for (int k = 0; k < c; k++)
						{
							if (num[j][k] == curr)
							{
								grid[j][k] = true;

								break outer;
							}
						}
					}
				}
			}

			for (int i = 0; i < r; i++)
			{
				for (int j = 2; j < c; j++)
				{
					if (grid[i][j])
					{
						if (grid[i][j - 1] && grid[i][j - 2])
							return false;
					}
				}
			}

			for (int i = 0; i < c; i++)
			{
				for (int j = 2; j < r; j++)
				{
					if (grid[j][i])
					{
						if (grid[j - 1][i] && grid[j - 2][i])
							return false;
					}
				}
			}

			return true;
		}

		int countOnes(String bin)
		{
			int len = bin.length();
			int count = 0;

			for (int i = 0; i < len; i++)
				if (bin.charAt(i) == '1')
					count++;

			return count;
		}

		//		@Override public void run()
		void x()
		{
			t = in.nextInt();

			for (int test = 1; test <= t; test++)
			{
				r = in.nextInt();
				c = in.nextInt();

				boolean entered = false;

				if (r < 3)
				{
					grid = new boolean[r][c];
					entered = true;

					for (int i = 0; i < r; i++)
					{
						int j = 0;

						for (; j < c; )
						{
							grid[i][j] = true;

							j++;

							if (j < c)
								grid[i][j] = true;

							j += 2;
						}
					}
				}
				else if (c < 3)
				{
					int temp = r;

					r = c;
					c = temp;
					grid = new boolean[r][c];
					entered = true;

					for (int i = 0; i < r; i++)
					{
						int j = 0;

						for (; j < c; )
						{
							grid[i][j] = true;

							j++;

							if (j < c)
								grid[i][j] = true;

							j += 2;
						}
					}
				}

				if (entered)
				{
					int count = 0;

					for (int i = 0; i < r; i++)
					{
						for (int j = 0; j < c; j++)
						{
							if (grid[i][j])
								count++;
						}
					}

					out.println("Case #" + test + ": " + count);

					continue;
				}

/*				System.out.println("grid : ");

				for (int i = 0; i < r; i++, System.out.println())
					for (int j = 0; j < c; j++)
						System.out.print(grid[i][j] ? "t " : "f ");*/

				grid = new boolean[r][c];

				int count = 0;
				int[] cycle = new int[]{2, 0, 1};
				int pointer = 0;

				for (int i = 0; i < r; i++)
				{
					int j = 0;
					int temp = cycle[pointer];

					while (temp > 0)
					{
						grid[i][j] = true;
						temp--;
						j++;
					}

					j++;

					for (; j < c; )
					{
						grid[i][j] = true;

						j++;

						if (j < c)
							grid[i][j] = true;

						j += 2;
					}

					pointer++;

					if (pointer == 3)
						pointer = 0;
				}

				System.out.println("grid : ");

				for (int i = 0; i < r; i++, System.out.println())
					for (int j = 0; j < c; j++)
						System.out.print(grid[i][j] ? "t " : "f ");

				for (int i = 0; i < r; i++)
				{
					for (int j = 0; j < c; j++)
					{
						if (grid[i][j])
							count++;
					}
				}

				out.println("Case #" + test + ": " + count);
			}
		}

		public Solver(InputReader in, OutputWriter out)
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

	static class OutputWriter
	{
		private PrintWriter writer;

		public OutputWriter(OutputStream stream)
		{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
		}

		public OutputWriter(Writer writer)
		{
			this.writer = new PrintWriter(writer);
		}

		public void println(int x)
		{
			writer.println(x);
		}

		public void print(int x)
		{
			writer.print(x);
		}

		public void println(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i] + " ");
		}

		public void println(long x)
		{
			writer.println(x);
		}

		public void print(long x)
		{
			writer.print(x);
		}

		public void println(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i]);
		}

		public void println(float num)
		{
			writer.println(num);
		}

		public void print(float num)
		{
			writer.print(num);
		}

		public void println(double num)
		{
			writer.println(num);
		}

		public void print(double num)
		{
			writer.print(num);
		}

		public void println(String s)
		{
			writer.println(s);
		}

		public void print(String s)
		{
			writer.print(s);
		}

		public void println()
		{
			writer.println();
		}

		public void printSpace()
		{
			writer.print(" ");
		}

		public void flush()
		{
			writer.flush();
		}

		public void close()
		{
			writer.close();
		}

	}

	static class CMath
	{
		static long power(long number, long power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			if (power == 1)
				return number;

			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}

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

}
