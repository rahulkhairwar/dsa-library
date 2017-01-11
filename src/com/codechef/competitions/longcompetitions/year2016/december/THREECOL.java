package com.codechef.competitions.longcompetitions.year2016.december;

import java.io.*;
import java.util.*;

public final class THREECOL
{
    public static void main(String[] args)
    {
        InputReader in = new InputReader(System.in);
        OutputWriter out = new OutputWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
        in.close();
        out.flush();
        out.close();
    }

    static class Solver
    {
		int n;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			// 00-01, 00-10, 01-11, 00-01, 10-11
			n = in.nextInt();

			if (n == 2)
			{
				out.println(5);
				out.println("1 1 1 2");
				out.println("1 1 2 1");
				out.println("1 2 2 2");
				out.println("1 1 1 2");
				out.println("2 1 2 2");
			}
			else if (n == 4)
			{
				out.println(44);

				// correct
				out.println("1 1 1 2");
				out.println("1 1 2 1");
				out.println("1 2 2 2");
				out.println("1 1 1 2");
				out.println("2 1 2 2");

				// correct
				out.println("1 3 1 4");
				out.println("1 3 2 3");
				out.println("1 4 2 4");
				out.println("1 3 1 4");
				out.println("2 3 2 4");

				// correct
				out.println("3 1 3 2");
				out.println("3 1 4 1");
				out.println("3 2 4 2");
				out.println("3 1 3 2");
				out.println("4 1 4 2");

				// correct
				out.println("3 3 3 4");
				out.println("3 3 4 3");
				out.println("3 4 4 4");
				out.println("3 3 3 4");
				out.println("4 3 4 4");

				// .......
				//
				out.println("1 2 1 3");
				out.println("2 2 2 3");
				out.println("1 1 1 2");
				out.println("2 1 2 2");
				out.println("1 3 1 4");
				out.println("2 3 2 4");

				out.println("3 2 3 3");
				out.println("4 2 4 3");
				out.println("3 1 3 2");
				out.println("4 1 4 2");
				out.println("3 3 3 4");
				out.println("4 3 4 4");

				out.println("2 1 3 1");
				out.println("2 2 3 2");
				out.println("2 3 3 3");
				out.println("3 4 3 4");

				out.println("1 1 2 1");
				out.println("1 2 2 2");
				out.println("1 3 2 3");
				out.println("1 4 2 4");

				out.println("3 1 4 1");
				out.println("3 2 4 2");
				out.println("3 3 4 3");
				out.println("3 4 4 4");
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
				max = Math.min(max, arr[i]);

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
				max = Math.min(max, arr[i]);

			return max;
		}

	}

}
