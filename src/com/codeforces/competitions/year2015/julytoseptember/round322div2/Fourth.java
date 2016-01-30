package com.codeforces.competitions.year2015.julytoseptember.round322div2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.InputMismatchException;

public final class Fourth
{
	static InputReader reader;
	static OutputWriter writer;
	
	public static void main(String[] args)
	{
		Fourth fourth = new Fourth();

		reader = fourth.new InputReader(System.in);
		writer = fourth.new OutputWriter(System.out);

		int n, w, weights[];

		//System.out.println("Enter the value of n : ");

		n = Integer.parseInt(reader.nextLine());
		//System.out.println("Enter the value of w : ");
		w = Integer.parseInt(reader.nextLine());

		//System.out.println("Enter the weights : ");

		String tok[] = reader.nextLine().split(" ");

		weights = new int[n];

		for (int i = 0; i < n; i++)
			weights[i] = Integer.parseInt(tok[i]);

		System.out.println("*******");
		System.out.println("Answer using dp : " + findMax(weights, n, w));
		System.out.println("Answer using sorting : " + useSort(weights, n, w));
	}
	
	static int findMax(int weights[], int n, int w)
	{
		int dp[][] = new int[n + 1][w + 1];
		
		for (int i = 0; i <= n; i++)
			Arrays.fill(dp[i], 0);
		
		dp[0][0] = 1;
		
		for (int i = 1; i <= n; i++)
		{
			for (int j = 0; j <= w; j++)
			{
				if (dp[i - 1][j] > 0)
				{
					//dp[i][j] = Math.max(dp[i - 1][j], dp[i][j]);
					dp[i][j] = dp[i - 1][j];
					
					if (j + weights[i - 1] <= w)
						dp[i][j + weights[i - 1]] = 1 + dp[i][j];
				}
			}
		}
		
		System.out.print("    ");
		for (int i = 0; i <= w; i++)
			System.out.print(i + " ");
		
		System.out.println();
		
		for (int i = 0; i <= n; i++)
		{
			String t = "" + i;
			
			System.out.print(i + (t.length() == 1 ? " : " : ": "));
			for (int j = 0; j <= w; j++)
			{
				String temp = "" + j;
				System.out.print(dp[i][j] + (temp.length() == 1 ? " " : "  "));
			}
			
			System.out.println();
		}
		
		int max = dp[n][0];
		
		for (int i = 1; i <= w; i++)
			if (dp[n][i] > max)
				max = dp[n][i];
		
		System.out.println("last : " + dp[n][w]);
		
		return max - 1;
	}

	static int useSort(int weights[], int n, int w)
	{
		Arrays.sort(weights);
		
		int count, temp, i;
		
		count = temp = i = 0;
		
		while (i < n && temp <= w)
		{
			if (weights[i] <= w - temp)
			{
				temp += weights[i];
				count++;
			}
			else
				break;
			
			i++;
		}
		
		return count;
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

10
30
2 8 19 3 22 4 3 10 5 8

*/
