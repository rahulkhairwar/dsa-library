package com.codechef.practice.medium.year2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

class BytelandianGoldCoins 
{
	private static long n;
	private static Map<Long, Long> map = new HashMap<Long, Long>();
	private static BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in));

	public static void main(String[] args) throws NumberFormatException, IOException 
	{
		start();
		
		br.close();
	}

	public static void start() throws NumberFormatException, IOException
	{
		String s;
		
		while ((s = br.readLine()) != null)
		{
			n = Long.parseLong(s);
			
			System.out.println(maximumAmount(n));
		}
	}
	
	public static long maximumAmount(long n)
	{
		if(n == 0)
			return 0;
		
		long result;
		
		if(map.containsKey(n))
			return map.get(n);

		result = Math.max(maximumAmount(n / 2) + maximumAmount(n / 3)
				+ maximumAmount(n / 4), n);

		map.put(n, result);

		return result; 
	}

}
