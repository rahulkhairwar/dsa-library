package com.codechef.competitions.longcompetitions.year2017.june;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class NEO01
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
        int t, n;
        int[] arr;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				arr = in.nextIntArray(n);

				long neg = 0;
				long pos = 0;
				int posSize = 0;

				for (int i = 0; i < n; i++)
				{
					if (arr[i] < 0)
						neg += arr[i];
					else
					{
						pos += arr[i];
						posSize++;
					}
				}

				long max = neg + posSize * pos;
				long[] leftCum = new long[n];
				long[] rightCum = new long[n];

				Arrays.sort(arr);
				leftCum[0] = arr[0];
				rightCum[n - 1] = arr[n - 1];

				for (int i = 1; i < n; i++)
					leftCum[i] = leftCum[i - 1] + arr[i];

				for (int i = n - 2; i >= 0; i--)
					rightCum[i] = rightCum[i + 1] + arr[i];

				int sz = 1;

				for (int i = n - 1; i >= 0; i--)
				{
					long x = rightCum[i] * sz + (i > 0 ? leftCum[i - 1] : 0);

					sz++;
					max = Math.max(max, x);
				}

				out.println(max);
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
