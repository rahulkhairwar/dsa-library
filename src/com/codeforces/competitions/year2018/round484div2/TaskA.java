package com.codeforces.competitions.year2018.round484div2;

import java.io.*;
import java.util.*;

public class TaskA
{
	public static void main(String[] args)
	{
		new TaskA(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n;
		BufferedReader in;
		//		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = Integer.parseInt(in.readLine());

			char[] s = in.readLine().toCharArray();

			if (n == 1 && s[0] == '0')
			{
				out.println("No");

				return;
			}

/*			for (int i = 0; i < n; i++)
			{
				if (s[i] == '0')
				{
					if (!valid(i, s))
					{
						out.println("No");

						return;
					}
				}
			}*/

			for (int i = 1; i < n; i++)
			{
				if (s[i] == '1' && s[i - 1] == '1')
				{
					out.println("No");

					return;
				}
				else if (s[i] == '0' && s[i - 1] == '0')
				{
					if (i - 2 < 0)
					{
						out.println("No");

						return;
					}
					else if (i + 1 >= n)
					{
						out.println("No");

						return;
					}
					else if (s[i - 2] == '0' || s[i + 1] == '0')
					{
						out.println("No");

						return;
					}
				}
			}

			out.println("Yes");

			/*if ((check(0, '1', s) && check(1, '0', s)) || (check(1, '1', s) && check(0, '0', s)))
				out.println("Yes");
			else
				out.println("No");*/
		}

		boolean valid(int pos, char[] s)
		{
			if (pos > 0 && s[pos - 1] == '0')
				return false;

			if (pos < n - 1 && s[pos + 1] == '0')
				return false;

			return true;
		}

		boolean check(int start, char ch, char[] s)
		{
			for (int i = start; i < n; i += 2)
				if (s[i] != ch)
					return false;

			return true;
		}

		void debug(Object... o)
		{
			System.err.println(Arrays.deepToString(o));
		}

		//		uncomment below line to change to BufferedReader
		public Solver(BufferedReader in, PrintWriter out)
//		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override public void run()
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

	public TaskA(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskA", 1 << 29);

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

3
000

3
101

3
010

2
00

2
01

2
10

2
11

*/
