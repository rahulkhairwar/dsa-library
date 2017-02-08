package com.facebookhackercup.hc2017.round1;

import java.io.*;
import java.util.*;

public final class TaskC
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
    	static final int INFINITY = (int) 1e8;
        int t, v, e, k;
		int[][] adj, dist;
		Delivery[] deliveries;
		long[][][] dp;
		InputReader in;
        PrintWriter out;

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				v = in.nextInt();
				e = in.nextInt();
				k = in.nextInt();
				adj = new int[v][v];
				dist = new int[v][v];
				deliveries = new Delivery[k];
				dp = new long[k][3][2];

				for (int i = 0; i < v; i++)
				{
					Arrays.fill(adj[i], INFINITY);
					Arrays.fill(dist[i], INFINITY);
				}

				for (int i = 0; i < e; i++)
				{
					int fr, to, wt;

					fr = in.nextInt() - 1;
					to = in.nextInt() - 1;
					wt = in.nextInt();

					if (adj[fr][to] > wt)
						adj[fr][to] = adj[to][fr] = wt;
				}

				for (int i = 0; i < v; i++)
					dijkstra(i);

/*				System.out.println("Found all pairs shortest paths :");
				for (int i = 0; i < v; i++, System.out.println())
					for (int j = 0; j < v; j++)
						System.out.print((dist[i][j] == INFINITY ? "oo" : dist[i][j]) + " ");*/

				for (int i = 0; i < k; i++)
					deliveries[i] = new Delivery(in.nextInt() - 1, in.nextInt() - 1);

				for (int i = 0; i < k; i++)
					for (int j = 0; j < 3; j++)
						Arrays.fill(dp[i][j], INFINITY);
			}
		}

		long find(int ind, int load, int pickedUp)
		{
			if (ind >= k)
				return 0;

			if (dp[ind][load][pickedUp] != INFINITY)
				return dp[ind][load][pickedUp];

			long min = INFINITY;

			if (ind == k - 1)
				return dist[deliveries[ind].from][deliveries[ind].to];

			// if load = 0, the truck is empty.
			if (load == 0)
			{
				int p1, d1, p2, d2;

				p1 = deliveries[ind].from;
				d1 = deliveries[ind].to;
				p2 = deliveries[ind + 1].from;
				d2 = deliveries[ind + 1].to;

				// picking up this package, then delivering it, then moving to the next package
				min = Math.min(min, dist[p1][d1] + find(ind + 1, 1, 0));
				min = Math.min(min, dist[p1][p2] + find(ind + 1, 2, 1));
			}
			else if (load == 1)	// if load = 1, the truck has 1 package.
			{
			}
			else	// if load = 2, the truck has 2 packages, i.e., is full.
			{
			}

			return 0;
		}

		long find2(int ind, int pos)
		{
			if (ind == k)
				return 0;

//			if (dp[ind][pos] != -1)
//				return dp[ind][pos];

			// now, we have an option to either

			return 0;
		}

		void dijkstra(int start)
		{
			TreeSet<Integer> set = new TreeSet<>(new Comparator<Integer>()
			{
				@Override public int compare(Integer o1, Integer o2)
				{
					if (dist[start][o1] == dist[start][o2])
						return Integer.compare(o1, o2);

					return Integer.compare(dist[start][o1], dist[start][o2]);
				}
			});

			dist[start][start] = 0;
			set.add(start);

			while (set.size() > 0)
			{
				int curr = set.pollFirst();

				for (int i = 0; i < v; i++)
				{
					if (dist[start][curr] + adj[curr][i] < dist[start][i])
					{
						set.remove(i);
						dist[start][i] = dist[start][curr] + adj[curr][i];
						set.add(i);
					}
				}
			}
		}

		class Edge
		{
			int to, weight;

			Edge(int to, int weight)
			{
				this.to = to;
				this.weight = weight;
			}

			@Override public int hashCode()
			{
				return Objects.hash(to);
			}

			@Override public boolean equals(Object obj)
			{
				Edge e = (Edge) obj;

				return to == e.to;
			}

		}

		class Delivery
		{
			int from, to;

			Delivery(int from, int to)
			{
				this.from = from;
				this.to = to;
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
		static long power(long number, long power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			if (power == 1)
				return number;

			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}

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

		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

		static long min(long... arr)
		{
			long min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static long max(long... arr)
		{
			long max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static int max(int... arr)
		{
			int max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

	}

}

/*

5
3 2 3
1 2 4
2 3 7

1
3 2 3
1 2 4
2 3 7

1
7 8 10
7 5 9
1 2 14
3 7 7
2 5 8
4 3 10
1 4 3
7 3 5
4 2 16

*/
