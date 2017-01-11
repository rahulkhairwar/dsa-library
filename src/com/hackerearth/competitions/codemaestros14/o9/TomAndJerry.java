package com.hackerearth.competitions.codemaestros14.o9;

import java.util.Scanner;

class TomAndJerry 
{
	private int t, n, m1, m2, count, result[];
	
	public static void main(String[] args) 
	{
		TomAndJerry main = new TomAndJerry();
		
		main.setAttributes();
	}
	
	public void setAttributes()
	{
		Scanner scanner = new Scanner(System.in);
		
		t = scanner.nextInt();
		
		result = new int[t];
		
		for(int i = 0 ; i < t ; i ++)
		{
			count = 1;
			n = scanner.nextInt();
			m1 = scanner.nextInt();
			m2 = scanner.nextInt();
			
			result[i] = getWinner(n, m1, m2);
		}
		
		for(int i = 0 ; i < t ; i ++)
			System.out.println(result[i]);
		
		scanner.close();
	}

	public int pickUpMarbles(int n, int m1, int m2)
	{
		if(m1 == n || m2 == n)
			return count % 2;
		
		if(m1 > n && m2 > n)
		{
			if(count % 2 == 0)
				return 1;
			else return 0;
		}
	
		if(m1 > n && m2 < n)
		{
			count ++;
			return pickUpMarbles(n - m2, m1, m2);
		}
		
		if(m1 < n && m2 > n)
		{
			count ++;
			return pickUpMarbles(n - m1, m1, m2);
		}
		
		else
		{
			if(count % 2 == 0)
			{
				count ++;
				
				if((pickUpMarbles(n - m1, m1, m2) == 0) || (pickUpMarbles(n - m2, m1, m2) == 0))
					return 0;
				else return 1;
			}
			
			else
			{
				count ++;
				
				if((pickUpMarbles(n - m1, m1, m2) == 1) || (pickUpMarbles(n - m2, m1, m2) == 1))
					return 1;
				else return 0;
			}
		}
	}
	
	public int getWinner(int n, int m1, int m2)
	{
		return pickUpMarbles(n, m1, m2);
	}
}
