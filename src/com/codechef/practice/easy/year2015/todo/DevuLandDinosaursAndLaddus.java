package com.codechef.practice.easy.year2015.todo;

import java.util.Scanner;

class DevuLandDinosaursAndLaddus 
{
	private int t, n, d[], count, flag;
	
	public static void main(String[] args) 
	{
		DevuLandDinosaursAndLaddus object = new DevuLandDinosaursAndLaddus();
		
		object.start();
	}
	
	public void start()
	{
		Scanner scanner = new Scanner(System.in);
		
		t = scanner.nextInt();
		
		for(int i = 0 ; i < t ; i ++)
		{
			n = scanner.nextInt();
			d = new int[n];
			count = 0;
			
			for(int j = 0 ; j < n ; j ++)
				d[j] = scanner.nextInt();
			
			//check!!
			for(int j = 0 ; j < n ; j ++)
			{
				flag = 1;
				
				if(d[j] > 0)
				{
					while(d[j] != 0)
					{
						check(j, flag);
						
						flag ++;
					}
				}
			}
			
			System.out.println(count);
		}
		
		scanner.close();
	}

	public void check(int e, int flag)
	{
		if(e - flag < 0)
		{
			countGrassUnits(e, flag);										//check only in front
		}
		
		else if(e - flag == 0)
		{
			countGrassUnits(e, - flag);
			countGrassUnits(e, flag);										//check for just the first element in rear and all in the front 
		}
		
		else if(e + flag > n)
		{
			countGrassUnits(e, - flag);										//check only in rear
		}
		
		else
		{
			countGrassUnits(e, - flag);										//first checks in rear, then
			countGrassUnits(e, flag);										//checks in the front
		}
	}
	
	public void countGrassUnits(int e, int flag)							//have to count from the beginning of the array and not the first leftmost element!!
	{
		if((e + flag) < n && d[e + flag] < 0)
		{
			if(d[e] > d[e + flag])
			{
				count = count - (d[e + flag] * (flag));
				
				d[e] = d[e] + d[e + flag];
				d[e + flag] = 0;
			}
			
			else if(d[e] < d[e + flag])
			{
				count = count + d[e];
				
				d[e + flag] = d[e + flag] + d[e];
				d[e] = 0;
			}
			
			else 
			{
				count = count + d[e];
				
				d[e] = d[e + flag] = 0;
			}
		}
	}
}

