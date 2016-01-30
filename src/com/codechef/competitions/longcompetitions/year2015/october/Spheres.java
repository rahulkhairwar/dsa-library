package com.codechef.competitions.longcompetitions.year2015.october;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.InputMismatchException;

class Spheres
{
	static int n, m, c, upper[], lower[];
	static long mod = 1000000007;
	static BigInteger bMod = BigInteger.valueOf(mod);
	static InputReader reader;
	static OutputWriter writer;
	
	public static void main(String[] args)
	{
		Spheres spheres = new Spheres();
		
		reader = spheres.new InputReader(System.in);
		writer = spheres.new OutputWriter(System.out);
		
		getAttributes5();
/*		getAttributes2();
		getAttributes3();*/
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	static void getAttributes5()
	{
		n = reader.nextInt();
		m = reader.nextInt();
		c = reader.nextInt();
		
		upper = new int[c + 1];
		lower = new int[c + 1];
		
		for (int i = 0; i < n; i++)
			upper[reader.nextInt()]++;
		
		for (int i = 0; i < m; i++)
			lower[reader.nextInt()]++;
		
		int count = 0;
		
		for (int i = 1; i <= c; i++)
			if (upper[i] > 0 && lower[i] > 0)
				count++;
		
		BigInteger[] circles = new BigInteger[count + 1];
		
		int temp = 1;
		
		for (int i = 1; i <= c; i++)
		{
			if (upper[i] > 0 && lower[i] > 0)
				circles[temp++] = BigInteger.valueOf(upper[i]).multiply(
						BigInteger.valueOf(lower[i]));
		}
		
		int min, max;
		
		min = 1 + Math.min(count, c);
		max = 1 + Math.max(count, c);
		
		BigInteger dp[][] = new BigInteger[min][max];
		
		for (int i = 0; i < min; i++)
			Arrays.fill(dp[i], BigInteger.valueOf(0));
		
		dp[0][1] = circles[1];
		
		//System.out.println(dp[0].length + ", " + circles.length);
		for (int i = 2; i < min; i++)
		{
			//System.out.println("i : " + i);
			dp[0][i] = dp[0][i - 1].add(circles[i]);
		}
		
		temp = count + 1;
		
		for (int i = 1; i < min; i++)
		{
			temp--;
			int countMinusI = count - i;
			
			for (int j = 1; j <= countMinusI; j++)
			{
/*				System.out.println("i : " + i + ", j : " + j
						+ ", dp[i][j-1] : " + dp[i][j - 1].intValue()
						+ " + (circles[j] : " + circles[j].intValue()
						+ " * (dp[i-1][temp] : " + dp[i - 1][temp].intValue()
						+ " - dp[i-1][j] : " + dp[i - 1][j].intValue() + "))");*/
				
				dp[i][j] = dp[i][j - 1].add(circles[j].multiply(dp[i - 1][temp]
						.subtract(dp[i - 1][j])));
			}
		}
/*		
		System.out.println("dp : ");
		
		for (int i = 0; i < min; i++)
		{
			for (int j = 0; j < max; j++)
			{
				if (dp[i][j] != null)
					System.out.print(dp[i][j].intValue() + " ");
				else
					System.out.print(0 + " ");
			}
			
			System.out.println();
		}*/
		
		for (int i = 1, j = count - 1; i < min; i++, j--)
		{
			//System.out.println("i : " + i + ", j : " + j);
			writer.print(mod(dp[i][j]).intValue() + " ");
		}
		
		for (int i = min; i <= c; i++)
			writer.print(0 + " ");
	}
	
	static void getAttributes4()
	{
		n = reader.nextInt();
		m = reader.nextInt();
		c = reader.nextInt();
		
		upper = new int[c + 1];
		lower = new int[c + 1];
		
		for (int i = 0; i < n; i++)
			upper[reader.nextInt()]++;
		
		for (int i = 0; i < m; i++)
			lower[reader.nextInt()]++;
		
		BigInteger circles[] = new BigInteger[c + 1];
		
		for (int i = 1; i <= c; i++)
			circles[i] = BigInteger.valueOf(upper[i]).multiply(
					BigInteger.valueOf(lower[i]));
		
		int count;
		
		count = 1;
		
		//long dp[][] = new long[c + 1][1001];
		BigInteger d[][] = new BigInteger[c + 1][1001];
		
		for (int i = 0; i <=c; i++)
			d[i][0] = BigInteger.valueOf(0);
		
		for (int i = 1; i <= c; i++)
		{
			if (circles[i].intValue() > 0)
			{
				d[0][count] = d[0][count - 1].add(circles[i]);
				count++;
			}
		}
		
/*		count = 1;
		for (int i = 1; i <= c; i++)
		{
			if (circles[i] > 0)
			{
				dp[0][count] = dp[0][count - 1] + circles[i];
				count++;
			}
		}*/
		
		int temp = count;
		
/*		for(int i = 1; i < c; i++)
		{
			temp--;
			int countMinusI = count - i;
			
			for (int j = 1; j < countMinusI; j++)
				dp[i][j] = dp[i][j - 1] + (dp[0][j] - dp[0][j - 1])
						* (dp[i - 1][temp] - dp[i - 1][j]);
		}*/
		
		temp = count;
		
		for (int i = 1; i < c; i++)
		{
			temp--;
			int countMinusI = count - i;
			
			for (int j = 1; j < countMinusI; j++)
			{
				d[i][j] = d[i][j - 1].add(circles[j].multiply(d[i - 1][temp]
						.subtract(d[i - 1][j])));
			}
		}
		
/*		System.out.println("dp : ");
		
		for (int i = 0; i <= c; i++)
		{
			for (int j = 0; j <= c; j++)
			{
				System.out.print(dp[i][j] + " ");
			}
			
			System.out.println();
		}*/
 
		//System.out.println("count : " + count);
		
		int min = Math.min(count, c);
		
		//System.out.println("count : " + count + ", c : " + c);
		
/*		for (int i = 1, j = count - 2; i < min; i++, j--)
		{
			//System.out.println("i : " + i + ", j : " + j);
			writer.print(mod(dp[i][j]) + " ");
		}
		
		for (int i = min; i <= c; i++)
			writer.print(0 + " ");*/
		
		//writer.println("\nusing BigInteger : ");
		for (int i = 1, j = count - 2; i < min; i++, j--)
			writer.print(mod(d[i][j]).intValue() + " ");
		
		for (int i = min; i <= c; i++)
			writer.print(0 + " ");
	}
	
	static void getAttributes3()
	{
		n = reader.nextInt();
		m = reader.nextInt();
		c = reader.nextInt();
		
		upper = new int[c + 1];
		lower = new int[c + 1];
		
		for (int i = 0; i < n; i++)
			upper[reader.nextInt()]++;
		
		for (int i = 0; i < m; i++)
			lower[reader.nextInt()]++;
		
		long circles[] = new long[c + 1];
		
		for (int i = 1; i <= c; i++)
			circles[i] = (long) upper[i] * lower[i];
		
		int count = 1;
		
		BigInteger dp[][] = new BigInteger[c + 1][1001];
		
		for (int i = 0; i <= c; i++)
			dp[i][0] = BigInteger.valueOf(0);
		
		for (int i = 1; i <= c; i++)
		{
			if (circles[i] > 0)
			{
				dp[count][0] = dp[count - 1][0].add(BigInteger.valueOf(circles[i]));
				count++;
			}
		}
		
		count--;
/*		
		System.out.println("The 0th column looks like : ");
		
		for (int i = 0; i <=c; i++)
			System.out.println(dp[i][0].intValue());
		
		System.out.println("\ncount is : " + count);*/
		
		int min = Math.min(count, c);
		
		for (int i = 1; i <= c; i++)
			dp[i][i] = BigInteger.valueOf(0);
		
		for (int i = 2; i <= min; i++)
		{
			for (int j = 1; j < i; j++)
			{
				//System.out.println("i : " + i + ", j : " + j);
				dp[i][j] = dp[i - 1][j].add(BigInteger.valueOf(circles[i])
						.multiply(dp[i - 1][j - 1]));
			}
		}
		
/*		System.out.println("dp[][] : ");
		
		for (int i = 0; i <= c; i++)
		{
			for (int j = 0; j <= c; j++)
			{
				if (dp[i][j] != null)
					System.out.print(dp[i][j].intValue() + " ");
				else
					System.out.print(0 + " ");
			}
			
			System.out.println();
		}
		
		writer.print("answer : ");*/
		
		for (int i = 1; i < min; i++)
			writer.print(mod(dp[min][i]).intValue() + " ");
		
		for (int i = min; i <= c; i++)
			writer.print(0 + " ");
	}
	
	static void getAttributes()
	{
		n = reader.nextInt();
		m = reader.nextInt();
		c = reader.nextInt();
		
		upper = new int[c + 1];
		lower = new int[c + 1];
		
		for (int i = 0; i < n; i++)
			upper[reader.nextInt()]++;
		
		for (int i = 0; i < m; i++)
			lower[reader.nextInt()]++;
		
		long circles[] = new long[c + 1];
		
		for (int i = 1; i <= c; i++)
			circles[i] = (long) upper[i] * lower[i];
		
		int count;
		
		count = 1;
		
		//long dp[][] = new long[c + 1][1001];
		BigInteger d[][] = new BigInteger[c + 1][1001];
		
		for (int i = 0; i <=c; i++)
			d[i][0] = BigInteger.valueOf(0);
		
		for (int i = 1; i <= c; i++)
		{
			if (circles[i] > 0)
			{
				d[0][count] = d[0][count - 1].add(BigInteger.valueOf(circles[i]));
				count++;
			}
		}
		
/*		count = 1;
		for (int i = 1; i <= c; i++)
		{
			if (circles[i] > 0)
			{
				dp[0][count] = dp[0][count - 1] + circles[i];
				count++;
			}
		}*/
		
		int temp = count;
		
		temp = count;
		
		for (int i = 1; i < c; i++)
		{
			temp--;
			int countMinusI = count - i;
			
			for (int j = 1; j < countMinusI; j++)
			{
				d[i][j] = d[i][j - 1].add(d[0][j].subtract(d[0][j - 1])
						.multiply(d[i - 1][temp].subtract(d[i - 1][j])));
			}
		}
		
/*		System.out.println("dp : ");
		
		for (int i = 0; i <= c; i++)
		{
			for (int j = 0; j <= c; j++)
			{
				System.out.print(dp[i][j] + " ");
			}
			
			System.out.println();
		}*/
 
		//System.out.println("count : " + count);
		
		int min = Math.min(count, c);
		
		//System.out.println("count : " + count + ", c : " + c);
		
/*		for (int i = 1, j = count - 2; i < min; i++, j--)
		{
			//System.out.println("i : " + i + ", j : " + j);
			writer.print(mod(dp[i][j]) + " ");
		}
		
		for (int i = min; i <= c; i++)
			writer.print(0 + " ");*/
		
		//writer.println("\nusing BigInteger : ");
		for (int i = 1, j = count - 2; i < min; i++, j--)
			writer.print(mod(d[i][j]).intValue() + " ");
		
		for (int i = min; i <= c; i++)
			writer.print(0 + " ");
	}
	
	static BigInteger mod(BigInteger num)
	{
		if (num.compareTo(bMod) == 1)
			return num.subtract(num.divide(bMod).multiply(
					bMod));
		else
			return num;
	}
	
	static void getAttributes2()
	{
		n = reader.nextInt();
		m = reader.nextInt();
		c = reader.nextInt();
		
		upper = new int[c + 1];
		lower = new int[c + 1];
		
		for (int i = 0; i < n; i++)
			upper[reader.nextInt()]++;
		
		for (int i = 0; i < m; i++)
			lower[reader.nextInt()]++;
		
		BigInteger circles[] = new BigInteger[c + 1];
		
		for (int i = 1; i <= c; i++)
			circles[i] = BigInteger.valueOf(upper[i]).multiply(
					BigInteger.valueOf(lower[i]));
		
/*		System.out.println("circles : ");
		
		for (int i = 1; i <=c; i++)
			System.out.println("i : " + i + ", circles[i] : " + circles[i].intValue());
*/
		int count = 1;
		
		//long dp[][] = new long[c + 1][1001];
		BigInteger d[][] = new BigInteger[c + 1][1001];
		
		for (int i = 0; i <=c; i++)
			d[i][0] = BigInteger.valueOf(0);
		
		for (int i = 1; i <= c; i++)
		{
			if (circles[i].intValue() > 0)
			{
				d[0][count] = d[0][count - 1].add(circles[i]);
				count++;
			}
		}
		
/*		System.out.println("dp : ");

		for (int i = 0; i <= c; i++)
		{
			for (int j = 0; j <= c; j++)
			{
				if (d[i][j] != null)
					System.out.print(d[i][j].intValue() + " ");
				else
					System.out.print(0 + " ");
			}

			System.out.println();
		}*/
		
		int temp = count;
		
		for (int i = 1; i < c; i++)
		{
			temp--;
			int countMinusI = count - i;
			
			for (int j = 1; j < countMinusI; j++)
			{
/*				System.out.println("i : " + i + ", j : " + j + ", temp : " + temp);
				System.out.println("d[i][j-1] : " + d[i][j - 1].intValue()
						+ " + circles[j] : " + circles[j].intValue()
						+ " * (d[i-1][temp] : " + d[i - 1][temp].intValue()
						+ " - d[i - 1][j] : " + d[i - 1][j].intValue() + ")");*/
				
				d[i][j] = d[i][j - 1].add(circles[j]
						.multiply(d[i - 1][temp].subtract(d[i - 1][j])));
			}
		}
		
/*		System.out.println("dp : ");
		
		for (int i = 0; i <= c; i++)
		{
			for (int j = 0; j <= c; j++)
			{
				if (d[i][j] != null)
					System.out.print(d[i][j] + " ");
				else
					System.out.print(0 + " ");
			}
			
			System.out.println();
		}*/
 
		int min = Math.min(count, c);
		
		for (int i = 1, j = count - 2; i < min; i++, j--)
			writer.print(mod(d[i][j]).intValue() + " ");
		
		for (int i = min; i <= c; i++)
			writer.print(0 + " ");
	}
		
	static long mod(long num)
	{
		return num - (num / mod) * mod;
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

6 8 4
1 1 1 2 3 4
1 1 2 2 3 4 4 4
answer : 47 72 36 0

3 4 3
1 2 3
1 1 3 2
answer : 5 2 0

4 5 5
1 1 2 3
1 1 2 2 1
answer : 12 0 0 0 0

10 8 5
1 1 2 3 4 5 2 5 4 1
1 2 2 5 4 3 2 1

answer : 104 280 336 144 0

10 

*/
