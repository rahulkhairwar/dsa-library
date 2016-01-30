package com.codeforces.competitions.educational.year2015.novtodec.round1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

public final class Third
{
	static int  n, arr[];
	static Vector[] vectors;
	static InputReader in;
	static OutputWriter out;
	
	public static void main(String[] args)
	{
		in = new InputReader(System.in);
		out = new OutputWriter(System.out);
		
		solve();
		
		out.flush();
		
		in.close();
		out.close();
	}

	static void solve()
	{
		n = in.nextInt();
		
		vectors = new Vector[n];
		
		for (int i = 0; i < n; i++)
		{
			int x, y;
			
			x = in.nextInt();
			y = in.nextInt();
			
			vectors[i] = new Vector(i + 1, x, y, x == 0);
		}
		
		for (int i = 0; i < n; i++)
		{
			if (vectors[i].m < 0)
				vectors[i].m += 360.0;
			
			if (vectors[i].angle.doubleValue() < 0)
				vectors[i].angle = vectors[i].angle.add(BigDecimal.valueOf(360));
		}
		
		Vector.sort(vectors);
//		Vector.bigSort(vectors);
		
		for (int i = 0; i < n; i++)
			System.out.println("index : " + vectors[i].index + ", deg. : "
					+ vectors[i].m + ", bigAngle : "
					+ vectors[i].angle.doubleValue());
	
		double minAngle = Double.MAX_VALUE;
		int one, two;
		
		one = two = 0;
		
		BigDecimal min = BigDecimal.valueOf(Double.MAX_VALUE);
		int bigOne, bigTwo;
		
		bigOne = bigTwo = 0;
		
		for (int i = 0; i < n - 1; i++)
		{
/*			System.out.printf("curr+1 - curr : %.20f\n",
					(vectors[i + 1].m - vectors[i].m));
			System.out.println("** diff. in BigDecimal : "
					+ (vectors[i + 1].angle.subtract(vectors[i].angle)));
*/		
			if (vectors[i + 1].m - vectors[i].m < minAngle)
			{
				minAngle = vectors[i + 1].m - vectors[i].m;
				one = vectors[i + 1].index;
				two = vectors[i].index;
			}
			
			if (vectors[i + 1].angle.subtract(vectors[i].angle).compareTo(min) == -1)
			{
				min = vectors[i + 1].angle.subtract(vectors[i].angle);
				bigOne = vectors[i + 1].index;
				bigTwo = vectors[i].index;
			}
		}
		
		if (360.0 - vectors[n - 1].m + vectors[0].m < minAngle)
		{
			//minAngle = vectors[n - 1].m - vectors[0].m;
			one = vectors[n - 1].index;
			two = vectors[0].index;
		}
		
		if (BigDecimal.valueOf(360).subtract(vectors[n - 1].angle)
				.add(vectors[0].angle).compareTo(min) == -1)
		{
			bigOne = vectors[n - 1].index;
			bigTwo = vectors[0].index;
		}
		
		out.println(one + " " + two);
		out.println("and, big : " + bigOne + " " + bigTwo);
		
		out.println(bigOne + " " + bigTwo);
	}
	
	static class Vector
	{
		int index;
		int x, y;
		boolean isX0;
		double m;
		BigDecimal angle;
		
		public Vector(int index, int x, int y, boolean isX0)
		{
			this.index = index;
			this.x = x;
			this.y = y;
			this.isX0 = isX0;
			
			this.m = Math.toDegrees(Math.atan2(y, x));
			
			this.angle = BigDecimal.valueOf(Math.toDegrees(Math.atan2(y, x)));
		}
		
		public static void sort(Vector[] allVectors)
		{
			Arrays.sort(allVectors, new Comparator<Vector>()
			{
				@Override
				public int compare(Vector o1, Vector o2)
				{
					return Double.compare(o1.m, o2.m);
				}
				
			});
		}
		
		public static void bigSort(Vector[] allVectors)
		{
			Arrays.sort(allVectors, new Comparator<Vector>()
			{
				@Override
				public int compare(Vector one, Vector two)
				{
					return one.angle.compareTo(two.angle);
				}
			});
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

}

/*

4
-1 0
0 -1
1 0
1 1

8
1 2
3 1
-2 -3
-4 -1
1 -4
2 -2
-2 1
-2 4

8
1 2
3 1
-2 -3
-4 -0
0 -4
2 -2
-2 1
-2 4

*/
