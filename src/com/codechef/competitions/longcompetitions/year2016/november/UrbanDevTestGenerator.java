package com.codechef.competitions.longcompetitions.year2016.november;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

class UrbanDevTestGenerator
{
	public static void main(String[] args)
	{
		PrintWriter out = new PrintWriter(System.out);
		Generator generator = new Generator(out);

		generator.generate();
		out.flush();
		out.close();
/*		try
		{
			PrintWriter out = new PrintWriter(new File(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/codechef/competitions/longcompetitions/year2016/november"
							+ "/UrbanDevInput.txt"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}*/
	}

	static private class Generator
	{
		PrintWriter out;

		void generate()
		{
			int n = (int) (Math.random() * 1e5) + 1;

			out.println(n);

			if (n == 1e5)
				n--;

			int horizontal, vertical;

			horizontal = (int) (Math.random() * n) + 1;

			if (horizontal > n)
				horizontal = n;

			vertical = n - horizontal;

			Set<Integer> used = new HashSet<>();
			int limit = (int) 1e5;
			int remaining = vertical;

			while (remaining > 0)
			{
				int x = (int) (Math.random() * limit) + 1;

				while (used.contains(x))
					x = (int) (Math.random() * limit) + 1;

				int lines = (int) (Math.random() * remaining) + 1;

				Set<Integer> yUsed = new HashSet<>();

				while (yUsed.size() < (lines << 1))
					yUsed.add((int) (Math.random() * limit) + 1);

				remaining -= lines;

				int len = yUsed.size();
				Integer[] allY = new Integer[len];
				int counter = 0;

				for (Integer y : yUsed)
					allY[counter++] = y;

				Arrays.sort(allY);

				for (int i = 0; i < len; i += 2)
					out.println(x + " " + allY[i] + " " + x + " " + allY[i + 1]);
			}

			used = new HashSet<>();
			remaining = horizontal;

			while (remaining > 0)
			{
				int y = (int) (Math.random() * limit) + 1;

				while (used.contains(y))
					y = (int) (Math.random() * limit) + 1;

				int lines = (int) (Math.random() * remaining) + 1;

				Set<Integer> xUsed = new HashSet<>();

				while (xUsed.size() < (lines << 1))
					xUsed.add((int) (Math.random() * limit) + 1);

				remaining -= lines;

				int len = xUsed.size();
				Integer[] allX = new Integer[len];
				int counter = 0;

				for (Integer x : xUsed)
					allX[counter++] = x;

				Arrays.sort(allX);

				for (int i = 0; i < len; i += 2)
					out.println(allX[i] + " " + y + " " + allX[i + 1] + " " + y);
			}
		}

		public Generator(PrintWriter out)
		{
			this.out = out;
		}

	}

}
