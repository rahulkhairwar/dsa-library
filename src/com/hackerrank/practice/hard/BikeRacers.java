package com.hackerrank.practice.hard;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

class BikeRacers
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
		static final int INFINITY = (int) 1e9;
        int n, m, k, s, t, flow, maxFlow;
		long maxDist;
		Point[] racers, bikes;
		long[][] distance;
		Edge[] edges;
		int[] par, parEdge;
		List<Integer>[] adj;
        InputReader in;
        OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			k = in.nextInt();
			racers = new Point[n];
			bikes = new Point[m];
			distance = new long[n][m];
			par = new int[(int) 1e5 + 5];
			parEdge = new int[(int) 1e5 + 5];

			for (int i = 0; i < n; i++)
				racers[i] = new Point(in.nextInt(), in.nextInt());

			for (int i = 0; i < m; i++)
				bikes[i] = new Point(in.nextInt(), in.nextInt());

			pre();

			long low, high, mid;

			low = 1;
			high = maxDist;
			mid = 0;

			while (low <= high)
			{
				mid = low + high >> 1L;

				boolean possible = possible(mid);

				if (possible)
				{
					boolean prev = possible(mid - 1);

					if (!prev)
						break;

					high = mid;
				}
				else
					low = mid;
			}

			out.println(mid);
		}

		void pre()
		{
			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < m; j++)
				{
					distance[i][j] = dist(racers[i], bikes[j]);
					maxDist = Math.max(maxDist, distance[i][j]);
				}
			}
		}

		boolean possible(long time)
		{
			// 0 - source
			// [1, n] - racers
			// [n + 1, n + m] - bikes
			// n + m + 1 - sink
			int v = n + m + 2;
			int e = (n * m + n + m) << 2;

			s = 0;
			t = v - 1;
			maxFlow = 0;
			edges = new Edge[e];
			adj = new ArrayList[v];

			for (int i = 0; i < v; i++)
				adj[i] = new ArrayList<>();

			int counter = 0;

			for (int i = 0; i < n; i++)
			{
				// adding an edge from the source to all racers.
				edges[counter] = new Edge(i + 1, 1);
				adj[s].add(counter++);
				edges[counter] = new Edge(s, 0);
				adj[i + 1].add(counter++);

				for (int j = 0; j < m; j++)
				{
					long dist = distance[i][j];

					if (dist <= time)
					{
						// add edge between this racer and this bike.
						edges[counter] = new Edge(n + 1 + j, 1);
						adj[i + 1].add(counter++);
						edges[counter] = new Edge(i + 1, 0);
						adj[n + 1 + j].add(counter++);
					}
				}
			}

			for (int i = 0; i < m; i++)
			{
				// adding an edge from all bikes to the sink.
				edges[counter] = new Edge(t, 1);
				adj[n + 1 + i].add(counter++);
				edges[counter] = new Edge(n + 1 + i, 0);
				adj[t].add(counter++);
			}

			while (true)
			{
				boolean[] visited = new boolean[v];
				Queue<Integer> queue = new LinkedList<>();

				flow = 0;
				par[s] = -1;
				parEdge[s] = -1;
				visited[s] = true;
				queue.add(s);

				while (queue.size() > 0)
				{
					int curr = queue.poll();

					if (curr == t)
						break;

					Iterator<Integer> iterator = adj[curr].iterator();

					while (iterator.hasNext())
					{
						int ed = iterator.next();
						int to = edges[ed].to;
						int weight = edges[ed].weight;

						if (weight > 0 && !visited[to])
						{
							visited[to] = true;
							queue.add(to);
							par[to] = curr;
							parEdge[to] = ed;
						}
					}
				}

				augment(t, INFINITY);

				if (flow == 0)
					break;

				maxFlow += flow;
			}

			return maxFlow >= k;
		}

		long dist(Point a, Point b)
		{
			return (long) (Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
		}

		void augment(int node, int minEdge)
		{
			if (node == s)
				flow = minEdge;
			else if (par[node] != -1)
			{
				int ed = parEdge[node];
				int weight = edges[ed].weight;

				augment(par[node], Math.min(minEdge, weight));

				// reduce forward flow, increase back edge flow.
				edges[ed].weight -= flow;
				edges[ed ^ 1].weight += flow;
			}
		}

		class Edge
		{
			int to, weight;

			public Edge(int to, int weight)
			{
				this.to = to;
				this.weight = weight;
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
