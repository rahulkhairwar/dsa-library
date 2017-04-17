package com.coursera.algorithms_divide_and_conquer;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * An implementation of Merge Sort algorithm.
 * <br />Running time : O(n logn), where n is the size of the input array.
 */
public class MergeSort
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		Sorter sorter = new Sorter(in, out);

		sorter.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Sorter
	{
		int n, src[], dest[];
		Scanner in;
		PrintWriter out;

		void solve()
		{
/*			n = in.nextInt();
			src = new int[n];
			dest = new int[n];

			for (int i = 0; i < n; i++)
				src[i] = in.nextInt();*/

			n = 8;
			src = new int[]{5, 1, 3, 8, 4, 7, 2, 6};
			dest = new int[8];

			sort(src, dest, 0, n - 1, 1);
//			mergeSort(src, dest, 0, n - 1, 0, 1);
			System.out.println("src : " + Arrays.toString(src) + ", dest : " + Arrays.toString(dest));
		}

		private static void mergeSort(int[] src, int[] dest, int low, int high, int off, int level)
		{
			int length = high - low;
			System.out.println("low : " + low + ", high : " + high + ", level : " + length);

			if (low == high)
			{
				if (level % 2 == 0)
					src[low] = dest[low];
				else
					dest[low] = src[low];

				return;
			}

/*			// Insertion sort on smallest arrays
			if (length < INSERTIONSORT_THRESHOLD) {
				for (int i=low; i<high; i++)
					for (int j=i; j>low &&
							((Comparable) dest[j-1]).compareTo(dest[j])>0; j--)
						swap(dest, j, j-1);
				return;
			}*/

			// Recursively sort halves of dest into src
			int destLow = low;
			int destHigh = high;
			low += off;
			high += off;
			int mid = (low + high) >>> 1;
//			int mid = low + high >> 1;
			mergeSort(dest, src, low, mid, -off, level + 1);
			mergeSort(dest, src, mid + 1, high, -off, level + 1);

			// If list is already sorted, just copy from src to dest.  This is an
			// optimization that results in faster sorts for nearly ordered lists.
			if (src[mid] <= src[mid + 1])
			{
				System.arraycopy(src, low, dest, destLow, length + 1);
				return;
			}

			// Merge sorted halves (now in src) into dest
			for (int i = destLow, p = low, q = mid + 1; i < destHigh; i++)
			{
				if (q >= high || p < mid && src[p] <= src[q])
					dest[i] = src[p++];
				else
					dest[i] = src[q++];
			}
		}

		void sort(int[] src, int[] dest, int left, int right, int level)
		{
			if (left == right)
			{
				if (level % 2 == 0)
					src[left] = dest[left];
				else
					dest[left] = src[left];

				return;
			}

			int mid = left + right >> 1;

			System.out.println("l : " + left + ", r : " + right + ", (l + r) >>> 1 : " + (left + right >>> 1));
			System.out.println("before sorting, src : " + Arrays.toString(src) + ", dest : " + Arrays.toString(dest));
			sort(dest, src, left, mid, level + 1);
			sort(dest, src, mid + 1, right, level + 1);
			System.out.println("after sorting, src : " + Arrays.toString(src) + ", dest : " + Arrays.toString(dest));

			int lPtr, rPtr;

			lPtr = left;
			rPtr = mid + 1;

//			System.out.println("** starting with lPtr : " + lPtr + ", rPtr : " + rPtr);

			if (level % 2 == 0)
			{
				for (int i = left; i < right; i++)
				{
					if (lPtr < mid)
					{
						if (rPtr < right && dest[lPtr] <= dest[rPtr])
							src[i] = dest[lPtr++];
						else
							src[i] = dest[rPtr++];
					}
					else
					{
//					System.out.println("\tlPtr : " + lPtr + ", rPtr : " + rPtr);
						src[i] = dest[rPtr++];
					}
				}
			}
			else
			{
				for (int i = left; i < right; i++)
				{
					if (lPtr < mid)
					{
						if (rPtr < right && src[lPtr] <= src[rPtr])
							dest[i] = src[lPtr++];
						else
							dest[i] = src[rPtr++];
					}
					else
					{
//					System.out.println("\tlPtr : " + lPtr + ", rPtr : " + rPtr);
						dest[i] = src[rPtr++];
					}
				}
			}
		}

		Sorter(Scanner in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

}

/*

8
5 1 3 8 4 7 2 6

*/
