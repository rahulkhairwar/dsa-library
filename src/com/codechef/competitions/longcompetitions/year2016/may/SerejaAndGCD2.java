package com.codechef.competitions.longcompetitions.year2016.may;

import java.io.*;
import java.util.HashSet;
import java.util.InputMismatchException;

/**
 * Created by rahulkhairwar on 14/05/16.
 */
class SerejaAndGCD2
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver();

		solver.solve(1, in, out);

		out.flush();

		in.close();
		out.close();
	}

	static class Solver
	{
		int t, n, m, max;
		int[] primes;
		long[][] nCR;
		HashSet<Integer>[] multiplesOfPrimes, primeFactorization, coprimeSets;
		long[] factorial;
		long mod = (long) (1e9 + 7);
		InputReader in;
		OutputWriter out;

		void solve(int testNumber, InputReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;

			long startTime = System.currentTimeMillis();

//			sieve();
			primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79,
					83, 89, 97};	// 25 primes

			max = (int) CMath.power(2, 25);

			calculateNCR();
			doPrimeFactorization();
			createCoprimeSets();

			System.out.println("time taken : " + (System.currentTimeMillis() - startTime));

/*			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				m = in.nextInt();

				out.println(find());
			}*/
		}

		private void sieve()
		{
			int max = (int) 1e8;
			int primesCount = 0;
			boolean[] isComposite = new boolean[max + 5];

			for (long i = 2; i <= max; i++)
			{
				if (isComposite[(int) i])
					continue;

				primesCount++;

/*				long next = i + i;

				while (next <= max)
				{
					isComposite[(int) next] = true;
					next += i;
				}*/

				if((i*i) <= max)
				{
					for(long j = i*i;j <= max;j += i)
					{
						isComposite[(int) j] = true;
					}
				}
			}

			System.out.println("primesCount : " + primesCount);
		}

		void calculateNCR()
		{
			nCR = new long[100][100];

			nCR[0][0] = 1;

			for (int i = 1; i < 100; i++)
			{
				nCR[i][0] = 1;

				for (int j = 1; j <= i; j++)
					nCR[i][j] = CMath.mod(nCR[i - 1][j - 1] + nCR[i - 1][j], mod);
			}
		}

		void doPrimeFactorization()
		{
			multiplesOfPrimes = new HashSet[101];
			primeFactorization = new HashSet[101];

			for (int i = 2; i <= 100; i++)
				primeFactorization[i] = new HashSet<>();

			boolean[] isComposite = new boolean[101];

			for (int i = 2; i < 101; i++)
			{
				if (isComposite[i])
					continue;

				multiplesOfPrimes[i] = new HashSet<>();
				multiplesOfPrimes[i].add(i);
				primeFactorization[i].add(i);

				int next = i + i;

				while (next < 101)
				{
					isComposite[next] = true;
					multiplesOfPrimes[i].add(next);
					primeFactorization[next].add(i);
					next += i;
				}
			}
		}

		void createCoprimeSets()
		{
			coprimeSets = new HashSet[max + 5];
			int checkCounter = 0;
			System.out.println("max : " + max);

			for (int i = 1; i <= max; i++)
			{
				coprimeSets[i] = new HashSet<>();
				String binary = binaryRepresentation(i, 25);
				int setSize = 0;
				int len = binary.length();

				for (int j = 0; j < len; j++)
				{
					if (binary.charAt(j) == '1')
					{
						setSize++;
						coprimeSets[i].addAll(multiplesOfPrimes[primes[j]]);
					}
				}

				if (i <= 100)
				{
					System.out.println("i : " + i + ", coprimeSets : " + coprimeSets[i].toString());
				}

				if (i % Math.pow(10, 6) == 0)
					System.out.println("check : " + checkCounter++);
			}
		}

		String binaryRepresentation(int num, int requiredLength)
		{
			StringBuilder answer = new StringBuilder();

			while (num != 0)
			{
				answer.append(num % 2);
				num /= 2;
			}

			answer.reverse();

			int len = answer.length();

			if (len < requiredLength)
			{
				String padding = "";

				while (len < requiredLength)
				{
					padding += "0";
					len++;
				}

				return padding + answer.toString();
			}

			return answer.toString();
		}

		boolean areCoPrime(int a, int b)
		{
			return false;
		}

		void calculateFactorials()
		{
			factorial = new long[(int) (1e5 + 5)];

			factorial[1] = 1;

			for (int i = 2; i <= 1e5; i++)
			{
				factorial[i] = CMath.mod(factorial[i - 1] * i, mod);

				if (factorial[i] == 0)
					break;
			}
		}

		long find()
		{
			int count = 0;

			for (int i = 0; i < primes.length; i++)
			{
				if (primes[i] <= m)
					count++;
				else
					break;
			}

			long answer = 0;

			if (count >= n)
			{
//				answer += CMath.mod(nCR[count][n] * factorial[n], mod);
				long temp = 1;

				for (int i = n; i >= 0; i--)
				{
					int ones = i;
					int others = n - i;

/*					System.out.println("count : " + count);
					System.out.println("n : " + n + ", ones : " + ones + ", others : " + others + ", adding : " +
							(temp * nCR[count][others]));*/

					answer += CMath.mod(temp * nCR[count][others], mod);
					answer = CMath.mod(answer, mod);
					temp = CMath.mod(temp * i, mod);
				}
			}
			else
			{
				long temp = 1;

				for (int i = n; i >= n - count; i--)
				{
					int ones = i;
					int others = n - i;

/*					System.out.println("count : " + count);
					System.out.println("n : " + n + ", ones : " + ones + ", others : " + others + ", ncr[][oth] : " +
							nCR[count][others] + ", n! / ones! : " + temp + ", adding : " + (temp * nCR[count][others]));*/
					answer += CMath.mod(temp * nCR[count][others], mod);
					answer = CMath.mod(answer, mod);
					temp = CMath.mod(temp * i, mod);
				}
			}

			return answer;
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

	}

}

/*

4
1 1
3 3
10 20
5 20

2
5 20
10 20

 */
