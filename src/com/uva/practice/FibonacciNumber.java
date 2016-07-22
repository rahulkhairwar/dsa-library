package com.uva.practice;

import java.io.*;
import java.util.Scanner;

/**
 * Question on Matrix Exponentiation for nth Fibonacci Number.
 * <br /><br />
 * <a href="https://uva.onlinejudge.org/index.php?option=onlinejudge&page=show_problem&problem=1170">Question Link</a>
 */
class FibonacciNumber
{
	static final int size = 2;
	static int n;
	static long mod;
	static Scanner in;
	static OutputWriter out;

	public static void main(String[] args)
	{
		in = new Scanner(System.in);
		out = new OutputWriter(System.out);

		solve();

		out.flush();

		in.close();
		out.close();
	}

	static void solve()
	{
		while (in.hasNextInt())
		{
			n = in.nextInt();
			mod = CMath.power(2, in.nextInt());

			out.println(findFibonacci(n));
			out.flush();
		}
	}

	static long findFibonacci(int number)
	{
		if (n == 0)
			return 0;
		else if (n == 1)
			return 1;
		else if (n == 2)
			return 1;

		long[] f1 = {1, 1};
		long[][] t = {{0, 1}, {1, 1}};

		t = power(t, number - 1);

		long answer = 0;

		for (int i = 0; i < size; i++)
		{
			answer += CMath.mod(t[0][i] * f1[i], mod);
			answer = CMath.mod(answer, mod);
		}

		return answer;
	}

	static long[][] multiply(long[][] a, long[][] b)
	{
		long[][] product = new long[size][size];

		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				long sum = 0;

				for (int k = 0; k < size; k++)
				{
					sum += CMath.mod(a[i][k] * b[k][j], mod);
					sum = CMath.mod(sum, mod);
				}

				product[i][j] = sum;
			}
		}

		return product;
	}

	static long[][] power(long[][] matrix, long power)
	{
		if (power == 1)
			return matrix;

		long[][] square = multiply(matrix, matrix);

		if (power % 2 == 0)
			return power(square, power / 2);
		else
			return multiply(matrix, power(square, power / 2));
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
		static long power(long number, int power)
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

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}
}
