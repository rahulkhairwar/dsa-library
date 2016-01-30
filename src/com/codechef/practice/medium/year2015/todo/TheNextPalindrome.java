package com.codechef.practice.medium.year2015.todo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class TheNextPalindrome
{
	private static int t;
	private static String number;
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		TheNextPalindrome palindrome = new TheNextPalindrome();
		
		reader = palindrome.new InputReader(System.in);
		writer = palindrome.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		int length, lengthMinusOne, incrementAt;
		char first, last;
		boolean canMakePalindromeWithoutIncreasingLength;
		
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			number = reader.next();
			
			length = number.length();
			lengthMinusOne = length - 1;
			incrementAt = -1;
			canMakePalindromeWithoutIncreasingLength = false;
			
			int j , k, flag;
			
			// if any digit is 9, the number can be converted into a 
			// palindrome without increasing it's length
			for (int l = 0; l < length; l++)
			{
				if (number.charAt(l) != '9')
				{
					canMakePalindromeWithoutIncreasingLength = true;
					
					break;
				}
			}
			
			flag = (length % 2 == 0 ? length / 2 - 1 : length / 2);
			
			for (j = 0, k = length - 1; j < length; j++, k--)
			{
				first = number.charAt(j);
				last = number.charAt(k);
				
				if (last < first)
				{
					incrementAt = k;
					canMakePalindromeWithoutIncreasingLength = true;
					
					break;
				}
			}
			
			if (length % 2 == 1 && incrementAt < length / 2)
			{
				if (number.charAt(length / 2) < '9')
					incrementAt = length / 2;
			}
			
			System.out.println("inremented at : " + incrementAt);
			
			StringBuilder answer = new StringBuilder();
			
			if (canMakePalindromeWithoutIncreasingLength)
			{
				int compare, temp;
				
				compare = (length % 2 == 0 ? length / 2 - 1 : length / 2);
				temp = (incrementAt < compare ? incrementAt : compare);
				
				for (j = 0; j < compare; j++)
					answer.append(number.charAt(j));
				
				answer.append((char) (number.charAt(incrementAt) + 1));
				
				int b = length / 2 - incrementAt;
				
				if (length % 2 == 0)
					b--;
				
				j = (b % 2 == 0 ? 1 : 0);
				j = 0;
				
				for (; j < b; j++)
					answer.append((char) 48);
				
				String a = answer.toString();
				int len2, diff;
				
				len2 = a.length();
				diff = length - len2;
				
/*				j = (length % 2 == 0 ? len2 - 1 : len2 - 2);
						
				for (; j >= 0; j--)
					answer.append(a.charAt(j));*/
				
				while (diff > 1 && answer.toString().length() - 1 < incrementAt)
				{
					answer.append(a.charAt(diff - 1));
					
					diff--;
					
					System.out.println("current answer : " + answer.toString());
				}
				
				answer.append((char) (number.charAt(incrementAt) + 1));
				diff--;
				
				System.out.println("current answer (outside) : " + answer.toString());
				
				while (diff > 0)
				{
					System.out.println("app : " + a.charAt(diff - 1));
					answer.append(a.charAt(diff - 1));
					
					diff--;
				}
			}
			else
			{
				for (j = (length % 2 == 0 ? length / 2 : length / 2 + 1); j < length; j++)
						if (number.charAt(j) != '9')
						{
							canMakePalindromeWithoutIncreasingLength = true;
							
						}
				
				if (canMakePalindromeWithoutIncreasingLength)
				{
					for (j = 0; j < length; j++)
						answer.append(9);
				}
				else
				{
					answer.append((char) 49);

					for (j = 0; j < length - 1; j++)
						answer.append((char) 48);

					answer.append((char) 49);
				}
			}
			
			writer.println(answer.toString());
		}
	}

	public static void getAttributes2()
	{
		int length, lengthMinusOne, incrementAt;
		char first, last;
		boolean canMakePalindrome;
		
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			number = reader.next();
			
			length = number.length();
			lengthMinusOne = length - 1;
			incrementAt = -1;
			canMakePalindrome = true;
			
			int j, k, flag;
			
			for (j = 0, k = length - 1; j < length; j++, k--)
			{
				first = number.charAt(j);
				last = number.charAt(k);
				
				if (last < first)
				{
					incrementAt = k;
					canMakePalindrome = true;
					
					// break;
				}
				else if (last == first && first != '9')
				{
					incrementAt = k;
					canMakePalindrome = true;
				}
			}
			
			if (length % 2 == 1 && incrementAt < length / 2)
				if (number.charAt(length / 2) < '9')
					incrementAt = length / 2;
			
			System.out.println("final incrementAt : " + incrementAt);
			
			StringBuilder answer = new StringBuilder();
			
			// if (canMakePalindrome)
		}
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
		
		public String nextLine()
		{
			int c = read();

			StringBuilder result = new StringBuilder();

			do
			{
				result.appendCodePoint(c);
				
				c = read();
			} while (c != '\n');

			return result.toString();
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

}

/*

14
123459976543
100001
808
2133
899999 999999
808 818 88188 99199
88991 8899991 8899988899231 889989999123455555

1
5465454

answer : 5465645

2
5666666
5999999

answer : 5667665, 6000006 

5666666
5223111
7342222

*/
