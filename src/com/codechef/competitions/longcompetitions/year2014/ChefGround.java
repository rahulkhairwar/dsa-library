package com.codechef.competitions.longcompetitions.year2014;

import java.util.Scanner;

class ChefGround 
{
	private int t, n[], m[], a[];
	private boolean r[];
	
	public static void main(String[] args)
	{
		ChefGround chefGround = new ChefGround();
		chefGround.getAttributes();
	}

	public void getAttributes()
	{
		Scanner scanner = new Scanner(System.in);
		
		t = scanner.nextInt();
		
		n = new int[t];
		m = new int[t];
		r = new boolean[t];
		
		for(int i = 0 ; i < t ; i ++)
		{
			n[i] = scanner.nextInt();
			m[i] = scanner.nextInt();
			
			a = new int[n[i]];
			
			for(int j = 0 ; j < n[i] ; j ++)
				a[j] = scanner.nextInt();
			
			r[i] = canMakeColumnsEqual(a, n[i], m[i]);
		}
		
		for(int i = 0 ; i < t ; i ++)
		{
			if(r[i] == true)
				System.out.println("Yes");
			else System.out.println("No");
		}
		
		scanner.close();
	}
	
	public boolean canMakeColumnsEqual(int[] a, int n, int m)
	{
		int count = m;
		
		for(int i = 0 ; i < n ; i ++)
			count = count + a[i];
		
		if(count % n == 0)
			return true;
		else return false;
	}
}
