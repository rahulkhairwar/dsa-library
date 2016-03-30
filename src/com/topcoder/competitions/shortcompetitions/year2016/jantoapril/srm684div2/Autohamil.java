package com.topcoder.competitions.shortcompetitions.year2016.jantoapril.srm684div2;

// Topcoder SRM 684 Div. 2 600 points question
public final class Autohamil
{
	public final String EXISTS = "Exists";
	public final String DOES_NOT_EXIST = "Does not exist";

	public String check(int[] z0, int[] z1)
	{
		boolean[] zero, one;

		zero = new boolean[z0.length];
		one = new boolean[z0.length];

		int currState = 0;

		while (true)
		{
			if (true)
				break;
		}

		return null;
	}

	public boolean exists(int[] z0, int[] z1)
	{
		return true;
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
