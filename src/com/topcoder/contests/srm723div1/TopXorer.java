package com.topcoder.contests.srm723div1;

public final class TopXorer
{
	public int maximalRating(int[] x)
	{
		int n = x.length;
		int[] present = new int[30];

		for (int i = 29; i >= 0; i--)
			for (int j = 0; j < n; j++)
				if ((x[j] & (1 << i)) > 0)
					present[i]++;

		int max = -1;

		for (int i = 29; i >= 0; i--)
		{
			if (present[i] > 1)
			{
				max = i;

				break;
			}
		}

		for (int i = max - 1; i >= 0; i--)
			present[i]++;

		int xor = 0;

		for (int i = 29; i >= 0; i--)
			if (present[i] > 0)
				xor |= (1 << i);

		return xor;
	}

}
