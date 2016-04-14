package com.test;

import java.io.*;
import java.util.Scanner;

/**
 * Created by rahulkhairwar on 13/02/16.
 */
public class Test
{
	public static void main(String[] args) throws IOException
	{
		Scanner reader = new Scanner(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive Programming/src/com/test/inputA.txt"));

		long count = 0;

		int t = reader.nextInt();
		int totalIntegers = 1;

		for (int i = 0; i < t; i++)
		{
			int n, m;

			n = reader.nextInt();
			m = reader.nextInt();
			totalIntegers += 2;

			System.out.println("t : " + (i + 1) + ", n : " + n + ", m : " + m);

			int from, to, weight;

			from = to = weight = 0;

			for (int j = 0; j < m; j++)
			{
				reader.nextInt();
				reader.nextInt();
				reader.nextInt();

				from++;
				to++;
				weight++;

				totalIntegers += 3;
			}

			int targetCity = reader.nextInt();
			System.out.println("from count : " + from + ", to count : " + to + ", weight count : " + weight + ", " +
					"target city : " + targetCity);
		}

		int extraCount = 0;

		while (reader.hasNext())
		{
			reader.nextInt();
			extraCount++;
		}

		System.out.println("Total integers : " + (totalIntegers + extraCount));
		System.out.println("Extra integers : " + extraCount);
	}

}
