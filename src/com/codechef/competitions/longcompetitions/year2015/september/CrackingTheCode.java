package com.codechef.competitions.longcompetitions.year2015.september;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;

class CrackingTheCode
{
	static double i, k, s, aI, bI;
	static double powerOf2, powerOf2InQ;
	static double a0, b0, a1, b1, aK, bK, root2, root3;
	static InputReader reader;

	public static void main(String[] args)
	{
		CrackingTheCode crack = new CrackingTheCode();

		reader = crack.new InputReader(System.in);
		
		System.out.println(BigDecimal.valueOf(5).divide(BigDecimal.valueOf(3),
				4, RoundingMode.HALF_EVEN));
		
		getAttributes2();

		reader.close();
	}

	static void getAttributes()
	{
		i = reader.nextInt();
		k = reader.nextInt();
		s = reader.nextLong();

		aI = reader.nextInt();
		bI = reader.nextInt();

		root2 = Math.sqrt(2);
		root3 = Math.sqrt(3);

		// i is even, so aI will be in terms of a0
		if (i % 2 == 0)
		{
			powerOf2 = 2 * i;

			// aI = 2^powerOf2 * a0
			// => a0 = aI / 2^powerOf2

			double pow2 = Math.pow(2, powerOf2);
			// double pow2 = powerOf2(1, (int) powerOf2);

			a0 = aI / pow2;
			b0 = bI / pow2;

			if (k % 2 == 0)
			{
				powerOf2InQ = 2 * k - s;

				System.out.printf("%f\n", Math.pow(2, powerOf2InQ) * (a0 + b0));
				System.out.printf("%f",
						powerOf2(aI + bI, (int) (powerOf2InQ - pow2 + 1)));
			}
			else
			{
				powerOf2InQ = 2 * (k - 1) - s;

				double root6;

				root6 = root2 * root3;
				// pow2 = Math.pow(2, 10);

				a1 = (root2 * (aI + bI) - root6 * (aI - bI));
				b1 = (root2 * (aI - bI) + root6 * (aI + bI));

				System.out.printf("%f\n", Math.pow(2, powerOf2InQ) * (a1 + b1));
				System.out.printf("%f",
						powerOf2(a1 + b1, (int) (powerOf2InQ - pow2 + 1)));
			}
		}
		else
		// i is odd, so aI will be in terms of a1
		{
			powerOf2 = 2 * (i - 1);

			// aI = 2^powerOf2 * a1
			// => a1 = aI / 2^powerOf2

			double pow2 = Math.pow(2, powerOf2);
			// double pow2 = powerOf2(1, (int) powerOf2);

			a1 = aI / pow2;
			b1 = bI / pow2;

			if (k % 2 == 1)
			{
				powerOf2InQ = 2 * (k - 1) - s;

				System.out.printf("%f\n", Math.pow(2, powerOf2InQ) * (a1 + b1));
				System.out.printf("%f",
						powerOf2(aI + bI, (int) (powerOf2InQ - pow2 + 1)));

				// System.out.println("\ni is odd, k is odd! powerOf2 : " +
				// powerOf2(a1 + b1, (int) powerOf2InQ));
			}
			else
			{
				powerOf2InQ = 2 * k - s;

				// pow2 = Math.pow(2, 10);

				/*
				 * a0 = ((2 + root3) * b1 - a1) * pow2 / (4 * root2); b0 = ((1 +
				 * root3) * a1 - (1 - root3)) * b1 * pow2 / (8 * root2);
				 */

				a0 = ((2 + root3) * bI - aI) / (4 * root2);
				b0 = ((1 + root3) * aI - (1 - root3)) * bI / (8 * root2);

				System.out.printf("%f\n", Math.pow(2, powerOf2InQ) * (a0 + b0));
				System.out.printf("%f",
						powerOf2(a0 + b0, (int) (powerOf2InQ - pow2 + 1)));
			}
		}
	}

	static void getAttributes2()
	{
		i = reader.nextInt();
		k = reader.nextInt();
		s = reader.nextLong();

		aI = reader.nextInt();
		bI = reader.nextInt();

		root2 = Math.sqrt(2);
		root3 = Math.sqrt(3);

		BigDecimal i2, ai2, bi2, r2, r3;
		double powerOf2;

		i2 = BigDecimal.valueOf(i);
/*		k2 = BigDecimal.valueOf(k);
		s2 = BigDecimal.valueOf(s);*/

		ai2 = BigDecimal.valueOf(aI);
		bi2 = BigDecimal.valueOf(bI);

		r2 = BigDecimal.valueOf(root2);
		r3 = BigDecimal.valueOf(root3);

		if (i % 2 == 0)
		{
			powerOf2 = BigDecimal.valueOf(2).multiply(i2).intValue();

			BigDecimal pow2 = power(BigDecimal.valueOf(2), (int) powerOf2);

			BigDecimal a02, b02;
			double powerOf2InQ;

			a02 = ai2.divide(pow2);
			b02 = bi2.divide(pow2);

			if (k % 2 == 0)
			{
				powerOf2InQ = 2 * k - s;

				// correct
				System.out.println(power(a02.add(b02), (int) powerOf2InQ)
						.toString());
			}
			else
			{
				powerOf2InQ = 2 * (k - 1) - s;

				double root6 = root2 * root3;

				BigDecimal a12, b12;

				a12 = BigDecimal
						.valueOf(root2)
						.multiply(ai2.add(bi2))
						.subtract(
								BigDecimal.valueOf(root6).multiply(
										ai2.subtract(bi2)));
				b12 = BigDecimal.valueOf(root2).multiply(ai2.subtract(bi2))
						.add(BigDecimal.valueOf(root6).multiply(ai2.add(bi2)));
				
				System.out.println(power(a12.add(b12),
						(int) (powerOf2InQ - pow2.intValue() + 1)).divide(
						BigDecimal.ONE, 4).toString());
			}
		}
		else
		{
			powerOf2 = 2 * (i - 1);
			
			BigDecimal pow2 = power(BigDecimal.valueOf(2), (int) powerOf2);
			BigDecimal a12, b12, a02, b02;
			
			a12 = ai2.divide(pow2);
			b12 = bi2.divide(pow2);
			
			if (k % 2 == 1)
			{
				powerOf2InQ = 2 * (k - 1) - s;
				
				System.out.println(power(ai2.add(bi2),
						(int) (powerOf2InQ - pow2.intValue() + 1)).toString());
			}
			else
			{
				powerOf2InQ = 2 * k - s;
				
				a02 = BigDecimal.valueOf(2).add(r3).multiply(bi2).subtract(ai2)
						.divide(BigDecimal.valueOf(4).multiply(r2), 4, RoundingMode.HALF_EVEN);
				b02 = BigDecimal.ONE.add(r3).multiply(ai2)
						.subtract(BigDecimal.ONE.subtract(r3)).multiply(bi2)
						.divide(BigDecimal.valueOf(8).multiply(r2));
			
				System.out.println(power(a02.add(b02), (int) (powerOf2InQ
						- pow2.intValue() + 1)).toString());
			}
		}
	}

	static BigDecimal power(BigDecimal number, int power)
	{
		if (power == 0)
			return BigDecimal.ONE;

		if (power == 1)
			return number;

		if (power % 2 == 0)
			return power(number.multiply(number), power / 2);
		else
			return power(number.multiply(number), power / 2).multiply(number);
	}

	static double powerOf2(double number, int power)
	{
		if (power <= 64)
			return number * Math.pow(2, power);

		double answer = number;

		int i = 0;

		for (; i < power; i += 64)
			answer *= Math.pow(2, 64);

		answer *= Math.pow(2, power - i);

		return answer;
	}

	class InputReader
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

		public float nextFloat() // problematic
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

		public double nextDouble() // not completely accurate
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

		public void close()
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

	}

}

/*

4 5 20
16 16

4 6 20
16 16

5 5 20
16 16

5 6 20
16 16

 */
