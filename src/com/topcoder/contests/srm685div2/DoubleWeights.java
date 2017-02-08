package com.topcoder.contests.srm685div2;

import java.util.Arrays;

public class DoubleWeights
{
	int n;
	char[][] adjOne, adjTwo;

	public int minimalCost(String[] weight1, String[] weight2)
	{
		n = weight1.length;
		adjOne = new char[n][];
		adjTwo = new char[n][];

		for (int i = 0; i < n; i++)
		{
			adjOne[i] = weight1[i].toCharArray();
			adjTwo[i] = weight2[i].toCharArray();
		}

		boolean[] visited = new boolean[n];
		long first, second;

		visited[0] = true;
		first = second = Integer.MAX_VALUE;

		for (int i = 0; i < n; i++)
		{
			if (i == 1)
				continue;

			if (adjOne[0][i] == '.')
				continue;

			long[] arr = dfs(0, i, adjOne[0][i], adjTwo[0][i], Arrays.copyOf(visited, n));

			if ((arr[0] * arr[1]) < first * second)
			{
				first = arr[0];
				second = arr[1];
			}
		}

		if (first == Integer.MAX_VALUE && second == Integer.MAX_VALUE)
			return -1;
		else
			return (int) (first * second);
	}

	public long[] dfs(int from, int to, int one, int two, boolean[] visited)
	{
		long min = Integer.MAX_VALUE;
		long first, second;

		first = one;
		second = two;

		for (int i = from; i < n; i++)
		{
			if (i == 1)
			{
				return new long[]{first + adjOne[from][1], second + adjTwo[from][1]};
			}

			if (i != from && adjOne[from][i] != '.' && !visited[i])
			{
				visited[i] = true;

				long[] arr = dfs(to, i, one + (int) adjOne[from][to], two + (int) adjTwo[from][to], Arrays.copyOf
						(visited, n));

/*				first = arr[0];
				second = arr[1];*/

				if (((one + arr[0]) * (two + arr[1])) < first * second)
				{
					first = one + arr[0];
					second = two + arr[1];
				}

				//min = Math.min(min, arr[0] * arr[1]);
			}
		}

		return new long[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
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
