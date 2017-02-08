package com.checker;

import com.checker.util.GeneratorUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

class GenerateTests
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
			int n = (int) (Math.random() * 1e5) + 1;

			out.println(n);

			for (int i = 0; i < n; i++)
				out.print(((int) (Math.random() * 1e5) + 1) + " ");

			out.println();

			int[] par = GeneratorUtils.getRandomTreeAsParentArray(n, false);

			for (int i = 1; i < n; i++)
				out.print(par[i] + " ");

			out.println();

			int q = (int) (Math.random() * 1e5) + 1;

			out.println(q);

			while (q-- > 0)
			{
				int u, v;

				do
				{
					u = (int) (Math.random() * n) + 1;
					v = (int) (Math.random() * n) + 1;
				} while (u == v);

				out.println(u + " " + v);
			}
		}

		Generator(PrintWriter out)
		{
			this.out = out;
		}
	}

}
