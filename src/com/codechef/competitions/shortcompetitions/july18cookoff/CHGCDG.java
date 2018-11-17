package com.codechef.competitions.shortcompetitions.july18cookoff;

import java.io.*;
import java.util.*;

class CHGCDG
{
	public static void main(String[] args)
	{
		new CHGCDG(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int t, n;
		int[] cnt;
		long[] arr;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			int lim = (int) 1e6 + 5;

			t = in.nextInt();
			cnt = new int[lim];

			while (t-- > 0)
			{
				n = in.nextInt();
				arr = in.nextLongArray(n);
				Arrays.fill(cnt, 0);

				for (int i = 0; i < n; i++)
				{
					List<Integer> pF = getPrimeFactors(arr[i]);

					for (int p : pF)
						cnt[p]++;
				}

				int gcd = 1;

				for (int i = 0; i < lim; i++)
				{
					if (cnt[i] < n)
						continue;

					if (poss(i, arr))
					{
//						System.out.println("poss for " + i);
//						System.out.println("^^ i : " + i + ", arr : " + Arrays.toString(arr));
						gcd *= i;
//						System.out.println("** i : " + i + ", arr : " + Arrays.toString(arr));
					}
				}

				out.println(gcd);
			}
		}

		boolean poss(int x, long[] arr)
		{
			long[] temp = new long[n];

			System.arraycopy(arr, 0, temp, 0, n);

			long sq = (long) x * x;
			PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>()
			{
				@Override public int compare(Integer o1, Integer o2)
				{
					int a = (int) temp[o1];
					int b = (int) temp[o2];
					int cntA = 0;
					int cntB = 0;

					while (a % x == 0)
					{
						a /= x;
						cntA++;
					}

					while (b % x == 0)
					{
						b /= x;
						cntB++;
					}

//					System.out.println("o1 : " + o1 + ", temp[o1] : " + temp[o1] + ", cntA : " + cntA);
//					System.out.println("o2 : " + o2 + ", temp[o2] : " + temp[o2] + ", cntB : " + cntB);

					return Integer.compare(cntB, cntA);
				}
			});


			for (int i = 0; i < n; i++)
			{
				if (temp[i] % sq == 0)
					queue.add(i);
			}

//			System.out.println("queue : " + queue);

			for (int i = 0; i < n; i++)
			{
				if (temp[i] % x != 0)
				{
//					System.out.println("x : " + x + ", i : " + i + ", temp[i] : " + temp[i]);
					if (queue.size() == 0)
						return false;

					int ind = queue.poll();
//					System.out.println("polled ind : " + ind + ", temp[i] : " + temp[ind]);

					if (temp[ind] % sq != 0)
						return false;

					temp[ind] /= sq;
					temp[i] *= x;

					if (temp[ind] % sq == 0)
						queue.add(ind);

					if (temp[i] % sq == 0)
						queue.add(i);
				}
			}

			for (int i = 0; i < n; i++)
				if (temp[i] % x != 0)
					return false;

			System.arraycopy(temp, 0, arr, 0, n);

			return true;
		}

		boolean poss(long gcd)
		{
			List<Integer> primeFactors = getPrimeFactors(gcd);
			long[] temp = new long[n];

			System.arraycopy(arr, 0, temp, 0, n);

			for (int pf : primeFactors)
			{
				long sq = pf * pf;
				PriorityQueue<Integer> squares = new PriorityQueue<>(new Comparator<Integer>()
				{
					@Override public int compare(Integer o1, Integer o2)
					{
						int a = (int) temp[o1];
						int b = (int) temp[o2];
						int cntA = 0;
						int cntB = 0;

						while (a % sq == 0)
						{
							a /= sq;
							cntA++;
						}

						while (b % sq == 0)
						{
							b /= sq;
							cntB++;
						}

						return Integer.compare(cntB, cntA);
					}
				});

				for (int i = 0; i < n; i++)
				{
					if (temp[i] % sq == 0)
						squares.add(i);
				}

				for (int i = 0; i < n; i++)
				{
					if (temp[i] % sq != 0)
					{
						if (squares.size() == 0)
							return false;

						int ind = squares.poll();

						temp[ind] /= sq;
						temp[i] *= pf;

						if (temp[ind] % sq == 0)
							squares.add(ind);

						if (temp[i] % sq == 0)
							squares.add(i);
					}
				}
			}

			for (int i = 0; i < n; i++)
				if (temp[i] % gcd != 0)
					return false;

			return true;
		}

		List<Integer> getPrimeFactors(long x)
		{
			int sqrt = (int) Math.sqrt(x);
			List<Integer> primeFactors = new ArrayList<>();

			for (int i = 2; i <= sqrt; i++)
			{
				while (x % i == 0)
				{
					x /= i;
					primeFactors.add(i);
				}
			}

			if (x > 1)
				primeFactors.add((int) x);

			return primeFactors;
		}

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

	public CHGCDG(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "CHGCDG", 1 << 29);

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
5
27 3 15 1024 15

1
5
8 16 32 3 6

*/
