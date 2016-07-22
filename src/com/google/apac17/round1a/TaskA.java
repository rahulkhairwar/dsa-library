package com.google.apac17.round1a;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

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
			in = new BufferedReader(new FileReader("/Users/rahulkhairwar/Documents/IntelliJ IDEA "
					+ "Workspace/Competitive "
					+ "Programming/src/com/google/apac17/round1a/inputA.txt"));

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
		int t, n;
		Name[] names;
		//		String[][] names;
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

				outer : for (int test = 1; test <= t; test++)
				{
					n = Integer.parseInt(in.readLine());
					names = new Name[n];
//					names = new String[n][];

					for (int i = 0; i < n; i++)
					{
						names[i] = new Name(i);
						names[i].name = in.readLine().split(" ");
					}

					int[][] count = new int[n][26];

					for (int i = 0; i < n; i++)
					{
						int len = names[i].name.length;

						for (int j = 0; j < len; j++)
						{
							String curr = names[i].name[j];

							for (int k = 0; k < curr.length(); k++)
							{
								if (curr.charAt(k) == ' ')
									continue;

								count[i][curr.charAt(k) - 'A']++;
							}
						}

						for (int j = 0; j < 26; j++)
							if (count[i][j] > 0)
								names[i].unique++;
					}

					Arrays.sort(names, new Comparator<Name>()
					{
						@Override public int compare(Name o1, Name o2)
						{
							return Integer.compare(o2.unique, o1.unique);
						}
					});

//					System.out.println("Arrays : " + Arrays.toString(names));

					int max = names[0].unique;
					int counter = 0;
					String[] list = new String[n];

					for (int i = 0; i < n; i++)
					{
						if (names[i].unique == max)
						{
							int currLen = names[i].name.length;
							String curr = "";

							for (int j = 0; j < currLen; j++)
								curr += names[i].name[j];

							list[counter++] = curr;
							names[i].com = curr;
						}
					}

					Arrays.sort(list, 0, counter);

					for (int i = 0; i < n; i++)
						if (names[i].com.equals(list[0]))
						{
							out.println("Case #" + test + ": " + names[i].toString());

							continue outer;
						}


/*					list.sort(new Comparator<Name>()
					{
						@Override public int compare(Name o1, Name o2)
						{
							return stringCompare(o1.name, o2.name);
						}
					});*/
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	static class Name
	{
		int index;
		String[] name;
		String com;
		int unique;

		public Name(int index)
		{
			this.index = index;
		}

		@Override public String toString()
		{
			int len = name.length;
			String name = "";

			for (int i = 0; i < len; i++)
				name += this.name[i] + " ";

			return name;
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
