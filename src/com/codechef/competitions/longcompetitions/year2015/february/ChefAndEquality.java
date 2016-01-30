package com.codechef.competitions.longcompetitions.year2015.february;

import java.util.Scanner;

class ChefAndEquality
{
	private static int t, n, occurences[];
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args)
	{
		getAttributes();
	}

	public static void getAttributes()
	{
		t = scanner.nextInt();

		for (int i = 0; i < t; i++)
		{
			n = scanner.nextInt();

			findMinimumNumberOfOperations();
		}
	}

	public static void findMinimumNumberOfOperations()
	{
		int a, max;
		
		max = 1;
		occurences = new int[100000];

		for (int i = 0; i < n; i++)
		{
			a = scanner.nextInt();

			occurences[a - 1]++;

			if (occurences[a - 1] > max)
				max = occurences[a - 1];
		}
		
		System.out.println(n - max);
	}

}

/*
test cases

 8
 1 1 1 1 1 2 3 4
 
 
 */
