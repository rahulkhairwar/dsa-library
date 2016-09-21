package com.codeforces.icpctraining.year2016.virtualcontests.sept17;

import java.io.*;
import java.util.*;

public class TaskA
{

	public static void main(String[] args) throws FileNotFoundException
	{
		long n, q;
		Scanner in = new Scanner(new File("adjustment.in"));
		PrintWriter out = new PrintWriter("adjustment.out");

//        Scanner in = new Scanner(System.in);
//        PrintWriter out = new PrintWriter(System.out);

		n = in.nextInt();
		q = in.nextInt();

		long rUsed, rUsedCount, cUsed, cUsedCount;
		boolean[] row, col;

		row = new boolean[(int) (1 + n)];
		col = new boolean[(int) (1 + n)];

		rUsed = cUsed = rUsedCount = cUsedCount = 0;

		in.nextLine();

		for (int i = 0; i < q; i++)
		{
			String[] tokens = in.nextLine().split(" ");

			if (tokens[0].charAt(0) == 'R')
			{
				int r = Integer.parseInt(tokens[1]);

				if (row[r])
				{
					out.println(0);

					continue;
				}

				long sum = r * n + (n * (n + 1)) / 2;

				sum -= (cUsed + cUsedCount * r);

				if (!row[r])
				{
					row[r] = true;
					rUsed += r;
					rUsedCount++;
				}

				out.println(sum);
			}
			else
			{
				int c = Integer.parseInt(tokens[1]);

				if (col[c])
				{
					out.println(0);

					continue;
				}

				long sum = c * n + (n * (n + 1)) / 2;

				sum -= (rUsed + rUsedCount * c);

				if (!col[c])
				{
					col[c] = true;
					cUsed += c;
					cUsedCount++;
				}

				out.println(sum);
			}
		}

		out.flush();
		out.close();
		in.close();
	}

}