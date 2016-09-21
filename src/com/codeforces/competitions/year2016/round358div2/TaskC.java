package com.codeforces.competitions.year2016.round358div2;

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
		solver.solve(1);

        out.flush();

        in.close();
        out.close();
    }

    static class Solver
    {
        int n, deleteCount;
		int[] maxAllowedDist;
		long[] distFromRoot;
		List<Point>[] adj;
		boolean[] visited;
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

			maxAllowedDist = new int[n + 1];
			distFromRoot = new long[n + 1];
			adj = new ArrayList[n + 1];
			visited = new boolean[n + 1];

			for (int i = 1; i <= n; i++)
				maxAllowedDist[i] = in.nextInt();

			for (int i = 1; i < n; i++)
			{
				int from = i + 1;
				int to = in.nextInt();
				int edgeWeight = in.nextInt();

				if (adj[from] == null)
					adj[from] = new ArrayList<>();

				if (adj[to] == null)
					adj[to] = new ArrayList<>();

				adj[from].add(new Point(to, edgeWeight));
				adj[to].add(new Point(from, edgeWeight));
			}

			findDistancesFromRoot(1, 0, 0);
			visited = new boolean[n + 1];
			dfs(1, 0, false);

			out.println(deleteCount);
		}

		void findDistancesFromRoot(int node, long distUptoParent, long edgeWeight)
		{
			visited[node] = true;
			distFromRoot[node] = distUptoParent + edgeWeight;

			Iterator<Point> iterator = adj[node].iterator();

			while (iterator.hasNext())
			{
				Point curr = iterator.next();

				if (!visited[curr.x])
					findDistancesFromRoot(curr.x, distFromRoot[node], curr.y);
			}
		}

		void dfs(int node, long min, boolean delete)
		{
			if (visited[node])
				return;

			visited[node] = true;
			Iterator<Point> iterator = adj[node].iterator();

			if (delete)
			{
				deleteCount++;

				while (iterator.hasNext())
				{
					Point curr = iterator.next();

					if (!visited[curr.x])
						dfs(curr.x, 0, true);
				}

				return;
			}

			long temp = distFromRoot[node] - min;

			if (temp > maxAllowedDist[node])
			{
				deleteCount++;

				while (iterator.hasNext())
				{
					Point curr = iterator.next();

					if (!visited[curr.x])
						dfs(curr.x, 0, true);
				}
			}
			else
			{
				while (iterator.hasNext())
				{
					Point curr = iterator.next();

					if (!visited[curr.x])
						dfs(curr.x, Math.min(min, distFromRoot[node]), false);
				}
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

}

/*

9
88 22 83 14 95 91 98 53 11
3 24
7 -8
1 67
1 64
9 65
5 12
6 -80
3 8
: 5

9
88 22 83 14 95 91 98 53 11
3 24
7 -80
1 67
1 64
9 65
5 12
6 -80
3 8
: 2

3
100 100 40
1 -100
2 50
: 1

 */
