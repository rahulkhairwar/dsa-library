package com.codechef.competitions.longcompetitions.year2015.march;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class DevuAndHisClass
{
	private static int t, type, girls, boys;
	private static String s, boyFirst, girlFirst;
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		DevuAndHisClass devuAndHisClass = new DevuAndHisClass();
		
		reader = devuAndHisClass.new InputReader(System.in);
		writer = devuAndHisClass.new OutputWriter(System.out);
		
		getAttributes();
		
		// readString2();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			type = reader.nextInt();
			
			readString2();
		}
	}
	
	public static String readString()
	{
		s = "";
		char c = 0;
		
		while (true)
		{
			c = (char) reader.read();
			
			if (c == ' ' || c == '\n')
				break;
			else if (c == 'G')
			{
				girls++;
			}
			else
			{
				boys++;
			}
				
			s = s + c;
		}
		
		return s;
	}
	
	public static void readString2()
	{
		s = "";
		
		int boyFirstWrongPos, boySecondWrongPos, girlFirstWrongPos, girlSecondWrongPos, count;
		char c1, c2;

		boys = girls = boyFirstWrongPos = boySecondWrongPos = girlFirstWrongPos = girlSecondWrongPos = count = 0;
		c1 = c2 = 0;
		
		reader.read();

		if (type == 0)
		{
			while (true)
			{
				c1 = (char) reader.read();

				if (c1 == ' ' || c1 == '\n' || c1 == '\0')
					break;
				else if (c1 == 'G')
				{
					girls++;
					girlSecondWrongPos++;
					count++;
				}
				else
				{
					boys++;
					boySecondWrongPos++;
					count++;
				}

				s = s + c1;
				c2 = (char) reader.read();

				if (c2 == ' ' || c2 == '\n')
					break;
				else if (c2 == 'G')
				{
					girls++;
					girlFirstWrongPos++;
					count++;
				}
				else
				{
					boys++;
					boyFirstWrongPos++;
					count++;
				}

				s = s + c2;
			}
			
			if (count % 2 != 0)
				boySecondWrongPos--;
			else
				boyFirstWrongPos--;
			
			boys--;
			
			if (boys > girls + 1)
			{
				System.out.println(-1);
				
				return;
			}
			else if (girls > boys + 1)
			{
				System.out.println(-1);
				
				return;
			}
			
			if (boys == girls + 1)
			{
				System.out.println(boyFirstWrongPos);
				
				return; 
			}
			else if (girls == boys + 1)
			{
				System.out.println(girlFirstWrongPos);
				
				return;
			}
			else
			{
				System.out.println(boyFirstWrongPos <= girlFirstWrongPos
						? boyFirstWrongPos
						: girlFirstWrongPos);
				
				return;
			}
		}
		else if (type == 1)
		{
			while (true)
			{
				c1 = (char) reader.read();

				if (c1 == ' ' || c1 == '\n')
					break;
				else if (c1 == 'G')
				{
					girls++;
				}
				else
				{
					boys++;
				}

				s = s + c1;
				c2 = (char) reader.read();

				if (c2 == ' ' || c2 == '\n')
					break;
				else if (c2 == 'G')
				{
					girls++;
				}
				else
				{
					boys++;
				}

				s = s + c2;
			}
		}
		else
		{
			while (true)
			{
				c1 = (char) reader.read();

				if (c1 == ' ' || c1 == '\n')
					break;
				else if (c1 == 'G')
				{
					girls++;
				}
				else
				{
					boys++;
				}

				s = s + c1;
				c2 = (char) reader.read();

				if (c2 == ' ' || c2 == '\n')
					break;
				else if (c2 == 'G')
				{
					girls++;
				}
				else
				{
					boys++;
				}

				s = s + c2;
			}
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

		public void printSpace()
		{
			writer.print(" ");
		}

		public void close()
		{
			writer.close();
		}

	}

}

/*
 * BGGBGBBGBGGB 3
 * GGGBBBBGBGGBG 3
 * BGGGBBBGBGGBG 4


3
0 BGGBGBBGBGGB
0 GGGBBBBGBGGBG
0 BGGGBBBGBGGBG

3
0
BB
0
BG
0
BBGG

 */
