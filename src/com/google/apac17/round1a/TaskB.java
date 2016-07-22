package com.google.apac17.round1a;

import java.io.*;

/**
 * Created by rahulkhairwar on 16/04/16.
 */
public class TaskB
{
	public static void main(String[] args)
	{
		BufferedReader in;

		try
		{
			in = new BufferedReader(new FileReader(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/google/apac17/round1a/inputB.txt"));

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
		int t, r, c, mat[][];
		BufferedReader in;
		OutputWriter out;

		public Solver(BufferedReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override public void run()
		{
			try
			{
				t = Integer.parseInt(in.readLine());

				for (int test = 0; test < t; test++)
				{
					String[] tokens = in.readLine().split(" ");

					r = Integer.parseInt(tokens[0]);
					c = Integer.parseInt(tokens[1]);

					mat = new int[r][c];
					long initial = 0;

					for (int j = 0; j < r; j++)
					{
						tokens = in.readLine().split(" ");

						for (int k = 0; k < c; k++)
						{
							mat[j][k] = Integer.parseInt(tokens[k]);
							initial += mat[j][k];
						}
					}

					for (int i = 0; i <= r / 2; i++)
					{
						// up
						for (int j = i; j < c - i; j++)
						{
							check(i, j);
						}
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		void check(int i, int j)
		{
			int min = 0;

//			if (i > 0)
//				min = Math.min()
		}

	}

	static class OutputWriter
	{
		private PrintWriter writer;

		public OutputWriter(OutputStream stream)
		{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
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
