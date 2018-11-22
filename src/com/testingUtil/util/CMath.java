package com.testingUtil.util;

public class CMath
{
	public static long power(long number, long power)
	{
		if (number == 1 || number == 0 || power == 0)
			return 1;

		if (power == 1)
			return number;

		if (power % 2 == 0)
			return power(number * number, power / 2);
		else
			return power(number * number, power / 2) * number;
	}

	public static long modPower(long number, long power, long mod)
	{
		if (number == 1 || number == 0 || power == 0)
			return 1;

		number = mod(number, mod);

		if (power == 1)
			return number;

		long square = mod(number * number, mod);

		if (power % 2 == 0)
			return modPower(square, power / 2, mod);
		else
			return mod(modPower(square, power / 2, mod) * number, mod);
	}

	public static long moduloInverse(long number, long mod)
	{
		return modPower(number, mod - 2, mod);
	}

	public static long mod(long number, long mod)
	{
		return number - (number / mod) * mod;
	}

	public static int gcd(int a, int b)
	{
		if (b == 0)
			return a;
		else
			return gcd(b, a % b);
	}

	public static long gcd(long a, long b)
	{
		if (b == 0)
			return a;
		else
			return gcd(b, a % b);
	}

	public static long min(long... arr)
	{
		long min = arr[0];

		for (int i = 1; i < arr.length; i++)
			min = Math.min(min, arr[i]);

		return min;
	}

	public static long max(long... arr)
	{
		long max = arr[0];

		for (int i = 1; i < arr.length; i++)
			max = Math.max(max, arr[i]);

		return max;
	}

	public static int min(int... arr)
	{
		int min = arr[0];

		for (int i = 1; i < arr.length; i++)
			min = Math.min(min, arr[i]);

		return min;
	}

	public static int max(int... arr)
	{
		int max = arr[0];

		for (int i = 1; i < arr.length; i++)
			max = Math.max(max, arr[i]);

		return max;
	}

}
