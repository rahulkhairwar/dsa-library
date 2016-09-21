package com.codechef.competitions.longcompetitions.year2016.september;

import java.io.*;
import java.util.*;

public final class TheNewRestaurant
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver(in, out);
		solver.solve();

		out.flush();

		in.close();
		out.close();
	}

	static class Solver
	{
		int n, q;
		Circle[] circles;
		Query[] queries;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			q = in.nextInt();
			circles = new Circle[n];
			queries = new Query[q];

			for (int i = 0; i < n; i++)
				circles[i] = new Circle(in.nextInt(), in.nextInt(), in.nextInt());

			for (int i = 0; i < q; i++)
			{
				queries[i] = new Query(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());
				queries[i].answer = find(i);

				out.printf("%.15f\n", queries[i].answer);
			}
		}

		double find(int index)
		{
			double ans = 0.0;

			for (int i = 0; i < n; i++)
				ans += area(queries[index].x1, queries[index].x2, queries[index].y1, queries[index].y2, circles[i].x,
						circles[i].y, circles[i].radius);

			return ans;
		}

		double section(double h, double r) // returns the positive root of intersection of line y = h with circle
		// centered at the origin and radius r
		{
//			r = 1;
			assert (r >= 0);
			// assume r is positive, leads to some simplifications in the formula below (can factor out r from the square root)

			return (h < r) ? Math.sqrt(r * r - h * h)
						   : 0; // http://www.wolframalpha.com/input/?i=r+*+sin%28acos%28x+%2F+r%29%29+%3D+h
		}

		double g(double x, double h, double r) // indefinite integral of circle segment
		{
//			r = 1;
			return .5f * (Math.sqrt(1 - x * x / (r * r)) * x * r + r * r * Math.asin(x / r) - 2 * h * x); // http://www
			// .wolframalpha.com/input/?i=r+*+sin%28acos%28x+%2F+r%29%29+-+h
		}

		double area(double x0, double x1, double h, double r) // area of intersection of an infinitely tall box with
		// left edge at x0, right edge at x1, bottom edge at h and top edge at infinity, with circle centered at the
		// origin with radius r
		{
			if (x0 > x1)
			{
				double temp = x0;

				x0 = x1;
				x1 = temp;
			}
//				swap(x0, x1); // this must be sorted otherwise we get negative area
			double s = section(h, r);
			return g(CMath.max(-s, Math.min(s, x1)), h, r) - g(CMath.max(-s, Math.min(s, x0)), h, r); // integrate the
			// area
		}

		double area(double x0, double x1, double y0, double y1, double r) // area of the intersection of a finite box
		// with a circle centered at the origin with radius r
		{
			if (y0 > y1)
			{
				double temp = y0;

				y0 = y1;
				y1 = temp;
			}

			if (y0 < 0)
			{
				if (y1 < 0)
					return area(x0, x1, -y0, -y1, r); // the box is completely under, just flip it above and try again
				else
					return area(x0, x1, 0, -y0, r) + area(x0, x1, 0, y1, r);
				// the box is both above and below, divide it to two boxes and go again
			}
			else
			{
				assert (y1
						>= 0); // y0 >= 0, which means that y1 >= 0 also (y1 >= y0) because of the swap at the
				// beginning
				return area(x0, x1, y0, r) - area(x0, x1, y1, r); // area of the lower box minus area of the higher box
			}
		}

		double area(double x0, double x1, double y0, double y1, double cx, double cy, double r) // area of the intersection
		// of a general box with a general circle
		{
			x0 -= cx;
			x1 -= cx;
			y0 -= cy;
			y1 -= cy;
			// get rid of the circle center

			long a, b;

			a = (long) (Math.random() * 1e5);
			b = (long) (Math.random() * 1e5);

			long gcd = CMath.gcd(a, b);

			return area(x0, x1, y0, y1, r);
		}

		class Circle
		{
			int x, y, radius;

			public Circle(int x, int y, int radius)
			{
				this.x = x;
				this.y = y;
				this.radius = radius;
			}

		}

		class Query
		{
			int x1, y1, x2, y2;
			double answer;

			public Query(int x1, int y1, int x2, int y2)
			{
				this.x1 = x1;
				this.y1 = y1;
				this.x2 = x2;
				this.y2 = y2;
			}

		}

		public Solver(InputReader in, OutputWriter out)
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
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
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

		public void println(char x)
		{
			writer.println(x);
		}

		public void print(char x)
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

		public void printf(String format, Object args)
		{
			writer.printf(format, args);
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

	static class CMath
	{
		static double min(double... arr)
		{
			double min = arr[0];
			int len = arr.length;

			for (int i = 1; i < len; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static double max(double... arr)
		{
			double max = arr[0];
			int len = arr.length;

			for (int i = 1; i < len; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

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

		static long gcd(long a, long b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

	}

}
