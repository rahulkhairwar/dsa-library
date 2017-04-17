package com.a2onlinejudge.groupcontests.feb14_2017;

import java.io.*;
import java.util.*;

public final class XOROnSegment
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
        int n, q;
        byte[][] arr;
        int[][] tree;
        boolean[][] lazy;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			arr = new byte[20][n];
			tree = new int[20][n << 2];
			lazy = new boolean[20][n << 2];

			for (int i = 0; i < n; i++)
			{
				int x = in.nextInt();

				for (int j = 0; j < 20; j++)
				{
					if ((x & (1 << j)) > 0)
						arr[j][i] = 1;
				}
			}

			for (int i = 0; i < 20; i++)
				build(tree[i], arr[i], 1, 0, n - 1);

			q = in.nextInt();

			while (q-- > 0)
			{
				int type = in.nextInt();
				int l = in.nextInt() - 1;
				int r = in.nextInt() - 1;

				if (type == 1)
				{
					long sum = 0;

					for (int i = 0; i < 20; i++)
						sum += query(tree[i], lazy[i], 1, 0, n - 1, l, r) << (long) i;

					out.println(sum);
				}
				else
				{
					int x = in.nextInt();

					for (int i = 0; i < 20; i++)
					{
						if ((x & (1 << i)) > 0)
							update(tree[i], lazy[i], 1, 0, n - 1, l, r);
					}
				}
			}
		}

		void build(int[] tree, byte[] arr, int node, int treeStart, int treeEnd)
		{
			if (treeStart == treeEnd)
			{
				tree[node] = arr[treeStart];

				return;
			}

			int mid = treeStart + treeEnd >> 1;
			int left = node << 1;
			int right = left + 1;

			build(tree, arr, left, treeStart, mid);
			build(tree, arr, right, mid + 1, treeEnd);
			tree[node] = tree[left] + tree[right];
		}

		void update(int[] tree, boolean[] lazy, int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			push(tree, lazy, node, treeStart, treeEnd);

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			{
				tree[node] = treeEnd - treeStart + 1 - tree[node];

				if (treeStart != treeEnd)
				{
					lazy[node << 1] = !lazy[node << 1];
					lazy[(node << 1) + 1] = !lazy[(node << 1) + 1];
				}

				return;
			}

			int mid = treeStart + treeEnd >> 1;
			int left = node << 1;
			int right = left + 1;

			update(tree, lazy, left, treeStart, mid, rangeStart, rangeEnd);
			update(tree, lazy, right, mid + 1, treeEnd, rangeStart, rangeEnd);
			tree[node] = tree[left] + tree[right];
		}

		long query(int[] tree, boolean[] lazy, int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			push(tree, lazy, node, treeStart, treeEnd);

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return 0;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return tree[node];

			int mid = treeStart + treeEnd >> 1;
			long x = query(tree, lazy, node << 1, treeStart, mid, rangeStart, rangeEnd);
			long y = query(tree, lazy, (node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			return x + y;
		}

		void push(int[] tree, boolean[] lazy, int node, int treeStart, int treeEnd)
		{
			if (lazy[node])
			{
				if (treeStart != treeEnd)
				{
					lazy[node << 1] = !lazy[node << 1];
					lazy[(node << 1) + 1] = !lazy[(node << 1) + 1];
				}

				lazy[node] = false;
				tree[node] = treeEnd - treeStart + 1 - tree[node];	// flipping.
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

/*

5
4 10 3 13 7
8
1 2 4
2 1 3 3
1 2 4
1 3 3
2 2 5 5
1 1 5
2 1 2 10
1 2 3

5
4 10 3 13 7
1
1 2 4

*/
