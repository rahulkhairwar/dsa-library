package com.a2oj.groupcontests.jan02_2017;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public final class PrimePermutation
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
    	boolean poss;
        char[] s, ans;
        boolean[] vis;
        List<Integer>[] adj;
        InputReader in;
        PrintWriter out;

        void solve()
		{
			s = in.next().toCharArray();
			n = s.length;
			ans = new char[n + 1];
			vis = new boolean[n + 1];
			adj = new List[n + 1];

			for (int i = 0; i <= n; i++)
				adj[i] = new ArrayList<>();

			boolean[] isPrime = new boolean[n + 1];

			for (int i = 2; i <= n; i++)
			{
				if (isPrime(i))
				{
					int prev = i;

					isPrime[i] = true;

					while (prev + i <= n)
					{
						adj[i].add(prev + i);
						adj[prev + i].add(i);
						prev += i;
					}
				}
			}

			PriorityQueue<Point> queue = new PriorityQueue<>(new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					return Integer.compare(o2.y, o1.y);
				}
			});

			int[] cnt = new int[26];
			int[] sizes = new int[n + 1];

			for (char x : s)
				cnt[x - 'a']++;

			for (int i = 0; i < 26; i++)
				if (cnt[i] > 0)
					queue.add(new Point(i, cnt[i]));

			for (int i = 1; i <= n; i++)
				if (!vis[i])
					sizes[i] = findSize(i);

			poss = true;
			Arrays.fill(vis, false);
			Arrays.fill(ans, '.');

			for (int i = 2; i <= n; i++)
			{
				if (isPrime[i] && !vis[i])
				{
					Point pt = queue.poll();

					if (pt.y < sizes[i])
					{
						out.println("NO");

						return;
					}

					mark(i, (char) (pt.x + 'a'));
					pt.y -= sizes[i];

					if (pt.y > 0)
						queue.add(pt);
				}
			}

			ans[1] = (char) (queue.poll().x + 'a');
			out.println("YES");

			for (int i = 1; i <= n; i++)
				out.print(ans[i]);
		}

		void mark(int node, char ch)
		{
			vis[node] = true;
			ans[node] = ch;

			for (int x : adj[node])
				if (!vis[x])
					mark(x, ch);
		}

		int findSize(int node)
		{
			vis[node] = true;

			int size = 1;

			for (int x : adj[node])
				if (!vis[x])
					size += findSize(x);

			return size;
		}

		boolean isPrime(int n)
		{
			int sqrt = (int) Math.sqrt(n);

			for (int i = 2; i <= sqrt; i++)
				if (n % i == 0)
					return false;

			return true;
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

aaaabbcd

*/
