package com.spoj.practice.classic;

import javafx.util.Pair;

import java.io.*;
import java.util.*;

/**
 * Solution times out. But I'm pretty sure that this solution is correct. So, submitted arr copied online code.
 */
class ChaosStrings
{
    public static void main(String[] args)
    {
        InputReader in = new InputReader(System.in);
        OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver(in, out);
		solver.solve(1);

        out.flush();

        in.close();
        out.close();
    }

    static class Solver
    {
        int n;
		Point[] points;
		int[] bit;
		int limit;
        InputReader in;
        OutputWriter out;

        public Solver(InputReader in, OutputWriter out)
        {
        	this.in = in;
        	this.out = out;
        }

		void solve(int testNumber)
		{
			n = in.nextInt();
			points = new Point[n];

			StringBuilder[] front;
			Pair<String, Integer>[] reverse;

			front = new StringBuilder[n];
			reverse = new Pair[n];

			for (int i = 0; i < n; i++)
				front[i] = new StringBuilder(in.next());

			Arrays.sort(front, new Comparator<StringBuilder>()
			{
				@Override public int compare(StringBuilder o1, StringBuilder o2)
				{
					return o1.toString().compareTo(o2.toString());
				}
			});

			for (int i = 0; i < n; i++)
				reverse[i] = new Pair<>(new String(front[i].reverse()), i);

			points[0] = new Point(1, -1);

			for (int i = 1; i < n; i++)
			{
				if (front[i].equals(front[i - 1]))
					points[i] = new Point(points[i - 1].x, -1);
				else
					points[i] = new Point(points[i - 1].x + 1, -1);
			}

			Arrays.sort(reverse, new Comparator<Pair<String, Integer>>()
			{
				@Override public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2)
				{
					return o1.getKey().compareTo(o2.getKey());
				}
			});

			points[reverse[0].getValue()].y = 1;

			for (int i = 1; i < n; i++)
			{
				if (reverse[i].getKey().equals(reverse[i - 1].getKey()))
					points[reverse[i].getValue()].y = points[reverse[i - 1].getValue()].y;
				else
					points[reverse[i].getValue()].y = points[reverse[i - 1].getValue()].y + 1;
			}

			limit = points[reverse[n - 1].getValue()].y;
			bit = new int[limit + 1];

			Arrays.sort(points, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					if (o1.y == o2.y)
						return Integer.compare(o1.x, o2.x);

					return Integer.compare(o1.y, o2.y);
				}
			});

			long total = 0;

			for (int i = 0; i < n; i++)
			{
				total += i - query(points[i].x);
				add(points[i].x);
			}

			out.println(total);
		}

		void add(int index)
		{
			while (index <= limit)
			{
				bit[index]++;
				index += index & -index;
			}
		}

		int query(int index)
		{
			int answer = 0;

			while (index > 0)
			{
				answer += bit[index];
				index -= index & -index;
			}

			return answer;
		}

		class Point
		{
			int x, y;

			public Point(int x, int y)
			{
				this.x = x;
				this.y = y;
			}

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

}
