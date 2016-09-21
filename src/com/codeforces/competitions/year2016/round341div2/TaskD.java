package com.codeforces.competitions.year2016.round341div2;

import java.io.*;
import java.util.*;

public final class TaskD
{
	static double x, y, z;
	static InputReader in;
	static OutputWriter out;

	public static void main(String[] args)
	{
		in = new InputReader(System.in);
		out = new OutputWriter(System.out);

		solve();

		out.flush();

		in.close();
		out.close();
	}

	static void solve()
	{
		String[] expressions = {"x^y^z", "x^z^y", "(x^y)^z", "(x^z)^y", "y^x^z", "y^z^x", "(y^x)^z", "(y^z)^x",
				"z^x^y", "z^y^x", "(z^x)^y", "(z^y)^x"};

		double[] a = new double[12];

		x = in.nextDouble();
		y = in.nextDouble();
		z = in.nextDouble();

		if (x > 1 && y > 1 && z > 1)
		{
			a[0] = z * Math.log(y) + Math.log(Math.log(x));
			a[1] = y * Math.log(z) + Math.log(Math.log(x));
			a[2] = y * z * Math.log(x);
			a[2] = Math.log(a[2]);
			a[3] = a[2];
			a[4] = z * Math.log(x) + Math.log(Math.log(y));
			a[5] = x * Math.log(z) + Math.log(Math.log(y));
			a[6] = z * x * Math.log(y);
			a[6] = Math.log(a[6]);
			a[7] = a[6];
			a[8] = y * Math.log(x) + Math.log(Math.log(z));
			a[9] = x * Math.log(y) + Math.log(Math.log(z));
			a[10] = y * x * Math.log(z);
			a[10] = Math.log(a[10]);
			a[11] = a[10];
		}
		else
		{
			if (x <= 1 && y <= 1 && z <= 1)
			{
				a[0] = Math.pow(x, Math.pow(y, z));
				a[1] = Math.pow(x, Math.pow(z, y));
				a[2] = Math.pow(Math.pow(x, y), z);
				a[3] = a[2];
				a[4] = Math.pow(y, Math.pow(x, z));
				a[5] = Math.pow(y, Math.pow(z, x));
				a[6] = Math.pow(Math.pow(y, x), z);
				a[7] = a[6];
				a[8] = Math.pow(z, Math.pow(x, y));
				a[9] = Math.pow(z, Math.pow(y, x));
				a[10] = Math.pow(Math.pow(z, x), y);
				a[11] = a[10];
			}
			else if (x <= 1 && y > 1 && z > 1)
			{
				a[0] = a[1] = a[2] = a[3] = -1e3;

				a[4] = z * Math.log(x) + Math.log(Math.log(y));
				a[5] = x * Math.log(z) + Math.log(Math.log(y));
				a[6] = z * x * Math.log(y);
				a[6] = Math.log(a[6]);
				a[7] = a[6];
				a[8] = y * Math.log(x) + Math.log(Math.log(z));
				a[9] = x * Math.log(y) + Math.log(Math.log(z));
				a[10] = y * x * Math.log(z);
				a[10] = Math.log(a[10]);
				a[11] = a[10];
			}
			else if (y <= 1 && x > 1 && z > 1)
			{
				a[4] = a[5] = a[6] = a[7] = -1e3;

				a[0] = z * Math.log(y) + Math.log(Math.log(x));
				a[1] = y * Math.log(z) + Math.log(Math.log(x));
				a[2] = y * z * Math.log(x);
				a[2] = Math.log(a[2]);
				a[3] = a[2];
				a[8] = y * Math.log(x) + Math.log(Math.log(z));
				a[9] = x * Math.log(y) + Math.log(Math.log(z));
				a[10] = y * x * Math.log(z);
				a[10] = Math.log(a[10]);
				a[11] = a[10];
			}
			else if (z <= 1 && x > 1 && y > 1)
			{
				a[8] = a[9] = a[10] = a[11] = -1e3;

				a[0] = z * Math.log(y) + Math.log(Math.log(x));
				a[1] = y * Math.log(z) + Math.log(Math.log(x));
				a[2] = y * z * Math.log(x);
				a[2] = Math.log(a[2]);
				a[3] = a[2];
				a[4] = z * Math.log(x) + Math.log(Math.log(y));
				a[5] = x * Math.log(z) + Math.log(Math.log(y));
				a[6] = z * x * Math.log(y);
				a[6] = Math.log(a[6]);
				a[7] = a[6];

			}
			else if (x > 1 && y <= 1 && z <= 1)
			{
				a[4] = a[5] = a[6] = a[7] = a[8] = a[9] = a[10] = a[11] = -1e3;

				a[0] = z * Math.log(y) + Math.log(Math.log(x));
				a[1] = y * Math.log(z) + Math.log(Math.log(x));
				a[2] = y * z * Math.log(x);
				a[2] = Math.log(a[2]);
				a[3] = a[2];
			}
			else if (x <= 1 && y > 1 && z <= 1)
			{
				a[0] = a[1] = a[2] = a[3] = a[8] = a[9] = a[10] = a[11] = -1e3;

				a[4] = z * Math.log(x) + Math.log(Math.log(y));
				a[5] = x * Math.log(z) + Math.log(Math.log(y));
				a[6] = z * x * Math.log(y);
				a[6] = Math.log(a[6]);
				a[7] = a[6];
			}
			else if (x <= 1 && y <= 1 && z > 1)
			{
				a[0] = a[1] = a[2] = a[3] = a[4] = a[5] = a[6] = a[7] = -1e3;

				a[8] = y * Math.log(x) + Math.log(Math.log(z));
				a[9] = x * Math.log(y) + Math.log(Math.log(z));
				a[10] = y * x * Math.log(z);
				a[10] = Math.log(a[10]);
				a[11] = a[10];
			}
		}

		int maxIndex = 0;

		for (int i = 1; i < 12; i++)
		{
			if (a[i] > a[maxIndex])
				maxIndex = i;
		}

		out.println(expressions[maxIndex]);
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
				} catch (IOException e)
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
			} catch (IOException e)
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
		static long power(long number, int power)
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

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

}
