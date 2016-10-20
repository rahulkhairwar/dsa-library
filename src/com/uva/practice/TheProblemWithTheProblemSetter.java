package com.uva.practice;

import java.io.*;
import java.util.*;

class TheProblemWithTheProblemSetter
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
        static final int INFINITY = (int) 1e8;
        int cat, probs, v, s, t, flow, maxFlow, target;
        int[] par, parEdge;
        Edge[] edges;
        List<Integer>[] adj;
        InputReader in;
        OutputWriter out;

        void solve()
        {
            while (true)
            {
                cat = in.nextInt();
                probs = in.nextInt();

                if (cat == 0 && probs == 0)
                    break;

                // s = 0
				// categories : [1, cat]
				// problems : [cat + 1, cat + probs]
				// t = cat + probs + 1
                v = cat + probs + 2;
                s = 0;
                t = v - 1;
                maxFlow = 0;
				target = 0;
                edges = new Edge[100000];
                adj = new ArrayList[v];

                for (int i = 0; i < v; i++)
                    adj[i] = new ArrayList<>();

                int counter = 0;

                // connecting each category to the source, with capacity of each category being taken as input.
                for (int i = 1; i <= cat; i++)
                {
                    int count = in.nextInt();

                    target += count;
                    edges[counter] = new Edge(i, count);
                    adj[0].add(counter++);
                    // adding back edge
                    edges[counter] = new Edge(0, 0);
                    adj[i].add(counter++);
                }

                // connecting each problem node to the sink, with capacity of each node being 1.
                for (int i = cat + 1; i <= cat + probs; i++)
                {
                    edges[counter] = new Edge(t, 1);
                    adj[i].add(counter++);
                    edges[counter] = new Edge(i, 0);
                    adj[t].add(counter++);
                }

                for (int i = 0; i < probs; i++)
                {
                    // number of categories of the current node.
                    int count = in.nextInt();

                    // connecting current node to each of it's categories.
                    for (int j = 0; j < count; j++)
                    {
                        int type = in.nextInt();

                        edges[counter] = new Edge(cat + 1 + i, 1);
                        adj[type].add(counter++);
                        edges[counter] = new Edge(type, 0);
                        adj[cat + 1 + i].add(counter++);
                    }
                }

				while (true)
                {
					// will do bfs to find an augmenting path from s to t, until we can't increase the flow anymore or
					// another path does not exist.
					flow = 0;
					par = new int[v];
					parEdge = new int[v];
					Arrays.fill(par, -1);
					Arrays.fill(parEdge, -1);

					boolean[] visited = new boolean[v];
					Queue<Integer> queue = new LinkedList<>();

					visited[s] = true;
					queue.add(s);

					while (queue.size() > 0)
                    {
						int curr = queue.poll();
						Iterator<Integer> iterator = adj[curr].iterator();

						if (curr == t)
							break;

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

				if (maxFlow == target)
                {
					// all categories can be allotted to the problems.
					out.println(1);

					for (int i = 1; i <= cat; i++)
					{
						Iterator<Integer> iterator = adj[i].iterator();

						while (iterator.hasNext())
						{
							int ed = iterator.next();
							int to = edges[ed].to;
							int weight = edges[ed].weight;

							if (to > i && weight == 0)
								out.print(to - cat + " ");
						}

						if (adj[i].size() > 0)
							out.println();
					}
                }
                else
                    out.println(0);
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

                augment(par[node], Math.min(minEdge, weight));

                if (ed % 2 == 0)
                {
                    // forward edge.
                    // if this edge is from a node to the sink, will add node to category-problemset.
                    edges[ed].weight -= flow;
                    edges[ed + 1].weight += flow;
                }
                else
                {
					// back edge.
					// if this edge is from a sink, will remove node problem from category-problemset.
                    edges[ed].weight -= flow;
                    edges[ed - 1].weight += flow;
                }
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

3 15
3 3 4
2 1 2
1 3
1 3
1 3
1 3
3 1 2 3
2 2 3
2 1 3
1 2
1 2
2 1 2
2 1 3
2 1 2
1 1
3 1 2 3
3 15
7 3 4
2 1 2
1 1
1 2
1 2
1 3
3 1 2 3 2 2 3
2 2 3
1 2
1 2
2 2 3
2 2 3
2 1 2
1 1
3 1 2 3
0 0
: possible, not possible

6 10
2 1 1 2 3 1
1 1
1 2
2 1 3
2 3 5
1 5
1 5
2 4 6
1 4
3 1 5 6
1 6
0 0
: possible

4 6
1 2 2 1
2 1 2
1 1
1 2
2 2 3
1 4
1 3
0 0
: possible

*/
