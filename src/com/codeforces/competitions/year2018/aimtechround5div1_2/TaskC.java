package com.codeforces.competitions.year2018.aimtechround5div1_2;

import java.io.*;
import java.util.*;

public class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final int OPEN = 1;
		static final int CLOSE = 2;
		int n;
		Rect[] rects;
		Pt[] hor, ver;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			rects = new Rect[n];

			int size = n << 1;
			int ctr = 0;

			hor = new Pt[size];
			ver = new Pt[size];

			for (int i = 0; i < n; i++)
			{
				rects[i] = new Rect(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());
				hor[ctr] = new Pt(rects[i].x1, OPEN, i);
				hor[ctr + 1] = new Pt(rects[i].x2, CLOSE, i);
				ver[ctr] = new Pt(rects[i].y1, OPEN, i);
				ver[ctr + 1] = new Pt(rects[i].y2, CLOSE, i);
				ctr += 2;
			}

			Arrays.sort(hor, (o1, o2) -> {
				if (o1.cd == o2.cd)
					return Integer.compare(o1.type, o2.type);

				return Long.compare(o1.cd, o2.cd);
			});

			Arrays.sort(ver, (o1, o2) -> {
				if (o1.cd == o2.cd)
					return Integer.compare(o1.type, o2.type);

				return Long.compare(o1.cd, o2.cd);
			});

			int openCnt = 0;

			for (int i = 0; i < size; i++)
			{
				if (hor[i].type == OPEN)
				{
					openCnt++;

					if (openCnt > n - 2)
					{
						if (checkForInside(rects[hor[i].ind]))
							return;
					}
				}
				else
					openCnt--;
			}
		}

		boolean checkForInside(Rect r)
		{
			int openCnt = 0;

//			System.out.println("Checking for inside, r(" + r.x1 + ", " + r.y1 + ", " + r.x2 + ", " + r.y2 + ")");

			for (int i = 0; i < (n << 1); i++)
			{
//				System.out.println("\ti : " + i + ", y : " + ver[i].cd + ", cnt : " + openCnt + ", type : " + ver[i]
//						.type);
				if (ver[i].type == OPEN)
				{
					openCnt++;

					if (openCnt > n - 2)
					{
//						System.out.println("y : " + ver[i].cd);
						if (checkIfInside(r.x1, ver[i].cd, r))
						{
							out.println(r.x1 + " " + ver[i].cd);

							return true;
						}
					}
				}
				else
					openCnt--;
			}

			return false;
		}

		Point checkForRect(Rect r)
		{
			long x = r.x1;
			long y = r.y1;

			if (check(x, y))
				return new Point(x, y);

			y = r.y2;

			if (check(x, y))
				return new Point(x, y);

			x = r.x2;

			if (check(x, y))
				return new Point(x, y);

			y = r.y1;

			if (check(x, y))
				return new Point(x, y);

			return null;
		}

		boolean check(long x, long y)
		{
			int cnt = 0;

			for (int i = 0; i < n; i++)
			{
				if (checkIfInside(x, y, rects[i]))
					cnt++;
			}

			return cnt >= n - 1;
		}

		boolean checkIfInside(long x, long y, Rect r)
		{
			return x >= r.x1 && x <= r.x2 && y >= r.y1 && y <= r.y2;
		}

		class Rect
		{
			long x1, y1, x2, y2;
			double diam;

			public Rect(int x1, int y1, int x2, int y2)
			{
				this.x1 = x1;
				this.y1 = y1;
				this.x2 = x2;
				this.y2 = y2;
				diam = Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
			}

		}

		class Point
		{
			long x, y;
			int type;

			public Point(long x, long y)
			{
				this.x = x;
				this.y = y;
			}

			public Point(long x, long y, int type)
			{
				this.x = x;
				this.y = y;
				this.type = type;
			}

		}

		class Pt
		{
			long cd;
			int type, ind;

			public Pt(long cd, int type, int ind)
			{
				this.cd = cd;
				this.type = type;
				this.ind = ind;
			}

		}

		void debug(Object... o)
		{
			System.err.println(Arrays.deepToString(o));
		}

//		uncomment below line to change to BufferedReader
//		public Solver(BufferedReader in, PrintWriter out)
		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
		{
			try
			{
				solve();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
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

	static class Utils
	{
		static boolean nextPermutation(int[] arr)
		{
			for (int a = arr.length - 2; a >= 0; --a)
			{
				if (arr[a] < arr[a + 1])
				{
					for (int b = arr.length - 1; ; --b)
					{
						if (arr[b] > arr[a])
						{
							int t = arr[a];

							arr[a] = arr[b];
							arr[b] = t;

							for (++a, b = arr.length - 1; a < b; ++a, --b)
							{
								t = arr[a];
								arr[a] = arr[b];
								arr[b] = t;
							}

							return true;
						}
					}
				}
			}

			return false;
		}

	}

	public TaskC(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskC", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			in.close();
			out.flush();
			out.close();
		}
	}

}

