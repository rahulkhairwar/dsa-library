package com.codechef.competitions.longcompetitions.year2015.april;

public class Test
{
	private static int count;
	
	public static void main(String[] args)
	{
/*		for (int i = 2; i <= 31622; i++)
			if (isPrime(i))
				count++;
		
		System.out.println(count);
		System.out.println();
		
		count = 0;
		
		for (int i = 2; i <= 31622; i++)
			if (isPrime2(i))
				count++;
		
		System.out.println(count);*/
		
		System.out.println(isPrime2(1000003));
		
		// System.out.println((long) Math.pow(1000000000, 10));
	}
	
	public static boolean isPrime(int a)
	{
		int sq = (int) Math.sqrt(a);
		
		// System.out.println("sq : " + sq);

/*		for (int i = 0; i < 25 && primesUpto100[i] < sq; i++)
			if (a % primesUpto100[i] == 0)
				return false;

		if (sq > 101)
		{
			for (int i = 0; i < 3377 && rest[i] < sq; i++)
				if (a % rest[i] == 0)
					return false;
		}*/

		for (int i = 2; i < (a / 2) + 1; i++)
			if (a % i == 0)
				return false;

		return true;
	}
	
	public static boolean isPrime2(int a)
	{
		int sq = (int) Math.sqrt(a);
		
		for (int i = 2; i <= sq; i++)
			if (a % i == 0)
				return false;
		
		return true;
	}

}
