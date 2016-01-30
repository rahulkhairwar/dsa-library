package com.codechef.competitions.longcompetitions.year2015.march;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

class CountSubstrings
{
	private static int t, n, k, q, l, r, count0, count1, array0[], array1[];
	private static String s;
	private static BufferedInputStream inputStream;
	private static PrintStream outputStream;
	private static InputReader reader;

	public static void main(String[] args)
	{
		inputStream = new BufferedInputStream(System.in);

		reader = new InputReader(System.in);
		
		//getAttributes();
		
		n = 10;
		System.out.println(readString());
		System.out.println("count 0 : " + count0);
		System.out.println("count 1 : " + count1);
		
		System.out.println(readString());
		System.out.println("count 0 : " + count0);
		System.out.println("count 1 : " + count1);
	}

	public static void getAttributes()
	{
		t = reader.readInt();

		for (int i = 0; i < t; i++)
		{
			n = reader.readInt();
			System.out.println("read n : " + n);
			k = reader.readInt();
			System.out.println("read k : " + k);
			q = reader.readInt();
			System.out.println("read q : " + q);
			array0 = array1 = new int[n];
			
			//reader.read();
			//readString();
			System.out.println("read char");
			s = readString();
			System.out.println("read strig : " + s);

			for (int j = 0; j < q; j++)
			{
				l = reader.readInt();
				r = reader.readInt();

				countSubstrings();
			}
		}
	}

	public static void countSubstrings()
	{
		int j;
		
		for (int i = l - 1; i <= r - k - 1; i++)
		{
			j = i + k + 1;
		}
	}

	public static String readString()
	{
		String s = "";
		char c = 0;
		
		try
		{
			for (int i = 0; i < n; i++)
			{
				c = (char) inputStream.read();
				
				if (c == ' ' || c == '\n')
					break;
				else if(c == '0')
				{
					count0++;
				}
				else if (c == '1')
				{
					count1++;
				}
				
				s += c;
				System.out.println("string currently : " + s);
			}
			/*do
			{
				c = (char) inputStream.read();
				
				if (c == ' ' || c == '\n')
					break;
				else if(c == '0')
					count0++;
				else if (c == '1')
					count1++;
				
				s += c;
			} while (c != ' ' || c != '\n');*/
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return s;
	}

}

class InputReader
{
	private InputStream stream;
	private byte[] buf = new byte[102400]; // a byte buffer to store the
											// characters in
	private int curChar; // to hold the count of current character
	private int numChars; // number of characters

	public InputReader(InputStream stream)
	{ // pass the inputstream class
		this.stream = stream;
	}

	public final int read()
	{
		if (numChars == -1)
			throw new InputMismatchException();

		if (curChar >= numChars)
		{
			curChar = 0;

			try
			{
				numChars = stream.read(buf); // this is the implicit function
												// present in the inputStream
												// which reads a Character into
												// a temporary buffer
			} catch (IOException e)
			{
				throw new InputMismatchException();
			}

			if (numChars <= 0)
				return -1;
		}

		return buf[curChar++];
	}

	public final int readInt()
	{ // a function to read nextInteger skipping the
		// newlines and empty spaces
		int c = read();

		while (isSpaceChar(c))
			//
			c = read();

		int sgn = 1;

		if (c == '-')
		{ // if the number is negative
			sgn = -1;
			c = read();
		}

		int res = 0; // integer variable to hold the number

		do
		{
			if (c < '0' || c > '9')
				throw new InputMismatchException();
			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));

		return res * sgn;
	}

	public final long readLong()
	{ // similar as Integer
		int c = read();

		while (isSpaceChar(c))
			c = read();

		int sgn = 1;

		if (c == '-')
		{
			sgn = -1;
			c = read();
		}

		long res = 0;

		do
		{
			if (c < '0' || c > '9')
				throw new InputMismatchException();
			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));

		return res * sgn;
	}

	public final String readString()
	{
		int c = read(); // read function returns one character at a time and the
						// unicode value of the character is returned

		while (isSpaceChar(c))
			c = read();

		StringBuilder res = new StringBuilder(); // using a String Builder to
													// build the String

		do
		{
			res.appendCodePoint(c); // appendCodePoint function to append the
									// character from its unicode value
			c = read();
		} while (!isSpaceChar(c));

		return res.toString();
	}

	public final static boolean isSpaceChar(int c)
	{
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}

	public final String next()
	{
		return readString();
	}

	public final void close()
	{
		try
		{
			this.stream.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}

/*
1
8
2
3
01110000
1
4
2
4
5
8
*/
