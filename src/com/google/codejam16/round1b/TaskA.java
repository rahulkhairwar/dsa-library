package com.google.codejam16.round1b;

import java.io.*;
import java.util.Arrays;

/**
 * Created by rahulkhairwar on 16/04/16.
 */
public class TaskA
{
	public static void main(String[] args)
	{
		BufferedReader in;

		try
		{
			in = new BufferedReader(new FileReader(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/google/codejam16/round1b/inputA.txt"));

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
		String s;
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
					s = in.readLine();
					out.println("Case #" + (i + 1) + ": " + find(s));
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		public String find(String num)
		{
			int len = num.length();
			int[] count = new int[26];

			for (int i = 0; i < len; i++)
			{
				count[num.charAt(i) - 'A']++;
			}

			int[] answer = new int[10];

			String[] digits = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"};

			// zero
			if (count['Z' - 'A'] > 0)
			{
				int temp = count['Z' - 'A'];

/*				if (num.equals("FRRNNIOZUEEO"))
					System.out.println("z count : " + temp);*/

				count['Z' - 'A'] -= temp;
				count['E' - 'A'] -= temp;
				count['R' - 'A'] -= temp;
				count['O' - 'A'] -= temp;
				answer[0] += temp;
			}

			// two
			if (count['W' - 'A'] > 0)
			{
				int temp = count['W' - 'A'];

				count['T' - 'A'] -= temp;
				count['W' - 'A'] -= temp;
				count['O' - 'A'] -= temp;
				answer[2] += temp;
			}

			// four
			if (count['U' - 'A'] > 0)
			{
				int temp = count['U' - 'A'];

				count['F' - 'A'] -= temp;
				count['O' - 'A'] -= temp;
				count['U' - 'A'] -= temp;
				count['R' - 'A'] -= temp;
				answer[4] += temp;

/*				if (num.equals("FRRNNIOZUEEO"))
				{
					System.out.println("u count : " + temp);

					System.out.println("**** count : " + Arrays.toString(count));
					System.out.println("**** answer : " + Arrays.toString(answer));
				}*/
			}

			// SIX
			if (count['X' - 'A'] > 0)
			{
				int temp = count['X' - 'A'];

				count['S' - 'A'] -= temp;
				count['I' - 'A'] -= temp;
				count['X' - 'A'] -= temp;
				answer[6] += temp;
			}

			// EIGHT
			if (count['G' - 'A'] > 0)
			{
				int temp = count['G' - 'A'];

				count['E' - 'A'] -= temp;
				count['I' - 'A'] -= temp;
				count['G' - 'A'] -= temp;
				count['H' - 'A'] -= temp;
				count['T' - 'A'] -= temp;
				answer[8] += temp;
			}

			for (int i = 0; i < 10; i++)
			{
				String curr = digits[i];

				while (possible(curr, num, count))
				{
					answer[i]++;
				}
			}

			StringBuilder ans = new StringBuilder();

			for (int i = 0; i < 10; i++)
			{
				int times = answer[i];

				while (times > 0)
				{
					ans.append("" + i);
					times--;
				}
			}

			return ans.toString();
		}

		public boolean possible(String digit, String number, int[] count)
		{
			int[] dig = new int[26];
			int len = digit.length();

			for (int i = 0; i < len; i++)
			{
				dig[digit.charAt(i) - 'A']++;
			}

			for (int i = 0; i < 26; i++)
			{
				if (count[i] < dig[i])
					return false;
			}

			for (int i = 0; i < 26; i++)
			{
				count[i] -= dig[i];
			}

			return true;
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
