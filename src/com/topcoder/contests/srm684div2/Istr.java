package com.topcoder.contests.srm684div2;

import java.util.*;

public final class Istr
{
	public int count(String s, int k)
	{
		int[] count = new int[26];
		int len, answer;

		len = s.length();
		answer = 0;

		for (int i = 0; i < len; i++)
			count[s.charAt(i) - 'a']++;

		TreeSet<Integer> treeSet = new TreeSet<>(new Comparator<Integer>()
		{
			@Override
			public int compare(Integer o1, Integer o2)
			{
				if (o1 < o2)
					return 1;
				else
					return -1;
			}
		});

		for (int i = 0; i < 26; i++)
		{
			if (count[i] > 0)
				treeSet.add(count[i]);
		}

		while (k > 0)
		{
			int max = treeSet.pollFirst();

			max--;
			treeSet.add(max);
			k--;
		}

		Iterator<Integer> iterator = treeSet.iterator();

		while (iterator.hasNext())
		{
			int x = iterator.next();

			x *= x;

			answer += x;
		}

		return answer;
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
