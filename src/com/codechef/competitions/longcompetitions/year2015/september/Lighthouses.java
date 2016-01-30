package com.codechef.competitions.longcompetitions.year2015.september;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

class Lighthouses
{
	static int t, n;
	static Island sortedAccToXCoordinate[], sortedAccToYCoordinate[];
	static InputReader reader;
	static OutputWriter writer;
	
	public static void main(String[] args)
	{
		Lighthouses lighthouses = new Lighthouses();
		
		reader = lighthouses.new InputReader(System.in);
		writer = lighthouses.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	static void getAttributes()
	{
		Island allLeftMost[], allRightMost[];
		int leftCount, rightCount;
		
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			n = reader.nextInt();
			
			sortedAccToXCoordinate = new Island[n];
			
			for (int j = 0; j < n; j++)
				sortedAccToXCoordinate[j] = new Island(j + 1, new Point(reader.nextInt(),
						reader.nextInt()));
			
			sortedAccToYCoordinate = Arrays.copyOf(sortedAccToXCoordinate, n);
			
			Island.sortAccToXCoordinate(sortedAccToXCoordinate, 0, n);
			Island.sortAccToYCoordinate(sortedAccToYCoordinate, 0, n);
			
			allLeftMost = new Island[n];
			allRightMost = new Island[n];
			
			leftCount = rightCount = 0;
			
			for (int j = 0; j < n; j++)
			{
				if (sortedAccToXCoordinate[j].position.x != sortedAccToXCoordinate[0].position.x)
					break;
				else
					allLeftMost[leftCount++] = sortedAccToXCoordinate[j];
			}
			
			for (int j = n - 1; j >= 0; j--)
			{
				if (sortedAccToXCoordinate[j].position.x != sortedAccToXCoordinate[n - 1].position.x)
					break;
				else
					allRightMost[rightCount++] = sortedAccToXCoordinate[j];
			}
			
			if (allLeftMost.length > 1)
				Island.sortAccToYCoordinate(allLeftMost, 0, leftCount);
			
			if (allRightMost.length > 1)
				Island.sortAccToYCoordinate(allRightMost, 0, rightCount);
			
			if (allLeftMost[0].position.y == sortedAccToYCoordinate[0].position.y)
			{
				writer.println(1 + "\n" + allLeftMost[0].islandNumber + " NE");
				
				continue;
			}
			else if (allRightMost[rightCount - 1].position.y == sortedAccToYCoordinate[n - 1].position.y)
			{
				writer.println(1 + "\n" + allRightMost[rightCount - 1].islandNumber + " SW");
				
				continue;
			}
			else if (allLeftMost[leftCount - 1].position.y == sortedAccToYCoordinate[n - 1].position.y)
			{
				writer.println(1 + "\n" + allLeftMost[leftCount - 1].islandNumber + " SE");
				
				continue;
			}
			else if (allRightMost[0].position.y == sortedAccToYCoordinate[0].position.y)
			{
				writer.println(1 + "\n" + allRightMost[0].islandNumber + " NW");
				
				continue;
			}
			else
			{
				if (allLeftMost[0].position.y <= allRightMost[rightCount - 1].position.y)
				{
					writer.println(2 + "\n" + allLeftMost[0].islandNumber
							+ " NE");
					writer.println(allRightMost[rightCount - 1].islandNumber
							+ " SW");
				}
				else
				{
					writer.println(2 + "\n" + allLeftMost[0].islandNumber
							+ " SE");
					writer.println(allRightMost[rightCount - 1].islandNumber
							+ " NW");
				}
			}
		}
	}
	
	static class Island
	{
		int islandNumber;
		Point position;
		
		public Island(int islandNumber, Point position)
		{
			this.islandNumber = islandNumber;
			this.position = position;
		}
		
		static void sortAccToXCoordinate(Island allIslands[], int fromIndex, int toIndex)
		{
			Arrays.sort(allIslands, fromIndex, toIndex, new Comparator<Island>()
			{
				@Override
				public int compare(Island i1, Island i2)
				{
					return Integer.compare(i1.position.x, i2.position.x);
				}
			});
		}
		
		static void sortAccToYCoordinate(Island allIslands[], int fromIndex, int toIndex)
		{
			Arrays.sort(allIslands, fromIndex, toIndex, new Comparator<Island>()
			{
				@Override
				public int compare(Island i1, Island i2)
				{
					return Integer.compare(i1.position.y, i2.position.y);
				}
			});
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

1
10
3 8
2 9
0 3
-2 8
4 -2
7 -3
-5 -6
2 10
100 1
-2 0
Just to check the sorting


2
5
0 0
1 0
2 0
0 -1
0 -2
4
5 0
-5 0
0 5
0 -5


4
20
-4 6
3 6
-6 5
-2 4
1 4
6 4
-4 3
1 3
5 3
-4 2
2 4
3 4
-5 1
5 -2
3 -2
-6 -3
2 -4
-4 -5
4 -5
-1 -6
21
-4 6
3 6
-6 5
-2 4
1 4
6 4
-4 3
1 3
5 3
-4 2
2 4
3 4
-5 1
5 -2
3 -2
-6 -3
2 -4
-4 -5
4 -5
-1 -6
-7 -6
21
-4 6
3 6
-6 5
-2 4
1 4
6 4
-4 3
1 3
5 3
-4 2
2 4
3 4
-5 1
5 -2
3 -2
-6 -3
2 -4
-4 -5
4 -5
-1 -6
10 -10
22
-4 6
3 6
-6 5
-2 4
1 4
6 4
-4 3
1 3
5 3
-4 2
2 4
3 4
-5 1
5 -2
3 -2
-6 -3
2 -4
-4 -5
4 -5
-1 -6
-7 -6
-10 10


*/
