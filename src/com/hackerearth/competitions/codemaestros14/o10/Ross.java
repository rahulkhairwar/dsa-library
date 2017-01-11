package com.hackerearth.competitions.codemaestros14.o10;

import java.util.Scanner;

class Ross 
{
	private int t, x, y, a, b, count;
	
	public static void main(String[] args) 
	{
		Ross ross = new Ross();

		ross.getAttributes();
	}

	public void getAttributes()
	{
		Scanner scanner = new Scanner(System.in);
		
		t = scanner.nextInt();
		
		for(int i = 0 ; i < t ; i ++)
		{
			count = 0;
			
			x = scanner.nextInt();
			y = scanner.nextInt();
			a = scanner.nextInt();
			b = scanner.nextInt();
			
			countCommonBricks(x, y, a, b);
			
			System.out.println(count);
		}
	}
	
	public void countCommonBricks(int x, int y, int a, int b)
	{
		/*for(int i = a ; i <= b ;)
		{
			if(i % x == 0 && i % y == 0)
			{	
				count ++;
				
				i +=  y;
			}
			
			else i ++;
		}*/
		
		count = (b - countBricks(x, y, 0, a)) / findLCM(x, y);
	}
	
	public int findLCM(int a, int b)
	{
		int num1, num2;
		
		if(a > b)
		{
			num1 = a;
			num2 = b;
		}
		
		else 
		{
			num1 = b;
			num2 = a;
		}
		
		for(int i = 1 ; i <= num2 ; i ++)
		{
			if((num1 * i) % num2 ==0)
				return i * num1;
		}
		
		return num1 * num2;
	}
	
	public int countBricks(int x, int y, int a, int b)
	{
		for(int i = a ; i <= b ;)
		{
			if(i % x == 0 && i % y == 0)
			{	
				count ++;
				
				i +=  y;
			}
			
			else i ++;
		}
		
		return count;
	}
}
