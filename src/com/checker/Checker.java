package com.checker;

import com.a2onlinejudge.groupcontests.jan10_2017.Crimewave;
import com.facebookhackercup.hc2017.qualification.TaskB;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Checker
{
	public static void main(String[] args)
	{
		int tests = 100;

		long start = System.currentTimeMillis();
		while (tests-- > 0)
		{

			GenerateTests.main(null);
//			System.out.println("generation in : " + (System.currentTimeMillis() - start));
//			start = System.currentTimeMillis();

//			BruteSolution.main(null);
//			System.out.println("brute in : " + (System.currentTimeMillis() - start));
//			start = System.currentTimeMillis();

			Crimewave.main(null);
			System.out.println("tests : " + tests);
//			System.out.println("sol in : " + (System.currentTimeMillis() - start));

/*			if (!check())
			{
				System.out.println("Caught!!");

				break;
			}*/
		}

		System.out.println("time taken for all tests : " + (System.currentTimeMillis() - start));
	}

	static boolean check()
	{
		Scanner one, two;

		try
		{
			one = new Scanner(new File(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/checker/bruteOutput.txt"));
			two = new Scanner(new File(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/checker/output.txt"));

			while (one.hasNext() || two.hasNext())
			{
				if (!one.hasNext() || !two.hasNext())
					return false;

				String a, b;

				a = one.nextLine();
				b = two.nextLine();

				if (!a.equals(b))
					return false;
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return true;
	}

}
