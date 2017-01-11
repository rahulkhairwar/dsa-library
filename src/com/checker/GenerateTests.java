package com.checker;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class GenerateTests
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
			int p = (int) (Math.random() * 100) + 1;

			out.println(p);

			while (p-- > 0)
			{
				int r, c, tot, b;

				r = (int) (Math.random() * 50) + 1;
				c = (int) (Math.random() * 50) + 1;
				tot = r * c;
				b = (int) (Math.random() * tot / 2) + 1;

				out.println(r + " " + c + " " + b);

				Set<Point> set = new HashSet<>();

				for (int i = 0; i < b; i++)
				{
					int u, v;

					u = (int) (Math.random() * r) + 1;
					v = (int) (Math.random() * c) + 1;

					if (!set.contains(new Point(u, v)))
					{
						set.add(new Point(u, v));
						out.println(u + " " + v);
					}
					else
						i--;
				}
			}
		}

		Generator(PrintWriter out)
		{
			this.out = out;
		}
	}

}
