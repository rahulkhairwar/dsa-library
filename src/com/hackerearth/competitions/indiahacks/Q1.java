package com.hackerearth.competitions.indiahacks;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

/**
 * Indiahacks Qualifier Round 3 : Zeroes
 */
class Q1
{
	static int t, n, q, primeCount;
	static int[] primes, factors, xFactors, firstPrimeFactor, flagCount;
	static Answer[] answers;
	static boolean[] isPrime, flag;
	static int flagSize;
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
		findPrimes();
		
		factors = new int[100000];
		answers = new Answer[(int) 1e5 + 5];
		
		n = in.nextInt();
		
		for (int i = 0; i < n; i++)
		{
			int a = in.nextInt();
			
			factorize(a);
		}
		
		q = in.nextInt();
		
		while (q-- > 0)
		{
			int x = in.nextInt();
			
			if (answers[x] != null)
			{
				out.println(answers[x].answer);
				
				continue;
			}
			
			xFactors = new int[(int) 1e5 + 5];
/*			flag = new boolean[(int) 1e5 + 5];
			flagCount = new int[(int) 1e5 + 5];
			flagSize = 0;*/
			
			factorizeX(x);
			
			long min = Long.MAX_VALUE;
			
			for (int i = 0; i < primeCount; i++)
			{
				if (primes[i] > x)
					break;
				
				if (xFactors[primes[i]] > 0)
					min = Math.min(min, factors[primes[i]] / xFactors[primes[i]]);
			}
			
/*			int i = 0;
			
			while (i < flagSize)
			{
				min = Math.min(min, factors[flagCount[i]]
						/ xFactors[flagCount[i]]);

				i++;
			}*/

			answers[x] = new Answer(min);
			out.println(min);
		}
	}
	

	static void findPrimes()
	{
		isPrime = new boolean[(int) (1e5 + 5)];
		primes = new int[10000];
		firstPrimeFactor = new int[(int) 1e5 + 5];
		
		isPrime[0] = isPrime[1] = true;
		
		for (int i = 2; i <= (int) 1e5; i++)
		{
			if (isPrime[i])
				continue;
			
			primes[primeCount++] = i;
			firstPrimeFactor[i] = i;
			
			int val = i * 2;
			
			while (val <= (int) 1e5)
			{
				isPrime[val] = true;
				
				if (firstPrimeFactor[val] == 0)
					firstPrimeFactor[val] = i;
				
				val += i;
			}
		}
	}
	
	static void factorize(int a)
	{
		if (a == 0 || a == 1)
			return;
		
		if (!isPrime[a])
		{
			factors[a]++;
			
			return;
		}
		
		factors[firstPrimeFactor[a]]++;
		factorize(a / firstPrimeFactor[a]);
	}
	
	static void factorizeX(int x)
	{
		if (x == 0 || x == 1)
			return;
		
/*		if (!flag[firstPrimeFactor[x]])
		{
			flag[firstPrimeFactor[x]] = true;
			flagCount[flagSize++] = firstPrimeFactor[x];
		}*/
		
		if (!isPrime[x])
		{
			xFactors[x]++;
			
			return;
		}
		
		xFactors[firstPrimeFactor[x]]++;
		factorizeX(x / firstPrimeFactor[x]);
	}
	
	static class Answer
	{
		long answer;
		
		public Answer(long answer)
		{
			this.answer = answer;
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
