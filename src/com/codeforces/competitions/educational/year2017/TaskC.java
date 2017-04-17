package com.codeforces.competitions.educational.year2017;

import java.io.*;
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
    	int len;
        char[] s, min;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			s = in.next().toCharArray();
			len = s.length;
			min = new char[len];
			min[len - 1] = s[len - 1];

			for (int i = len - 2; i >= 0; i--)
				min[i] = (char) Math.min(min[i + 1], s[i]);

			Stack<Character> t = new Stack<>();
			StringBuilder ans = new StringBuilder("");

			for (int i = 0; i < len; i++)
			{
				while (t.size() > 0 && t.peek() <= min[i])
					ans.append(t.pop());

				if (s[i] == min[i])
					ans.append(s[i]);
				else
					t.add(s[i]);
			}

			while (t.size() > 0)
				ans.append(t.pop());

			out.println(ans);
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
