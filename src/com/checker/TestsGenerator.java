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
		int n, m;
		char[] s;
		PrintWriter out;

		void generate()
		{
//			n = (int) (Math.random() * 2e5) + 1;
//			m = (int) (Math.random() * 2e5) + 1;
			n = (int) 2e5;
			m = (int) 2e5;
//			n = 1000;
//			m = 1000;
			s = new char[n];
			out.println(n + " " + m);

			for (int i = 0; i < n; i++)
			{
				s[i] = (char) ((int) (Math.random() * 26) + 'a');
				out.print(s[i]);
			}

			out.println();
			out.flush();

			for (int i = 0; i < m; i++)
			{
				int l = (int) (Math.random() * n);
				int r = (int) (Math.random() * n);
				char ch = (char) ((int) (Math.random() * 26) + 'a');

				if (l > r)
				{
					int temp = l;

					l = r;
					r = temp;
				}

				out.println(l + 1 + " " + (r + 1) + " " + ch);
				delete(l, r, ch);
			}
		}

		void delete(int l, int r, char ch)
		{
			char[] temp = new char[n];
			int ctr = 0;
			int deleted = 0;

			for (int i = l; i <= r; i++)
			{
				if (s[i] == ch)
					deleted++;
				else
					temp[ctr++] = s[i];
			}

			n -= deleted;
			s = temp;
		}

		Generator(PrintWriter out)
		{
			this.out = out;
		}

	}

}
