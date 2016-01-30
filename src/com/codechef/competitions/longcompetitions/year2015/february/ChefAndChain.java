package com.codechef.competitions.longcompetitions.year2015.february;

import java.util.Scanner;

class ChefAndChain
{
	private static int t;
	private static String s;

	public static void main(String[] args)
	{
		getAttributes();
	}
	
	public static void getAttributes()
	{
		Scanner scanner = new Scanner(System.in);

		t = scanner.nextInt();
		scanner.nextLine();

		for (int i = 0; i < t; i++)
		{
			s = scanner.nextLine();

			countReplacements();
		}
		
		scanner.close();
	}
	public static void countReplacements()
	{
		int countWithSameFirstCharacter, countWithChangedFirstCharacter, length;
		char firstChar, secondChar;
		firstChar = s.charAt(0);
		
		if (firstChar == '+')
			secondChar = '-';
		else
			secondChar = '+';
		
		length = s.length();
		countWithSameFirstCharacter = countWithChangedFirstCharacter = 0;
		
		for (int i = 0; i < length; i += 2)
		{
			if (s.charAt(i) != firstChar)
				countWithSameFirstCharacter++;
			else
				countWithChangedFirstCharacter++;
		}
		
		for (int i = 1; i < length; i += 2)
		{
			if (s.charAt(i) != secondChar)
				countWithSameFirstCharacter++;
			else
				countWithChangedFirstCharacter++;
		}
		
		if (countWithSameFirstCharacter <= countWithChangedFirstCharacter)
			System.out.println(countWithSameFirstCharacter);
		else
			System.out.println(countWithChangedFirstCharacter);
	}

}
