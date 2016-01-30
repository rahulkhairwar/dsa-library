package com.codechef.practice.easy.year2014;

import java.util.Scanner;

class ATM 
{
	private double x, y;
	
	public static void main(String[] args) 
	{
		ATM atm = new ATM();
		
		atm.start();
	}

	public void start()
	{
		Scanner scanner = new Scanner(System.in);
		
		x = scanner.nextDouble();
		y = scanner.nextDouble();
		
		if(x < y - 0.50)
		{
			if(x % 5 == 0)
			{
				y = y - x - 0.50;
			}
		}
		
		scanner.close();
		System.out.printf("%2.2f", y);
	}
}
