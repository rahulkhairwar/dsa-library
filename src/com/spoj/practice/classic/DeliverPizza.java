package com.spoj.practice.classic;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

class DeliverPizza
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
		static final int INFINITY = Integer.MAX_VALUE;
        int t, n, m, total, root, size;
		String[] matrix;
		int[][] num;
		List<Integer> buildings;
		List<Edge>[] edges;
		Point[] nodes;
        InputReader in;
        OutputWriter out;

        public Solver(InputReader in, OutputWriter out)
        {
        	this.in = in;
        	this.out = out;
        }

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				m = in.nextInt();
				matrix = new String[n];
				num = new int[n][m];

				for (int i = 0; i < n; i++)
					matrix[i] = in.next();

				createGraph();
				dijkstra();

				size = buildings.size();

				buildings.sort(new Comparator<Integer>()
				{
					@Override public int compare(Integer o1, Integer o2)
					{
						return Integer.compare(nodes[o1].y, nodes[o2].y);
					}
				});

				if (nodes[buildings.get(size - 1)].y == INFINITY)
					out.println(-1);
				else
					out.println(find(0, 0));
			}
		}

		void createGraph()
		{
			buildings = new ArrayList<>();
			int leftCounter, rightCounter;

			total = n * m;
			leftCounter = 1;
			rightCounter = total;
			root = -1;
			int x = 1;

			edges = new ArrayList[total + 1];

			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < m; j++)
				{
					char c = matrix[i].charAt(j);
					edges[x++] = new ArrayList<>();

					if (c == '$')
					{
						buildings.add(leftCounter);
						num[i][j] = leftCounter++;
					}
					else if (c == 'X')
					{
						root = rightCounter;
						num[i][j] = rightCounter--;
					}
					else
						num[i][j] = rightCounter--;
				}
			}

			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < m; j++)
				{
					if (matrix[i].charAt(j) == 'X' || matrix[i].charAt(j) == '$')
					{
						if (i > 0 && Character.isDigit(matrix[i - 1].charAt(j)))
						{
							edges[num[i][j]].add(new Edge(num[i][j], num[i - 1][j], 2));
							edges[num[i - 1][j]].add(new Edge(num[i - 1][j], num[i][j], 2));
						}

						if (j > 0 && Character.isDigit(matrix[i].charAt(j - 1)))
						{
							edges[num[i][j]].add(new Edge(num[i][j], num[i][j - 1], 2));
							edges[num[i][j - 1]].add(new Edge(num[i][j - 1], num[i][j], 2));
						}

						if (j < m - 1)
						{
							edges[num[i][j]].add(new Edge(num[i][j], num[i][j + 1], 2));
							edges[num[i][j + 1]].add(new Edge(num[i][j + 1], num[i][j], 2));
						}

						if (i < n - 1)
						{
							edges[num[i][j]].add(new Edge(num[i][j], num[i + 1][j], 2));
							edges[num[i + 1][j]].add(new Edge(num[i + 1][j], num[i][j], 2));
						}
					}
					else
					{
						if (j < m - 1)
						{
							int abs = Math.abs(matrix[i].charAt(j) - matrix[i].charAt(j + 1));

							if (abs == 0)
							{
								edges[num[i][j]].add(new Edge(num[i][j], num[i][j + 1], 1));
								edges[num[i][j + 1]].add(new Edge(num[i][j + 1], num[i][j], 1));
							}
							else if (abs == 1)
							{
								edges[num[i][j]].add(new Edge(num[i][j], num[i][j + 1], 3));
								edges[num[i][j + 1]].add(new Edge(num[i][j + 1], num[i][j], 3));
							}
						}

						if (i < n - 1)
						{
							int abs = Math.abs(matrix[i].charAt(j) - matrix[i + 1].charAt(j));

							if (abs == 0)
							{
								edges[num[i][j]].add(new Edge(num[i][j], num[i + 1][j], 1));
								edges[num[i + 1][j]].add(new Edge(num[i + 1][j], num[i][j], 1));
							}
							else if (abs == 1)
							{
								edges[num[i][j]].add(new Edge(num[i][j], num[i + 1][j], 3));
								edges[num[i + 1][j]].add(new Edge(num[i + 1][j], num[i][j], 3));
							}
						}
					}
				}
			}
		}

		void dijkstra()
		{
			PriorityQueue<Point> queue = new PriorityQueue<>(new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					return Integer.compare(o1.y, o2.y);
				}
			});

			nodes = new Point[total + 1];
			int counter = 1;

			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++)
					nodes[counter] = new Point(counter++, INFINITY);

			nodes[root].y = 0;
			queue.add(nodes[root]);

			while (queue.size() > 0)
			{
				Point curr = queue.poll();
				Iterator<Edge> iterator = edges[curr.x].iterator();

				while (iterator.hasNext())
				{
					Edge next = iterator.next();

					if (nodes[next.to].y > curr.y + next.weight)
					{
						nodes[next.to].y = curr.y + next.weight;
						queue.add(nodes[next.to]);
					}
				}
			}
		}

		int find(int index, int mask)
		{
			if (index == size)
			{
				int oneMaxIndex, twoMaxIndex, oneTotal, twoTotal;

				oneMaxIndex = twoMaxIndex = -1;
				oneTotal = twoTotal = 0;

				for (int i = 0; i < size; i++)
				{
					if ((mask & (1 << i)) > 0)
					{
						oneTotal += nodes[buildings.get(i)].y * 2;
						oneMaxIndex = i;
					}
					else
					{
						twoTotal += nodes[buildings.get(i)].y * 2;
						twoMaxIndex = i;
					}
				}

				if (oneMaxIndex != -1)
					oneTotal -= nodes[buildings.get(oneMaxIndex)].y;

				if (twoMaxIndex != -1)
					twoTotal -= nodes[buildings.get(twoMaxIndex)].y;

				return Math.max(oneTotal, twoTotal);
			}

			return Math.min(find(index + 1, mask | (1 << index)), find(index + 1, mask));
		}

		class Edge
		{
			int from, to, weight, distance;

			public Edge(int from, int to, int weight)
			{
				this.from = from;
				this.to = to;
				this.weight = weight;
			}

			@Override public String toString()
			{
				return "\tto : " + to + ", weight : " + weight + "\n";
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
