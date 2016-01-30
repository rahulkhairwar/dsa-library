package com.codechef.competitions.shortcompetitions.year2016.january.cookoff;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;

class Q1
{
	static int t, n, arr[];
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
		t = in.nextInt();
		
		while (t-- > 0)
		{
			n = in.nextInt();
			arr = new int[n];
			
			Map<Integer, Integer> map = new HashMap<>();
			TreeSet<Pair> tree = new TreeSet<>(new Comparator<Pair>()
			{
				@Override
				public int compare(Pair o1, Pair o2)
				{
					// never true for this question
					if (o1.number == o2.number)
						return 0;

					if (o2.count >= o1.count)
						return 1;
					else
						return -1;
				}
			});

			for (int i = 0; i < n; i++)
			{
				arr[i] = in.nextInt();
				
				if (map.containsKey(arr[i]))
					map.put(arr[i], map.get(arr[i]) + 1);
				else
					map.put(arr[i], 1);
			}

			Iterator<Entry<Integer, Integer>> iterator = map.entrySet()
					.iterator();

			while (iterator.hasNext())
			{
				Entry<Integer, Integer> curr = iterator.next();
				Pair temp = new Pair(curr.getKey(), curr.getValue());

				tree.add(temp);
			}
			
			int total = 0;
			
			while (tree.size() > 1)
			{
				Pair max, secondMax;
				
				max = tree.pollFirst();
				secondMax = tree.pollFirst();
				
				max.count--;
				secondMax.count--;
				total++;
				
				if (max.count > 0)
					tree.add(max);
				
				if (secondMax.count > 0)
					tree.add(secondMax);
			}
			
			Pair last = tree.pollFirst();
			
			if (last != null)
				total += last.count;
			
			out.println(total);
		}
	}
	
	static class Pair implements Comparable<Pair>
	{
		int number, count;
		
		public Pair(int number, int count)
		{
			this.number = number;
			this.count = count;
		}

		@Override
		public int hashCode()
		{
			return super.hashCode();
		}

		@Override
		public boolean equals(Object obj)
		{
			// for this question, all objects are unique, that is, no 2 objects
			// are ever equal.
			return false;
		}

		@Override
		public int compareTo(Pair o)
		{
			return o.count - this.count;
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

/*

1 6 1 1 2 2 3 3

1
20
5 5 5 5 5 4 4 4 4 3 3 3 3 2 1 8 9 10 11 12
: 10

1
21
5 5 5 5 5 4 4 4 4 3 3 3 3 2 1 8 9 10 11 12 13
: 11


1
15
5 5 5 5 5 4 4 4 4 3 3 3 3 2 1
: 8

1
20
3 3 8 8 8 8 2 2 2 3 3 1 1 1 1 1 4 5 6 7
: 10

1
18
5 5 5 5 5 4 4 4 4 3 3 3 2 2 2 1 1 1
: 9

1
10
1 2 3 4 5 6 7 8 9 10
: 5

1
11
1 2 3 4 5 6 7 8 9 10 11
: 6

1
11
1 2 3 4 5 6 7 8 9 10 10
: 6

1
10
1 2 3 4 5 6 7 8 9 1
: 5

1
10
1 2 3 4 5 6 7 8 1 1
: 5

1
10
1 1 1 1 1 2 3 4 5 7
: 5

1
10
1 1 1 1 1 1 1 1 2 2

1
2
1 2

1
2
1 1

1
3
1 2 3

3
2
1 2
2
1 1
3
1 2 3

1
3
1 2 3

1
4
1 2 3 4

1
10
1 1 2 5 2 3 4 4 4 1

*/
