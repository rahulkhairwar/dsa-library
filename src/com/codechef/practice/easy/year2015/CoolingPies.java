package com.codechef.practice.easy.year2015;

import java.util.Arrays;
import java.util.Scanner;
 
class CoolingPies
{
	private static int t, n, pies[], racks[];
	
	public static void main(String[] args)
	{
		getAttributes();
	}
	
	public static void getAttributes()
	{
		Scanner scanner = new Scanner(System.in);
		
		t = scanner.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			n = scanner.nextInt();
 
			pies = new int[n];
			racks = new int[n];
			
			for (int j = 0; j < n; j++)
				pies[j] = scanner.nextInt();
			
			for (int j = 0; j < n; j++)
				racks[j] = scanner.nextInt();
			
			Arrays.sort(pies);
			Arrays.sort(racks);
			
			find();
		}
		
		scanner.close();
	}
	
	public static void find()
	{
		int count = 0;
		
		for (int i = 0; i < n; i++)
		{
			if (pies[count] <= racks[i])
				count++;
		}
		
		System.out.println(count);
	}
 
}