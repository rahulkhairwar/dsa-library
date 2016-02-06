package com.common.implementation;

import java.util.Scanner;

/**
 * Prime Factorization in O()
 *
 * will complete soon.
 */
public class PrimeFactorization
{
	static Scanner in;

	public static void main(String[] args)
	{
		in = new Scanner(System.in);

		solve();
	}

	static void solve()
	{
		int count = 0;

		for (int i = 1; i <= 1000; i++)
		{
			if (gcd(i, 1000) == 5)
				count++;
		}

		System.out.println("Count : " + count);
	}

	static int gcd(int a, int b)
	{
		if (b == 0)
			return a;

		return gcd(b, a % b);
	}

}
