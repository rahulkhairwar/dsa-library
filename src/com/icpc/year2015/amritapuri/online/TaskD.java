package com.icpc.year2015.amritapuri.online;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class TaskD
{
	public static void main(String[] args)
	{
		BufferedReader in = null;
		PrintWriter out = null;

		try
		{
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
			Solver solver = new Solver(in, out);

			solver.solve();
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	static class Solver
	{
		static final long MOD = (long) 1e9 + 7;
		int t, n;
		int[] arr, multiplesCount;
		long[] gcdCount;
		List<Integer>[] divisors;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			pre();

			String[] tokens = in.readLine().split(" ");
			t = Integer.parseInt(tokens[0]);

			while (t-- > 0)
			{
				tokens = in.readLine().split(" ");
				n = Integer.parseInt(tokens[0]);
				arr = new int[n];
				multiplesCount = new int[(int) 1e5 + 5];
				gcdCount = new long[(int) 1e5 + 5];

				tokens = in.readLine().split(" ");

				for (int i = 0; i < n; i++)
				{
					int x = Integer.parseInt(tokens[i]);

					for (int div : divisors[x])
						multiplesCount[div]++;
				}

				for (int i = (int) 1e5; i > 0; i--)
				{
					long remove = 0;
					int curr = i << 1;

					while (curr <= 1e5)
					{
						remove += gcdCount[curr];
						curr += i;
					}

					long total = CMath.modPower(2, multiplesCount[i], MOD - 1) - 1;

					gcdCount[i] = total - remove;

					while (gcdCount[i] < 0)
						gcdCount[i] += MOD - 1;
				}

				long product = 1;

				for (int i = 2; i <= 1e5; i++)
				{
					product *= CMath.modPower(i, gcdCount[i], MOD);
					product = CMath.mod(product, MOD);
				}

				out.println(product);
			}
		}

		void pre()
		{
			divisors = new ArrayList[(int) 1e5 + 5];

			for (int i = 1; i <= 1e5; i++)
				divisors[i] = new ArrayList<>();

			for (int i = 1; i <= 1e5; i++)
			{
				int curr = i;

				while (curr <= 1e5)
				{
					divisors[curr].add(i);
					curr += i;
				}
			}
		}

		public Solver(BufferedReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
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

	}

}

/*

1
5
2 8 15 30 9

1
10
2 8 15 30 9 8 16 27 90 10

*/
