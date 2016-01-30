package com.codechef.competitions.longcompetitions.year2015.march;

import java.util.Scanner;


class ChefAndNotebooks
{
	private static int t, x, y, k, n, p, c;
	
	public static void main(String[] args)
	{
		getAttributes();
	}
	
	public static void getAttributes()
	{
		Scanner scanner = new Scanner(System.in);
		int j, flag;
		
		t = scanner.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			x = scanner.nextInt();
			y = scanner.nextInt();
			k = scanner.nextInt();
			n = scanner.nextInt();
			flag = 0;
			
			for (j = 0; j < n; j++)
			{
				p = scanner.nextInt();
				c = scanner.nextInt();
				
				if ((y + p) >= x && c <= k)
					flag++;
			}
			
			if (flag >= 1)
				System.out.println("LuckyChef");
			else
				System.out.println("UnluckyChef");
		}
		
		scanner.close();
	}

}
