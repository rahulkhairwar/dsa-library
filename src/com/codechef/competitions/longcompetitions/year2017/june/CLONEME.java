package com.codechef.competitions.longcompetitions.year2017.june;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public final class CLONEME
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		int t, n, q, sqrtN;
		int[] arr;
		Query[] queries;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				q = in.nextInt();
				sqrtN = (int) Math.sqrt(n);
				arr = in.nextIntArray(n);
				queries = new Query[q];

				for (int i = 0; i < q; i++)
				{
					int a = in.nextInt() - 1;
					int b = in.nextInt() - 1;
					int c = in.nextInt() - 1;
					int d = in.nextInt() - 1;

					queries[i] = new Query(i, a, b, c, d);
				}

				Arrays.sort(queries, new Comparator<Query>()
				{
					@Override public int compare(Query o1, Query o2)
					{
						if (o1.blockNumber == o2.blockNumber)
							return Integer.compare(o1.b, o2.b);

						return Integer.compare(o1.blockNumber, o2.blockNumber);
					}
				});

				Comparator<Integer> comparator = new Comparator<Integer>()
				{
					@Override public int compare(Integer o1, Integer o2)
					{
						if (o1 <= o2)
							return -1;

						return 1;
					}
				};

//				TreeSet<Integer> map = new TreeSet<>(comparator);
//				TreeSet<Integer> compMap;
				TreeMap<Integer, Integer> map = new TreeMap<>();
				TreeMap<Integer, Integer> compMap;
				int left = 0;
				int right = -1;

				for (int i = 0; i < q; i++)
				{
					while (right < queries[i].b)
					{
						right++;
//						map.add(arr[right]);
						add(map, arr[right]);
					}

					while (left > queries[i].a)
					{
						left--;
//						map.add(arr[left]);
						add(map, arr[left]);
					}

					while (right > queries[i].b)
					{
//						map.remove(arr[right]);
						remove(map, arr[right]);
						right--;
					}

					while (left < queries[i].a)
					{
//						System.out.println("trying to remove " + arr[left] + " from map : " + map);
//						map.remove(arr[left]);
//						System.out.println("after remove operation : " + map);
						remove(map, arr[left]);
						left++;
					}

//					compMap = new TreeSet<>();
					compMap = new TreeMap<>();

					for (int j = queries[i].c; j <= queries[i].d; j++)
						add(compMap, arr[j]);
//						compMap.add(arr[j]);

					if (similar(map, compMap))
						queries[i].areSimilar = true;
				}

				Arrays.sort(queries, new Comparator<Query>()
				{
					@Override public int compare(Query o1, Query o2)
					{
						return Integer.compare(o1.ind, o2.ind);
					}
				});

				for (Query query : queries)
					out.println(query.areSimilar ? "YES" : "NO");
			}
		}

		void add(TreeMap<Integer, Integer> map, int x)
		{
		}

		void remove(TreeMap<Integer, Integer> map, int x)
		{
		}

//		boolean similar(TreeSet<Integer> set, TreeSet<Integer> compSet)
		boolean similar(TreeMap<Integer, Integer> map, TreeMap<Integer, Integer> compMap)
		{
/*			int diff = 0;
			Iterator<Integer> one = set.iterator();
			Iterator<Integer> two = compSet.iterator();
			System.out.println("set : " + set + ", compSet : " + compSet);

			while (one.hasNext())
			{
				if (!one.next().equals(two.next()))
					diff++;

				if (diff > 1)
					return false;
			}

			return true;*/

			int diff = 0;
			Iterator<Map.Entry<Integer, Integer>> one = map.entrySet().iterator();
			Iterator<Map.Entry<Integer, Integer>> two = compMap.entrySet().iterator();
			PriorityQueue<Integer> queue = new PriorityQueue<>();

			return false;
		}

		class Query
		{
			int ind, blockNumber, a, b, c, d;
			boolean areSimilar;

			public Query(int ind, int a, int b, int c, int d)
			{
				this.ind = ind;
				this.a = a;
				this.b = b;
				this.c = c;
				this.d = d;
				blockNumber = ind / sqrtN;
			}

		}

		public Solver(InputReader in, PrintWriter out)
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

}
