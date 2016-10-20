package com.codeforces.competitions.year2016.round375div2;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public final class TaskD
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
        int n, m, k;
		char[][] map;
		int[][] num;
		boolean[] visited;
		List<Integer>[] adj;
		Point[] points;
        InputReader in;
        OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			k = in.nextInt();
			map = new char[n][];
			num = new int[n][m];

			int total = n * m + 1;

			visited = new boolean[total];
			adj = new ArrayList[total];

			for (int i = 0; i < n; i++)
				map[i] = in.next().toCharArray();

			for (int i = 0; i < total; i++)
				adj[i] = new ArrayList<>();

			int counter = 1;
			points = new Point[total];

			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < m; j++)
				{
					num[i][j] = counter;
					points[counter++] = new Point(i, j);

					// connect to left cell
					if (map[i][j] == '.' && j > 0 && map[i][j - 1] == '.')
					{
						adj[num[i][j]].add(num[i][j - 1]);
						adj[num[i][j - 1]].add(num[i][j]);
					}

					// connect to above cell
					if (map[i][j] == '.' && i > 0 && map[i - 1][j] == '.')
					{
						adj[num[i][j]].add(num[i - 1][j]);
						adj[num[i - 1][j]].add(num[i][j]);
					}
				}
			}

			for (int i = 0; i < n; i++)
				if (map[i][0] == '.' && !visited[num[i][0]])
					mark(num[i][0]);

			for (int i = 0; i < n; i++)
				if (map[i][m - 1] == '.' && !visited[num[i][m - 1]])
					mark(num[i][m - 1]);

			for (int i = 0; i < m; i++)
				if (map[0][i] == '.' && !visited[num[0][i]])
					mark(num[0][i]);

			for (int i = 0; i < m; i++)
				if (map[n - 1][i] == '.' && !visited[num[n - 1][i]])
					mark(num[n - 1][i]);

			Pair[] pairs = new Pair[total];
			int cnt = 0;

			for (int i = 1; i < counter; i++)
				if (!visited[i] && map[points[i].x][points[i].y] == '.')
					pairs[cnt++] = new Pair(i, dfs(i));

			Arrays.sort(pairs, 0, cnt, new Comparator<Pair>()
			{
				@Override public int compare(Pair o1, Pair o2)
				{
					return Integer.compare(o1.size, o2.size);
				}
			});

			int curr = 0;
			int count = 0;

			visited = new boolean[total];

			while (cnt > k)
			{
				dfs2(pairs[curr].rep);
				count += pairs[curr].size;
				cnt--;
				curr++;
			}

			out.println(count);

			for (int i = 0; i < n; i++, out.println())
				for (int j = 0; j < m; j++)
					out.print(map[i][j] == '^' ? '.' : map[i][j]);
		}

		void mark(int node)
		{
			visited[node] = true;
			map[points[node].x][points[node].y] = '^';

			Iterator<Integer> iterator = adj[node].iterator();

			while (iterator.hasNext())
			{
				int curr = iterator.next();

				if (!visited[curr])
					mark(curr);
			}
		}

		int dfs(int node)
		{
			visited[node] = true;

			Iterator<Integer> iterator = adj[node].iterator();
			int size = 0;

			while (iterator.hasNext())
			{
				int curr = iterator.next();

				if (!visited[curr])
					size += dfs(curr);
			}

			return size + 1;
		}

		void dfs2(int node)
		{
			visited[node] = true;

			int x, y;
			Iterator<Integer> iterator = adj[node].iterator();

			x = points[node].x;
			y = points[node].y;
			map[x][y] = '*';

			while (iterator.hasNext())
			{
				int curr = iterator.next();

				if (!visited[curr])
					dfs2(curr);
			}
		}

		class Pair
		{
			int rep, size;

			public Pair(int rep, int sum)
			{
				this.rep = rep;
				this.size = sum;
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
