package com.codeforces.competitions.year2017.round416div2;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver
	{
		//		BufferedReader in;
		static final int INF = (int) 1e7;
		int n, lim, ctr;
		int[] city, first, last;
		int[][] xor;
		Point[] pts;
		long[][] dp;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			lim = (int) 5e3 + 5;
			city = new int[n + 1];
			first = new int[lim];
			last = new int[lim];
			xor = new int[n + 1][n + 1];
			pts = new Point[lim];
			Arrays.fill(first, INF);
			Arrays.fill(last, -1);

			for (int i = 1; i <= n; i++)
			{
				city[i] = in.nextInt();

				if (first[city[i]] == INF)
					first[city[i]] = i;

				last[city[i]] = Math.max(last[city[i]], i);
			}

			ctr = 0;

			for (int i = 0; i < lim; i++)
			{
				if (first[i] != INF)
					pts[ctr++] = new Point(first[i], last[i]);
			}

			Arrays.sort(pts, 0, ctr, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					if (o1.x == o2.x)
						return Integer.compare(o1.y, o2.y);

					return Integer.compare(o1.x, o2.x);
				}
			});

			for (int i = 1; i <= n; i++)
			{
				Set<Integer> set = new HashSet<>();
				int x = xor[i][i] = city[i];

				set.add(city[i]);

				for (int j = i + 1; j <= n; j++)
				{
					if (!set.contains(city[j]))
					{
						set.add(city[j]);
						x ^= city[j];
					}

					xor[i][j] = x;
				}
			}

			dp = new long[ctr + 1][n + 1];

			for (int i = 0; i <= ctr; i++)
				Arrays.fill(dp[i], -1);

			out.println(find(0, 0));
		}

		void updateRight(Point pt)
		{
			for (int i = pt.x; i <= pt.y; i++)
				pt.y = Math.max(pt.y, pts[i].y);
		}

		long find(int curr, int maxR)
		{
			if (curr == ctr)
				return 0;

			if (dp[curr][maxR] != -1)
				return dp[curr][maxR];

			long max = 0;

			if (maxR != 0)
			{
//				System.out.println("curr : " + curr + ", maxR : " + maxR + ", pt[curr] : " + pts[curr]);
				if (pts[curr].x <= maxR)
					max = Math.max(max, find(curr + 1, maxR));
				else
					max = CMath.max(max, xor[pts[curr].x][pts[curr].y] + find(curr + 1, Math.max(maxR, pts[curr].y)),
							find(curr + 1, maxR));
			}
			else
				max = CMath.max(max, xor[pts[curr].x][pts[curr].y] + find(curr + 1, Math.max(maxR, pts[curr].y)),
						find(curr + 1, maxR));

			return dp[curr][maxR] = max;
		}

		//		uncomment below line to change to BufferedReader
//		public Solver(BufferedReader in, PrintWriter out)
		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

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

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	static class CMath
	{
		static long power(long number, long power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			if (power == 1)
				return number;

			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}

		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);
			else
				return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

		static long min(long... arr)
		{
			long min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static long max(long... arr)
		{
			long max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static int max(int... arr)
		{
			int max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

	}

	public TaskC(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Solver solver = new Solver(in, out);

		try
		{
			solver.solve();
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		out.flush();
		out.close();
	}

}

/*

1
5

2
5 5

2
5 6

3
5 6 5

5
7 7 7 7 7

12
5 6 2 6 5 6 6 6 6 6 2 6

*/
