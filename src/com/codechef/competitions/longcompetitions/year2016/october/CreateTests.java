package com.codechef.competitions.longcompetitions.year2016.october;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CreateTests
{
	public static void main(String[] args)
	{
		try
		{
			PrintWriter out = new PrintWriter(new File(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/codechef/competitions/longcompetitions/year2016/october/input"
							+ ".txt"));
			Solver solver = new Solver(out);

			solver.createTests();
			out.flush();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	static class Solver
	{
		int t, n;
		String a, b, c;
		PrintWriter out;

		void createTests()
		{
			t = (int) (Math.random() * 10) + 1;

			out.println(t);

			while (t-- > 0)
			{
				int lenA, lenB, lenC;

				lenA = (int) (Math.random() * 10) + 1;
				lenB = (int) (Math.random() * 10) + 1;
				lenC = (int) (Math.random() * 10) + 1;

				a = createRandomString(lenA);
				b = createRandomString(lenB);
				c = createRandomString(lenC);

				n = (int) (Math.random() * 10) + 1;

				out.println(a + " " + b + " " + c + " " + n);
			}
		}

		String createRandomString(int len)
		{
			StringBuilder sb = new StringBuilder("");

			for (int i = 0; i < len; i++)
				sb.append((int) (Math.random() * 2));

			return sb.toString();
		}

		public Solver(PrintWriter out)
		{
			this.out = out;
		}

	}

}
