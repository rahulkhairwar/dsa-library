package com.uva.practice;

import java.io.*;
import java.util.*;

class InternetBandwidth
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
        int v, s, t, e, flow, maxFlow;
		Edge[] edges;
		int[] par, parEdge;
		List<Integer>[] adj;
        InputReader in;
        OutputWriter out;

		void solve()
		{
			int test = 1;

			while (true)
			{
				v = in.nextInt();

				if (v == 0)
					break;

				s = in.nextInt() - 1;
				t = in.nextInt() - 1;
				e = in.nextInt();
				maxFlow = 0;
				edges = new Edge[e << 2];
				adj = new ArrayList[v];

				for (int i = 0; i < v; i++)
					adj[i] = new ArrayList<>();

				int counter = 0;

				for (int i = 0; i < e; i++)
				{
					int from, to, weight;

					from = in.nextInt() - 1;
					to = in.nextInt() - 1;
					weight = in.nextInt();
					edges[counter] = new Edge(to, weight);
					edges[counter].oppEdge = counter + 2;
					adj[from].add(counter++);
					edges[counter] = new Edge(from, 0);
					edges[counter].oppEdge = counter + 2;
					adj[to].add(counter++);
					edges[counter] = new Edge(from, weight);
					edges[counter].oppEdge = counter - 2;
					adj[to].add(counter++);
					edges[counter] = new Edge(to, 0);
					edges[counter].oppEdge = counter - 2;
					adj[from].add(counter++);
				}

				while (true)
				{
					int[] dist = new int[v];
					Queue<Integer> queue = new LinkedList<>();

					flow = 0;
					par = new int[v];
					parEdge = new int[v];
					Arrays.fill(dist, INFINITY);
					Arrays.fill(par, -1);
					Arrays.fill(parEdge, -1);
					dist[s] = 0;
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

							if (weight > 0 && dist[to] == INFINITY)
							{
								dist[to] = dist[curr] + 1;
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

				out.println("Network " + test++);
				out.println("The bandwidth is " + maxFlow + ".\n");
			}
		}

		void augment(int node, int minEdge)
		{
			if (node == s)
				flow = minEdge;
			else if (par[node] != -1)
			{
				int ed = parEdge[node];
				int weight = edges[ed].weight;
				int oppEdge = edges[ed].oppEdge;

				augment(par[node], Math.min(minEdge, weight));

				if (ed % 2 == 0)
				{
					edges[ed].weight -= flow;
					edges[ed + 1].weight += flow;
					edges[oppEdge].weight -= flow;
				}
				else
				{
					edges[ed].weight -= flow;
					edges[ed - 1].weight += flow;
					edges[oppEdge].weight -= flow;
				}
			}
		}

		class Edge
		{
			int to, weight, oppEdge;

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

4
1 4 5
1 2 20
1 3 10
2 3 5
2 4 10
3 4 20
0

*/
