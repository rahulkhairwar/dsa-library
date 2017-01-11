package com.codeforces.competitions.year2016.goodbye2016;

import java.io.*;
import java.util.*;

public final class TaskB
{
	public static void main(String[] args)
	{
		PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(out);

		try
		{
			solver.solve();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		out.flush();
		out.close();
	}

	static class Solver
	{
		int n, arr[];
		BufferedReader bf;
		PrintWriter out;

		void solve() throws IOException
		{
			bf = new BufferedReader(new InputStreamReader(System.in));

			n = Integer.parseInt(bf.readLine());
			arr = new int[n];

			char[] dir = new char[n];

			for (int i = 0; i < n; i++)
			{
				String[] tok = bf.readLine().split(" ");

				arr[i] = Integer.parseInt(tok[0]);

				switch (tok[1])
				{
					case "North":
						dir[i] = 'n';
						break;
					case "South":
						dir[i] = 's';
						break;
					case "West":
						dir[i] = 'w';
						break;
					case "East":
						dir[i] = 'e';
						break;
				}
			}

			int ns = 0;

			for (int i = 0; i < n; i++)
			{
				if (dir[i] == 'n')
				{
					if (ns == 0)
					{
						out.println("NO");

						return;
					}

					ns -= arr[i];
				}
				else if (dir[i] == 's')
				{
					if (ns == 20000)
					{
						out.println("NO");

						return;
					}

					ns += arr[i];
				}
				else
				{
					if (ns == 0 || ns == 20000)
					{
						out.println("NO");

						return;
					}
				}

				if (ns > 20000 || ns < 0)
				{
					out.println("NO");

					return;
				}
			}

			if (ns != 0)
				out.println("NO");
			else
				out.println("YES");
		}

		public Solver(PrintWriter out)
		{
			this.out = out;
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

		public float nextFloat()
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

		public double nextDouble()
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

}

/*

5
2000 South
3000 East
4000 North
4000 South
2000 North

*/
