package com.testingUtil;

import com.testingUtil.util.GeneratorUtils;

import java.io.*;

class TestsGenerator
{
	private static class Generator implements Runnable
	{
		int t, n;
		int[] s;
		PrintWriter out;

		void generate() throws IOException
		{
/*			t = 1;
			out.println(t);

			while (t-- > 0)
			{
				n = GeneratorUtils.nextRandomInt(8, 10);
				n <<= 1;
				s = new int[n];

				for (int i = 0; i < n; i++)
					s[i] = GeneratorUtils.nextRandomInt(1, 11);

				out.println(n);

				for (int x : s)
					out.print(x + " ");

				out.println();
			}*/

			int n = GeneratorUtils.nextRandomInt(1, (int) 1e2);
			int upper = GeneratorUtils.nextRandomInt(2, n);
			int lower = GeneratorUtils.nextRandomInt(2, n);
			int[][] mat = new int[2][n];

			out.println(n + " " + upper + " " + lower);
			fill(mat[0], upper);
			fill(mat[1], lower);

			for (int i = 0; i < n; i++) {
				out.print(mat[0][i] + mat[1][i] + " ");
			}
		}

		void fill(int[] arr, int sum) {
			for (int i = 0; i < arr.length; i++) {
				if (sum == arr.length - i) {
					arr[i] = 1;
					sum--;
				} else if (sum > 0) {
					int random = GeneratorUtils.nextRandomInt(0, 1);

					arr[i] = random;
					sum -= random;
				}
			}
		}

		@Override public void run()
		{
			try
			{
				generate();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		Generator(PrintWriter out)
		{
			this.out = out;
		}

	}

	TestsGenerator(OutputStream outputStream)
	{
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Generator(out), "Generator", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			out.flush();
			out.close();
		}
	}

}
