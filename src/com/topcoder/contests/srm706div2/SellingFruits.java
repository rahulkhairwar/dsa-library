package com.topcoder.contests.srm706div2;

/**
 * Created by rahulkhairwar on 21/01/17.
 */
public class SellingFruits
{
	public static void main(String[] args)
	{
		SellingFruits x = new SellingFruits();
		System.out.println(x.maxDays(3, 5, 100, 10));
	}

	public int maxDays(int x, int f, int d, int p)
	{
		int frDays = f;
		int dDays = d / x;
		int days = Math.min(frDays, dDays);

//		System.out.println("fr : " + frDays + ", dd : " + dDays);

		if (frDays < dDays)
		{
			int rem = d - days * x;

			frDays += rem / (p + x);
		}

		return Math.min(frDays, dDays);
	}

}
