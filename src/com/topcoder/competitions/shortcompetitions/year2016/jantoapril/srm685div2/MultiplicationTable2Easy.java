package com.topcoder.competitions.shortcompetitions.year2016.jantoapril.srm685div2;

import java.util.HashSet;
import java.util.Set;

public final class MultiplicationTable2Easy
{
	public String good, notGood;

/*	public static void main(String[] args)
	{
		int[] table = {1, 1, 2, 3, 1, 0, 2, 3, 3, 3, 0, 3, 2, 2, 2, 0};
		int[] t = {1};

		MultiplicationTable2Easy mul = new MultiplicationTable2Easy();

		System.out.println(mul.isGoodSet(table, t));
	}*/

	public String isGoodSet(int[] table, int[] t)
	{
		int n = (int) Math.sqrt(table.length);
		int size = t.length;

		good = "Good";
		notGood = "Not Good";

		Set<Integer> set = new HashSet<>();

		for (int i = 0; i < size; i++)
			set.add(t[i]);

		boolean exists = true;

		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				int mul = table[t[i] * n + t[j]];

				if (!set.contains(mul))
				{
					exists = false;

					break;
				}

				mul = table[t[j] * n + t[i]];

				if (!set.contains(mul))
				{
					exists = false;

					break;
				}
			}
		}

		if (exists)
			return good;
		else
			return notGood;
	}

	static class CMath
	{
		static long power(long number, long power)
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

		static long modPower(long number, long power, long mod)
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

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

}
