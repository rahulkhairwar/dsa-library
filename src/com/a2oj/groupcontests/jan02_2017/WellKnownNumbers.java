package com.a2oj.groupcontests.jan02_2017;

import java.io.*;
import java.util.*;

public final class WellKnownNumbers
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
        int sum, k, max;
        long[] arr;
        List<Integer> ans;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			sum = in.nextInt();
			k = in.nextInt();
			arr = new long[100];
			arr[1] = 0;	// ~ arr[0] = arr[1] = .... = arr[k - 1] = 0
			arr[2] = 1;	// ~ arr[k] = 1

			for (int i = 3; arr[i - 1] <= 1e9; i++)
			{
				int ctr = 1;
				long xx = 0;

				while (ctr <= k && i - ctr >= 0)
				{
					xx += arr[i - ctr];
					ctr++;
				}

				arr[i] = xx;
				max = i;
			}

			ans = new ArrayList<>();
			ans.add(0);

			for (int i = max; i > 0; i--)
			{
				if (arr[i] <= sum)
				{
					ans.add(i);
					sum -= arr[i];

					if (sum == 0)
						break;
				}
			}

			out.println(ans.size());

			for (int x : ans)
				out.print(arr[x] + " ");
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

100 2

100 5

1000000000 5

*/
