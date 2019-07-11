package com.a2oj.groupcontests.feb20_2017;

import java.io.*;
import java.util.*;
import java.util.List;

public final class LomsatGelral
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
        int n;
        int[] col;
        long[] ans;
        List<Integer>[] adj;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			col = in.nextIntArray(n);
			ans = new long[n];
			adj = new List[n];

			for (int i = 0; i < n; i++)
				adj[i] = new ArrayList<>();

			for (int i = 1; i < n; i++)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				adj[u].add(v);
				adj[v].add(u);
			}

			dfs(0, -1);

			for (long x : ans)
				out.print(x + " ");
		}

		Node dfs(int node, int par)
		{
			Node a = new Node();

			for (int x : adj[node])
			{
				if (x == par)
					continue;

				Node b = dfs(x, node);

				if (b.count.size() > a.count.size())
				{
					Node temp = a;

					a = b;
					b = temp;
				}

				a.merge(b);
			}

			a.put(col[node], 1);
			ans[node] = a.sum;

			return a;
		}

		class Node
		{
			Map<Integer, Integer> count = new HashMap<>();
			long sum;
			int max;

			void put(int col, int cnt)
			{
				Integer x = count.getOrDefault(col, 0);

				x += cnt;
				count.put(col, x);

				if (x > max)
				{
					max = x;
					sum = col;
				}
				else if (x == max)
					sum += col;
			}

			void merge(Node other)
			{
				for (Map.Entry<Integer, Integer> entry : other.count.entrySet())
					put(entry.getKey(), entry.getValue());
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

        public boolean isSpaceChar(int c)
        {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
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
