package com.a2oj.groupcontests.feb14_2017;

import java.io.*;
import java.util.*;

public final class AntColony
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
		int[] arr, tree;
		Map<Integer, List<Integer>> map;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			arr = new int[n];
			tree = new int[n << 2];
			map = new HashMap<>();

			for (int i = 0; i < n; i++)
			{
				arr[i] = in.nextInt();
				map.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
			}

			build(1, 0, n - 1);
			q = in.nextInt();

			while (q-- > 0)
			{
				int l = in.nextInt() - 1;
				int r = in.nextInt() - 1;
				int gcd = query(1, 0, n - 1, l, r);

				if (gcd == -1)
					out.println(r - l + 1);
				else
				{
					// gcd >= 1.
					if (!map.containsKey(gcd))
						out.println(r - l + 1);
					else
					{
						// some ants have the same strength as the gcd.
						int left = findLeft(map.get(gcd), l);
						int right = findRight(map.get(gcd), r);

						if (left == -1)
							out.println(r - l + 1);
						else
							out.println(r - l + 1 - (right - left + 1));
					}
				}
			}
		}

		void build(int node, int treeStart, int treeEnd)
		{
			if (treeStart == treeEnd)
			{
				tree[node] = arr[treeStart];

				return;
			}

			int mid = treeStart + treeEnd >> 1;
			int left = node << 1;
			int right = left + 1;

			build(left, treeStart, mid);
			build(right, mid + 1, treeEnd);
			tree[node] = CMath.gcd(tree[left], tree[right]);
		}

		int query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return -1;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return tree[node];

			int mid = treeStart + treeEnd >> 1;
			int left = query(node << 1, treeStart, mid, rangeStart, rangeEnd);
			int right = query((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			if (left == -1)
				return right;

			if (right == -1)
				return left;

			return CMath.gcd(left, right);
		}

		int findLeft(List<Integer> list, int l)
		{
			int low, high, mid;

			low = 0;
			high = list.size() - 1;

			while (low <= high)
			{
				mid = low + high >> 1;

				if (list.get(mid) >= l)
				{
					if (mid == 0 || list.get(mid - 1) < l)
						return mid;

					high = mid - 1;
				}
				else
					low = mid + 1;
			}

			return -1;
		}

		int findRight(List<Integer> list, int r)
		{
			int low, high, mid;

			low = 0;
			high = list.size() - 1;

			while (low <= high)
			{
				mid = low + high >> 1;

				if (list.get(mid) <= r)
				{
					if (mid == list.size() - 1 || list.get(mid + 1) > r)
						return mid;

					low = mid + 1;
				}
				else
					high = mid - 1;
			}

			return -1;
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

	static class CMath
	{
		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

	}

}
