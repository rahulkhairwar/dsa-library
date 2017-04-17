package com.coursera.algorithms.graphSearch_shortestPaths_dS;

import java.io.*;
import java.util.*;

/**
 * Directi interview question(friend's interview).
 * <br />Question : Given a binary search tree, find the total number of input orders which will create the same tree.
 */
public final class Temp
{
    public static void main(String[] args)
    {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
        in.close();
        out.flush();
        out.close();
    }

    static class Solver
    {
    	static final long mod = (long) 1e9 + 7;
        int n;
        int[] par, pos;
        long[] fact, invFact;
        Node[] nodes;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			pre();
			n = in.nextInt();
			par = new int[n];
			pos = new int[n];
			nodes = new Node[n];

			Arrays.fill(par, -1);

			// input given as - nodeNumber leftChild rightChild
			for (int i = 0; i < n; i++)
			{
				int x = in.nextInt() - 1;
				int left = in.nextInt() - 1;
				int right = in.nextInt() - 1;

				nodes[x] = new Node(left, right);

				if (left != -1)
					par[left] = x;

				if (right != -1)
					par[right] = x;
			}

			int root = -1;

			for (int i = 0; i < n; i++)
				if (par[i] == -1)
					root = i;

			long[] ans = count(root);

			out.println("size : " + ans[0] + ", ans : " + ans[1]);
		}

		long[] count(int node)
		{
			long[] ret = new long[]{1, 1};
			long a, b;

			a = b = 0;

			if (nodes[node].left != -1)
			{
				long[] temp = count(nodes[node].left);

				a = temp[0];
				ret[1] *= temp[1];
			}

			if (nodes[node].right != -1)
			{
				long[] temp = count(nodes[node].right);

				b = temp[0];
				ret[1] *= temp[1];
			}

			ret[0] = 1 + a + b;
			ret[1] *= nCR((int) (a + b), (int) a);
//			System.out.println("node : " + node + ", size : " + ret[0] + ", cnt : " + ret[1]);

			return ret;
		}

		void pre()
		{
			int lim = (int) 1e5;

			fact = new long[lim + 5];
			invFact = new long[lim + 5];
			fact[0] = fact[1] = 1;

			for (int i = 2; i <= lim; i++)
				fact[i] = CMath.mod(fact[i - 1] * i, mod);

			invFact[lim] = CMath.moduloInverse(fact[lim], mod);

			for (int i = lim; i > 0; i--)
				invFact[i - 1] = CMath.mod(invFact[i] * i, mod);
		}

		long nCR(int n, int r)
		{
			return CMath.mod(CMath.mod(fact[n] * invFact[r], mod) * invFact[n - r], mod);
		}

		class Node
		{
			int left, right;

			public Node(int left, int right)
			{
				this.left = left;
				this.right = right;
			}

		}

        public Solver(InputReader in, PrintWriter out)
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

	static class CMath
	{
		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);
			else
				return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

}

/*

5
3 1 4
1 0 2
2 0 0
4 0 5
5 0 0

7
1 0 0
2 1 3
3 0 0
4 2 6
5 0 0
6 5 7
7 0 0

*/
