package com.codechef.competitions.shortcompetitions.year2016.septcookoff;

import java.io.*;
import java.util.*;

class TaskB
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
        int t, n, arr[];
        InputReader in;
        OutputWriter out;

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				arr = in.nextIntArray(n);

				if (n == 2)
				{
					out.println(Math.min(arr[0], arr[1]));

					continue;
				}

				if (n == 3)
				{
					out.println(Math.min(arr[0], Math.min(arr[1], arr[2])));

					continue;
				}

				Map<Integer, Integer> map = new HashMap<>();

				for (int i = 1; i < n; i++)
				{
					int diff = arr[i] - arr[i - 1];
					Integer count = map.get(diff);

					if (count == null)
						map.put(diff, 1);
					else
						map.put(diff, count + 1);
				}

				if (map.size() > 3)
				{
					out.println(-1);

					continue;
				}

				if (map.size() == 1)
				{
					out.println(Math.min(arr[0], arr[n - 1]));

					continue;
				}

				int lt, rt;

				lt = arr[1] - arr[0];

				Integer ct = map.get(lt);
				List<Integer> can = new ArrayList<>();

				if (ct == 1)
					map.remove(lt);
				else
					map.put(lt, ct - 1);

				if (map.size() == 1)
					can.add(arr[0]);

				ct = map.get(lt);

				if (ct == null)
					map.put(lt, 1);
				else
					map.put(lt, ct + 1);

				rt = arr[n - 1] - arr[n - 2];
				ct = map.get(rt);

				if (ct == 1)
					map.remove(rt);
				else
					map.put(rt, ct - 1);

				if (map.size() == 1)
					can.add(arr[n - 1]);

				ct = map.get(rt);

				if (ct == null)
					map.put(rt, 1);
				else
					map.put(rt, ct + 1);

				for (int i = 1; i < n - 1; i++)
				{
					int left, right;

					left = arr[i] - arr[i - 1];
					right = arr[i + 1] - arr[i];

					Integer one, two;

					one = map.get(left);

					if (one == 1)
						map.remove(left);
					else
						map.put(left, one - 1);

					two = map.get(right);

					if (two == 1)
						map.remove(right);
					else
						map.put(right, two - 1);

					int next = arr[i + 1] - arr[i - 1];

					if (map.size() <= 1 && (map.get(next) != null || map.size() == 0))
						can.add(arr[i]);

					one = map.get(left);

					if (one == null)
						map.put(left, 1);
					else
						map.put(left, one + 1);

					two = map.get(right);

					if (two == null)
						map.put(right, 1);
					else
						map.put(right, two + 1);
				}

				if (can.size() == 0)
				{
					out.println(-1);

					continue;
				}

				int min = can.get(0);

				for (int i = 0; i < can.size(); i++)
					min = Math.min(min, can.get(i));

				out.println(min);
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

}

/*

3
2
3 4
4
1 5 3 4
6
2 4 6 7 8 10

1
5
1 3 6 11 16

3
4 2 1 2 3
4 1 5 2 3
4 1 2 5 3

 */
