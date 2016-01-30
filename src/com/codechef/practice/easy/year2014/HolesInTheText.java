package com.codechef.practice.easy.year2014;

import java.util.Scanner;

class HolesInTheText 
{
	private int t, count;
	private String text;
	
	public static void main(String[] args) 
	{
		HolesInTheText holesInTheText = new HolesInTheText();
		
		holesInTheText.start();
	}

	public void start()
	{
		Scanner scanner = new Scanner(System.in);
		
		t = scanner.nextInt();
		text = scanner.nextLine();
		for(int i = 0 ; i < t ; i ++)
		{
			count = 0;
			text = scanner.nextLine();
			
			countHoles(text);
			
			System.out.println(count);
		}
		
		scanner.close();
	}
	
	public void countHoles(String text)
	{
		char a;
		
		for(int i = 0 ; i < text.length() ; i ++)
		{
			a = text.charAt(i);

			if(a == 'A' || a == 'D' || a == 'O' || a == 'P' || a == 'Q' || a == 'R')
				count ++;
			else if(a == 'B')
				count += 2;
		}
	}
}
