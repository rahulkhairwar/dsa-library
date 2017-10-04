package com.checker;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

class TestsGenerator
{
	public static void main(String[] args)
	{
		try
		{
			PrintWriter out = new PrintWriter(new File(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/checker/input.txt"));
			Generator generator = new Generator(out);

			generator.generate();
			out.flush();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	private static class Generator
	{
		PrintWriter out;

		void generate()
		{
			int n = 10;
			int q = 5;

			out.println(n + " " + q);

			for (int i = 0; i < n; i++)
				out.print((int) (Math.random() * 50 + 1) + " ");

			out.println();

			while (q-- > 0)
			{
				int type = (int) (Math.random() * 4) + 1;
				int x = (int) (Math.random() * n) + 1;
				int y = (int) (Math.random() * n) + 1;
				int val = (int) (Math.random() * 50) + 1;

				if (x > y)
				{
					int temp = x;

					x = y;
					y = temp;
				}

				out.print(type + " " + x + " " + y);

				if (type < 4)
					out.print(" " + val + "\n");
				else
					out.println();
			}
		}

		Generator(PrintWriter out)
		{
			this.out = out;
		}
	}

	public static List<Point> getRandomTreeAsEdges(int v, boolean isZeroBased)
	{
		List<Point> edges = new ArrayList<>();

		for (int i = 1; i < v; i++)
			edges.add(new Point((int) (Math.random() * i), i));

		if (!isZeroBased)
		{
			for (Point pt : edges)
			{
				pt.x++;
				pt.y++;
			}
		}

		return edges;
	}

}
