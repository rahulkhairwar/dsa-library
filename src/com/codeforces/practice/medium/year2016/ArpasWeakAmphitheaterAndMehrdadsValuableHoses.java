package com.codeforces.practice.medium.year2016;

import java.io.*;
import java.util.*;

public final class ArpasWeakAmphitheaterAndMehrdadsValuableHoses
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
        int n, m, w, size;
        int[] xx;
        long[][] dp;
        Node[] nodes;
        InputReader in;
        OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();
			w = in.nextInt();
			nodes = new Node[n];

			for (int i = 0; i < n; i++)
				nodes[i] = new Node(i, in.nextInt());

			for (int i = 0; i < n; i++)
				nodes[i].beauty = nodes[i].totBeauty = in.nextInt();

			for (int i = 0; i < m; i++)
			{
				int a, b;

				a = in.nextInt() - 1;
				b = in.nextInt() - 1;
				union(nodes[a], nodes[b]);
			}

			Set<Integer> set = new HashSet<>();

			for (int i = 0; i < n; i++)
			{
				Node par = findParent(nodes[i]);

				par.children.add(i);
				set.add(par.key);
			}

			size = set.size();
			xx = new int[size];
			dp = new long[size][w + 1];

			Iterator<Integer> iterator = set.iterator();

			for (int i = 0; i < size; i++)
				xx[i] = iterator.next();

			for (int i = 0; i < size; i++)
				Arrays.fill(dp[i], -1);

			long max = find(0, w);

			out.println(max);
		}

		long find(int ind, int rem)
		{
			if (ind == size)
				return 0;

			if (rem <= 0)
				return 0;

			if (dp[ind][rem] != -1)
				return dp[ind][rem];

			long max = 0;

			if (nodes[xx[ind]].totWeight <= rem)
				max = nodes[xx[ind]].totBeauty + find(ind + 1, rem - nodes[xx[ind]].totWeight);

			for (int x : nodes[xx[ind]].children)
			{
				if (nodes[x].weight <= rem)
					max = Math.max(max, nodes[x].beauty + find(ind + 1, rem - nodes[x].weight));
			}

			max = Math.max(max, find(ind + 1, rem));

			return dp[ind][rem] = max;
		}

		void union(Node one, Node two)
		{
			Node parentOne, parentTwo;

			parentOne = findParent(one);
			parentTwo = findParent(two);

			if (parentOne == parentTwo)
				return;

			if (parentOne.height > parentTwo.height)
			{
				parentTwo.parent = parentOne;
				parentOne.size += parentTwo.size;
				parentOne.totBeauty += parentTwo.totBeauty;
				parentOne.totWeight += parentTwo.totWeight;
			}
			else if (parentOne.height < parentTwo.height)
			{
				parentOne.parent = parentTwo;
				parentTwo.size += parentOne.size;
				parentTwo.totBeauty += parentOne.totBeauty;
				parentTwo.totWeight += parentOne.totWeight;
			}
			else
			{
				parentTwo.parent = parentOne;
				parentOne.size += parentTwo.size;
				parentOne.totBeauty += parentTwo.totBeauty;
				parentOne.totWeight += parentTwo.totWeight;
				parentOne.height++;
			}
		}

		Node findParent(Node one)
		{
			if (one.key == one.parent.key)
				return one;

			return one.parent = findParent(one.parent);
		}

		class Node
		{
			int key, size, height, totBeauty, totWeight, beauty, weight;
			Node parent;
			List<Integer> children;

			public Node(int key, int weight)
			{
				this.key = key;
				this.weight = totWeight = weight;
				size = 1;
				height = 0;
				parent = this;
				children = new ArrayList<>();
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
