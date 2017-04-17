package com.codeforces.competitions.year2017.round400div1_2;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public final class TaskC
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
        int n, k, arr[];
        InputReader in;
        PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			k = in.nextInt();
			arr = in.nextIntArray(n);

			long[] search = new long[100];
			Set<Long> set = new HashSet<>();

			search[0] = 1;
			set.add(1L);

			for (int i = 1; ; i++)
			{
				if (BigInteger.valueOf(search[i - 1]).multiply(BigInteger.valueOf(k)).compareTo(BigInteger.valueOf(
						(long) 1e16)) > 0)
					break;

				if (set.contains(search[i - 1] * k))
					break;

				search[i] = search[i - 1] * k;
				set.add(search[i]);
			}

			long cnt = 0;
			long[] cum = new long[n];

			cum[0] = arr[0];

			for (int i = 1; i < n; i++)
				cum[i] = cum[i - 1] + arr[i];

			Map<Long, Integer> map = new HashMap<>();

			map.put(0L, 1);

			for (int i = 0; i < n; i++)
			{
				Integer c;

				for (int j = 0; search[j] != 0; j++)
				{
					long toFind = cum[i] - search[j];

					c = map.get(toFind);

					if (c == null)
						continue;

					cnt += c;
				}

				c = map.get(cum[i]);

				if (c == null)
					map.put(cum[i], 1);
				else
					map.put(cum[i], c + 1);
			}

			out.println(cnt);
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
