package com.checker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TestsGenerator
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
		int t, n;
		int[] s;
		PrintWriter out;

		void generate()
		{
			t = 1;

			out.println(t);

			while (t-- > 0)
			{
				n = nextRandomInt(8, 10);
				n <<= 1;
				s = new int[n];

				for (int i = 0; i < n; i++)
					s[i] = nextRandomInt(1, 11);

				out.println(n);

				for (int x : s)
					out.print(x + " ");

				out.println();
			}
		}


		/**
		 * Returns a random integer in the range [start, limit).
		 *
		 * @param start start is the lower limit for the randomly generated integer.
		 * @param limit limit - 1 is the upper limit for the randomly generated integer.
		 * @return returns a random integer in the range [start, limit).
		 */
		int nextRandomInt(int start, int limit)
		{
			return (int) (Math.random() * (limit - start + 1)) + start;
		}

		Generator(PrintWriter out)
		{
			this.out = out;
		}

	}

}
