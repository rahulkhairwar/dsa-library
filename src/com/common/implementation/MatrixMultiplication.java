package com.common.implementation;

import java.util.Scanner;

/**
 * Created by rahulkhairwar on 03/02/16.
 */
public class MatrixMultiplication
{
	static Scanner in;

	public static void main(String[] args)
	{
		in = new Scanner(System.in);

		int[][] a, b;
		int aRows, aColumns, bRows, bColumns;

		System.out.println("Enter the number of rows and columns in matrix A : ");
		aRows = in.nextInt();
		aColumns = in.nextInt();

		a = new int[aRows][aColumns];

		System.out.println("Enter the elements of matrix A : ");

		for (int i = 0; i < aRows; i++)
			for (int j = 0; j < aColumns; j++)
				a[i][j] = in.nextInt();

		System.out.println("Enter the number of rows and columns in matrix B : ");

		bRows = in.nextInt();
		bColumns = in.nextInt();

		if (aColumns != bRows)
			System.out.println("Matrices with entered orders cannot be multiplied with each other.");
		else
		{
			int[][] product = new int[aRows][bColumns];

			
		}
	}

}
