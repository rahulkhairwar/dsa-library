package com.codeforces.competitions.year2016.round378div2;

import java.io.*;
import java.util.*;

public final class TaskC
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
    	int n, k, a[], b[];
    	StringBuilder[] ans;
        InputReader in;
        OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			a = in.nextIntArray(n);
			k = in.nextInt();
			b = in.nextIntArray(k);

			List<Integer> ends = new ArrayList<>();
			int left, right;

			left = right = 0;

			while (true)
			{
				int sum = 0;

				while (left < n && sum < b[right])
				{
					sum += a[left];
					left++;
				}

				if (sum != b[right])
				{
					out.println("NO");

					return;
				}

				ends.add(left - 1);
				right++;

				if (right == k)
					break;
			}

			if (left != n)
			{
				out.println("NO");

				return;
			}

			ans = new StringBuilder[k];
			left = 0;

			for (int i = 0; i < k; i++)
			{
				List<Integer> list = new ArrayList<>();

				for (int j = left; j <= ends.get(i); j++)
					list.add(a[j]);

				if (!find(list, i))
				{
					out.println("NO");

					return;
				}

				left = ends.get(i) + 1;
			}

			out.println("YES");

			for (int i = 0; i < k; i++)
			{
				if (ans[i] != null)
					out.println(ans[i].toString());
			}
		}

		boolean find(List<Integer> list, int counter)
		{
			if (list.size() == 1)
				return true;

			int max, pos, next;

			max = next = 0;
			pos = -1;

			for (int x : list)
				max = Math.max(max, x);

			for (int i = 0; i < list.size(); i++)
			{
				if (list.get(i) == max)
				{
					if (i > 0 && list.get(i - 1) < max)
					{
						pos = i;
						next = -1;

						break;
					}

					if (i < list.size() - 1 && list.get(i + 1) < max)
					{
						pos = i;
						next = 1;

						break;
					}
				}
			}

			if (pos == -1)
				return false;

			int right = list.size() - 1 - pos;

			ans[counter] = new StringBuilder("");

			if (next == 1)
			{
				eatRight(right, pos, counter);
				eatLeft(pos, counter);
			}
			else
			{
				eatLeft(pos, counter);
				pos = 0;
				eatRight(right, pos, counter);
			}

			return true;
		}

		void eatRight(int right, int pos, int counter)
		{
			while (right > 0)
			{
				ans[counter].append(pos + counter + 1).append(" R\n");
				right--;
			}
		}

		void eatLeft(int pos, int counter)
		{
			while (pos > 0)
			{
				ans[counter].append(pos + counter + 1).append(" L\n");
				pos--;
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

}

/*

6
6 6 2 4 1 1
2
6 14

3
3 3 3
3
3 3 3

*/
