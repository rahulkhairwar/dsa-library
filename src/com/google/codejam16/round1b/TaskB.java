package com.google.codejam16.round1b;

import java.io.*;
import java.util.Arrays;

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
							+ "Programming/src/com/google/codejam16/round1b/inputB.txt"));

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
		int t;
		String c, j;
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
			try
			{
				t = Integer.parseInt(in.readLine());

				for (int i = 0; i < t; i++)
				{
					String[] tokens = in.readLine().split(" ");

					c = tokens[0];
					j = tokens[1];

					out.println("Case #" + (i + 1) + ": " + find(new StringBuilder(c), new StringBuilder(j)));
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		private String find(StringBuilder c, StringBuilder j)
		{
			int cLen, jLen;

			cLen = c.length();
			jLen = j.length();

			if (cLen > jLen)
			{
				int diff = cLen - jLen;
				int temp = diff;
				int i = 0;

				while (true)
				{
					if (temp == 0)
						break;

					i++;

					if (c.charAt(i - 1) != '?')
						continue;

					c.setCharAt(i - 1, '0');
					temp--;
				}


			}
			else if (cLen < jLen)
			{
				int diff = jLen - cLen;
				int temp = diff;
				int i = 0;

				while (true)
				{
					if (temp == 0)
						break;

					i++;

					if (j.charAt(i - 1) != '?')
						continue;

					j.setCharAt(i - 1, '0');
					temp--;
				}
			}
			else
			{
				boolean greaterFound = false;
				boolean cGreater = false;

				for (int i = 0; i < cLen; i++)
				{
					if (!greaterFound && c.charAt(i) == '?' && j.charAt(i) == '?')
					{
						c.setCharAt(i, '0');
						j.setCharAt(i, '0');

						continue;
					}

					if (!greaterFound && c.charAt(i) != '?' && j.charAt(i) != '?' && c.charAt(i) > j.charAt(i))
					{
						cGreater = true;
						greaterFound = true;
					}

					if (greaterFound)
					{
						if (cGreater)
						{
							if (c.charAt(i) == '?')
								c.setCharAt(i, '0');

							if (j.charAt(i) == '?')
								j.setCharAt(i, '9');
						}
						else
						{
							if (j.charAt(i) == '?')
								j.setCharAt(i, '0');

							if (c.charAt(i) == '?')
								c.setCharAt(i, '9');
						}
					}
				}

				System.out.println("cGreater : " + cGreater);
				System.out.println("c : " + c.toString() + ", j : " + j.toString());
			}

			return null;
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
