package com.codechef.practice.easy.year2014;

import java.util.Scanner;

class LifeUniverseAndEverything 
{
	private int a;
	
	public static void main(String[] args) 
	{
		LifeUniverseAndEverything object = new LifeUniverseAndEverything();
		
		object.getAttributes();
	}

	public void getAttributes()
	{
		Scanner scanner = new Scanner(System.in);
		
		do
		{
			a = scanner.nextInt();
			
			print(a);
		} while(a != 42);
			
		a = scanner.nextInt();
		scanner.close();
	}
	
	public void print(int n)
	{
		if(n != 42)
			System.out.println(n);
	}
}
