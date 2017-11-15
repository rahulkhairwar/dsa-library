package com.codechef.practice.easy.year2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

class SmallFactorials 
{
	private static int t, n;
	
	public static void main(String[] args) throws NumberFormatException, IOException 
	{
		start();
	}

	public static void start() throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		t = Integer.parseInt(br.readLine());
		
		for(int i = 0 ; i < t ; i ++)
		{
			n = Integer.parseInt(br.readLine());
			
			System.out.println(factorial(BigInteger.valueOf(n)).toString());
		}
	}
	
	public static BigInteger factorial(BigInteger n)
	{
		if(n == BigInteger.valueOf(0))
			return BigInteger.valueOf(1);
		else if(n == BigInteger.valueOf(1))
			return BigInteger.valueOf(1);
		else
			return n.multiply(factorial(n.subtract(BigInteger.ONE)));
	}

}
