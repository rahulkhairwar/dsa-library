package com.codeforces.competitions.year2017.round392div2;

import java.io.*;
import java.util.*;

public final class TaskB
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
    	char[] s;
        int n;
        InputReader in;
        PrintWriter out;

        void solve()
		{
			s = in.next().toCharArray();
			n = s.length;

			int r, g, b, y;

			r = g = b = y = 0;

			for (int i = 0; i < n; i++)
			{
				if (s[i] == 'R')
					r = i;
				else if (s[i] == 'G')
					g = i;
				else if (s[i] == 'B')
					b = i;
				else if (s[i] == 'Y')
					y = i;
			}

			int rr, bb, yy, gg;
			int ar, ab, ay, ag;

			rr = bb = yy = gg = ar = ab = ay = ag = 0;

			for (int i = r; i < n; i += 4)
			{
				if (s[i] != 'R')
					ar++;
			}

			for (int i = r; i >= 0; i -= 4)
			{
				if (s[i] != 'R')
					ar++;
			}

			for (int i = b; i < n; i += 4)
			{
				if (s[i] != 'B')
					ab++;
			}

			for (int i = b; i >= 0; i -= 4)
			{
				if (s[i] != 'B')
					ab++;
			}
			for (int i = y; i < n; i += 4)
			{
				if (s[i] != 'Y')
					ay++;
			}

			for (int i = y; i >= 0; i -= 4)
			{
				if (s[i] != 'Y')
					ay++;
			}
			for (int i = g; i < n; i += 4)
			{
				if (s[i] != 'G')
					ag++;
			}

			for (int i = g; i >= 0; i -= 4)
			{
				if (s[i] != 'G')
					ag++;
			}

			out.println(ar + " " + ab + " " + ay + " " + ag);
		}

        void solve3()
		{
			s = in.next().toCharArray();
			n = s.length;

			int r, g, b, y;

			r = g = b = y = 0;

			for (int i = 0; i < n; i++)
			{
				if (s[i] == 'R')
					r++;
				else if (s[i] == 'G')
					g++;
				else if (s[i] == 'B')
					b++;
				else if (s[i] == 'Y')
					y++;
			}

			int req = n / 4;
			int ansR, ansG, ansB, ansY;
			int mod = n % 4;

			ansR = ansG = ansB = ansY = 0;

			for (int i = 0; i < n; i++)
			{
				if (s[i] == '!')
				{
					boolean rr = mark(s[i], 'R');
					boolean gg = mark(s[i], 'G');
					boolean bb = mark(s[i], 'B');
					boolean yy = mark(s[i], 'Y');

					if (i + 1 < n)
					{
						rr |= mark(s[i + 1], 'R');
						gg |= mark(s[i + 1], 'G');
						bb |= mark(s[i + 1], 'B');
						yy |= mark(s[i + 1], 'Y');
					}

					if (i + 2 < n)
					{
						rr |= mark(s[i + 2], 'R');
						gg |= mark(s[i + 2], 'G');
						bb |= mark(s[i + 2], 'B');
						yy |= mark(s[i + 2], 'Y');
					}

					if (i + 3 < n)
					{
						rr |= mark(s[i + 3], 'R');
						gg |= mark(s[i + 3], 'G');
						bb |= mark(s[i + 3], 'B');
						yy |= mark(s[i + 3], 'Y');
					}

					if (!rr)
						ansR++;
					else if (!gg)
						ansG++;
					else if (!bb)
						ansB++;
					else if (!yy)
						ansY++;
				}
			}

			//			ansY += req - y;
			ansR += Math.max(0, req - r);
			ansB += Math.max(0, req - b);
			ansY += Math.max(0, req - y);
			ansG += Math.max(0, req - g);
//			System.out.println("r : " + r + ", b : " + b + ", y : " + y + ", g : " + g);


			out.println(ansR + " " + ansB + " " + ansY + " " + ansG);
		}

		void solve2()
		{
			s = in.next().toCharArray();
			n = s.length;

			int r, g, b, y;

			r = g = b = y = 0;

			for (int i = 0; i < n; i++)
			{
				if (s[i] == 'R')
					r++;
				else if (s[i] == 'G')
					g++;
				else if (s[i] == 'B')
					b++;
				else if (s[i] == 'Y')
					y++;
			}

//			System.out.println("r : " + r + ", b : " + b + ", y : " + y + ", g : " + g);

			int req = n / 4;
			int ansR, ansG, ansB, ansY;
			int mod = n % 4;

			ansR = ansG = ansB = ansY = 0;

			if (mod > 0)
			{
				if (n < 4)
				{
					if (r == 0)
						ansR++;
					else if (g == 0)
						ansG++;
					else if (b == 0)
						ansB++;
					else if (y == 0)
						ansY++;
				}

				if (mod == 1)
				{
					if (s[n - 1] != '!')
						;
					else
					{
						if (n < 4)
						{
							if (r == 0)
								ansR++;
							else if (g == 0)
								ansG++;
							else if (b == 0)
								ansB++;
							else if (y == 0)
								ansY++;
						}
						else
						{
							boolean rr = mark(s[n - 2], 'R');
							boolean gg = mark(s[n - 2], 'G');
							boolean bb = mark(s[n - 2], 'B');
							boolean yy = mark(s[n - 2], 'Y');

							rr |= mark(s[n - 3], 'R');
							gg |= mark(s[n - 3], 'G');
							bb |= mark(s[n - 3], 'B');
							yy |= mark(s[n - 3], 'Y');

							rr |= mark(s[n - 4], 'R');
							gg |= mark(s[n - 4], 'G');
							bb |= mark(s[n - 4], 'B');
							yy |= mark(s[n - 4], 'Y');

							if (!rr)
								ansR++;
							else if (!gg)
								ansG++;
							else if (!bb)
								ansB++;
							else if (!yy)
								ansY++;
						}
					}
				}
				else if (mod == 2)
				{
					boolean rr = mark(s[n - 1], 'R');
					boolean gg = mark(s[n - 1], 'G');
					boolean bb = mark(s[n - 1], 'B');
					boolean yy = mark(s[n - 1], 'Y');

					rr |= mark(s[n - 2], 'R');
					gg |= mark(s[n - 2], 'G');
					bb |= mark(s[n - 2], 'B');
					yy |= mark(s[n - 2], 'Y');

					rr |= mark(s[n - 3], 'R');
					gg |= mark(s[n - 3], 'G');
					bb |= mark(s[n - 3], 'B');
					yy |= mark(s[n - 3], 'Y');

					rr |= mark(s[n - 4], 'R');
					gg |= mark(s[n - 4], 'G');
					bb |= mark(s[n - 4], 'B');
					yy |= mark(s[n - 4], 'Y');

					if (!rr)
						ansR++;
					else if (!gg)
						ansG++;
					else if (!bb)
						ansB++;
					else if (!yy)
						ansY++;
				}
				else if (mod == 3)
				{
					boolean rr = mark(s[n - 1], 'R');
					boolean gg = mark(s[n - 1], 'G');
					boolean bb = mark(s[n - 1], 'B');
					boolean yy = mark(s[n - 1], 'Y');

					rr |= mark(s[n - 2], 'R');
					gg |= mark(s[n - 2], 'G');
					bb |= mark(s[n - 2], 'B');
					yy |= mark(s[n - 2], 'Y');

					rr |= mark(s[n - 3], 'R');
					gg |= mark(s[n - 3], 'G');
					bb |= mark(s[n - 3], 'B');
					yy |= mark(s[n - 3], 'Y');

					rr |= mark(s[n - 4], 'R');
					gg |= mark(s[n - 4], 'G');
					bb |= mark(s[n - 4], 'B');
					yy |= mark(s[n - 4], 'Y');

					if (!rr)
						ansR++;
					else if (!gg)
						ansG++;
					else if (!bb)
						ansB++;
					else if (!yy)
						ansY++;
				}
			}

//			System.out.println("ansr : " + ansR + ", ansg  : " + ansG + ", ansb : " + ansB + ", ansy : " + ansY);
//			System.out.println("req : " + req + ", r : " + r);
//			ansR += req - r;
//			ansG += req - g;
//			ansB += req - b;
//			ansY += req - y;
			ansR += Math.max(0, req - r);
			ansB += Math.max(0, req - b);
			ansY += Math.max(0, req - y);
			ansG += Math.max(0, req - g);
			System.out.println("r : " + r + ", b : " + b + ", y : " + y + ", g : " + g);


			out.println(ansR + " " + ansB + " " + ansY + " " + ansG);
		}

		boolean mark(char x, char y)
		{
			return x == y;
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

        public float nextFloat()
        {
            float result, div;
            byte c;

            result = 0;
            div = 1;
            c = (byte) read();

            while (c <= ' ')
                c = (byte) read();

            boolean isNegative = (c == '-');

            if (isNegative)
                c = (byte) read();

            do
            {
                result = result * 10 + c - '0';
            } while ((c = (byte) read()) >= '0' && c <= '9');

            if (c == '.')
                while ((c = (byte) read()) >= '0' && c <= '9')
                    result += (c - '0') / (div *= 10);

            if (isNegative)
                return -result;

            return result;
        }

        public double nextDouble()
        {
            double ret = 0, div = 1;
            byte c = (byte) read();

            while (c <= ' ')
                c = (byte) read();

            boolean neg = (c == '-');

            if (neg)
                c = (byte) read();

            do
            {
                ret = ret * 10 + c - '0';
            } while ((c = (byte) read()) >= '0' && c <= '9');

            if (c == '.')
                while ((c = (byte) read()) >= '0' && c <= '9')
                    ret += (c - '0') / (div *= 10);

            if (neg)
                return -ret;

            return ret;
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

	static class CMath
	{
		static long power(long number, long power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			if (power == 1)
				return number;

			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}

		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);
			else
				return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

		static long min(long... arr)
		{
			long min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static long max(long... arr)
		{
			long max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static int max(int... arr)
		{
			int max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

	}

}
