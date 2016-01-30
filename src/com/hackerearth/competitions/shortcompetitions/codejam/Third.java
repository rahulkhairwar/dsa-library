package com.hackerearth.competitions.shortcompetitions.codejam;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;

class Third
{
	static int n, k;
	static Point points[];
	static InputReader in;
	static OutputWriter out;
	
	public static void main(String[] args)
	{
		Third third = new Third();
		
		in = third.new InputReader(System.in);
		out = third.new OutputWriter(System.out);
		
		solve();
		
		out.flush();
		
		in.close();
		out.close();
	}

	static void solve()
	{
		n = in.nextInt();
		k = in.nextInt();
		
		points = new Point[n];
		
		Map<Integer, Point> map = new HashMap<Integer, Point>();
		
		for (int i = 0; i < n; i++)
		{
			points[i] = new Point(in.nextInt(), in.nextInt());
			
			map.put(i, points[i]);
		}
		
/*		System.out.println("all points : ");
		
		for (int i = 0; i < n; i++)
			System.out.println(points[i].x + " " + points[i].y);*/
		
		Line[] validLines = new Line[n * (n + 1) / 2];
		Map<Integer, Line> lineMap = new HashMap<Integer, Line>();
		int validLineCount = 0;
		
		for (int i = - 105; i <= 105; i++)
		{
			for (int j = -105; j <= 105; j++)
			{
				Point curr = new Point(j, i);
				Point temp = new Point(j + k, i);
				
				if (map.containsValue(curr) && map.containsValue(temp))
				{
					validLines[validLineCount] = new Line(j, j + k, i, i, k);

/*					System.out.println("xleft : " + j + ", xright : " + (j + k)
							+ ", yleft : " + i + ", yright : " + i + ", len : "
							+ k);*/
					
					if (!lineMap.containsValue(validLines[validLineCount]))
					{
//						System.out.print("+++ putting in the map, size before : " + lineMap.size());
						lineMap.put(lineMap.size() + 1, validLines[validLineCount]);
//						System.out.println(", after : " + lineMap.size());
					}

					validLineCount++;
				}
			}
		}
		
/*		System.out.println("validLinecount : " + validLineCount);
		System.out.println("in map : " + lineMap.size());*/
		
		Collection<Line> allLines = lineMap.values();
		Iterator<Line> iterator = allLines.iterator();
		int finalCount = 0;
		
		while (iterator.hasNext())
		{
			Line iter = (Line) iterator.next();
			Line temp = new Line(iter.xLeft, iter.xRight, iter.yLeft + k, iter.yRight + k, k);
			
/*			System.out.println("xleft : " + iter.xLeft + ", xright : " + iter.xRight
					+ ", yleft : " + iter.yLeft + ", yright : " + iter.yRight);*/
			
			if (lineMap.containsValue(temp))
				finalCount++;
		}

		out.println(finalCount);
	}
	
	static class Line
	{
		int xLeft, xRight, yLeft, yRight, length;
		
		public Line(int xLeft, int xRight, int yLeft, int yRight, int length)
		{
			this.xLeft = xLeft;
			this.xRight = xRight;
			this.yLeft = yLeft;
			this.yRight = yRight;
			this.length = length;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			Line other = (Line) obj;
			
			if (this.xLeft == other.xLeft && this.xRight == other.xRight 
					&& this.yLeft == other.yLeft && this.yRight == other.yRight
					&& this.length == other.length)
				return true;
			else
				return false;
		}
		
	}
	
	static class Square
	{
		Line upper, lower;
		int height;
		
		public Square(Line upper, Line lower, int height)
		{
			this.upper = upper;
			this.lower = lower;
			this.height = height;
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

5 1
1 1
2 1
1 2
2 2
5 5

20 1
1 1
1 2
2 2
2 1
-2 -2
-1 -2
-1 -3
-2 -3
-3 -3
-4 -3
-5 -3
-1 -4
-2 -4
-3 -4
-4 -4
-5 -4
-1 -5
-2 -5
-4 -5
-5 -5

*/
