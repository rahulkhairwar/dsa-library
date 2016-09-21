package com.codeforces.icpctraining.year2016.virtualcontests.aug21;

import java.io.*;
import java.util.*;

public class TaskL
{
	public static void main(String[] args) throws FileNotFoundException
	{
		int r, c;
		Scanner in = new Scanner(new File("lucky.in"));
		PrintWriter out = new PrintWriter("lucky.out");

		r = in.nextInt();
		c = in.nextInt();

		int[][] mat = new int[r][c];

		for (int i = 0; i < r; i++)
			for (int j = 0; j < c; j++)
				mat[i][j] = in.nextInt();

		int[][] ans = new int[r][c];

		for (int i = 0; i < r; i++)
		{
			for (int j = 0; j < c; j++)
			{
				// left
				boolean found = false;
				int index = j - 1;

				while (index >= 0)
				{
					if (mat[i][index] >= mat[i][j])
					{
						found = true;

						break;
					}

					index--;
				}

				if (!found)
					ans[i][j]++;

				// right
				found = false;
				index = j + 1;

				while (index < c)
				{
					if (mat[i][index] >= mat[i][j])
					{
						found = true;

						break;
					}

					index++;
				}

				if (!found)
					ans[i][j]++;

				// up
				found = false;
				index = i - 1;

				while (index >= 0)
				{
					if (mat[index][j] >= mat[i][j])
					{
						found = true;

						break;
					}

					index--;
				}

				if (!found)
					ans[i][j]++;

				// down
				found = false;
				index = i + 1;

				while (index < r)
				{
					if (mat[index][j] >= mat[i][j])
					{
						found = true;

						break;
					}

					index++;
				}

				if (!found)
					ans[i][j]++;
			}
		}

		long answer = 0;

		for (int i = 0; i < r; i++)
			for (int j = 0; j < c; j++)
				answer += ans[i][j];

		out.println(answer);
		in.close();
		out.flush();
		out.close();
	}

}