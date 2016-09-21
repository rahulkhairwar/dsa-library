package com.dsa.algorithms.dynamicprogramming;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

class RMQSparseTable
{
	static int array[], logTable[], rmq[][];
	static InputReader in;
	
	public static void main(String[] args)
	{
		//int array[] = {1, 5, -2, 3};
		array = new int[]{1, 5, -2, 3, 1, 0, 8, 9, 4, 3};
		
		RMQSparseTable st = new RMQSparseTable();
		
		in = st.new InputReader(System.in);
		
		int from, to, minPos;
		boolean doAsk = true;

		while (doAsk)
		{
			System.out.println("Range starting index(0-based) : ");
			from = in.nextInt();
			System.out.println("Range ending index(0-based) : ");
			to = in.nextInt();

			minPos = st.minPos(from, to);

			System.out.println("The minimum number in the range [" + from + ", " + to + "] is " + array[minPos]
					+ " at position " + minPos);

			System.out.println("\nDo you want to ask another query? (y/n) : ");

			if (in.read() != 'y')
				doAsk = false;
		}
	}
	
	public RMQSparseTable()
	{
		int n = array.length;
		
		logTable = new int[n + 1];
		
		for (int i = 2; i <= n; i++)
			logTable[i] = logTable[i >> 1] + 1;
		
		// this 2-D array stores the array index of the smallest element in a range
		rmq = new int[logTable[n] + 1][n];

		// all elements will be the smallest in their own cell(range = 1 cell)
		for (int i = 0; i < n; i++)
			rmq[0][i] = i;
		
		for (int i = 1; (1 << i) < n; ++i)
		{
			for (int j = 0; (1 << i) + j <= n; j++)
			{
				int x, y;
				
				x = rmq[i - 1][j];
				// y = rmq[prev. row][2^(i - 1) + j]
				y = rmq[i - 1][(1 << (i - 1)) + j];
				
				rmq[i][j] = array[x] <= array[y] ? x : y;
			}
		}
	}
	
	public int minPos(int left, int right)
	{
		int k, x, y;
		
		// to find the size of the 2 intervals which completely cover the required interval
		k = logTable[right - left];
		x = rmq[k][left];
		y = rmq[k][right - (1 << k) + 1];	// y = rmq[k][right - 2^k + 1]
		
		return array[x] <= array[y] ? x : y;
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

}
