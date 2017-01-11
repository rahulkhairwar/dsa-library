package com.codeforces.competitions.year2016.round378div2;

import java.io.*;
import java.util.*;

public final class TaskD
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
		int n;
		Stone[] stones;
		Map<Pair, TreeSet<Val>> map;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			stones = new Stone[n];

			for (int i = 0; i < n; i++)
				stones[i] = new Stone(i, in.nextInt(), in.nextInt(), in.nextInt());

			map = new HashMap<>();

			Comparator<Val> comparator = new Comparator<Val>()
			{
				@Override public int compare(Val o1, Val o2)
				{
					if (o1.length == o2.length)
						return Integer.compare(o1.index, o2.index);

					return Long.compare(o2.length, o1.length);
				}
			};

			for (int i = 0; i < n; i++)
			{
				TreeSet<Val> set = map.get(stones[i].ab);

				if (set == null)
				{
					set = new TreeSet<>(comparator);
					set.add(new Val(i, stones[i].c));
					map.put(stones[i].ab, set);
				}
				else
					set.add(new Val(i, stones[i].c));

				set = map.get(stones[i].bc);

				if (set == null)
				{
					set = new TreeSet<>(comparator);
					map.put(stones[i].bc, set);
				}

				set.add(new Val(i, stones[i].a));
				set = map.get(stones[i].ca);

				if (set == null)
				{
					set = new TreeSet<>(comparator);
					map.put(stones[i].ca, set);
				}

				set.add(new Val(i, stones[i].b));
			}

			int one, two;
			long max = 0;

			one = two = -1;

			for (int i = 0; i < n; i++)
			{
				Set<Val> set = map.get(stones[i].ab);

				if (set != null)
				{
					for (Val val : set)
					{
						if (val.index == i)
							continue;

						long min = CMath.min(stones[i].a, stones[i].b, stones[i].c + val.length);

						if (min > max)
						{
							max = min;
							one = i;
							two = val.index;
						}

						break;
					}
				}

				set = map.get(stones[i].bc);

				if (set != null)
				{
					for (Val val : set)
					{
						if (val.index == i)
							continue;

						long min = CMath.min(stones[i].b, stones[i].c, stones[i].a + val.length);

						if (min > max)
						{
							max = min;
							one = i;
							two = val.index;
						}

						break;
					}
				}

				set = map.get(stones[i].ca);

				if (set != null)
				{
					for (Val val : set)
					{
						if (val.index == i)
							continue;

						long min = CMath.min(stones[i].c, stones[i].a, stones[i].b + val.length);

						if (min > max)
						{
							max = min;
							one = i;
							two = val.index;
						}

						break;
					}
				}
			}

			boolean single = false;

			for (int i = 0; i < n; i++)
			{
				long min = CMath.min(stones[i].a, stones[i].b, stones[i].c);

				if (min >= max)
				{
					single = true;
					max = min;
					one = i;
				}
			}

			if (single)
				out.println(1 + "\n" + (one + 1));
			else
			{
				out.println(2);
				out.println(one + 1 + " " + (two + 1));
			}
		}

		class Val
		{
			int index;
			long length;

			Val(int index, long length)
			{
				this.index = index;
				this.length = length;
			}

			@Override public int hashCode()
			{
				return Objects.hash(index, length);
			}

			@Override public boolean equals(Object obj)
			{
				Val val = (Val) obj;

				return index == val.index && length == val.length;
			}

		}

		class Stone
		{
			int index;
			long a, b, c;
			Pair ab, bc, ca;

			Stone(int index, long a, long b, long c)
			{
				this.index = index;

				long[] arr = new long[]{a, b, c};
				Arrays.sort(arr);

				this.a = a = arr[0];
				this.b = b = arr[1];
				this.c = c = arr[2];
				ab = new Pair(a, b);
				bc = new Pair(b, c);
				ca = new Pair(c, a);
			}

		}

		class Pair
		{
			long x, y;

			Pair(long x, long y)
			{
				this.x = x;
				this.y = y;
			}

			@Override public int hashCode()
			{
				return Objects.hash(x, y);
			}

			@Override public boolean equals(Object obj)
			{
				Pair p = (Pair) obj;

				return (x == p.x && y == p.y) || (x == p.y && y == p.x);
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

		static long min(long... arr)
		{
			long min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static long max(long... arr)
		{
			long max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.min(max, arr[i]);

			return max;
		}

		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static int max(int... arr)
		{
			int max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.min(max, arr[i]);

			return max;
		}

	}

}

/*

3
20 30 5
30 20 6
10 10 10

*/
