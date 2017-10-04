package com.codeforces.competitions.educational.year2017.round21;

import java.io.*;
import java.util.*;

public final class TaskD
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
    	static final long INFINITY = (int) 1e9;
        int n;
        long sum;
        int[] arr;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			arr = new int[n];

			for (int i = 0; i < n; i++)
			{
				arr[i] = in.nextInt();
				sum += arr[i];
			}

			if (sum % 2 == 1)
			{
				out.println("NO");

				return;
			}
			else if (possible(arr))
			{
				out.println("YES");

				return;
			}

			for (int i = 0; i < n / 2; i++)
			{
				int temp = arr[i];

				arr[i] = arr[n - 1 - i];
				arr[n - 1 - i] = temp;
			}

			if (possible(arr))
				out.println("YES");
			else
				out.println("NO");
		}

		boolean possible(int[] arr)
		{
			Set<Long> set = new HashSet<>();
			boolean poss = false;
			long curr = arr[0];

			set.add((long) arr[0]);

			for (int i = 1; i < n; i++)
			{
				set.add((long) arr[i]);
				curr += arr[i];

				long rest = sum - curr;
				long diff = curr - rest;

				if (diff / 2 <= INFINITY && set.contains(diff / 2))
				{
					poss = true;

					break;
				}
			}

			return poss;
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

6
1 1 1 1 1 1

5
1 1 1 1 2

5
2 1 2 1 2

*/
