package com.codechef.competitions.longcompetitions.year2018.july;

import java.io.*;
import java.util.*;

class PDELIV
{
	public static void main(String[] args)
	{
		new PDELIV(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final long INF = (long) 2e18 + 5;
        int n, m;
        Pizzeria[] pizzerias;
        Consumer[] consumers;
        Point[] pts;
        InputReader in;
        PrintWriter out;

        void solve() throws IOException
		{
			n = in.nextInt();
			m = in.nextInt();
			pizzerias = new Pizzeria[n];
			consumers = new Consumer[m];

			for (int i = 0; i < n; i++)
				pizzerias[i] = new Pizzeria(i, in.nextInt(), in.nextInt());

			for (int i = 0; i < m; i++)
				consumers[i] = new Consumer(i, in.nextInt(), in.nextInt());

			for (int i = 0; i < m; i++)
			{
				for (int j = 0; j < n; j++)
				{
					if (consumers[i].set.contains(j))
						continue;

					long cost = consumers[i].pos - pizzerias[j].pos;

					cost *= cost;
					cost += pizzerias[j].base;
					consumers[i].minCost = Math.min(consumers[i].minCost, cost);
				}
			}

			for (int i = 0; i < m; i++)
				out.println(consumers[i].minCost);
		}

		void solve2() throws IOException
		{
			n = in.nextInt();
			m = in.nextInt();
			pizzerias = new Pizzeria[n];
			consumers = new Consumer[m];

			for (int i = 0; i < n; i++)
				pizzerias[i] = new Pizzeria(i, in.nextInt(), in.nextInt());

			for (int i = 0; i < m; i++)
				consumers[i] = new Consumer(i, in.nextInt(), in.nextInt());

			int size = n + m;

			pts = new Point[size];

			for (int i = 0; i < n; i++)
				pts[i] = new Point(i, pizzerias[i].pos, false);

			for (int i = 0, j = n; i < m; i++, j++)
				pts[j] = new Point(i, consumers[i].pos, true);

			Arrays.sort(pts, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					if (o1.pos == o2.pos)
					{
						if (!o1.isConsumer)
							return -1;
						else if (!o2.isConsumer)
							return 1;
						else
							return 0;
					}

					return Integer.compare(o1.pos, o2.pos);
				}
			});

			int ptr = 0;
			long minPizzeriaCost = INF;
			int startPos = -1;
			long base = -1;

			for (int i = 0; i < size; i++)
			{
				if (pts[i].isConsumer)
				{
					if (startPos == -1)
						continue;

					int ind = pts[i].ind;
					long cost = (consumers[ind].pos - startPos);

					cost *= cost;
					cost += base;

					if (cost < consumers[ind].minCost)
					{
						consumers[ind].minCost = cost;
					}
				}
			}
		}

		class Point
		{
			int ind, pos;
			boolean isConsumer;

			public Point(int ind, int pos, boolean isConsumer)
			{
				this.ind = ind;
				this.pos = pos;
				this.isConsumer = isConsumer;
			}

		}

		class Pizzeria
		{
			int ind, pos, base;

			Pizzeria(int ind, int pos, int base)
			{
				this.ind = ind;
				this.pos = pos;
				this.base = base;
			}

		}

		class Consumer
		{
			int ind, pos, unlikeCnt;
			long minCost;
			Set<Integer> set;

			Consumer(int ind, int pos, int unlikeCnt)
			{
				this.ind = ind;
				this.pos = pos;
				this.unlikeCnt = unlikeCnt;
				minCost = INF;
				set = new HashSet<>();

				for (int i = 0; i < unlikeCnt; i++)
					set.add(in.nextInt() - 1);
			}

		}

		void debug(Object... o)
		{
			System.err.println(Arrays.deepToString(o));
		}

//		uncomment below line to change to BufferedReader
//		public Solver(BufferedReader in, PrintWriter out)
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
				max = Math.max(max, arr[i]);

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
				max = Math.max(max, arr[i]);

			return max;
		}

	}

	static class Utils
	{
		static boolean nextPermutation(int[] arr)
		{
			for (int a = arr.length - 2; a >= 0; --a)
			{
				if (arr[a] < arr[a + 1])
				{
					for (int b = arr.length - 1; ; --b)
					{
						if (arr[b] > arr[a])
						{
							int t = arr[a];

							arr[a] = arr[b];
							arr[b] = t;

							for (++a, b = arr.length - 1; a < b; ++a, --b)
							{
								t = arr[a];
								arr[a] = arr[b];
								arr[b] = t;
							}

							return true;
						}
					}
				}
			}

			return false;
		}

	}

	public PDELIV(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "PDELIV", 1 << 29);

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
