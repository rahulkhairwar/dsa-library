package com.codechef.competitions.longcompetitions.year2017.june;

import java.io.*;
import java.util.*;

public class SUMQ
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
    	static final long mod = (long) 1e9 + 7;
        int t, p, q, r;
        long[] a, b, c, cumA, cumC;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				p = in.nextInt();
				q = in.nextInt();
				r = in.nextInt();
				a = in.nextLongArray(p);
				b = in.nextLongArray(q);
				c = in.nextLongArray(r);
				cumA = new long[p];
				cumC = new long[r];
				Arrays.sort(a);
				Arrays.sort(b);
				Arrays.sort(c);

				long sum = 0;

				cumA[0] = a[0];
				cumC[0] = c[0];

				for (int i = 1; i < p; i++)
					cumA[i] = CMath.mod(cumA[i - 1] + a[i], mod);

				for (int i = 1; i < r; i++)
					cumC[i] = CMath.mod(cumC[i - 1] + c[i], mod);

				for (int i = 0; i < q; i++)
				{
					int left = findLessThanOrEqualTo(a, b[i]);
					int right = findLessThanOrEqualTo(c, b[i]);

					if (left == -1 || right == -1)
						continue;

					left++;
					right++;
					sum = CMath.mod(sum + CMath.mod(right * CMath.mod(b[i] * cumA[left - 1], mod), mod), mod);
					sum = CMath.mod(sum + CMath.mod(left * CMath.mod(b[i] * cumC[right - 1], mod), mod), mod);
					sum = CMath.mod(sum + CMath.mod(cumA[left - 1] * cumC[right - 1], mod), mod);
					sum = CMath.mod(sum + CMath.mod(left * CMath.mod(right * CMath.mod(b[i] * b[i], mod), mod),
							mod), mod);
				}

				out.println(sum);
			}
		}

		int findLessThanOrEqualTo(long[] arr, long x)
		{
			int low, high, mid;

			low = 0;
			high = arr.length - 1;

			while (low <= high)
			{
				mid = low + ((high - low) >> 1);

				if (arr[mid] <= x)
				{
					if (mid == arr.length - 1 || arr[mid + 1] > x)
						return mid;

					low = mid + 1;
				}
				else
				{
					if (mid == 0)
						return -1;
					else if (arr[mid - 1] <= x)
						return mid - 1;

					high = mid - 1;
				}
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
		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

}
