package com.codechef.competitions.longcompetitions.year2016.october;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Compare
{
	public static void main(String[] args)
	{
		int tests = 0;

		while (tests < 1e3)
		{
			BQCreateTests.main(null);
			BQBruteSolver.main(null);
			BigQueries.main(null);

			try
			{
				if (!check())
				{
					System.out.println("Caught!!");

					break;
				}
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}

			tests++;
		}
	}

	public static void main1(String[] args)
	{
		int tests = 0;

		while (tests < 1e3)
		{
			CreateTests.main(null);
			BruteSolver.main(null);
			FenwickIterations.main(null);

			try
			{
				if (!check())
				{
					System.out.println("Caught!!");

					break;
				}
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}

			tests++;
		}
	}

	static boolean check() throws FileNotFoundException
	{
		Scanner one, two;

		one = new Scanner(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
				+ "Programming/src/com/codechef/competitions/longcompetitions/year2016/october/bruteOutput.txt"));
		two = new Scanner(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
				+ "Programming/src/com/codechef/competitions/longcompetitions/year2016/october/output.txt"));

		while (one.hasNext())
		{
			String a, b;

			a = one.nextLine();
			b = two.nextLine();

			if (!a.equals(b))
			{
				System.out.println("caught for a : " + a + ", b : " + b);
				return false;
			}
		}

		return true;
	}

}
