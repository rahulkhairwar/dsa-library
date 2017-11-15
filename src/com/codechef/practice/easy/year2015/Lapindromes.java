package com.codechef.practice.easy.year2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
class Lapindromes
{
	private static int t;
	private static String s;
	private static int countInFirstHalf[], countInSecondHalf[];
 
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		getAttributes();
	}
	
	public static void getAttributes() throws NumberFormatException, IOException
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		t = Integer.parseInt(br.readLine());
		
		for (int i = 0; i < t; i++)
		{
			s = br.readLine();
			
			countInFirstHalf = new int[26];
			countInSecondHalf = new int[26];
			
			checkForLapindrome();
		}
		
		br.close();
	}
	
	public static void checkForLapindrome()
	{
		int length, searchUpto, secondHalfStart;
		char c, d;
		
		length = s.length();
 
		searchUpto = length / 2;
		
		if (length % 2 == 0)
			secondHalfStart = length / 2;
		else
			secondHalfStart = (length / 2) + 1;
		
		for (int i = 0, j = secondHalfStart; i < searchUpto; i++, j++)
		{
			c = s.charAt(i);
			d = s.charAt(j);
			
			countInFirstHalf[c - 97]++;
			countInSecondHalf[d - 97]++;
		}
		
		int i = 0;
		
		for (; i < 26; i++)
			if (countInFirstHalf[i] != countInSecondHalf[i])
				break;
		
		if (i < 26)
			System.out.println("NO");
		else
			System.out.println("YES");
	}
 
}