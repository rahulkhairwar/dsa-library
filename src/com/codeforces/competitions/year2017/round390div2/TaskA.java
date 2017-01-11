package com.codeforces.competitions.year2017.round390div2;

import java.io.*;
import java.util.*;

public final class TaskA
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
        int n, arr[];
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			arr = in.nextIntArray(n);

			List<Integer> a, b;
			int left = 0;

			a = new ArrayList<>();
			b = new ArrayList<>();

			for (int i = 0; i < n; i++)
			{
				if (arr[i] != 0)
				{
					int r = i + 1;

					while (r < n && arr[r] == 0)
						r++;

					a.add(left);
					b.add(r - 1);
					left = r;
				}
			}

			boolean isZero = true;

			for (int i = 0; i < n; i++)
				if (arr[i] != 0)
					isZero = false;

			if (isZero)
				out.println("NO");
			else
			{
				out.println("YES");
				out.println(a.size());

				for (int i = 0; i < a.size(); i++)
					out.println(a.get(i) + 1 + " " + (b.get(i) + 1));
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

10
1 0 2 3 4 5 0 0 0 2

5
0 0 0 0 1

5
0 0 1 0 0

 */
