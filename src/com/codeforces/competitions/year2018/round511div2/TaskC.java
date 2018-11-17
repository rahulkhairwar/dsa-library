package com.codeforces.competitions.year2018.round511div2;

import java.io.*;
import java.util.*;

public class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final int lim = (int) (1.5 * 1e7) + 5;
		int n;
		int[] arr, cnt, spf;
		List<Integer>[] primeFactors;
//		BufferedReader in;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			arr = in.nextIntArray(n);
			cnt = new int[lim];
			spf = new int[lim];
			primeFactors = new List[lim];
			pre();

			int min = arr[0];
			int gcd = arr[0];

			cnt[min]++;

			for (int i = 1; i < n; i++)
			{
				min = Math.min(min, arr[i]);
				gcd = CMath.gcd(gcd, arr[i]);
				cnt[arr[i]]++;
			}

			if (cnt[min] == n)
			{
				out.println(-1);

				return;
			}

			int removeCnt = cnt[min];
			int rem = n - removeCnt;
			int[] b = new int[rem];
			int ctr = 0;

			for (int i = 0; i < n; i++)
			{
				if (arr[i] > min && primeFactors[arr[i]] == null)
				{
					primeFactors[arr[i]] = getPrimeFactors(arr[i]);
					b[ctr++] = arr[i];
				}
				else if (arr[i] > min)
					b[ctr++] = arr[i];
			}

//			int[] match = new int[lim];
			List<Integer>[] match = new List[lim];

			for (int i = 0; i < lim; i++)
				match[i] = new ArrayList<>();

			for (int x : b)
			{
				for (int pf : primeFactors[x])
					match[pf].add(x);
			}

			int newGcd = 1;
			int minCntInd = 0;

			for (int i = 1; i < lim; i++)
			{
				if (match[i].size() == 0)
					continue;

				if (match[i].size() < match[minCntInd].size())
					minCntInd = i;

				if (match[i].size() == rem)
					newGcd *= i;
			}

			if (newGcd > gcd)
			{
				out.println(removeCnt);

				return;
			}

/*			TreeSet<Integer> set = new TreeSet<>(new Comparator<Integer>()
			{
				@Override public int compare(Integer o1, Integer o2)
				{


					return Integer.compare(match[o1].size(), match[o2].size());
				}
			});*/

			HashMap<Integer, List<Integer>> map = new HashMap<>();
			int minSizeInd = 2;

			for (int i = 2; i < lim; i++)
			{
				if (match[i].size() == 0)
					continue;

				int sz = match[i].size();
				List<Integer> list = map.get(sz);

				if (list == null)
				{
					list = new ArrayList<>();
					map.put(sz, list);
				}

				list.add(i);

				if (sz < match[minSizeInd].size())
					minSizeInd = i;
			}


		}

		void pre()
		{
			spf[2] = 2;

			int ctr = 2;

			while ((ctr << 1) < lim)
			{
				spf[ctr] = 2;
				ctr++;
			}

			for (int i = 3; i < lim; i += 2)
			{
				if (spf[i] != 0)
					continue;

				spf[i] = i;
				ctr = i + i;

				while (ctr < lim)
				{
					spf[ctr] = i;
					ctr += i;
				}
			}
		}

		List<Integer> getPrimeFactors(int x)
		{
			List<Integer> pF = new ArrayList<>();

			while (x > 1)
			{
				pF.add(spf[x]);

				int s = spf[x];

				while (x > 1 && x % s == 0)
					x /= s;
			}

			return pF;
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

	public TaskC(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskC", 1 << 29);

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

