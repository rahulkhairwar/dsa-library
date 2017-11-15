package com.codeforces.practice.hard.year2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class E436Div2
{
    public static void main(String[] args)
    {
        new E436Div2(System.in, System.out);
    }

    private static class Solver implements Runnable
    {
        int n, maxValue;
        Item[] items;
        int[][] dp;
        InputReader in;
        PrintWriter out;

        void solve() throws IOException
        {
            n = in.nextInt();
            items = new Item[n];
			dp = new int[n][2001];

			for (int i = 0; i < n; i++)
                items[i] = new Item(i, in.nextInt(), in.nextInt(), in.nextInt());

			Arrays.sort(items, new Comparator<Item>()
            {
                @Override
                public int compare(Item one, Item two)
                {
                    if (one.expiry == two.expiry)
                        return Integer.compare(two.val, one.val);

                    return Integer.compare(one.expiry, two.expiry);
                }
            });

            // dp[i][j] = max value possible using items up to i, with total time taken until now = j.
            for (int i = 0; i < n; i++)
            {
                for (int j = 1; j < 2001; j++)
                {
                    if (i == 0)
                        dp[i][j] = (items[i].expiry > j && j >= items[i].time) ? items[i].val : 0;
                    else
                    {
                        if (items[i].expiry > j)
                        {
                            if (j - items[i].time >= 0)
                                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - items[i].time] + items[i].val);
                            else
                                dp[i][j] = dp[i - 1][j];
                        }
                        else
                        	dp[i][j] = dp[i - 1][j];
                    }
                }
            }

			int maxAt = -1;

			for (int i = 0; i < 2001; i++)
			{
				if (dp[n - 1][i] > maxValue)
				{
					maxValue = dp[n - 1][i];
					maxAt = i;
				}
			}

            List<Integer> list = new ArrayList<>();
            int curr = maxValue;
            int i = n - 1;
            int j = maxAt;

            while (i >= 0 && curr > 0)
            {
                if (i > 0)
                {
					if (j - items[i].time >= 0 && dp[i - 1][j - items[i].time] + items[i].val == curr)
                    {
                        list.add(i);
                        curr -= items[i].val;
                        j -= items[i].time;
                        i--;
                    }
                    else
                        i--;
                }
                else
                {
                    if (j - items[i].time >= 0 && items[i].val == curr)
                    {
                        list.add(i);
                        curr = 0;
                    }

                    i--;
                }
            }

            Collections.reverse(list);
            out.println(maxValue);
            out.println(list.size());

            for (int x : list)
                out.print(items[x].ind + 1 + " ");
        }

        static class Item
        {
            int ind, time, expiry, val;

            Item(int ind, int time, int expiry, int val)
            {
                this.ind = ind;
                this.time = time;
                this.expiry = expiry;
                this.val = val;
            }

            @Override
            public String toString()
            {
                return "time : " + time + ", exp : " + expiry + ", val : " + val;
            }

        }

        Solver(InputReader in, PrintWriter out)
        {
            this.in = in;
            this.out = out;
        }

        @Override
        public void run()
        {
            try
            {
                solve();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    private static class InputReader
    {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        int read()
        {
            if (numChars == -1)
                throw new InputMismatchException();

            if (curChar >= numChars)
            {
                curChar = 0;
                try
                {
                    numChars = stream.read(buf);
                }
                catch (IOException e)
                {
                    throw new InputMismatchException();
                }
                if (numChars <= 0)
                    return -1;
            }

            return buf[curChar++];
        }

        int nextInt()
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

        boolean isSpaceChar(int c)
        {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        void close()
        {
            try
            {
                stream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        InputReader(InputStream stream)
        {
            this.stream = stream;
        }

    }

    private E436Div2(InputStream inputStream, OutputStream outputStream)
    {
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Thread thread = new Thread(null, new Solver(in, out), "Solver", 1 << 29);

        try
        {
            thread.start();
            thread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        in.close();
        out.flush();
        out.close();
    }

}
