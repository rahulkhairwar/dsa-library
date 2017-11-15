package com.codechef.practice.easy.year2015;

import java.util.Scanner;

class Factorial
{
	private static int t, n;

	public static void main(String[] args)
	{
		start();
	}

	public static void start()
	{
		Scanner scanner = new Scanner(System.in);

		t = scanner.nextInt();

		for (int i = 0; i < t; i++)
		{
			n = scanner.nextInt();

			System.out.println(countZeroes());
		}
		
		scanner.close();
	}

	public static int countZeroes()
	{
		int count = 0, a;

		a = n % 25;

		count = (n / 25) * 6;
		
		for (int i = 3; i < 13; i++)
		{
			count += (n / (int) Math.pow(5, i)) * 1;
		}
		
		if (a >= 20)
			count += 4;
		else if (a >= 15)
			count += 3;
		else if (a >= 10)
			count += 2;
		else if (a >= 5)
			count += 1;

		return count;
	}
}
