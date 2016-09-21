package com.csacademy.competitions.year2016.round11;

import java.io.*;
import java.util.*;

public final class TaskD
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
		private static final int INFINITY = Integer.MAX_VALUE;
		int n, k, m, s;
		int[] arr;
		boolean[] forbidden;
		Node[] nodes;
        InputReader in;
        OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			k = in.nextInt();
			m = in.nextInt();
			s = in.nextInt() - 1;
			arr = new int[n];
			forbidden = new boolean[n];

			for (int i = 0; i < m; i++)
				forbidden[in.nextInt() - 1] = true;

			if ((k & 1) == 1)
				odd();
			else
				even();
		}

		void odd()
		{
			Arrays.fill(arr, INFINITY);
			arr[s] = 0;

			boolean[] visited = new boolean[n];
			int start = s;
			int step = 1;

			while (start < n)
			{
				int curr = start + 2;
				int last = start;

//				System.out.println("** start : " + start + ", curr : " + curr);

				while (curr - start < k)
				{
					if (curr >= n)
						break;

//					System.out.println("start : " + start + ", last : " + last + ", curr : " + curr);
					if (!visited[curr])
					{
						if (!forbidden[curr])
						{
							arr[curr] = step;
							last = curr;
						}

						visited[curr] = true;
					}

					curr += 2;
//					System.out.println("^^^^ curr : " + curr);
				}

				if (last > start)
					start = last;
				else
					break;

//				in.nextInt();
				step++;
			}

			start = s;
			step = 1;

			while (start >= 0)
			{
				int curr = start - 2;
				int last = start;

//				System.out.println("** start : " + start + ", curr : " + curr);

				while (start - curr < k)
				{
					if (curr < 0)
						break;

//					System.out.println("start : " + start + ", last : " + last + ", curr : " + curr);
					if (!visited[curr])
					{
						if (!forbidden[curr])
						{
							arr[curr] = step;
							last = curr;
						}

						visited[curr] = true;
					}

					curr -= 2;
//					System.out.println("^^^^ curr : " + curr);
				}

				if (last < start)
					start = last;
				else
					break;

				step++;
			}

			for (int i = 0; i < n; i++)
				if (arr[i] == INFINITY)
					arr[i] = -1;

/*			System.out.print("\t   ");
			for (int i = 0; i < n; i++)
				System.out.print(i + "   ");

			System.out.println();
			System.out.println("arr : " + Arrays.toString(arr));*/
		}

		void even()
		{

		}

		void even2()
		{
			Arrays.fill(arr, INFINITY);
			arr[s] = 0;

			boolean[] visited = new boolean[n];
			int start = s;
			int step = 1;

			while (start < n)
			{
				int curr = start + 1;
				int last = start;

				while (curr - start < k)
				{
					if (curr >= n)
						break;

					if (!visited[curr])
					{
						if (!forbidden[curr])
						{
							arr[curr] = step;
							last = curr;

/*							if (curr + 1 < n && !visited[curr + 1])
							{
								if (!forbidden[curr + 1])
									arr[curr + 1] = step + 1;

								visited[curr + 1] = true;
							}*/
						}

						visited[curr] = true;
					}

					curr += 2;
				}

				if (last > start)
					start = last;
				else
					break;

				step++;
			}

			start = s;
			step = 1;

			while (start >= 0)
			{
				int curr = start - 1;
				int last = start;

				while (start - curr > k)
				{
					if (curr < 0)
						break;

					if (!visited[curr])
					{
						if (!forbidden[curr])
						{
							arr[curr] = step;
							last = curr;

/*							if (curr - 1 >= 0 && !visited[curr - 1])
							{
								if (!forbidden[curr - 1])
									arr[curr - 1] = step + 1;

								visited[curr - 1] = true;
							}*/
						}

						visited[curr] = true;
					}

					curr -= 2;
				}

				if (last < start)
					start = last;
				else
					break;

				step++;
			}
		}

		void solve2()
		{
			n = in.nextInt();
			k = in.nextInt();
			m = in.nextInt();
			s = in.nextInt();
			nodes = new Node[n];

			for (int i = 0; i < n; i++)
				nodes[i] = new Node();

			for (int i = 0; i < m; i++)
				nodes[in.nextInt()].forbidden = true;

			if ((k & 1) > 0)	// odd k
			{
				int curr = s + 1;

				while (curr - s < k)
				{

				}
			}
		}

		class Node
		{
			int min;
			boolean forbidden;
			List<Integer> adj;
			
			public Node()
			{
				min = INFINITY;
				adj = new ArrayList<>();
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

	}

}

/*

15 5 5 4
8 9 11 12 13

15 5 5 4
8 9 12 13 11

15 5 5 10
8 9 12 13 11

 */
