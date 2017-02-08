package com.coursera.algorithms_divide_and_conquer;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public final class KaratsubaMultiplication
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
        int n, arr[];
        InputReader in;
        PrintWriter out;

		void solve()
		{
//			n = in.nextInt();
			BigInteger a, b;

			a = new BigInteger("3141592653589793238462643383279502884197169399375105820974944592");
			b = new BigInteger("2718281828459045235360287471352662497757247093699959574966967627");

//			a.gcd();
//			a.multiply();
//			a.modInverse();
//			a.modPow()
			out.println(a.multiply(b));
		}

		StringBuilder multiply(char[] x, char[] y, int left, int right)
		{
			if (left == right)
				return new StringBuilder((x[left] - '0') * (y[left] - '0'));

			int len = right - left + 1;
			int mid = left + right >> 1;
			// x = .... | ....
			//		 a	    b
			// y = .... | ....
			// 		 c      d

			// i.e., mul(x, y, l, mid) = ac
			// and, mul(x, y, mid + 1, r) = bd

			StringBuilder ac, bd;

			ac = multiply(x, y, left, mid);
			bd = multiply(x, y, mid + 1, right);

			char[] aPlusB, cPlusD;
			int aPlusBLen, cPlusDLen, max, carry;

			aPlusBLen = len / 2;
			cPlusDLen = len - aPlusBLen;
			max = Math.max(aPlusBLen, cPlusDLen);
			carry = 0;
			aPlusB = new char[max + 1];
			cPlusD = new char[max + 1];
			Arrays.fill(aPlusB, '0');
			Arrays.fill(cPlusD, '0');

			for (int i = 0, j = mid; j >= left; i++, j--)
			{
				int xx = x[j] - '0' + y[j] - '0' + carry;

				if (xx >= 10)
				{
					carry = xx / 10;
					xx %= 10;
				}

				aPlusB[i] = (char) xx;
			}

			if (carry > 0)
				aPlusB[aPlusBLen] = (char) carry;

			carry = 0;

			for (int i = 0, j = right; j > mid; i++, j--)
			{
				int xx = x[j] - '0' + y[j] - '0' + carry;

				if (xx >= 10)
				{
					carry = xx / 10;
					xx %= 10;
				}

				cPlusD[i] = (char) xx;
			}

			if (carry > 0)
				cPlusD[cPlusDLen] = (char) carry;

			if (aPlusB[aPlusBLen] == '0' && cPlusD[cPlusDLen] == '0')
				max--;

			for (int i = 0, j = max - 1; i < max; i++, j--)
			{
				char temp = aPlusB[i];

				aPlusB[i] = aPlusB[j];
				aPlusB[j] = temp;
				temp = cPlusD[i];
				cPlusD[i] = cPlusD[j];
				cPlusD[j] = temp;
			}

			StringBuilder bCPlusAD = multiply(aPlusB, cPlusD, 0, max - 1);

			return null;
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
