package com.codechef.competitions.longcompetitions.year2016.october;

import java.io.*;
import java.util.*;

class FenwickIterations
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		int t;
		long n;
		String a, b, c;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				a = in.next();
				b = in.next();
				c = in.next();
				n = in.nextInt();

				long lenA, lenB, lenC, zeroesA, zeroesB, zeroesC, onesA, onesB, onesC;
				long ans = 1;

				lenA = a.length();
				lenB = b.length();
				lenC = c.length();
				zeroesA = countZeroes(a);
				zeroesB = countZeroes(b);
				zeroesC = countZeroes(c);
				onesA = countOnes(a);
				onesB = countOnes(b);
				onesC = countOnes(c);

				if (endingOnes(c) == lenC)
				{    // c has only 1s
					if (endingOnes(b) == lenB)
					{    // b also has only 1s
						if (endingOnes(a) == onesA)    // a also has only 1s, so L = 111....1
							out.println(1);
						else
						{    // a has 1 or more 1s after 1 or more 0s from the right.
							int aa = nonEndingOnes(a);

							out.println(aa + 1);
						}
					}
					else
					{    // b has 1 or more 1s after 1 or more 0s from the right.
						int bb = nonEndingOnes(b);

						ans += bb;
						ans += (n - 1) * onesB;
						ans += onesA;

						out.println(ans);
					}

					continue;
				}

				if (zeroesA == lenA)
				{    // a has only 0s
					if (zeroesB == lenB)
					{    // b also has only 0s
						if (zeroesC == lenC)    // c also has only 0s, so L = 0.
							out.println(1);
						else
						{    // only 1s that come after 1 or more 0s from right in c are counted.
							int cc = nonEndingOnes(c);

							out.println(cc + 1);
						}
					}
					else
					{    // b does have 1s
						if (onesC != 0)
						{
							int cc = nonEndingOnes(c);

							if (c.charAt(0) == '0')
							{
								ans += cc;
								ans += n * onesB;

								out.println(ans);
							}
							else
							{
								int bb = nonEndingOnes(b);

								if (onesC == lenC)
								{
									ans += bb;
									ans += (n - 1) * onesB;
								}
								else
								{
									ans += cc;
									ans += n * onesB;
								}

								out.println(ans);
							}
						}
						else
						{
							ans += n * onesB;

							out.println(ans);
						}
					}
				}
				else
				{
					int cc = nonEndingOnes(c);

					if (endingOnes(c) == lenC)
					{
						int bb = nonEndingOnes(b);

						if (bb == 0)
						{
							int aa = nonEndingOnes(a);

							if (aa == 0)
								out.println(1);
							else
								out.println(aa + 1);
						}
						else
						{
							if (endingOnes(c) == lenC)
								ans += bb;
							else
								ans += onesB;

							ans += (n - 1) * onesB;
							ans += onesA;

							out.println(ans);
						}
					}
					else
					{
						ans += cc;
						ans += onesB * n;
						ans += onesA;

						out.println(ans);
					}
				}
			}
		}

		int nonEndingOnes(String s)
		{
			int len = s.length();
			int cnt = 0;
			int i = len - 1;

			while (i >= 0 && s.charAt(i) == '1')
				i--;

			while (i >= 0)
			{
				if (s.charAt(i) == '1')
					cnt++;

				i--;
			}

			return cnt;
		}

		int endingOnes(String s)
		{
			int len = s.length();
			int cnt = 0;
			int i = len - 1;

			while (i >= 0 && s.charAt(i) == '1')
			{
				cnt++;
				i--;
			}

			return cnt;
		}

		int countZeroes(String s)
		{
			int len = s.length();
			int cnt = 0;

			for (int i = 0; i < len; i++)
				if (s.charAt(i) == '0')
					cnt++;

			return cnt;
		}

		int countOnes(String s)
		{
			int len = s.length();
			int cnt = 0;

			for (int i = 0; i < len; i++)
				if (s.charAt(i) == '1')
					cnt++;

			return cnt;
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

		public void println(char x)
		{
			writer.println(x);
		}

		public void print(char x)
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

		public void printf(String format, Object args)
		{
			writer.printf(format, args);
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

}

/*

4
001 100 011 4
1000 1101 100 3
1010 001 101 4
010 101 000 4

1
001 100 011 4

4
111 11111 1111111 5
111 1111 11 2
1001 1001011 000 2
1010 101101 101 2

1
11 1 0111 2

*/
