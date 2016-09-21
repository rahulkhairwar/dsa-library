package com.codeforces.competitions.year2016.round345div2;

import java.awt.*;
import java.io.*;
import java.util.*;

public final class TaskC
{
    static int n;
	static Point[] points, sortedX, sortedY;;
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

		points = new Point[n];

		for (int i = 0; i < n; i++)
			points[i] = new Point(in.nextInt(), in.nextInt());

		sortedX = Arrays.copyOf(points, n);
		sortedY = Arrays.copyOf(points, n);

		Arrays.sort(sortedX, new Comparator<Point>()
		{
			@Override
			public int compare(Point o1, Point o2)
			{
				return Integer.compare(o1.x, o2.x);
			}
		});

		Arrays.sort(sortedY, new Comparator<Point>()
		{
			@Override
			public int compare(Point o1, Point o2)
			{
				return Integer.compare(o1.y, o2.y);
			}
		});

		Arrays.sort(points, new Comparator<Point>()
		{
			@Override
			public int compare(Point o1, Point o2)
			{
				if (o1.x < o2.x)
					return -1;
				else if (o1.x > o2.x)
					return 1;
				else
					return Integer.compare(o1.y, o2.y);
			}
		});

		long total = 0;

		for (int i = 0; i < n; i++)
		{
			int leftX, leftY, rightX, rightY, leftSame, rightSame;

			leftX = searchLeftX(points[i].x);
			leftY = searchLeftY(points[i].y);
			rightX = searchRightX(points[i].x);
			rightY = searchRightY(points[i].y);
			leftSame = searchLeftBoth(points[i].x, points[i].y);
			rightSame = searchRightBoth(points[i].x, points[i].y);

			total += (rightX - leftX);
			total += (rightY - leftY);
			total -= (rightSame - leftSame);
		}

		out.println(total / 2);
    }

	static int searchLeftX(int value)
	{
		int first, last, middle;

		first = 0;
		last = n - 1;

		while (first <= last)
		{
			middle = first + (last - first) / 2;

			if (sortedX[middle].x == value)
			{
				if (middle == 0)
					return middle;

				if (sortedX[middle - 1].x < value)
					return middle;

				last = middle - 1;
			}
			else if (sortedX[middle].x < value)
				first = middle + 1;
			else
				last = middle - 1;
		}

		return -1;
	}

	static int searchLeftY(int value)
	{
		int first, last, middle;

		first = 0;
		last = n - 1;

		while (first <= last)
		{
			middle = first + (last - first) / 2;

			if (sortedY[middle].y == value)
			{
				if (middle == 0)
					return middle;

				if (sortedY[middle - 1].y < value)
					return middle;

				last = middle - 1;
			}
			else if (sortedY[middle].y < value)
				first = middle + 1;
			else
				last = middle - 1;
		}

		return -1;
	}

	static int searchRightX(int value)
	{
		int first, last, mid;

		first = 0;
		last = n - 1;

		while (first <= last)
		{
			mid = first + (last - first) / 2;

			if (sortedX[mid].x == value)
			{
				if (mid == n - 1)
					return mid;

				if (sortedX[mid + 1].x > value)
					return mid;

				first = mid + 1;
			}
			else if (sortedX[mid].x < value)
				first = mid + 1;
			else
				last = mid - 1;
		}

		return -1;
	}

	static int searchRightY(int value)
	{
		int first, last, mid;

		first = 0;
		last = n - 1;

		while (first <= last)
		{
			mid = first + (last - first) / 2;

			if (sortedY[mid].y == value)
			{
				if (mid == n - 1)
					return mid;

				if (sortedY[mid + 1].y > value)
					return mid;

				first = mid + 1;
			}
			else if (sortedY[mid].y < value)
				first = mid + 1;
			else
				last = mid - 1;
		}

		return -1;
	}

	static int searchLeftBoth(int x, int y)
	{
		int first, last, mid;

		first = 0;
		last = n - 1;

		while (first <= last)
		{
			mid = first + (last - first) / 2;

			if (points[mid].x == x && points[mid].y == y)
			{
				if (mid == 0)
					return mid;

				if (points[mid - 1].x != x || points[mid - 1].y != y)
					return mid;

				last = mid - 1;
			}
			else if (points[mid].x == x)
			{
				if (points[mid].y < y)
					first = mid + 1;
				else
					last = mid - 1;
			}
			else if (points[mid].x < x)
				first = mid + 1;
			else
				last = mid - 1;
		}

		return -1;
	}

	static int searchRightBoth(int x, int y)
	{
		int first, last, mid;

		first = 0;
		last = n - 1;

		while (first <= last)
		{
			mid = first + (last - first) / 2;

			if (points[mid].x == x)
			{
				if (points[mid].y == y)
				{
					if (mid == n - 1)
						return mid;

					if (points[mid + 1].x != x || points[mid + 1].y != y)
						return mid;

					first = mid + 1;
				}
				else if (points[mid].y < y)
					first = mid + 1;
				else
					last = mid - 1;
			}
			else if (points[mid].x < x)
				first = mid + 1;
			else
				last = mid - 1;
		}

		return -1;
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
                } catch (IOException e)
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
            } catch (IOException e)
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

	}

}
