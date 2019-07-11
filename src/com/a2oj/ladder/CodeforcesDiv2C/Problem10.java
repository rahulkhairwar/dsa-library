package com.a2oj.ladder.CodeforcesDiv2C;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;

/**
 * Created by rahulkhairwar on 16/02/16.
 */
public final class Problem10
{
	static int n, pos;
	static String s;
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
		n = in.nextInt();
		pos = in.nextInt() - 1;

		s = in.next();

		Integer[] leftCum, rightCum;

		leftCum = new Integer[n];
		rightCum = new Integer[n];

		leftCum[0] = (s.charAt(0) == s.charAt(n - 1) ? 0 : 1);
		rightCum[n - 1] = leftCum[0];

		for (int i = 1; i < n / 2; i++)
		{
			leftCum[i] = leftCum[i - 1];

			if (s.charAt(i) != s.charAt(n - i - 1))
				leftCum[i]++;
		}

		rightCum = Arrays.copyOf(leftCum, n);
		Collections.reverse(Arrays.asList(rightCum));

		if (pos == n / 2 || (n % 2 == 0 && pos == n / 2 - 1))
			out.println(Math.min(goLeft(), goRight()));
		else if (pos > n / 2)
			out.println(Math.min(goLeftThenRightR(), goRightThenLeftR()));
		else
			out.println(Math.min(goRightThenLeftL(), goLeftThenRightL()));
	}

	static int aToB(char a, char b)
	{
		return Math.min(Math.abs(a - b), 26 - Math.abs(a - b));
	}

	static int goLeft()
	{
		int count, i, pending;

		count = 0;
		i = pos;
		pending = 0;

		while (i >= 0)
		{
			char left, right;

			left = s.charAt(i);
			right = s.charAt(n - i - 1);

			if (left != right)
			{
				count += aToB(left, right);
				count += pending;
				pending = 0;
			}

			pending++;
			i--;
		}

		return count;
	}

	static int goRight()
	{
		int count, i, pending;

		count = 0;
		i = pos;
		pending = 0;

		while (i < n)
		{
			char left, right;

			left = s.charAt(n - i - 1);
			right = s.charAt(i);

			if (left != right)
			{
				count += aToB(left, right);
				count += pending;
				pending = 0;
			}

			pending++;
			i++;
		}

		return count;
	}

	static int goLeftThenRightL()
	{
		int count, i, pending;

		count = 0;
		i = pos;
		pending = 0;

		while (i >= 0)
		{
			char left, right;

			left = s.charAt(i);
			right = s.charAt(n - i - 1);

			if (left != right)
			{
				count += aToB(left, right);
				count += 2 * pending;
				pending = 0;
			}

			pending++;
			i--;
		}

		i = pos + 1;
		pending = 1;

		while (i < n / 2)
		{
			char left, right;

			left = s.charAt(i);
			right = s.charAt(n - i - 1);

			if (left != right)
			{
				count += aToB(left, right);
				count += pending;
				pending = 0;
			}

			pending++;
			i++;
		}

		return count;
	}

	static int goLeftThenRightR()
	{
		int count, i, pending;

		count = 0;
		i = pos;
		pending = 0;

		while (i >= n / 2)
		{
			char left, right;

			left = s.charAt(n - i - 1);
			right = s.charAt(i);

			if (left != right)
			{
				count += aToB(left, right);
				count += 2 * pending;
				pending = 0;
			}

			pending++;
			i--;
		}

		i = pos + 1;
		pending = 1;

		while (i < n)
		{
			char left, right;

			left = s.charAt(n - i - 1);
			right = s.charAt(i);

			if (left != right)
			{
				count += aToB(left, right);
				count += pending;
				pending = 0;
			}

			pending++;
			i++;
		}

		return count;
	}

	static int goRightThenLeftL()
	{
		int count, i, pending, limit;

		count = 0;
		i = pos;
		pending = 0;
		limit = (n % 2 == 0 ? n / 2 - 1 : n / 2);

		while (i <= limit)
		{
			char left, right;

			left = s.charAt(i);
			right = s.charAt(n - i - 1);

			if (left != right)
			{
				count += aToB(left, right);
				count += 2 * pending;
				pending = 0;
			}

			pending++;
			i++;
		}

		i = pos - 1;
		pending = 1;

		while (i >= 0)
		{
			char left, right;

			left = s.charAt(i);
			right = s.charAt(n - i - 1);

			if (left != right)
			{
				count += aToB(left, right);
				count += pending;
				pending = 0;
			}

			pending++;
			i--;
		}

		return count;
	}

	static int goRightThenLeftR()
	{
		int count, i, pending;

		count = 0;
		i = pos;
		pending = 0;

		while (i < n)
		{
			char left, right;

			left = s.charAt(n - i - 1);
			right = s.charAt(i);

			if (left != right)
			{
				count += aToB(left, right);
				count += 2 * pending;
				pending = 0;
			}

			pending++;
			i++;
		}

		i = pos - 1;
		pending = 1;

		while (i >= n / 2)
		{
			char left, right;

			left = s.charAt(n - i - 1);
			right = s.charAt(i);

			if (left != right)
			{
				count += aToB(left, right);
				count += pending;
				pending = 0;
			}

			pending++;
			i--;
		}

		return count;
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
