package com.hackerearth.competitions.codemaestros14.o10;

import java.util.Scanner;

class Joey 
{
	private int t, k, a[]; 
	
	public static void main(String[] args) 
	{
		
	}

	public void getAttributes()
	{
		Scanner scanner = new Scanner(System.in);
		
		t = scanner.nextInt();
		
		for(int i = 0 ; i < t ; i ++)
		{
			k = scanner.nextInt();
			
			a = new int[k / 3];
			
			System.out.println(getAnswer(k));
		}
	}
	
	public int getAnswer(int k)
	{
		if(k % 3 == 0)
		{
			convert(k - 1);
		}
		
		return 0;
	}
	
	public void convert(int n)
	{
		int i = 0;
		
		if(n == 0)
			return;
		
		while(n != 0)
		{
			a[i] = n % 3;
			
			n /= 3;
			
			i ++;
		}
	}
	
	public int addOne(int k)
	{
		int a = 0, i = 0;
		
		while(k != 0)
		{
			a += ((k % 10) + 1) * i;
			
			k /= 10;
		}
		return 0;
	}
}
