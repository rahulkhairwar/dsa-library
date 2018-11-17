package com.dsa.algorithms.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class KMP
{
	public static void main(String[] args)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Solver solver = new Solver(in);

		try
		{
			solver.solve();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	static class Solver
	{
		BufferedReader in;

		void solve() throws IOException
		{
			char[] text, pattern;

			pattern = in.readLine().toCharArray();
			text = in.readLine().toCharArray();

			int n, m;

			n = text.length;
			m = pattern.length;

			int[] pre = StringFunctions.prefixFunction(pattern);
			List<Integer> pos = new ArrayList<>();
			int i = 0, j = 0;

			while (i < n)
			{
				if (text[i] == pattern[j])
				{
					if (j == m - 1)
					{
						pos.add(i - m + 1);
						j = pre[j] - 1;
					}

					i++;
					j++;
				}
				else
				{
					if (j > 0)
						j = pre[j - 1];
					else
						i++;
				}
			}

			System.out.println("The pattern is present in the text at positions : " + pos);
		}

		public Solver(BufferedReader in)
		{
			this.in = in;
		}
	}

}
