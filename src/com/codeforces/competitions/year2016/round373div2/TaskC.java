package com.codeforces.competitions.year2016.round373div2;

import java.io.*;
import java.util.*;

public final class TaskC
{
    public static void main(String[] args)
    {
        InputReader in = new InputReader(System.in);
        OutputWriter out = new OutputWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
        in.close();
        out.flush();
        out.close();
    }

    static class Solver
    {
        InputReader in;
        OutputWriter out;

		void solve()
		{
			int n = in.nextInt();
			int t = in.nextInt();
			char[] s = new char[n + 5];
			String str = in.next();

			Arrays.fill(s, '*');

			for (int i = 0; i < n; i++)
				s[i] = str.charAt(i);

			int dot = 0;

			while (dot < n && s[dot] != '.')
				dot++;

			if (dot == n)
			{
				String ans = new String(s);
				int ind = ans.indexOf('*');

				ans = ans.substring(0, ind);
				out.println(ans);

				return;
			}

			if (dot == 0)
			{
				for (int i = n; i > 0; i--)
					s[i] = s[i - 1];

				s[n + 1] = '*';
				s[0] = '0';
				dot = 1;
			}

			int i = dot + 1;
			boolean flag = false;

			while (s[i] != '*' && s[i] < '5')
				i++;

			if (s[i] != '*')
			{
				s[i] = '*';
				i--;

				while (i-- > 0)
				{
					if (s[i] == '.')
					{
						i--;
						flag = true;

						continue;
					}

					if (flag && s[i] == '9')
					{
						s[i] = '0';
						i--;

						while (i >= 0 && s[i] == '9')
						{
							s[i] = '0';
							i--;
						}

						if (i < 0)
							out.print('1');
						else
							s[i]++;
					}
					else
					{
						s[i]++;
						i--;
					}

					t--;

					if (t == 0)
						break;

					if (flag)
						break;

					if (s[i + 1] < '5')
						break;
				}
			}

			if (flag)
			{
				s[dot] = '*';

				String ans = new String(s);
				int ind = ans.indexOf('*');

				ans = ans.substring(0, ind);
				out.print(ans);
			}
			else
			{
				s[i + 2] = '*';

				if (s[0] == '0')
					for (int j = 0; s[j] != '*'; j++)
						out.print(s[j]);
				else
				{
					String ans = new String(s);
					int ind = ans.indexOf('*');

					ans = ans.substring(0, ind);
					out.print(ans);
				}
			}
		}

		void solve2()
		{
			int n = in.nextInt();
			int t = in.nextInt();
			StringBuilder s = new StringBuilder(in.next());
			List<Integer> list = new ArrayList<>();
			int l = 0;

			for (; l < n; l++)
			{
				if (s.charAt(l) != '.' && s.charAt(l) >= '5')
				{
					while (l < n && s.charAt(l) >= 5)
						l++;

					list.add(l - 1);
				}
			}

			System.out.println("list : " + list);
			Collections.reverse(list);

//			for (int i = Math.min(t, list.size()) - 1; i >= 0; i--)
			for (int i = 0; i < Math.min(t, list.size()); i++)
			{
				int r = list.get(i);

				System.out.println("** r : " + r + ", old char : " + s.charAt(r) + ", list : " + list);
				s.setCharAt(r, (char) (s.charAt(r) + 1));
				System.out.println("r : " + r + ", new char : " + s.charAt(r));

				if (s.charAt(r) >= '5')
				{
					s.setCharAt(r, '0');

					if (r > 0)
					{
						s.setCharAt(r - 1, (char) (s.charAt(r - 1) + 1));

						if (s.charAt(r - 1) == '9' + 1)
						{
							s.setCharAt(r - 1, '9');
							list.add(r - 1);
						}
					}

					r--;

					if (s.charAt(r) == '.')
						r--;

					System.out.println("&&r : " + r + ", char[r] : " + s.charAt(r));

					while (r >= 0)
					{
						System.out.println("r : " + r + ", char[r] : " + s.charAt(r));
						s.setCharAt(r, (char) (s.charAt(r) + 1));

						if (s.charAt(r) != '0')
							break;

						r--;
					}
				}
			}

			if (s.charAt(0) == '0')
				s = new StringBuilder("1" + s.toString());

			n = s.length();

			int r = n - 1;

			while (r >= 0 && s.charAt(r) == '0')
				r--;

			if (s.charAt(r) == '.')
				r--;

			System.out.println(" s : " + s);

			out.println(s.substring(0, r + 1));
		}

        public Solver(InputReader in, OutputWriter out)
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

    static class OutputWriter
    {
        private PrintWriter writer;

        public OutputWriter(OutputStream stream)
        {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    stream)));
        }

        public OutputWriter(Writer writer)
        {
            this.writer = new PrintWriter(writer);
        }

        public void println(int x)
        {
            writer.println(x);
        }

        public void print(int x)
        {
            writer.print(x);
        }

		public void println(char x)
		{
			writer.println(x);
		}

		public void print(char x)
		{
			writer.print(x);
		}

        public void println(int array[], int size)
        {
            for (int i = 0; i < size; i++)
                println(array[i]);
        }

        public void print(int array[], int size)
        {
            for (int i = 0; i < size; i++)
                print(array[i] + " ");
        }

        public void println(long x)
        {
            writer.println(x);
        }

        public void print(long x)
        {
            writer.print(x);
        }

        public void println(long array[], int size)
        {
            for (int i = 0; i < size; i++)
                println(array[i]);
        }

        public void print(long array[], int size)
        {
            for (int i = 0; i < size; i++)
                print(array[i]);
        }

        public void println(float num)
        {
            writer.println(num);
        }

        public void print(float num)
        {
            writer.print(num);
        }

        public void println(double num)
        {
            writer.println(num);
        }

        public void print(double num)
        {
            writer.print(num);
        }

        public void println(String s)
        {
            writer.println(s);
        }

        public void print(String s)
        {
            writer.print(s);
        }

        public void println()
        {
            writer.println();
        }

        public void printSpace()
        {
            writer.print(" ");
        }

		public void printf(String format, Object args)
		{
			writer.printf(format, args);
		}

        public void flush()
        {
            writer.flush();
        }

        public void close()
        {
            writer.close();
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

	}

}
