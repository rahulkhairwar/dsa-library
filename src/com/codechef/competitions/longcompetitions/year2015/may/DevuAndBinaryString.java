package com.codechef.competitions.longcompetitions.year2015.may;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.InputMismatchException;

class DevuAndBinaryString
{
	private static int t, n, k;
	private static String s;
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		DevuAndBinaryString string = new DevuAndBinaryString();
		
		reader = string.new InputReader(System.in);
		writer = string.new OutputWriter(System.out);
		
		getAttributes();
		getAttributes2();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			n = reader.nextInt();
			k = reader.nextInt();
			s = reader.next();
			
			if (k == 1)
			{
				String zeroFirst, oneFirst;
				
				zeroFirst = oneFirst = "";
				
				for (int j = 0; j < n; j++)
				{
					if (j % 2 == 0)
					{
						zeroFirst += 0;
						oneFirst += 1;
					}
					else
					{
						zeroFirst += 1;
						oneFirst += 0;
					}
				}
				
				// System.out.println("zeroFirst : " + zeroFirst + ", oneFirst : " + oneFirst);
				
				int countWithZeroFirst, countWithOneFirst;
				
				countWithZeroFirst = countWithOneFirst = 0;
				
				for (int j = 0; j < n; j++)
				{
					if (s.charAt(j) != zeroFirst.charAt(j))
						countWithZeroFirst++;
					if (s.charAt(j) != oneFirst.charAt(j))
						countWithOneFirst++;
				}
				
				if (countWithZeroFirst < countWithOneFirst)
					writer.println(countWithZeroFirst + "\n" + zeroFirst);
				else
					writer.println(countWithOneFirst + "\n" + oneFirst);
			}
			else if (k >= n)
				writer.println(0 + "\n" + s);
			else
			{
				String tokenArray[];
				char currentChar;
				int numberOfTokens;
				
				tokenArray = new String[n];
				numberOfTokens = 0;
				
				Arrays.fill(tokenArray, "");
				
				for (int j = 0; j < n;)
				{
					currentChar = s.charAt(j);
					tokenArray[numberOfTokens] += currentChar;
					
					j++;
					
					while (j < n && s.charAt(j) == currentChar)
					{
						tokenArray[numberOfTokens] += currentChar;
						
						j++;
					}
					
					numberOfTokens++;
				}
				
				/*System.out.println("the tokens arrays looks like :\n" + Arrays.toString(tokenArray));
				System.out.println("and the number of tokens is : " + numberOfTokens);*/
				
				StringBuilder answer;
				int inversionCount, kPlusOne, length, currentCount;
				char inverseChar, token[];
				
				answer = new StringBuilder("");
				inversionCount = 0;
				kPlusOne = k + 1;
				
				for (int j = 0; j < numberOfTokens; j++)
				{
					length = tokenArray[j].length();
					
					currentCount = length / kPlusOne;
					
					// System.out.println("length : " + length + ", currentCount : " + currentCount + ", tokenArray[curr] " + tokenArray[j]);
					
					if (currentCount == 0)
					{
						answer.append(tokenArray[j]);
						
						// System.out.println("appending : " + tokenArray[j]);
						
						continue;
					}
					
					inversionCount = inversionCount + currentCount;
					
					if (tokenArray[j].charAt(0) == '0')
						inverseChar = '1';
					else
						inverseChar = '0';
					
					token = tokenArray[j].toCharArray();
					
					int m = 1;
					
					while (m < currentCount)
					{
						token[m * kPlusOne - 1] = inverseChar;
						
						// System.out.println("current index : " + (m * kPlusOne));
						
						m++;
					}
					
					if (length % kPlusOne == 0)
					{
						// System.out.println("divisible and m * kPlusOne - 1 : " + (m * kPlusOne - 1));
						
						token[m * kPlusOne - 2] = inverseChar;
					}
					else
						token[m * kPlusOne - 1] = inverseChar;
					
					for (int p = 0; p < length; p++)
						answer.append(token[p]);
					
					// System.out.println("appended : " + Arrays.toString(token));
				}
				
				writer.println(inversionCount + "\n" + answer.toString());
			}
		}
	}
	
	public static void getAttributes2()
	{
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			n = reader.nextInt();
			k = reader.nextInt();
			s = reader.next();
			
			if (k == 1)
			{
				String zeroFirst, oneFirst;
				
				zeroFirst = oneFirst = "";
				
				for (int j = 0; j < n; j++)
				{
					if (j % 2 == 0)
					{
						zeroFirst += 0;
						oneFirst += 1;
					}
					else
					{
						zeroFirst += 1;
						oneFirst += 0;
					}
				}
				
				// System.out.println("zeroFirst : " + zeroFirst + ", oneFirst : " + oneFirst);
				
				int countWithZeroFirst, countWithOneFirst;
				
				countWithZeroFirst = countWithOneFirst = 0;
				
				for (int j = 0; j < n; j++)
				{
					if (s.charAt(j) != zeroFirst.charAt(j))
						countWithZeroFirst++;
					if (s.charAt(j) != oneFirst.charAt(j))
						countWithOneFirst++;
				}
				
				if (countWithZeroFirst < countWithOneFirst)
					writer.println(countWithZeroFirst + "\n" + zeroFirst);
				else
					writer.println(countWithOneFirst + "\n" + oneFirst);
			}
			else if (k >= n)
				writer.println(0 + "\n" + s);
			else
			{
				String tokenArray[];
				char currentChar;
				int numberOfTokens, kPlusOne, inversionCount;
				
				tokenArray = new String[n];
				numberOfTokens = 0;
				kPlusOne = k + 1;
				inversionCount = 0;
				
				Arrays.fill(tokenArray, "");
				
				for (int j = 0; j < n;)
				{
					currentChar = s.charAt(j);
					tokenArray[numberOfTokens] += currentChar;
					
					j++;
					
					while (j < n && s.charAt(j) == currentChar)
					{
						if (tokenArray[numberOfTokens].length() % k == 0)
						{
							tokenArray[numberOfTokens] += absolute(currentChar - 48 - 1);	// change the whole string here
							
							System.out.println("previous : " + tokenArray[numberOfTokens]);
							
							int length = tokenArray[numberOfTokens].length();
							String temp = tokenArray[numberOfTokens];
							
							tokenArray[numberOfTokens] = temp.substring(0, length - 2);
							
							tokenArray[numberOfTokens] += absolute(temp.charAt(length - 2) - 48 - 1);
							tokenArray[numberOfTokens] += absolute(temp.charAt(length - 1) - 48 - 1);
							
							System.out.println("modified : " + tokenArray[numberOfTokens]);
						}
						else
							tokenArray[numberOfTokens] += currentChar;
						
						j++;
						inversionCount++;
					}
					
					if (tokenArray[numberOfTokens].length() % k == 0)
					{
						String temp = tokenArray[numberOfTokens];
						int length = temp.length();
						
						tokenArray[numberOfTokens] = temp.substring(0, length - 2)
								+ absolute(temp.charAt(length - 2) - 48 - 1)
								+ absolute(temp.charAt(length - 1) - 48 - 1);
					}
					
					numberOfTokens++;
				}
				
				writer.println("\n" + inversionCount);
				
				for (int j = 0; j < numberOfTokens; j++)
					writer.print(tokenArray[j]);
			}
		}
	}
	
	public static int absolute(int a)
	{
		if (a < 0)
			return -a;
		else
			return a;
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

5
15 5
100011101010111
15 2
111111111111111
15 2
101010101010101

3
15 2
100011101010111
7 3
0000111
37 3
0000111000111000111000111100011110000

2
21 3
000000000000000011111
20 3
00000000000000011111
1
20 30
00000000000000011111

*/
