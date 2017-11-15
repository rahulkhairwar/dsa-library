package com.codechef.practice.easy.year2015;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class PrimePalindromes
{
	private static int n, nPlus1, primeCount, primes[];
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		PrimePalindromes palindromes = new PrimePalindromes();
		
		reader = palindromes.new InputReader(System.in);
		writer = palindromes.new OutputWriter(System.out);

		getAttributes();
		
		
/*		System.out.println("The first 1000 prime numbers are : ");
		
		for (int i = 0; i < 10000; i++)
			System.out.println(primes[i]);*/
		
		// test();
		
		// System.out.println(isPrime(1000001));
		// System.out.println(isPalindrome(1000001));
		// isPalindrome(48765);
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void test()
	{
		primes = new int[10060];
		
		primes[0] = 2;
		primeCount = 1;
		
		for (int i = 3; i < 100003; i += 2)
			if (isPrime2(i))// && isPalindrome(i))
				System.out.println(i);
		
/*		System.out.println("first 10 primes : ");
		
		for (int i = 0; i < 10; i++)
			System.out.println(primes[i]);*/
		
		for (int i = 1000001; i < 1100000; i++)
			if (isPrime2(i))
			{
				if (isPalindrome(i))
				{
					System.out.println("finally found the next Prime Palindrome!! It is : " + i);
					
					i = 1100000;
				}
				
				primeCount++;
			}
		
		System.out.println("Total number of prime numbers : " + primeCount);
	}
	
	public static void getAttributes()
	{
		primes = new int[78500];
		
		primes[0] = 2;
/*		primes[1] = 3;
		primes[2] = 5;
		primes[3] = 7;*/
		
		primeCount = 1;
		
		n = reader.nextInt();
		nPlus1 = n + 1;
		
		if (n == 2)
		{
			writer.println(2);
			
			return;
		}
		else if (n >= 1000000)
		{
			writer.println(1003001);
			
			return;
		}
		
		findPrimes();
	}
	
	public static void findPrimes()
	{
		for (int i = 3; i /*< 1000001*/< n; i += 2)
			if (isPrime(i))
				primeCount++;
		
		if (isPrime(n) && isPalindrome(n))
		{
			writer.println(n);
			
			return;
		}
		
		for (int i = (n % 2 == 0 ? nPlus1 : n + 2); i < 1000000; i += 2)
			if (isPrime(i) && isPalindrome(i))
			{
				writer.println(i);
				
				return;
			}
		
		writer.println(1003001);
		
		// System.out.println("Total number of prime numbers : " + primeCount);
	}
	
	public static boolean isPrime(int a)
	{
		int sq;
		
		sq = (int) Math.sqrt(a);
		
		for (int i = 0; (primes[i] <= sq && i < primeCount); i++)
			if (a % primes[i] == 0)
				return false;
		
		primes[primeCount] = a;
		
		return true;
	}
	
	public static boolean isPrime2(int a)
	{
		int sq;
		
		sq = (int) Math.sqrt(a);
		
		for (int i = 0; (primes[i] <= sq && i < primeCount); i++)
			if (a % primes[i] == 0)
				return false;
		
		primes[primeCount++] = a;
		
		return true;
	}

	public static boolean isPalindrome(int a)
	{
		String num;
		int length;
		
		num = "" + a;
		length = num.length();
		
		for (int i = 0; i < length / 2; i++)
			if (num.charAt(i) != num.charAt(length - 1 - i))
				return false;
		
		return true;
	}
	
	class InputReader
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

	class OutputWriter
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

		public void println(long x)
		{
			writer.println(x);
		}

		public void print(long x)
		{
			writer.print(x);
		}

		public void println(String s)
		{
			writer.println(s);
		}

		public void print(String s)
		{
			writer.print(s);
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

}
