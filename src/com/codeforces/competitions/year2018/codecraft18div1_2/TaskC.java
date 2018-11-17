package com.codeforces.competitions.year2018.codecraft18div1_2;

import java.io.*;
import java.util.*;

public class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final long MOD = (long) 1e9 + 7;
		int n, k;
		String s;
		List<Integer> two, four, eight;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			s = in.readLine();
//			n = Integer.parseInt(s, 2);
			n = s.length();
			k = Integer.parseInt(in.readLine());

			int ones = 0;

			for (int i = 0; i < n; i++)
				if (s.charAt(i) == '1')
					ones++;

			two = new ArrayList<>();
			four = new ArrayList<>();
			eight = new ArrayList<>();
			System.out.println("n : " + n);

			int cnt = 0;
			List<Integer> list = new ArrayList<>();
			int max = 0;
			List<Integer>[] lists = new List[5];

			for (int i = 0; i < 5; i++)
				lists[i] = new ArrayList<>();


			for (int i = 1; i <= 1000; i++)
			{
				max = Math.max(max, countSteps(i));
				lists[countSteps(i)].add(i);
			}

			System.out.println("lists ");

			for (int i = 0; i < 5; i++)
				System.out.println("i : " + i + ", lists[i] : " + lists[i]);

			System.out.println("max no. of steps : " + max);

/*			for (int i = 1; i <= n; i++)
			{
				if (check(i))
				{
					cnt++;
					print(i);

					if (Integer.bitCount(i) == 1)
						list.add(i);
				}
			}*/

			System.out.println("list :");
			for (int x : list)
				System.out.println(x + ", bin : " + Integer.toBinaryString(x));

			out.println(cnt);
//			System.out.println("two : " + two);

/*			for (int i = 0; i < two.size(); i++)
				System.out.println("i : " + i + ", t[i] : " + two.get(i) + ", bin : " + Integer.toBinaryString(two
						.get(i)));*/
		}

		int countSteps(int x)
		{
			int temp = x;
			int steps = 0;

			while (temp > 1)
			{
				steps++;
				temp = Integer.bitCount(temp);
			}

			return steps;
		}

		long find(int ones, int steps)
		{
			return 0;
		}

		boolean check(int x)
		{
			int temp = x;
			int steps = 0;

			while (x > 1)
			{
				steps++;
				x = Integer.bitCount(x);
			}

//			if (steps == k)
//				System.out.println("x : " + temp + ", steps : " + steps);

			return steps == k;
		}

		void print(int x)
		{
			int temp = x;
//			System.out.println("****x : " + x + ", bin : " + Integer.toBinaryString(x));
			while (x > 1)
			{
				x = Integer.bitCount(x);

				if (x == 2)
					two.add(temp);
				else if (x == 4)
					four.add(temp);
				else if (x == 8)
					four.add(temp);
//				System.out.println("\tch to " + x + ", bin : " + Integer.toBinaryString(x));
			}
		}

		void debug(Object... o)
		{
			System.err.println(Arrays.deepToString(o));
		}

		public Solver(BufferedReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
		{
			try
			{
				solve();
			}
			catch (IOException e)
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

	static class Utils
	{
		static boolean nextPermutation(int[] arr)
		{
			for (int a = arr.length - 2; a >= 0; --a)
			{
				if (arr[a] < arr[a + 1])
				{
					for (int b = arr.length - 1; ; --b)
					{
						if (arr[b] > arr[a])
						{
							int t = arr[a];

							arr[a] = arr[b];
							arr[b] = t;

							for (++a, b = arr.length - 1; a < b; ++a, --b)
							{
								t = arr[a];
								arr[a] = arr[b];
								arr[b] = t;
							}

							return true;
						}
					}
				}
			}

			return false;
		}

	}

	public TaskC(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskC", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			out.flush();
			out.close();
		}
	}

}

/*

111111011
2

111111011
3

111111011
1

11111111111111111
2

*/
