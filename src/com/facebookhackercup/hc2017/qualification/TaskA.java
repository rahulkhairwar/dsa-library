package com.facebookhackercup.hc2017.qualification;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
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
        int t, p, x, y;
        InputReader in;
        PrintWriter out;

		void solve()
		{
			t = in.nextInt();

			for (int test = 1; test <= t; test++)
			{
				p = in.nextInt();
				x = in.nextInt();
				y = in.nextInt();

				double dist = Point2D.distance(50, 50, x, y);

				if (dist > 50)
				{
					out.println("Case #" + test + ": white");

					continue;
				}

				Point a, b;

				a = new Point(0, 50);
				b = new Point(x - 50, y - 50);

				long dot = dotProduct(a, b);
				double magA, magB;

				magA = Math.sqrt(dotProduct(a, a));
				magB = Math.sqrt(dotProduct(b, b));

				double cos = Math.acos(dot / magA / magB);
				double deg = Math.toDegrees(cos);

				if (x < 50)
					deg = 360.0 - deg;

				double sec = p * 3.6;

				out.print("Case #" + test + ": ");

				if (deg <= sec)
					out.println("black");
				else
					out.println("white");
			}
		}

		long dotProduct(Point a, Point b)
		{
			return a.x * b.x + a.y * b.y;
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

1
100 50 50

*/
