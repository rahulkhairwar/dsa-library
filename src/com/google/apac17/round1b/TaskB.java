package com.google.apac17.round1b;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class TaskB
{
	public static void main(String[] args)
	{
		BufferedReader in;

		try
		{
			in = new BufferedReader(new FileReader("/Users/rahulkhairwar/Documents/IntelliJ IDEA "
					+ "Workspace/Competitive "
					+ "Programming/src/com/google/apac17/round1b/inputB.txt"));

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
		int size;
		long t, a, b, n, k, newN, mod;
		List<Integer> primes;
		BufferedReader in;
		OutputWriter out;

		@Override public void run()
		{
			try
			{
				t = Integer.parseInt(in.readLine());

				for (int test = 1; test <= t; test++)
				{
					String[] tokens = in.readLine().split(" ");

					a = Long.parseLong(tokens[0]);
					b = Long.parseLong(tokens[1]);
					n = Long.parseLong(tokens[2]);
					k = Long.parseLong(tokens[3]);
					mod = (long) (1e9 + 7);

					long ans = solve();

					System.out.println("Case #" + test + ": " + ans);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		long solve()
		{
			long count = 0;

			for (int i = 1; i <= n; i++)
			{
				for (int j = 1; j <= n; j++)
				{
					if (i == j)
						continue;

					if ((CMath.modPower(i, a, k) + CMath.modPower(j, b, k)) % k == 0)
						count++;
				}
			}

			return CMath.mod(count, mod);
		}

		void solve2()
		{
			findPrimes();

			List<Point> factors = factorise(k);
			int max = 0;
			long i = 1;
			Iterator<Point> iterator = factors.iterator();

			while (iterator.hasNext())
			{
				Point curr = iterator.next();

				max = Math.max(max, curr.y);
				i *= curr.x;
			}

			System.out.println("i : " + i);
			i = CMath.power(i, max);
			System.out.println("i : " + i);

			newN = n / i;

			size = primes.size();

			long countI = 1 + find(0, 1);

			System.out.println("countI : " + countI);

//			out.println(find(0, newN));

//			System.out.println(
//					"factorise 2 * 2 * 2 * 7 * 11  * 19 * 19: " + factorise(2 * 2 * 2 * 7 * 11 * 19 * 19).toString());
		}

		long find(int index, long mul)
		{
			int curr = primes.get(index);

			System.out.println("curr : " + curr + ", newN : " + newN);

			if (curr > newN || newN == 1 || mul > newN)
				return 0;

			long count = 1;

			for (int i = 0; i < size; i++)
			{
				int x = primes.get(i);

				if (x > newN)
					break;

				System.out.println("multiplying : " + mul + ", by : " + x);
				count += find(i, mul * x);
			}

			return CMath.mod(count, mod);
		}

		void findPrimes()
		{
			boolean[] isComp = new boolean[(int) 1e6 + 5];
			int counter = 1;

			primes = new ArrayList<>();
			primes.add(2);

			while ((1 << counter) <= 1e6)
			{
				isComp[1 << counter] = true;
				counter++;
			}

			for (int i = 3; i <= 1e6; i += 2)
			{
				if (isComp[i])
					continue;

				primes.add(i);
				counter = 2;

				while (i * counter <= 1e6)
				{
					isComp[i * counter] = true;
					counter++;
				}
			}
		}

		List<Point> factorise(long num)
		{
			List<Point> factors = new ArrayList<>();
			int i = 0;

			while (num > 1)
			{
				int curr = primes.get(i);
				int count = 0;

				while (num % curr == 0)
				{
					count++;
					num /= curr;
				}

				if (count > 0)
					factors.add(new Point(curr, count));

				i++;
			}

			return factors;
		}

		public Solver(BufferedReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
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
