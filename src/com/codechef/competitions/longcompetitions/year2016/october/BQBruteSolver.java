package com.codechef.competitions.longcompetitions.year2016.october;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BQBruteSolver
{
	public static void main(String[] args)
	{
		try
		{
			Scanner in = new Scanner(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
					+ "Programming/src/com/codechef/competitions/longcompetitions/year2016/october/input.txt"));
			PrintWriter out = new PrintWriter(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA "
					+ "Workspace/Competitive Programming/src/com/codechef/competitions/longcompetitions/year2016"
					+ "/october/bruteOutput.txt"));
			Solver solver = new Solver(in, out);

			solver.solve();
			in.close();
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
		int t, n, q;
		long[] arr;
		Scanner in;
		PrintWriter out;

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				q = in.nextInt();
				arr = new long[n];

				for (int i = 0; i < n; i++)
					arr[i] = in.nextInt();

				int ans = 0;

				for (int i = 0; i < q; i++)
				{
					int type, left, right;

					type = in.nextInt();
					left = in.nextInt() - 1;
					right = in.nextInt() - 1;

					if (type == 1)
					{
						int x = in.nextInt();

						for (int j = left; j <= right; j++)
							arr[j] *= x;
					}
					else if (type == 2)
					{
						int y = in.nextInt();
						int counter = 1;

						for (int j = left; j <= right; j++, counter++)
							arr[j] = counter * y;
					}
					else
					{
						int twos, fives;

						twos = fives = 0;

						for (int j = left; j <= right; j++)
						{
							long temp = arr[j];
//							System.out.println("j : " + j + ", arr[j] : " + arr[j]);

							while (temp % 2 == 0)
							{
								temp /= 2;
								twos++;
							}

							while (temp % 5 == 0)
							{
								temp /= 5;
								fives++;
							}
						}

						ans += Math.min(twos, fives);
						out.println(Math.min(twos, fives));
					}
				}

//				out.println(ans);
			}
		}

		public Solver(Scanner in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

}
