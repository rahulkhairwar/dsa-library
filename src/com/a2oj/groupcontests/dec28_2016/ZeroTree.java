package com.a2oj.groupcontests.dec28_2016;

import java.io.*;
import java.util.*;
import java.util.List;

public final class ZeroTree
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
        int[] val;
        List<Integer>[] adj;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			adj = new List[n];

			for (int i = 0; i < n; i++)
				adj[i] = new ArrayList<>();

			for (int i = 1; i < n; i++)
			{
				int u, v;

				u = in.nextInt() - 1;
				v = in.nextInt() - 1;
				adj[u].add(v);
				adj[v].add(u);
			}

			val = in.nextIntArray(n);

			Pair ans = dfs(0, -1);

			out.println(ans.sub + ans.add);
		}

		Pair dfs(int node, int par)
		{
			Pair pp = new Pair(0, 0);

			for (int x : adj[node])
			{
				if (x == par)
					continue;

				Pair pt = dfs(x, node);

				pp.sub = Math.max(pp.sub, pt.sub);
				pp.add = Math.max(pp.add, pt.add);
			}

			long xx = val[node] - pp.sub + pp.add;

			if (xx < 0)
				pp.add += -xx;
			else
				pp.sub += xx;

			return pp;
		}

		class Pair
		{
			long sub, add;

			public Pair(long sub, long add)
			{
				this.sub = sub;
				this.add = add;
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

/*

7
1 2
1 4
2 3
2 5
4 6
4 7
-15 -10 3 -10 6 3 6

7
1 2
1 4
2 3
2 5
4 6
4 7
25 -15 30 -10 -5 6 16

9
1 2
1 3
2 4
2 5
2 8
4 6
4 7
8 9
25 -50 -70 50 20 10 11 15 10

9
1 2
1 3
2 4
2 5
2 8
4 6
4 7
8 9
25 -50 70 50 20 10 11 15 10

*/
