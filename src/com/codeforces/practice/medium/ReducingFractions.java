package com.codeforces.practice.medium;

import java.io.*;
import java.util.*;

public final class ReducingFractions
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
        int n, m;
        int[] sp;
        Map<Integer, Integer> one, two;
        InputReader in;
        OutputWriter out;

		void solve()
		{
			n = in.nextInt();
			m = in.nextInt();

			compute();

			one = new HashMap<>();
			two = new HashMap<>();

			for (int i = 0; i < n; i++)
				add(in.nextInt(), one);

			for (int i = 0; i < m; i++)
				add(in.nextInt(), two);

			Set<Integer> removeA, removeB;

			removeA = new HashSet<>();
			removeB = new HashSet<>();

			for (Map.Entry<Integer, Integer> entry : one.entrySet())
			{
				int key = entry.getKey();
				int cntA = entry.getValue();
				Integer cntB = two.get(key);

				if (cntB == null)
					continue;

				if (cntA < cntB)
				{
					two.put(key, cntB - cntA);
					removeA.add(key);
				}
				else if (cntA > cntB)
				{
					one.put(key, cntA - cntB);
					removeB.add(key);
				}
				else
				{
					removeA.add(key);
					removeB.add(key);
				}
			}

			for (int x : removeA)
				one.remove(x);

			for (int x : removeB)
				two.remove(x);

			List<Integer> tempA, tempB, ansA, ansB;

			tempA = new ArrayList<>();
			tempB = new ArrayList<>();
			ansA = new ArrayList<>();
			ansB = new ArrayList<>();

			for (Map.Entry<Integer, Integer> entry : one.entrySet())
			{
				int key, val;

				key = entry.getKey();
				val = entry.getValue();

				while (val-- > 0)
					tempA.add(key);
			}

			for (Map.Entry<Integer, Integer> entry : two.entrySet())
			{
				int key, val;

				key = entry.getKey();
				val = entry.getValue();

				while (val-- > 0)
					tempB.add(key);
			}

//			System.out.println("tempA : " + tempA + ", tempB : " + tempB);

			if (tempA.size() == 0)
				tempA.add(1);

			if (tempB.size() == 0)
				tempB.add(1);

			int sz = tempA.size();
			long prod = 1;

			for (int i = 0; i < sz; i++)
			{
				if (prod * tempA.get(i) > 1e7)
				{
					ansA.add((int) prod);
					prod = 1;
				}

				prod *= tempA.get(i);
			}

			ansA.add((int) prod);
			sz = tempB.size();
			prod = 1;

			for (int i = 0; i < sz; i++)
			{
				if (prod * tempB.get(i) > 1e7)
				{
					ansB.add((int) prod);
					prod = 1;
				}

				prod *= tempB.get(i);
			}

			ansB.add((int) prod);

			if (ansA.size() == 0)
				ansA.add(1);

			if (ansB.size() == 0)
				ansB.add(1);

			out.println(ansA.size() + " " + ansB.size());

			for (int x : ansA)
				out.print(x + " ");

			out.println();

			for (int x : ansB)
				out.print(x + " ");
		}

		void add(int x, Map<Integer, Integer> map)
		{
			while (x > 1)
			{
				int cnt = 0;
				int div = sp[x];

				while (x % div == 0)
				{
					cnt++;
					x /= div;
				}

				Integer temp = map.get(div);

				if (temp == null)
					map.put(div, cnt);
				else
					map.put(div, temp + cnt);
			}
		}

		void compute()
		{
			sp = new int[(int) 1e7 + 5];

			int curr = 2;

			while (curr <= 1e7)
			{
				sp[curr] = 2;
				curr += 2;
			}

			for (int i = 3; i <= 1e7; i += 2)
			{
				if (sp[i] != 0)
					continue;

				curr = i;

				while (curr <= 1e7)
				{
					if (sp[curr] == 0)
						sp[curr] = i;

					curr += i;
				}
			}
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

}
