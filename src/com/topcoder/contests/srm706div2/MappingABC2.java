package com.topcoder.contests.srm706div2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rahulkhairwar on 21/01/17.
 */
public class MappingABC2
{
	public static void main(String[] args)
	{
		MappingABC2 x = new MappingABC2();
//		int[] t = new int[]{9, 9, 2, 9, 4};
//		int[] t = new int[]{1, 2, 3};
		int[] t = new int[]{7, 50, 1, 50, 1, 50, 1, 10, 7};

		System.out.println(x.countStrings(t));
	}

	final long mod = (long) 1e9 + 7;
	int n, lim;
	int[] bit;

	public int countStrings(int[] t)
	{
		n = t.length;
		lim = n + 1;
		bit = new int[lim];

		boolean[] used = new boolean[100];

		for (int i = 0; i < n; i++)
		{
			if (used[t[i]])
				continue;

			add(i + 1, 1);
			used[t[i]] = true;
		}

		long ans = 0;

		Arrays.fill(used, false);
		System.out.println("t : " + Arrays.toString(t));

		for (int i = 0; i < n; i++)
		{
			if (used[t[i]])
				continue;

			int unique = findUnique(t, i);

			if (unique < 2)
				continue;

			System.out.println("i : " + i + ", t[i] : " + t[i] + ", unique : " + unique + ", ways : " + find(t, i));
			ans += find(t, i);
			ans = CMath.mod(ans, mod);
			used[t[i]] = true;
		}

		return (int) (ans % mod);
	}

	int findUnique(int[] arr, int ind)
	{
		Set<Integer> set = new HashSet<>();

		for (int i = ind + 1; i < n; i++)
			set.add(arr[i]);

		if (set.contains(arr[ind]))
			set.remove(arr[ind]);

		return set.size();
	}

	int find(int[] arr, int ind)
	{
		Set<Integer> set = new HashSet<>();

		for (int i = ind + 1; i < n; i++)
			set.add(arr[i]);

		if (set.contains(arr[ind]))
			set.remove(arr[ind]);

		// set contains all unique values that occur after ind.
		int sz = set.size();
		int[] aa = new int[sz];
		int ctr = 0;
		long ans = 0;

		for (int x : set)
			aa[ctr++] = x;

		System.out.println("\tset : " + set);

		ans = 1;

		ans = CMath.mod(ans * (sz - 1), mod);
		ans = CMath.mod(ans * sz, mod);
		ans = CMath.mod(ans * CMath.moduloInverse(2, mod), mod);

		int rem = sz - 2;

		ans = CMath.mod(ans * CMath.modPower(3, rem, mod), mod);

/*
		for (int i = 0; i < sz; i++)
		{
			for (int j = i + 1; j < sz; j++)
			{
				System.out.println("\t\ta : " + aa[i] + ", b : " + aa[j]);
				if (poss(arr, aa[i], aa[j], ind))
				{
					int rem = sz - 2;

					if (rem > 0)
						ans = CMath.mod(ans + CMath.modPower(3, rem, mod), mod);
				}

				if (poss(arr, aa[j], aa[i], ind))
				{
					int rem = sz - 2;

					ans++;

					if (rem > 0)
						ans = CMath.mod(ans + CMath.modPower(3, rem, mod), mod);
				}
			}
		}*/

		return (int) ans;
	}

	boolean poss(int[] arr, int a, int b, int ind)
	{
		boolean foundA = false;

//		System.out.println("** a : " + a + ", b : " + b);
		for (int i = ind + 1; i < n; i++)
		{
//			System.out.println("\ti : " + i + ", arr[i] : " + arr[i] + ", fA : " + foundA);
			if (foundA && arr[i] == b)
				return true;

			if (arr[i] == a)
				foundA = true;
		}

		return false;
	}

	void add(int ind, int val)
	{
		while (ind < lim)
		{
			bit[ind] += val;
			ind += ind & -ind;
		}
	}

	int query(int ind)
	{
		int ans = 0;

		while (ind > 0)
		{
			ans += bit[ind];
			ind -= ind & -ind;
		}

		return ans;
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

		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

		static long min(long... arr)
		{
			long min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static long max(long... arr)
		{
			long max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static int max(int... arr)
		{
			int max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

	}

}
