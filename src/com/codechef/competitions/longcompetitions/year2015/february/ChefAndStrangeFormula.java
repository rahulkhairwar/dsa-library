package com.codechef.competitions.longcompetitions.year2015.february;

import java.util.Scanner;

class ChefAndStrangeFormula
{
	private static int n;
	private static long gArray[];
	private static long p, m, mainSum, maxIndex, mFactorialArray[];

	public static void main(String[] args)
	{
		maxIndex = -1;
		
		getAttributes();
	}

	public static void getAttributes()
	{
		Scanner scanner = new Scanner(System.in);

		n = scanner.nextInt();
		m = scanner.nextInt();

		mFactorialArray = new long[(int) m];
		gArray = new long[(int) m];

		createMFactorialAndGArrays();
		
		for (int i = 0; i < n; i++)
		{
			p = scanner.nextLong();

			mainSum = (mainSum + f(p)) % m;
		}

		System.out.println(mainSum);

		scanner.close();
	}

	public static void createMFactorialAndGArrays()
	{
		long sum, factorial;

		sum = factorial = mFactorialArray[0] = gArray[0] = 1;

		for (long i = 2; i <= m; i++)
		{
			factorial = (factorial * i) % m;

			if (factorial == 0)
			{
				maxIndex = (int) (i - 2);

				break;
			}

			mFactorialArray[(int) (i - 1)] = factorial;

			sum = (sum + (i * mFactorialArray[(int) (i - 1)]) % m) % m;
			gArray[(int) (i - 1)] = (int) sum;
		}
	}

	public static int f(long x)
	{
		long sum;
		
		if ((x - 1) >= maxIndex)
			sum = (int) ((gArray[(int) maxIndex] + h(x)) % m);
		else
			sum = (int) ((gArray[(int) (x - 1)] + h(x)) % m);
		
		return (int) sum;
	}

	public static int h(long x)
	{
		long sum, xModM;

		xModM = (x % m);
		
		if (x % 2 == 0)
		{
			long z = (int) ((xModM * ((x / 2) % m)) % m);
			sum = (int) ((z * ((x + 1) % m)) % m);
		}
		else
			sum = (int) ((((xModM * xModM) % m) * (((x + 1) / 2) % m)) % m);
		
		return (int) sum;
	}

}

/*
test cases

5 7
1 2 3 4 5


1 10000000
12346427467262476


5 17
9 18 25 34 87

5 9817365
1000000000000000000 100000000000000005 109375632948726583 198375628394700173 183742233783645009
// getting answer = 5212192

*/
