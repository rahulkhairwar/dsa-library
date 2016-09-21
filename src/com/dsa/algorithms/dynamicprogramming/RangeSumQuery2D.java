package com.dsa.algorithms.dynamicprogramming;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Sum Area Table(<a href="https://en.wikipedia.org/wiki/Summed_area_table">SAT</a>) method to answer sum queries of
 * any submatrix in a matrix in O(1) after
 */
public class RangeSumQuery2D
{
	static int[] log;
	static int[][] arr, sum;
	static int[][][][] rmq;

	public static void main(String[] args)
	{
		arr = new int[][]{{1, 0, 3, 2, 6}, {10, 100, 12, 3, 0}, {12, 16, 15, 2, 4}, {8, 10, 50, 20, 1}, {1, 2, 3, 4,
				5}};

		buildSumTable(arr.length, arr[0].length);

		System.out.println("The matrix looks like : ");

		for (int i = 0; i < arr.length; i++)
			System.out.println("i : " + i + " => " + Arrays.toString(arr[i]));

		System.out.println("\nThe sum table looks like : ");

		for (int i = 0; i < sum.length; i++)
			System.out.println("i : " + i + " => " + Arrays.toString(sum[i]));

		Scanner in = new Scanner(System.in);

		while (true)
		{
			System.out.println("Enter the top-left & bottom-right points of the submatrix : ");

			int x1, y1, x2, y2;

			x1 = in.nextInt();
			y1 = in.nextInt();
			x2 = in.nextInt();
			y2 = in.nextInt();

			System.out.println(sum(x1, y1, x2, y2));
			System.out.println("Do you want to ask any more queries ? (yes/no) : ");

			if (!in.next().equals("yes"))
				break;
		}
	}

	static void buildSumTable(int rows, int columns)
	{
		sum = new int[rows][columns];

		sum[0][0] = arr[0][0];

		for (int i = 1; i < rows; i++)
			sum[i][0] = sum[i - 1][0] + arr[i][0];

		for (int i = 1; i < columns; i++)
			sum[0][i] = sum[0][i - 1] + arr[0][i];

		for (int i = 1; i < rows; i++)
			for (int j = 1; j < columns; j++)
				sum[i][j] = arr[i][j] + sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1];
	}

	static int sum(int x1, int y1, int x2, int y2)
	{
		return sum[x2][y2] - (x1 > 0 ? sum[x1 - 1][y2] : 0) - (y1 > 0 ? sum[x2][y1 - 1] : 0) + (x1 > 0 && y1 > 0 ?
				sum[x1 - 1][y1 - 1] : 0);
	}

}
