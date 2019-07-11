package com.codeforces.competitions.year2019.round570div3;

import java.io.*;
import java.util.*;

public class TaskG
{
	public static void main(String[] args)
	{
		new TaskG(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int q;
//		BufferedReader in;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			q = in.nextInt();

			while (q-- > 0)
			{
				int n = in.nextInt();
				Candy[] candies = new Candy[n];

				Map<Integer, Integer> map = new HashMap<>();
				Map<Integer, Integer> giveMap = new HashMap<>();

				for (int i = 0; i < n; i++)
				{
					int type = in.nextInt();
					int give = in.nextInt();
					Integer cnt = map.get(type);
					Integer giveCnt = giveMap.get(type);

					candies[i] = new Candy(type, give);

					if (cnt == null)
						map.put(type, 1);
					else
						map.put(type, cnt + 1);

					if (give == 1)
					{
						if (giveCnt == null)
							giveMap.put(type, 1);
						else
							giveMap.put(type, giveCnt + 1);
					}
				}

				int size = map.size();
				int cnt = 0;
				int[] counts = new int[size];
				Pair[] pairs = new Pair[size];

				for (Map.Entry<Integer, Integer> entry : map.entrySet())
				{
					int count = entry.getValue();
					int giveCnt = giveMap.getOrDefault(entry.getKey(), 0);

					counts[cnt] = count;
					pairs[cnt++] = new Pair(giveCnt, count);
				}

				Arrays.sort(counts);

				long maxPossible = 0;
				int ptr = size - 1;
				long curr = counts[size - 1];

				while (true)
				{
					maxPossible += curr;
					ptr--;

					if (ptr == -1)
						break;

					curr = Math.min(counts[ptr], curr - 1);

					if (curr <= 0)
						break;
				}

				Arrays.sort(pairs, (o1, o2) -> {
					if (o1.totCnt == o2.totCnt)
						return Integer.compare(o2.giveCnt, o1.giveCnt);

					return Integer.compare(o2.totCnt, o1.totCnt);
				});

				long maxGiveCnt = 0;

				ptr = size - 1;
				curr = counts[size - 1];

				while (true)
				{
					System.out.println("pair : " + pairs[ptr].totCnt + ", " + pairs[ptr].giveCnt + ", curr : " + curr + ", mng : " + maxGiveCnt);

//					int sub = (int) Math.max(0, curr - pairs[ptr].giveCnt);
					long sub = Math.min(curr, pairs[ptr].giveCnt);

					maxGiveCnt += sub;
					System.out.println("\tmng updated : " + maxGiveCnt);
					ptr--;

					if (ptr == -1)
						break;

					curr = Math.min(curr - 1, pairs[ptr].totCnt);

					if (curr <= 0)
						break;
				}

				out.println(maxPossible + " " + maxGiveCnt);
			}
		}

		class Candy
		{
			int type, give;

			Candy(int type, int give)
			{
				this.type = type;
				this.give = give;
			}

		}

		class Pair
		{
			int giveCnt, totCnt;

			public Pair(int giveCnt, int totCnt)
			{
				this.giveCnt = giveCnt;
				this.totCnt = totCnt;
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

	public TaskG(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskG", 1 << 29);

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
8
1 0
4 1
2 0
4 1
5 1
6 1
3 0
2 0

1
4
1 1
1 1
2 1
2 1

1
9
2 0
2 0
4 1
4 1
4 1
7 0
7 1
7 0
7 1

*/
