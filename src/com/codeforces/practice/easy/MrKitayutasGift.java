package com.codeforces.practice.easy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public final class MrKitayutasGift
{
	private static InputReader reader;
	private static OutputWriter writer;

	public static void main(String[] args)
	{
		MrKitayutasGift gift = new MrKitayutasGift();

		reader = gift.new InputReader(System.in);
		writer = gift.new OutputWriter(System.out);

		getAttributes();

		writer.flush();

		reader.close();
		writer.close();
	}

	public static void getAttributes()
	{
		String s = reader.next();

		if (isPalindrome(s))
		{
			String answer = "";
			int length = s.length();

			for (int i = 0; i < length / 2; i++)
				answer += s.charAt(i);

			answer += s.charAt(length / 2);

			for (int i = length / 2; i < length; i++)
				answer += s.charAt(i);

			writer.println(answer);

			return;
		}

		String answer;
		int length, count, pos;
		boolean result;

		answer = "";
		length = s.length();
		count = 0;
		pos = 0;
		result = false;

		int i, j;

		i = 0;
		j = length - 1;

		while (s.charAt(i) == s.charAt(j) && i < length / 2)
		{
			// System.out.println("current i : " + i + ", j : " + j);

			i++;
			j--;
		}

		while (true && i < length / 2)
		{
			// System.out.println("current i : " + i + ", j : " + j + ", count : " + count);

			if (s.charAt(i) == s.charAt(j - 1))
			{
				// System.out.println("here");

				if (isPalindrome(s.substring(i, j)))
				{
					pos = i;
					
					// System.out.println("postiion is : " + pos);
					
					break;
				}
				else if (s.charAt(i + 1) == s.charAt(j))
				{
					if (isPalindrome(s.substring(i + 1, j + 1)))
					{
						pos = j;
						
						// System.out.println("position is : " + pos);
						
						break;
					}
					else
					{
						writer.println("NA");
						
						return;
					}
				}
				else
				{
					writer.println("NA");
					
					return;
				}
			}
			else if (s.charAt(i + 1) == s.charAt(j))
			{
				// System.out.println("yo!!");
				
				if (isPalindrome(s.substring(i + 1, j + 1)))
				{
					pos = j;
					
					// System.out.println("position is : " + pos);
					
					break;
				}
				else
				{
					writer.println("NA");
					
					return;
				}
			}
			else if (s.charAt(i) != s.charAt(j))
				count++;
			else
			{
				writer.println("NA");
				
				return;
			}

			if (count > 1)
				break;

			// System.out.println("leaving i : " + i + ", j : " + j + ", count : " + count);

			i++;
			j--;
		}

		// System.out.println("count : " + count + ", pos : " + pos);

		if (count <= 1)
			result = true;

		if (result == false)
			writer.println("NA");
		else
		{
			if (length % 2 != 0)
			{
				if (pos > length / 2)
				{
					if (s.charAt(length / 2) != s.charAt(length / 2 + 1))
						result = false;
				}
				else if (s.charAt(length / 2) != s.charAt(length / 2 - 1))
					result = false;
			}

			if (!result)
			{
				writer.println("NA");

				return;
			}

			if (pos < length / 2)
			{
				for (int k = 0; k < pos; k++)
					answer += s.charAt(k);

				answer += s.charAt(length - 1 - pos);

				for (int k = pos; k < length; k++)
					answer += s.charAt(k);
			}
			else
			{
				for (int k = 0; k <= pos; k++)
					answer += s.charAt(k);

				answer += s.charAt(length - 1 - pos);
				
				for (int k = pos + 1; k < length; k++)
					answer += s.charAt(k);
			}

			writer.println(answer);
		}
	}

	public static boolean isPalindrome(String s)
	{
		int length = s.length();

		for (int i = 0; i < length / 2; i++)
			if (s.charAt(i) != s.charAt(length - 1 - i))
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
 * 
 * reviive reviave
 */
