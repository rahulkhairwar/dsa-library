package com.google.codejam16.qualificationround;

import java.io.*;
import java.math.BigInteger;
import java.util.Random;

/**
 * Created by rahulkhairwar on 09/04/16.
 */
public class TaskC
{
	public static void main(String[] args)
	{
		BufferedReader in;

		try
		{
			in = new BufferedReader(new FileReader(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/google/codejam16/qualificationround/inputB.txt"));

			OutputWriter out = new OutputWriter(System.out);
			Thread thread = new Thread(null, new Solver(in, out), "Solver", 1 << 28);

			thread.start();

			try
			{
				thread.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			out.flush();

			in.close();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	static class Solver implements Runnable
	{
		int t, n, j;
		String[] fiftyComposites = {"1000000000000001", "1000000000000011", "1000000000000101", "1000000000000111",
				"1000000000001001", "1000000000001011", "1000000000001101", "1000000000001111", "1000000000010001",
				"1000000000010011", "1000000000010101", "1000000000010111", "1000000000011001", "1000000000011011",
				"1000000000011101", "1000000000011111", "1000000000100001", "1000000000100011", "1000000000100101",
				"1000000000100111", "1000000000101001", "1000000000101011", "1000000000101101", "1000000000101111",
				"1000000000110001", "1000000000110011", "1000000000110101", "1000000000110111", "1000000000111001",
				"1000000000111011", "1000000000111101", "1000000000111111", "1000000001000001", "1000000001000011",
				"1000000001000101", "1000000001000111", "1000000001001001", "1000000001001011", "1000000001001101",
				"1000000001001111", "1000000001010001", "1000000001010011", "1000000001010101", "1000000001010111",
				"1000000001011001", "1000000001011011", "1000000001011101", "1000000001011111", "1000000001100001",
				"1000000001100011"};
		BigInteger[][] factors;
		BufferedReader in;
		OutputWriter out;

		public Solver(BufferedReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
		{
			factors = new BigInteger[50][11];

			for (int i = 0; i < 50; i++)
			{
				for (int k = 2; k <= 10; k++)
				{
					factors[i][k] = findFactor(convertToBase(fiftyComposites[i], BigInteger.valueOf(k)));
				}
			}

			System.out.println("***** factors : ");

			for (int i = 0; i < 50; i++)
			{
				System.out.println("$$$$$$$ i : " + i + ", comp. : " + fiftyComposites[i] + ", and it's factors : ");

				for (int j = 2; j <= 10; j++)
					System.out.println("base : " + j + ", factor : " + factors[i][j]);
			}

/*			try
			{
				t = Integer.parseInt(in.readLine());

				String[] tokens = in.readLine().split(" ");

				n = Integer.parseInt(tokens[0]);
				j = Integer.parseInt(tokens[1]);

				for (int i = 0; i < 100; i++)
				{
					System.out.println(returnBinary(i));
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}*/
		}

		public BigInteger findFactor(BigInteger num)
		{
			for (int i = 2; i < 10000000; i += 2)
			{
				BigInteger temp = BigInteger.valueOf(i);

				if (num.mod(temp).equals(BigInteger.ZERO))
					return temp;
			}

			return BigInteger.valueOf(-1);
		}

		public String returnBinary(int num, int requiredLength)
		{
			String temp, binary;

			temp = "";
			binary = "";

			while (num != 0)
			{
				temp += (num % 2);
				num >>= 1;
			}

			if (temp.length() < requiredLength)
			{
				int bal = requiredLength - temp.length();

				for (int i = 0; i < bal; i++)
					temp += "0";
			}

			int len = temp.length();

			for (int i = len - 1; i >= 0; i--)
				binary += temp.charAt(i);

			return binary;
		}

		public BigInteger convertToBase(String binary, BigInteger base)
		{
			BigInteger basePower, converted;

			basePower = BigInteger.ONE;
			converted = BigInteger.ZERO;

			for (int i = binary.length() - 1; i >= 0; i--)
			{
				converted = converted.add(BigInteger.valueOf(binary.charAt(i) - '0').multiply(basePower));
				basePower = basePower.multiply(base);
			}

//			System.out.println(binary + " converted to base " + base + " is : " + converted.toString());

			return converted;
		}

		public boolean isPrime(BigInteger n, int iteration)
		{
			if (n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE))
				return false;

			BigInteger two = BigInteger.valueOf(2);

			if (n.equals(two))
				return true;

			if (n.mod(two).equals(BigInteger.ZERO))
				return false;

//			long s = n - 1;
			BigInteger s = n.subtract(BigInteger.ONE);

//			while (s % 2 == 0)
			while (s.mod(two).equals(BigInteger.ZERO))
				s = s.divide(two);
//				s /= 2;

			Random rand = new Random();

			for (int i = 0; i < iteration; i++)
			{
/*				long r = Math.abs(rand.nextLong());
				long a = r % (n - 1) + 1, temp = s;
				long mod = modPow(a, temp, n);*/

				BigInteger r, a, temp, mod;

				r = BigInteger.valueOf(Math.abs(rand.nextLong()));
				a = r.mod(n.subtract(BigInteger.ONE)).add(BigInteger.ONE);
				temp = new BigInteger(s.toString());
				mod = modPow(a, temp, n);

				while (!temp.equals(n.subtract(BigInteger.ONE)) && !mod.equals(BigInteger.ONE) && !mod.equals(n
						.subtract(BigInteger.ONE)))
				{
					mod = mulMod(mod, mod, n);
					temp = temp.multiply(two);
				}

/*				while (temp != n - 1 && mod != 1 && mod != n - 1)
				{
					mod = mulMod(mod, mod, n);
					temp *= 2;
				}*/

/*				if (mod != n - 1 && temp % 2 == 0)
					return false;*/

				if (!mod.equals(n.subtract(BigInteger.ONE)) && temp.mod(two).equals(BigInteger.ZERO))
					return false;
			}
			return true;
		}

		public BigInteger modPow(BigInteger a, BigInteger b, BigInteger c)
		{
//			long res = 1;
			BigInteger res = BigInteger.ONE;

//			System.out.println("&&&& |b.intValue()|" + Math.abs(b.intValue()));
			int limit = Math.abs(b.intValue()) < 1e4 ? Math.abs(b.intValue()) : (int) 1e4;

			for (int i = 0; i < limit; i++)
			{
/*				res *= a;
				res %= c;*/

				res = res.multiply(a);
				res = res.mod(c);
			}

//			return res % c;
			return res.mod(c);
		}

		public BigInteger mulMod(BigInteger a, BigInteger b, BigInteger mod)
		{
			return a.multiply(b).mod(mod);
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

	}

}
