package com.hackerearth.competitions.inter_nit_contest;

import java.io.*;
import java.util.*;

public final class TaskB
{
    public static void main(String[] args)
    {
    	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(in, out);

		try
		{
			solver.solve();
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

        out.flush();
        out.close();
    }

    static class Solver
    {
        int n, q;
        char[] s;
        BufferedReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			String[] ss = in.readLine().split(" ");
			n = Integer.parseInt(ss[0]);
			q = Integer.parseInt(ss[1]);
			s = in.readLine().toCharArray();

			int[] cnt = new int[26];

			for (char x : s)
				cnt[x - 'A']++;

			while (q-- > 0)
			{
				String[] tok = in.readLine().split(" ");

				if (tok.length == 3)
				{
					int x = Integer.parseInt(tok[1]) - 1;
					char ch = tok[2].charAt(0);

					cnt[s[x] - 'A']--;
					cnt[ch - 'A']++;
					s[x] = ch;
				}
				else
				{
					int x = Integer.parseInt(tok[1]);
					int tot = 0;

					for (int i = 0; i < 26; i++)
					{
						tot += cnt[i];

						if (tot >= x)
						{
							out.println((char) (i + 'A'));

							break;
						}
					}
				}
			}
		}

        public Solver(BufferedReader in, PrintWriter out)
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

        public String nextLine()
        {
            int c = read();

            StringBuilder result = new StringBuilder();

            do
            {
                result.appendCodePoint(c);

                c = read();
            } while (!isNewLine(c));

            return result.toString();
        }

        public boolean isNewLine(int c)
        {
            return c == '\n';
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
