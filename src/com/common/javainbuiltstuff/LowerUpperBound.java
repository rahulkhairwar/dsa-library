package com.common.javainbuiltstuff;

import java.io.*;
import java.util.*;

/**
 * Simple lower and upper bound Binary Search program in 2 ways :<br />
 * 1. Implemented one of my own.<br />
 * 2. Used headSet() and tailSet() methods of TreeSet class.<br /><br />
 *
 * SPOJ Question Link : <b>http://www.spoj.com/problems/BSEARCH1/</b>
 */
public class LowerUpperBound
{
	static int n, q, arr[];
	static Scanner in;

	public static void main(String[] args)
	{
		solve2();

//		in.close();
	}

	static void solve2()
	{
		TreeSet<Integer> treeSet = new TreeSet<Integer>(new Comparator<Integer>()
		{
			@Override public int compare(Integer o1, Integer o2)
			{
				if (o1 < o2)
					return -1;

				return 1;
			}
		});

		for (int i = 0; i < 5000; i++)
			treeSet.add(5);

		System.out.println("tS.tailSet(5, true).size() : " + treeSet.tailSet(5, true).size());
		System.out.println("tS.tailSet(5, false).size() : " + treeSet.tailSet(5, false).size());
		System.out.println("tS.tailSet(4, true).size() : " + treeSet.tailSet(4, true).size());
		System.out.println("tS.tailSet(4, false).size() : " + treeSet.tailSet(4, false).size());
	}

	static void solve()
	{
		n = in.nextInt();
		q = in.nextInt();

		arr = new int[n];

		for (int i = 0; i < n; i++)
			arr[i] = in.nextInt();

		while (q-- > 0)
		{
			int number = in.nextInt();

//			out.println(binarySearch(number));

//			int index = Arrays.binarySearch(arr, number - 1);

/*			if (index == n - 1)
				System.out.println(-1);
			else
			{
				if (index < 0)
				{
					index *= -1;
					index--;

					if (arr[index] == number)
						System.out.println(index);
					else
						System.out.println(-1);
				}
				else
				{
					if (arr[index + 1] == number)
						System.out.println(index + 1);
					else
						System.out.println(-1);
				}
			}*/
		}
	}

	static int binarySearch(int value)
	{
		int first, last, mid;

		first = 0;
		last = n - 1;

		while (first <= last)
		{
			mid = first + (last - first) / 2;

			if (arr[mid] == value)
			{
				if (mid == 0)
					return mid;

				if (arr[mid - 1] != value)
					return mid;

				last = mid - 1;
			}
			else if (arr[mid] > value)
				last = mid - 1;
			else
				first = mid + 1;
		}

		return -1;
	}

}

