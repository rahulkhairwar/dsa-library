package com.codechef.competitions.longcompetitions.year2015.january;

import java.util.Scanner;

class ChefAndStones
{
	private static int t, n, k, a[], b[];
	private static long m;
	
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
			k = scanner.nextInt();
			
			a = new int[n];
			b = new int[n];
			
			for (int j = 0; j < n; j++)
				a[j] = scanner.nextInt();
			
			for (int j = 0; j < n; j++)
				b[j] = scanner.nextInt();
			
			calculateProfit();
		}
		
		scanner.close();
	}
	
	public static void calculateProfit()
	{
		long p;
		
		m = (long) (k / a[0]) * b[0];
		
		for (int i = 1; i < n; i++)
		{
			if (a[i] > k)
				continue;
			
			p = (long) (k / a[i]) * b[i];
			
			if (p > m)
				m = p;
		}
		
		System.out.println(m);
	}
	
}
