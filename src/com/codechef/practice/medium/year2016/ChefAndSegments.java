package com.codechef.practice.medium.year2016;

import java.io.*;
import java.util.*;

public final class ChefAndSegments
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
        int t, n, max, arr[];
		List<Integer>[] pos;
		Node[] nodes;
		long answer;
        InputReader in;
        OutputWriter out;

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				arr = new int[n];

				for (int i = 0; i < n; i++)
					arr[i] = in.nextInt();

				compress();
				pos = new List[max];

				for (int i = 0; i < max; i++)
					pos[i] = new ArrayList<>();

				for (int i = 0; i < n; i++)
					pos[arr[i]].add(i);

				answer = 0;

				for (int i = 0; i < n - 1; i++)
					count(0, i);

				out.println(answer);
			}
		}

		void count(int left, int right)
		{
			nodes = new Node[n];

			for (int i = 0; i < n; i++)
				nodes[i] = new Node(i, true);

			boolean[] done = new boolean[max];
			int[] cnt = new int[max];

			for (int i = left; i <= right; i++)
			{
				cnt[arr[i]]++;

				if (done[arr[i]])
					continue;

				for (int curr : pos[arr[i]])
					nodes[curr].isZero = false;

				done[arr[i]] = true;
			}

			for (int i = right + 1; i < n; i++)
			{
				if (!nodes[i].isZero)
					continue;

				if (nodes[i - 1].isZero)
					union(nodes[i - 1], nodes[i]);
			}

			done = new boolean[n];

			long count = 0;

			// getting the initital count, that is for the original array [left, right].
			for (int i = right + 1; i < n; i++)
			{
				Node par = findParent(nodes[i]);

				if (done[par.key] || !nodes[i].isZero)
					continue;

				count += (par.size * (par.size + 1) / 2);
				done[par.key] = true;
			}

			// have to now increase the count due to the groups' sizes increased by decreasing the size of the left
			// subarray by increasing the left end point.
			long prev, curr;

			prev = count;
			answer += count;

			for (int i = left; i < right; i++)
			{
				cnt[arr[i]]--;
				curr = prev;

				if (cnt[arr[i]] == 0)
				{
					for (int x : pos[arr[i]])
					{
						if (x <= right)
							continue;

						nodes[x].isZero = true;

						if (x - 1 > right && nodes[x - 1].isZero)
						{
							Node par = findParent(nodes[x - 1]);

							curr -= (par.size * (par.size + 1) / 2);
							union(nodes[x - 1], nodes[x]);
						}

						if (x + 1 < n && nodes[x + 1].isZero)
						{
							Node par = findParent(nodes[x + 1]);

							curr -= (par.size * (par.size + 1) / 2);
							union(nodes[x], nodes[x + 1]);
						}

						Node par = findParent(nodes[x]);

						curr += par.size * (par.size + 1) / 2;
					}
				}

				answer += curr;
				prev = curr;
			}
		}

		void compress()
		{
			int[] copy = Arrays.copyOf(arr, n);
			Map<Integer, Integer> map = new HashMap<>();
			int counter = 1;

			Arrays.sort(copy);

			for (int i = 0; i < n; i++)
				if (!map.containsKey(copy[i]))
					map.put(copy[i], counter++);

			max = counter;

			for (int i = 0; i < n; i++)
				arr[i] = map.get(arr[i]);
		}

		Node findParent(Node one)
		{
			if (one.key == one.parent.key)
				return one;
			else
				return one.parent = findParent(one.parent);
		}

		void union(Node one, Node two)
		{
			Node parentOne, parentTwo;

			parentOne = findParent(one);
			parentTwo = findParent(two);

			if (parentOne.key == parentTwo.key)
				return;

			if (parentOne.height > parentTwo.height)
			{
				parentTwo.parent = parentOne;
				parentOne.size += parentTwo.size;
			}
			else if (parentOne.height < parentTwo.height)
			{
				parentOne.parent = parentTwo;
				parentTwo.size += parentOne.size;
			}
			else
			{
				parentTwo.parent = parentOne;
				parentOne.size += parentTwo.size;
				parentOne.height++;
			}
		}

		class Node
		{
			int key, height, size;
			Node parent;
			boolean isZero;

			public Node(int key, boolean isZero)
			{
				this.key = key;
				this.isZero = isZero;
				height = 0;
				size = 1;
				parent = this;
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

1
3
1 2 3
: 5

1
4
1 2 1 2
: 4

1
10
1 2 3 5 3 6 1 2 6 4
: 237

1
5
5 1 3 7 1
: 23

1
4
4 9 1 4
: 9

1
6
5 8 6 3 6 5
: 40

*/
