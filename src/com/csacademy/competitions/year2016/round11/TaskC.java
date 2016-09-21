package com.csacademy.competitions.year2016.round11;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public final class TaskC
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
		int n, m;
		int[] parent;
		Node[] nodes;
		List<Point> extra;
        InputReader in;
        OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			parent = new int[n + 1];
			nodes = new Node[n + 1];
			extra = new ArrayList<>();

			for (int i = 0; i <= n; i++)
			{
				nodes[i] = new Node();
				parent[i] = i;
			}

			for (int i = 0; i < m; i++)
			{
				int from, to;

				from = in.nextInt();
				to = in.nextInt();

				nodes[from].adj.add(to);
				nodes[to].adj.add(from);

				int one, two;

				one = findParent(from);
				two = findParent(to);

				if (one != two)
					parent[one] = two;
				else
					extra.add(new Point(from, to));
			}

			List<Integer> conn = new ArrayList<>();

			for (int i = 1; i <= n; i++)
				if (parent[i] == i)
					conn.add(i);

			if (conn.size() - 1 > extra.size())
				out.println(-1);
			else
			{
				int size = conn.size();
				out.println(size - 1);

				for (int i = 0; i < size - 1; i++)
					out.println(extra.get(i).x + " " + extra.get(i).y + " " + conn.get(0) + " " + conn.get(i + 1));
			}
		}

		int findParent(int x)
		{
			if (parent[x] == x)
				return x;

			return parent[x] = findParent(parent[x]);
		}

		class Node
		{
			int parent;
			List<Integer> adj;

			public Node()
			{
				adj = new ArrayList<>();
				parent = -1;
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

11 9
1 2
8 9
2 4
2 5
3 6
3 7
4 8
7 9
3 9

10 9
1 2
8 9
2 4
2 5
3 6
3 7
4 8
7 9
3 9

3 1
1 2

5 4
1 2
2 3
3 4
4 5

10 9
1 2
2 3
3 4
3 5
3 6
1 5
1 4
1 6
4 6

6 5
1 2
1 3
1 4
2 3
3 4

8 7
1 2
2 3
3 4
4 5
5 6
1 6
1 5

7 6
1 2
2 3
3 4
4 5
1 6
1 5

16 15
1 2
2 3
3 4
4 5
1 6
1 5
5 6
6 7
7 8
8 9
9 10
5 7
5 8
5 9
5 10

17 15
1 2
2 3
3 4
4 5
1 6
1 5
5 6
6 7
7 8
8 9
9 10
5 7
5 8
5 9
5 10

*/
