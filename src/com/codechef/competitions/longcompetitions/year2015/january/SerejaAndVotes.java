package com.codechef.competitions.longcompetitions.year2015.january;

import java.util.Scanner;

class SerejaAndVotes
{
	private static int t, n, sum, b[];
	
	public static void main(String[] args)
	{
		start();
	}
	
	public static void start()
	{
		Scanner scanner = new Scanner(System.in);
		
		t = scanner.nextInt();
		
		for (int i =0; i < t; i++)
		{
			sum = 0;
			
			n = scanner.nextInt();
			
			b = new int[n];
			
			for (int j = 0; j < n; j++)
			{	
				b[j] = scanner.nextInt();
				
				sum = sum + b[j];
			}
			
			if (sum <= (100 + n - 1) && sum >= 100)
				System.out.println("YES");
			
			else
				System.out.println("NO");
		}
		
		scanner.close();
	}

}

/*
3
3
30 30 30
4
25 25 25 25
2
50 51
*/
