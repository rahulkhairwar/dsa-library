package com.codechef.practice.easy.year2014;

import java.util.Scanner;

class DoubleStrings
{
	private static int t, n;
	private static String m;
	
	public static void main(String[] args)
	{
		start();
	}

	public static void start()
	{
		Scanner scanner = new Scanner(System.in);
		
		t = scanner.nextInt();
		
		scanner.nextLine();
		
		for (int i = 0; i < t; i ++)
		{
			m = scanner.nextLine();
			
			n = Integer.parseInt(m);
			
			int remainder;
			if (m.length() > 1)
				remainder = (int) (n % (Math.pow(10, m.length() - 1)));
			
			else remainder = n;
			
			if (remainder == 0 || remainder == 2 || remainder == 4 || remainder == 6 || remainder == 8)
				System.out.println(remainder);

			else 
				System.out.println(remainder);
		}
		
		scanner.close();
	}
	
}

/*
 * 
10
2
4
1001
1003
1000
400
100
50
25
12
 */