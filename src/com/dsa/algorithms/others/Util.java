package com.dsa.algorithms.others;

/**
 * Utility algorithms, like nextPermutation.
 */
public class Util
{
	static boolean nextPermutation(int[] arr)
	{
		for (int a = arr.length - 2; a >= 0; --a)
		{
			if (arr[a] < arr[a + 1])
			{
				for (int b = arr.length - 1; ; --b)
				{
					if (arr[b] > arr[a])
					{
						int t = arr[a];

						arr[a] = arr[b];
						arr[b] = t;

						for (++a, b = arr.length - 1; a < b; ++a, --b)
						{
							t = arr[a];
							arr[a] = arr[b];
							arr[b] = t;
						}

						return true;
					}
				}
			}
		}

		return false;
	}

}
