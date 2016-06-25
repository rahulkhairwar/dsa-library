package com.codechef.competitions.longcompetitions.year2016.june;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;

/**
 * Created by rahulkhairwar on 10/06/16.
 */
class ChefAndCities
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver(in, out);

		solver.solve(1);

		out.flush();

		in.close();
		out.close();
	}

	static class Solver
	{
		int n, q, arr[];
		ArrayList<Integer>[] divisors;
		double[] logs;
		double[] logSums;
		long[] products;
		long mod = (long) 1e9 + 7;
		InputReader in;
		OutputWriter out;

		void solve(int testNumber)
		{
			n = in.nextInt();

			arr = new int[n + 1];
			logs = new double[n + 1];

			for (int i = 0; i < n; i++)
			{
				arr[i] = in.nextInt();
				logs[i] = Math.log10(arr[i]);
			}

			if (n <= 10)
			{
				q = in.nextInt();

				while (q-- > 0)
				{
					int type = in.nextInt();

					if (type == 1)
					{
						int p, f;

						p = in.nextInt() - 1;
						f = in.nextInt();

						arr[p] = f;
						logs[p] = Math.log10(f);
					}
					else
					{
						int r = in.nextInt();
						long answer = arr[0];
						double log = logs[0];

						for (int i = 1; i * r < n; i++)
						{
							answer = CMath.mod(answer * arr[i * r], mod);
							log += logs[i * r];
						}

						String s = "" + Math.round(Math.pow(10d, log));

						out.println(s.charAt(0) + " " + answer);
					}
				}
			}
			else
			{

				pre();
				q = in.nextInt();

				while (q-- > 0)
				{
					int type = in.nextInt();

					if (type == 1)
					{
						int p, f;

						p = in.nextInt() - 1;
						f = in.nextInt();

						if (p == 0)
						{
							arr[0] = f;
							logs[0] = Math.log10(f);
						}
						else
							update(p, f);
					}
					else
					{
						int r = in.nextInt();
						long answer = CMath.mod(arr[0] * products[r], mod);
						double log = logs[0] + logSums[r];

						log = log - Math.floor(log);

						long power = (long) Math.pow(10d, log);

						while (power < 1)
							power *= 10;

						String s = "" + power;

						out.println(s.charAt(0) + " " + answer);
					}
				}
			}
		}

		void pre()
		{
			divisors = new ArrayList[n + 1];
			logSums = new double[n + 1];
			products = new long[n + 1];

			for (int i = 0; i <= n; i++)
				divisors[i] = new ArrayList<>();

			for (int i = 1; i < n; i++)
			{
				int sqrt = (int) Math.sqrt(i);

				for (int j = 1; j < sqrt; j++)
				{
					if (i % j == 0)
					{
						divisors[i].add(j);
						divisors[i].add(i / j);
					}
				}

				if (i % sqrt == 0)
				{
					if (sqrt * sqrt == i)
						divisors[i].add(sqrt);
					else
					{
						divisors[i].add(sqrt);
						divisors[i].add(i / sqrt);
					}
				}
			}

			for (int i = 1; i < n; i++)
			{
				int temp = i;
				products[i] = 1;

				while (temp < n)
				{
					products[i] = CMath.mod(products[i] * arr[temp], mod);
					logSums[i] += logs[temp];
					temp += i;
				}
			}
		}

		void update(int p, int f)
		{
			Iterator<Integer> iterator = divisors[p].iterator();
			double logF = Math.log10(f);
			long modInverse = CMath.moduloInverse(arr[p], mod);

			while (iterator.hasNext())
			{
				int next = iterator.next();

				products[next] = CMath.mod(products[next] * modInverse, mod);
				logSums[next] -= logs[p];
				products[next] = CMath.mod(products[next] * f, mod);
				logSums[next] += logF;
			}

			arr[p] = f;
			logs[p] = logF;
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

		public float nextFloat() // problematic
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

		public double nextDouble() // not completely accurate
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
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					stream)));
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

		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

	}

}

/*

5
1 1 1 1 1
1
2 1

5
1 1 1 1 10
1
2 1

10
2 2 2 2 2 2 2 2 2 2
1
2 1

 */
