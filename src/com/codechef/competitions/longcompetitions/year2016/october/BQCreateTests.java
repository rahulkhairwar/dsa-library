package com.codechef.competitions.longcompetitions.year2016.october;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BQCreateTests
{
	public static void main(String[] args)
	{
		try
		{
			PrintWriter out = new PrintWriter(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA "
					+ "Workspace/Competitive Programming/src/com/codechef/competitions/longcompetitions/year2016/october"
					+ "/input.txt"));
			TestCreator creator = new TestCreator(out);

			creator.create();
			out.flush();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	static class TestCreator
	{
		int t, n, q;
		PrintWriter out;

		void create()
		{
			t = (int) (Math.random() * 0) + 1;

			out.println(t);

			while (t-- > 0)
			{
				n = (int) (Math.random() * 10) + 1;
				q = (int) (Math.random() * 5) + 1;

				out.println(n + " " + q);

				for (int i = 0; i < n; i++)
					out.print((int) (Math.random() * 100) + 1 + " ");

				out.println();

				for (int i = 0; i < q; i++)
				{
					int type = (int) (Math.random() * 3) + 1;
					int left = (int) (Math.random() * n) + 1;
					int right = (int) (Math.random() * n) + 1;

					if (left > right)
					{
						int temp = left;

						left = right;
						right = temp;
					}

					if (type == 3)
					{
						out.println(type + " " + left + " " + right);
					}
					else
					{
						int x = (int) (Math.random() * 50) + 1;

						out.println(type + " " + left + " " + right + " " + x);
					}
				}
			}
		}

		public TestCreator(PrintWriter out)
		{
			this.out = out;
		}

	}

}
