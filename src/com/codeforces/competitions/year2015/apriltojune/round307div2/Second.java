package com.codeforces.competitions.year2015.apriltojune.round307div2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.InputMismatchException;

public final class Second
{
	private static String a, b, c;
	private static int charsA[], charsB[], charsC[];
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		Second second = new Second();
		
		reader = second.new InputReader(System.in);
		writer = second.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		a = reader.next();
		b = reader.next();
		c = reader.next();
		
		charsA = new int[26];
		charsB = new int[26];
		charsC = new int[26];
		
		int lenA, lenB, lenC;
		
		lenA = a.length();
		lenB = b.length();
		lenC = c.length();
		
		for (int i = 0; i < lenA; i++)
			charsA[a.charAt(i) - 97]++;
		
		for (int i = 0; i < lenB; i++)
			charsB[b.charAt(i) - 97]++;
		
		for (int i = 0; i < lenC; i++)
			charsC[c.charAt(i) - 97]++;
		
		
/*		System.out.println("a : " + Arrays.toString(charsA));
		System.out.println("b : " + Arrays.toString(charsB));
		System.out.println("c : " + Arrays.toString(charsC));*/
		
		/*************************************************/
		
/*		boolean canB, canC;
		
		canB = true;
		canC = true;
		
		for (int i = 0; i < 26; i++)
		{
			// System.out.println("i : " + i + ", charsB[i] : " + charsB[i] + ", charsA[i] : " + charsA[i]);
			
			if (charsB[i] > charsA[i])
			{
				canB = false;
				
				break;
			}
		}
		
		// System.out.println("canB : " + canB);
		
		int minB = findMinB();
		StringBuilder k = new StringBuilder();
		
		// System.out.println("minB : " + minB);
		
		while (minB > 0 && canB)
		{
			k.append(b);
			
			for (int i = 0; i < 26; i++)
			{
				charsA[i] -= charsB[i];
				
				if (charsA[i] < charsB[i])
				{
					// System.out.println("doing for i : " + i);
					canB = false;
				}
			}
		}
		
		for (int i = 0; i < 26; i++)
		{
			// System.out.println("i : " + i + ", charsC[i] : " + charsC[i] + ", charsA[i] : " + charsA[i]);
			
			if (charsC[i] > charsA[i])
			{
				canC = false;
				
				break;
			}
		}
		
		// System.out.println("canC : " + canC);
		
		int minC = findMinC();
		
		while (minC > 0 && canC)
		{
			k.append(c);
			
			for (int i = 0; i < 26; i++)
			{
				charsA[i] -= charsC[i];
				
				if (charsA[i] < charsC[i])
					canC = false;
			}
		}
		
		for (int i = 0; i < 26; i++)
		{
			while (charsA[i] > 0)
			{
				// System.out.println("appending : " + (char) (i + 97));
				k.append((char) (i + 97));
				
				charsA[i]--;
			}
		}*/
		
		// writer.print("ans : ");
		// writer.println(k.toString());
		
		if (appendBOrC() == 1)
			appendBFirst();
		else
			appendCFirst();
	}
	
	public static void appendBFirst()
	{
		boolean canB, canC;
		
		canB = true;
		canC = true;
		
		for (int i = 0; i < 26; i++)
		{
			// System.out.println("i : " + i + ", charsB[i] : " + charsB[i] + ", charsA[i] : " + charsA[i]);
			
			if (charsB[i] > charsA[i])
			{
				canB = false;
				
				break;
			}
		}
		
		// System.out.println("canB : " + canB);
		
		int minB = findMinB();
		StringBuilder k = new StringBuilder();
		
		// System.out.println("minB : " + minB);
		
		while (minB > 0 && canB)
		{
			k.append(b);
			
			for (int i = 0; i < 26; i++)
			{
				charsA[i] -= charsB[i];
				
				if (charsA[i] < charsB[i])
				{
					// System.out.println("doing for i : " + i);
					canB = false;
				}
			}
		}
		
		for (int i = 0; i < 26; i++)
		{
			// System.out.println("i : " + i + ", charsC[i] : " + charsC[i] + ", charsA[i] : " + charsA[i]);
			
			if (charsC[i] > charsA[i])
			{
				canC = false;
				
				break;
			}
		}
		
		// System.out.println("canC : " + canC);
		
		int minC = findMinC();
		
		while (minC > 0 && canC)
		{
			k.append(c);
			
			for (int i = 0; i < 26; i++)
			{
				charsA[i] -= charsC[i];
				
				if (charsA[i] < charsC[i])
					canC = false;
			}
		}
		
		for (int i = 0; i < 26; i++)
		{
			while (charsA[i] > 0)
			{
				// System.out.println("appending : " + (char) (i + 97));
				k.append((char) (i + 97));
				
				charsA[i]--;
			}
		}
		
		// writer.print("ans : ");
		writer.println(k.toString());
	}
	
	public static void appendCFirst()
	{
		boolean canB, canC;
		
		canB = true;
		canC = true;
		
		for (int i = 0; i < 26; i++)
		{
			// System.out.println("i : " + i + ", charsC[i] : " + charsC[i] + ", charsA[i] : " + charsA[i]);
			
			if (charsC[i] > charsA[i])
			{
				canC = false;
				
				break;
			}
		}
		
		// System.out.println("canB : " + canB);
		
		StringBuilder k = new StringBuilder();
		
		// System.out.println("minB : " + minB);
		
		int minC = findMinC();
		
		while (minC > 0 && canC)
		{
			k.append(c);
			
			for (int i = 0; i < 26; i++)
			{
				charsA[i] -= charsC[i];
				
				if (charsA[i] < charsC[i])
					canC = false;
			}
		}
		
		int minB = findMinB();
		
		for (int i = 0; i < 26; i++)
		{
			// System.out.println("i : " + i + ", charsB[i] : " + charsB[i] + ", charsA[i] : " + charsA[i]);
			
			if (charsB[i] > charsA[i])
			{
				canB = false;
				
				break;
			}
		}
		
		// System.out.println("canC : " + canC);
		
		// int minC = findMinC();
		
		while (minB > 0 && canB)
		{
			k.append(b);
			
			for (int i = 0; i < 26; i++)
			{
				charsA[i] -= charsB[i];
				
				if (charsA[i] < charsB[i])
				{
					// System.out.println("doing for i : " + i);
					canB = false;
				}
			}
		}
		
		for (int i = 0; i < 26; i++)
		{
			while (charsA[i] > 0)
			{
				// System.out.println("appending : " + (char) (i + 97));
				k.append((char) (i + 97));
				
				charsA[i]--;
			}
		}
		
		// writer.print("ans : ");
		writer.println(k.toString());
	}
	
	public static int findMinB()
	{
		int min = 100000000;
		
		for (int i = 0; i < 26; i++)
		{
			if (charsB[i] < min && charsB[i] != 0)
				min = charsB[i];
		}
		
		return min;
	}
	
	public static int findMinC()
	{
		int min = 100000000;
		
		for (int i = 0; i < 26; i++)
		{
			if (charsC[i] < min && charsC[i] != 0)
				min = charsC[i];
		}
		
		return min;
	}
	
	public static int appendBOrC()
	{
		int bCount, cCount;
		
		bCount = 10000000;
		cCount = 10000000;
		
		for (int i = 0; i < 26; i++)
		{
			if (charsB[i] != 0 && charsA[i] / charsB[i] < bCount)
				bCount = charsA[i] / charsB[i];
		}
		
		for (int i = 0; i < 26; i++)
		{
			if (charsC[i] != 0 && charsA[i] / charsC[i] < cCount)
				cCount = charsA[i] / charsC[i];
		}
		
		return (bCount > cCount ? 1 : 2);
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
			}
			while (!isSpaceChar(c));

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
			}
			while (!isSpaceChar(c));

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
			}
			while (!isSpaceChar(c));

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

		public void println(long x)
		{
			writer.println(x);
		}

		public void print(int x)
		{
			writer.print(x);
		}

		public void print(long x)
		{
			writer.println(x);
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
