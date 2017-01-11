package com.codechef.competitions.longcompetitions.year2016.december;

import java.io.*;
import java.util.*;

public class ALEXROSE
{
    public static void main(String[] args)
    {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

		try
		{
			in = new InputReader(new FileInputStream(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
					+ "Programming/src/com/checker/input.txt")));
			out = new PrintWriter(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA "
					+ "Workspace/Competitive Programming/src/com/checker/output.txt"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		Solver solver = new Solver(in, out);

		solver.solve();
        in.close();
        out.flush();
        out.close();
    }

    static class Solver
    {
		static final int inf = (int) 1e6;
		int t, n, k, size;
		int[] count;
		long[] lengths;
		int[][] dp;
		TreeMap<Long, Integer> map;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				k = in.nextInt();
				lengths = new long[n];
				map = new TreeMap<>();

				for (int i = 0; i < n; i++)
				{
					lengths[i] = in.nextLong();

					Integer cnt = map.get(lengths[i]);

					if (cnt == null)
						map.put(lengths[i], 1);
					else
						map.put(lengths[i], cnt + 1);
				}

				Set<Long> remove = new HashSet<>();

				for (Map.Entry<Long, Integer> entry : map.entrySet())
					if (entry.getValue() % k == 0)
						remove.add(entry.getKey());

				for (long x : remove)
					map.remove(x);

				size = map.size();
				count = new int[size + 1];
				dp = new int[size + 1][n + 1];

				for (int i = 0; i <= size; i++)
					Arrays.fill(dp[i], -1);

				int ctr = 1;

				for (Map.Entry<Long, Integer> entry : map.entrySet())
					count[ctr++] = entry.getValue() % k;

				out.println(find(size, 0));
			}
		}

		int find(int ind, int extra)
		{
			if (ind == 0)
				return extra > 0 ? inf : 0;

			if (dp[ind][extra] != -1)
				return dp[ind][extra];

			int req = k - count[ind];
			int min;

			if (extra >= req)	// can either make a bouquet or not
				min = Math.min(find(ind - 1, extra - req) + req, find(ind - 1, extra + count[ind]));
			else
				min = find(ind - 1, extra + count[ind]);

			dp[ind][extra] = min;

			return min;
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

}

/*

2
8 4
1 2 2 3 4 5 5 6
4 4
1 1 1 3

1
4 4
1 1 1 3

1
12 4
1 2 2 3 3 3 4 4 5 5 5 6
: 5

*/
