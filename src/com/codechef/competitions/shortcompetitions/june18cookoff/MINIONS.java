package com.codechef.competitions.shortcompetitions.june18cookoff;

import java.io.*;
import java.util.*;

class MINIONS
{
	public static void main(String[] args)
	{
		new MINIONS(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int t, n;
		Pair[] pairs;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				pairs = new Pair[n];

				for (int i = 0; i < n; i++)
					pairs[i] = new Pair(i, in.nextInt(), in.nextInt());

				Arrays.sort(pairs, Comparator.comparingDouble(a -> a.a));
				out.println(binarySearch());
			}
		}

		int binarySearch()
		{
			int low = 0;
			int high = n - 1;
			int ans = 0;

			while (low <= high)
			{
				int mid = low + high >> 1;
				int midCnt = find(mid);

				ans = Math.max(ans, midCnt);

				if (mid == n - 1 || find(mid + 1) < ans)
					break;
				else
				{
//					System.out.println("mid : " + mid + ", low : " + low);
					low = mid + 1;
				}

/*				if (mid == -1 || mid == n)
					break;

				int midCnt = find(mid);
				int x;

				ans = Math.max(ans, midCnt);

				if (mid < n - 1 && (x = find(mid + 1)) > ans)
				{
					ans = x;
					low = mid + 1;
				}
				else if (mid > 0)
					high = mid - 1;*/
			}

			return ans;
		}

		int find(int ind)
		{
//			System.out.println("ind : " + ind + ", n : " + n);

			TreeSet<Pair> set = new TreeSet<>(new Comparator<Pair>()
			{
				@Override public int compare(Pair o1, Pair o2)
				{
					if (o1.diff == o2.diff)
					{
						if (o1.a == o2.a)
							return Integer.compare(o1.ind, o2.ind);

						return Double.compare(o2.a, o1.a);
					}

					return Double.compare(o2.diff, o1.diff);
				}
			});

			for (int i = ind + 1; i < n; i++)
				set.add(pairs[i]);

			double min = pairs[ind].a;
			double sum = pairs[ind].b;
			int cnt = 1;

			while (set.size() > 0)
			{
				Pair pair = set.pollFirst();
				double mm = Math.min(min, pair.a);

				if (mm >= (sum + pair.b) / (cnt + 1))
				{
					min = mm;
					sum += pair.b;
					cnt++;
				}
			}

//			System.out.println("with min : " + pairs[ind].a + ", sum : " + sum + ", cnt : " + cnt + ", min : " + min);

			if (min >= sum / cnt)
				return cnt;

			return 0;
		}

		class Pair
		{
			int ind;
			double a, b, div, diff;

			public Pair(int ind, double a, double b)
			{
				this.ind = ind;
				this.a = a;
				this.b = b;
				div = a / b;
				diff = a - b;
			}

			@Override public boolean equals(Object o)
			{
				Pair pair = (Pair) o;

				return Double.compare(pair.a, a) == 0 && Double.compare(pair.b, b) == 0;
			}

			@Override public int hashCode()
			{
				return Objects.hash(a, b);
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

		@Override public void run()
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

	public MINIONS(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "MINIONS", 1 << 29);

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
