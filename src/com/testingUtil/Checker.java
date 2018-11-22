package com.testingUtil;

import com.codechef.competitions.longcompetitions.year2018.july.GEARS;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

@SuppressWarnings("ALL") public class Checker
{
	private static final String propertyFilePath = "/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
			+ "Programming/src/com/dsa/testingUtil/properties.properties";
	private static String inputFilePath;
	private static String bruteOutputFilePath;
	private static String outputFilePath;

	public static void main(String[] args) throws IOException
	{
		int tests = 1;
		long start = System.currentTimeMillis();

//		while (true)
		while (tests-- > 0)
		{
			FileReader fileReader = new FileReader(propertyFilePath);
			Properties properties = new Properties();

			properties.load(fileReader);
			inputFilePath = properties.getProperty("inputPath");
			bruteOutputFilePath = properties.getProperty("bruteOutputPath");
			outputFilePath = properties.getProperty("outputPath");

			InputStream inputStream = new FileInputStream(new File(inputFilePath));
			OutputStream outputStream = new FileOutputStream(new File(outputFilePath));
			OutputStream testsOutputStream = new FileOutputStream(new File(inputFilePath));
			OutputStream bruteOutputStream = new FileOutputStream(new File(bruteOutputFilePath));

			/*********************** TESTS GENERATION ***********************/
//			start = System.currentTimeMillis();
			new TestsGenerator(testsOutputStream);
//			System.out.printf("Tests generated in : %dms\n", (System.currentTimeMillis() - start));

			/*********************** BRUTE SOLUTION ***********************/
//			start = System.currentTimeMillis();
			new BruteSolution(inputStream, bruteOutputStream);
//			System.out.printf("Brute done in : %dms\n", (System.currentTimeMillis() - start));

			/*********************** OPTIMISED SOLUTION ***********************/
//			start = System.currentTimeMillis();
			new GEARS(inputStream, outputStream);
//			System.out.printf("Solution done in : %dms\n", (System.currentTimeMillis() - start));

			if (!check())
			{
				System.out.println("Caught!!");

				break;
			}

			fileReader.close();
			inputStream.close();
			outputStream.flush();
			outputStream.close();
			testsOutputStream.flush();
			testsOutputStream.close();
			bruteOutputStream.flush();
			bruteOutputStream.close();
		}

		System.out.printf("Time taken for all tests : %dms\n", (System.currentTimeMillis() - start));
	}

	private static boolean check()
	{
		Scanner one, two;

		try
		{
			one = new Scanner(new File(bruteOutputFilePath));
			two = new Scanner(new File(outputFilePath));

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
